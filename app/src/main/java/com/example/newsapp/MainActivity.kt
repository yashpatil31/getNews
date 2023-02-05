package com.example.newsapp

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest


class MainActivity : AppCompatActivity(), NewsItemClicked {
    private lateinit var mAdapter: NewsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.news_recyclerview)
        recyclerView.layoutManager=LinearLayoutManager(this)

        val items = fetchdata()
        mAdapter= NewsAdapter(this)
        recyclerView.adapter = mAdapter
    }

    private fun fetchdata(){
        val url = "https://gnews.io/api/v4/top-headlines?token=041285efc9ce96602f0f5fc87b98e607&topic=breaking-news&country=in&lang=en"
        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
            {
                val newsJsonArray = it.getJSONArray("articles")
                val newsArray = ArrayList<News>()

                for(i in 0 until newsJsonArray.length())
                {
                    val newsJsonobject = newsJsonArray.getJSONObject(i)
                    val news = News(
                        newsJsonobject.getString("title"),
                        newsJsonobject.getString("url"),
                        newsJsonobject.getString("image")
                    )
                    newsArray.add(news)
                }

                mAdapter.updateNews(newsArray)

            },
            { error ->
                // TODO: Handle error
            }
        )

    // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }

    override fun onItemClicked(item: News) {
        val builder = CustomTabsIntent.Builder()
        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(this, Uri.parse(item.url))
    }
}