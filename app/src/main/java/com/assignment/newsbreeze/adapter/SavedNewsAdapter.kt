package com.assignment.newsbreeze.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.assignment.newsbreeze.data.models.Article
import com.assignment.newsbreeze.databinding.HeadlineListItemLayoutBinding
import com.assignment.newsbreeze.databinding.SavedHeadlineListItemLayoutBinding
import com.assignment.newsbreeze.utils.ListItemClickListener
import kotlin.properties.Delegates

class SavedNewsAdapter(
    private val itemClickListener: ListItemClickListener<Article>
) :
    RecyclerView.Adapter<SavedNewsAdapter.NewsHeadlineViewHolder>() {

    var itemList: List<Article> by Delegates.observable(emptyList()) { _, _, _ ->
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsHeadlineViewHolder =
        NewsHeadlineViewHolder(SavedHeadlineListItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: NewsHeadlineViewHolder, position: Int) {
        val article = itemList[position]
        holder.bind(article)
        holder.itemView.setOnClickListener { itemClickListener(article) }
    }

    override fun getItemCount() = itemList.size

    /**
     * ViewHolder class for [SavedNewsAdapter]
     */
    class NewsHeadlineViewHolder(private val binding: SavedHeadlineListItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(article: Article) {
            binding.article = article
        }
    }
}