package com.techhouseandroid.digitalnewspaper

import android.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.squareup.picasso.Picasso
import com.techhouseandroid.digitalnewspaper.Adapter.ViewHolder.ListNewsAdapter
import com.techhouseandroid.digitalnewspaper.Common.Common
import com.techhouseandroid.digitalnewspaper.Interface.Newservice
import com.techhouseandroid.digitalnewspaper.Model.News
import dmax.dialog.SpotsDialog
import kotlinx.android.synthetic.main.activity_list_news.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ListNews : AppCompatActivity() {


    var source=""
    var websiteHotUrl:String?=""

    lateinit var dialog: AlertDialog

    lateinit var mService:Newservice
    lateinit var adapter: ListNewsAdapter



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_news)


        mService=Common.newsService

        dialog=SpotsDialog(this)


        swipeRefresh.setOnRefreshListener { loadNews(source,true) }


        diagonallayout.setOnClickListener {

        }

        lastNews.setHasFixedSize(true)
        lastNews.layoutManager = LinearLayoutManager(this)


        if (intent!=null){

            source=intent.getStringExtra("source")

            if (!source.isEmpty())

                loadNews(source,false)

        }
    }

    private fun loadNews(source: String?, isRefreshed: Boolean) {

        if (isRefreshed){

            dialog.show()
            mService.getNewsFromSource(Common.getNewsAPI(source!!))
                .enqueue(object : Callback<News> {
                    override fun onFailure(call: Call<News>, t: Throwable) {


                    }

                    override fun onResponse(call: Call<News>, response: Response<News>) {

                        dialog.dismiss()

                        Picasso.with(baseContext)
                            .load(response.body()!!.articles!![0].urlToImage)
                            .into(top_image)


                        top_title.text=response.body()!!.articles!![0].title
                        top_aurthor.text=response.body()!!.articles!![0].author

                        websiteHotUrl=response.body()!!.articles!![0].url

                        val removeFristNews=response.body()!!.articles

                        removeFristNews!!.removeAt(0)

                        adapter=ListNewsAdapter(removeFristNews!!,baseContext)

                        adapter.notifyDataSetChanged()
                        lastNews.adapter=adapter







                    }


                })


        }

        else{
            swipeRefresh.isRefreshing=true

            mService.getNewsFromSource(Common.getNewsAPI(source!!))
                .enqueue(object : Callback<News> {
                    override fun onFailure(call: Call<News>, t: Throwable) {


                    }

                    override fun onResponse(call: Call<News>, response: Response<News>) {

                        swipeRefresh.isRefreshing=false

                        Picasso.with(baseContext)
                            .load(response.body()!!.articles!![0].urlToImage)
                            .into(top_image)


                        top_title.text=response.body()!!.articles!![0].title
                        top_aurthor.text=response.body()!!.articles!![0].author

                        websiteHotUrl=response.body()!!.articles!![0].url

                        val removeFristNews=response.body()!!.articles

                        removeFristNews!!.removeAt(0)

                        adapter=ListNewsAdapter(removeFristNews!!,baseContext)

                        adapter.notifyDataSetChanged()
                        lastNews.adapter=adapter





                    }


                })



        }



    }
}
