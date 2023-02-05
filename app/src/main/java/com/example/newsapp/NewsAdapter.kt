package com.example.newsapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class NewsAdapter(private val listener: NewsItemClicked): RecyclerView.Adapter<NewsAppViewHolder>() {
    val items:ArrayList<News> = ArrayList<News>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsAppViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.news_item,parent,false)
        val newsviewholder = NewsAppViewHolder(view)

        view.setOnClickListener {
            listener.onItemClicked(items[newsviewholder.absoluteAdapterPosition])
        }
        return newsviewholder
    }

    override fun onBindViewHolder(holder: NewsAppViewHolder, position: Int) {
        val currentItem = items[position]
        holder.titleView.text = currentItem.title
        Glide.with(holder.itemView.context).load(currentItem.imageUrl).into(holder.img)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun updateNews(newsArray:ArrayList<News>)
    {
        items.clear()
        items.addAll(newsArray)

        notifyDataSetChanged()
    }

}

class NewsAppViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val titleView: TextView = itemView.findViewById(R.id.txt_title)
    val img : ImageView = itemView.findViewById(R.id.image)
}

interface NewsItemClicked
{
    fun onItemClicked(item:News)
}
