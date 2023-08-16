package com.tufelmalik.dailykill.ui.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tufelmalik.dailykill.R
import com.tufelmalik.dailykill.data.model.Article
import com.tufelmalik.dailykill.ui.activity.NewsActivity
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.Date

class NewsAdapter(private val context: Context, private var newsList: List<Article>)
    :RecyclerView.Adapter<NewsAdapter.NewsViewHolder>(){

    fun updateData(list: List<Article>) {
        newsList = list
        notifyDataSetChanged()
    }

    class NewsViewHolder(binding : View) : RecyclerView.ViewHolder(binding){
        var title : TextView = binding.findViewById(R.id.tvTitle)
        var image : ImageView = binding.findViewById(R.id.ivArticleImage)
        var description : TextView = binding.findViewById(R.id.tvDescription)
        var published : TextView = binding.findViewById(R.id.tvPublishedAt)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        var layout = LayoutInflater.from(context)
            .inflate(R.layout.news_layout,parent,false)
        return NewsViewHolder(layout)
    }

    override fun onBindViewHolder(binding : NewsViewHolder, position: Int) {
        val data = newsList[position]
        binding.title.text = data.title
        Glide.with(context).load(data.urlToImage).into(binding.image)
        binding.description.text = data.description
        settingPublishedTime(binding.published,data.publishedAt)

        try {
            binding.itemView.setOnClickListener {
                val intent = Intent(context, NewsActivity::class.java)
                intent.putExtra("key", data.urlToImage.trim())
                context.startActivity(intent)
            }
        }catch (e: Exception){
            Toast.makeText(context,e.message.toString(),Toast.LENGTH_SHORT).show()
        }
    }

    private fun settingPublishedTime(published: TextView, publishedAt: String) {
        val timestamp = publishedAt
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
        val outputFormat = SimpleDateFormat("yyyy-MM-dd")
        val date: Date = inputFormat.parse(timestamp)
        val formattedDate: String = outputFormat.format(date)
        published.text = formattedDate
    }

    override fun getItemCount() = newsList.size
}