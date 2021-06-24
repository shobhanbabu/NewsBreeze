package com.assignment.newsbreeze.utils

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.databinding.BindingAdapter
import com.assignment.newsbreeze.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import retrofit2.Response
import java.io.IOException

/**
 * Readable naming convention for Network call lambda
 */
typealias NetworkAPIInvoke<T> = suspend () -> Response<T>

/**
 * typealias for lambda passed when a photo is tapped on in Popular Photos Fragment
 */
typealias ListItemClickListener<T> = (T) -> Unit

/**
 * Sealed class type-restricts the result of IO calls to success and failure. The type
 * <T> represents the model class expected from the API call in case of a success
 * In case of success, the result will be wrapped around the OnSuccessResponse class
 * In case of error, the throwable causing the error will be wrapped around OnErrorResponse class
 */
sealed class IOTaskResult<out DTO : Any> {
    data class OnSuccess<out DTO : Any>(val data: DTO) : IOTaskResult<DTO>()
    data class OnFailed(val throwable: Throwable) : IOTaskResult<Nothing>()
}

/**
 * Utility function that works to perform a Retrofit API call and return either a success model
 * instance or an error message wrapped in an [Exception] class
 * @param messageInCaseOfError Custom error message to wrap around [IOTaskResult.OnFailed]
 * with a default value provided for flexibility
 * @param networkApiCall lambda representing a suspend function for the Retrofit API call
 * @return [IOTaskResult.OnSuccess] object of type [T], where [T] is the success object wrapped around
 * [IOTaskResult.OnSuccess] if network call is executed successfully, or [IOTaskResult.OnFailed]
 * object wrapping an [Exception] class stating the error
 */
@ExperimentalCoroutinesApi
suspend fun <T : Any> performSafeNetworkApiCall(
    messageInCaseOfError: String = "Network error",
    allowRetries: Boolean = true,
    numberOfRetries: Int = 2,
    networkApiCall: NetworkAPIInvoke<T>
): Flow<IOTaskResult<T>> {
    var delayDuration = 1000L
    val delayFactor = 2
    return flow {
        val response = networkApiCall()
        if (response.isSuccessful) {
            response.body()?.let {
                emit(IOTaskResult.OnSuccess(it))
            }
                ?: emit(IOTaskResult.OnFailed(IOException("API call successful but empty response body")))
            return@flow
        }
        emit(
            IOTaskResult.OnFailed(
                IOException(
                    "API call failed with error - ${
                        response.errorBody()
                            ?.string() ?: messageInCaseOfError
                    }"
                )
            )
        )
        return@flow
    }.catch { e ->
        emit(IOTaskResult.OnFailed(IOException("Exception during network API call: ${e.message}")))
        return@catch
    }.retryWhen { cause, attempt ->
        if (!allowRetries || attempt > numberOfRetries || cause !is IOException) return@retryWhen false
        delay(delayDuration)
        delayDuration *= delayFactor
        return@retryWhen true
    }.flowOn(Dispatchers.IO)
}


/**
 * [ImageView] extension function adds the capability to loading image by directly specifying
 * the url
 * @param url Image URL
 */
fun ImageView.loadUrl(
    @NonNull url: String,
    placeholder: Drawable = this.context.getDrawable(R.drawable.image_place_holder)!!,
    error: Drawable = this.context.getDrawable(R.drawable.broken_image_place_holder)!!
) {
    Glide.with(context)
        .load(url)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .placeholder(placeholder)
        .error(error)
        .into(this)
}

/**
 * Alternate implementation to the above loadUrl method using data binding instead of extn functions
 * @param view [ImageView] to load the image via url
 * @param url URL of the image
 */
@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, url: String) {
    view.loadUrl(url)
}

/**
 * Lets the UI act on a controlled bound of states that can be defined here
 */
sealed class ViewState<out T : Any> {
    /**
     * Represents UI state where the UI should be showing a loading UX to the user
     * @param isLoading will be true when the loading UX needs to display, false when not
     */
    data class Loading(val isLoading: Boolean) : ViewState<Nothing>()

    /**
     * Represents the UI state where the operation requested by the UI has been completed successfully
     * and the output of type [T] as asked by the UI has been provided to it
     * @param output result object of [T] type representing the fruit of the successful operation
     */
    data class RenderSuccess<out T : Any>(val output: T) : ViewState<T>()

    /**
     * Represents the UI state where the operation requested by the UI has failed to complete
     * either due to a IO issue or a service exception and the same is conveyed back to the UI
     * to be shown the user
     * @param throwable [Throwable] instance containing the root cause of the failure in a [String]
     */
    data class RenderFailure(val throwable: Throwable) : ViewState<Nothing>()
}

/**
 * Extension function on a fragment to show a toast message
 */
fun Context.showToast(@NonNull message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

/**
 * Util method that takes a suspend function returning a [Flow] of [IOTaskResult] as input param and returns a
 * [Flow] of [ViewState], which emits [ViewState.Loading] with true prior to performing the IO Task. If the
 * IO operation results a [IOTaskResult.OnSuccess], the result is mapped to a [ViewState.RenderSuccess] instance and emitted,
 * else a [IOTaskResult.OnFailed] is mapped to a [ViewState.RenderFailure] instance and emitted.
 * The flowable is then completed by emitting a [ViewState.Loading] with false
 */
@ExperimentalCoroutinesApi
suspend fun <T : Any> getViewStateFlowForNetworkCall(ioOperation: suspend () -> Flow<IOTaskResult<T>>) =
    flow {
        emit(ViewState.Loading(true))
        ioOperation().map {
            when (it) {
                is IOTaskResult.OnSuccess -> ViewState.RenderSuccess(it.data)
                is IOTaskResult.OnFailed -> ViewState.RenderFailure(it.throwable)
            }
        }.collect {
            emit(ViewState.Loading(false))
            emit(it)
        }
    }.flowOn(Dispatchers.IO)