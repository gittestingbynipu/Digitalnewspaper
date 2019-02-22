package com.techhouseandroid.digitalnewspaper

import android.app.AlertDialog
import android.media.MediaRouter
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.google.gson.Gson
import com.techhouseandroid.digitalnewspaper.Adapter.ViewHolder.ListSourceAdapter
import com.techhouseandroid.digitalnewspaper.Common.Common
import com.techhouseandroid.digitalnewspaper.Interface.Newservice
import com.techhouseandroid.digitalnewspaper.Model.Website
import dmax.dialog.SpotsDialog
import io.paperdb.Paper
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class MainActivity : AppCompatActivity() {
    lateinit var layoutManager: LinearLayoutManager
    lateinit var mService:Newservice
    lateinit var adapter: ListSourceAdapter

    lateinit var dialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

          Paper.init(this)

        mService=Common.newsService

        swipe_refresh.setOnRefreshListener {

            loadWebsiteSource(true)
        }

        recyclerView.setHasFixedSize(true)
        layoutManager=LinearLayoutManager(this)
        recyclerView.layoutManager=layoutManager
        dialog=SpotsDialog(this)

        loadWebsiteSource(false)

    }

    private fun loadWebsiteSource(isRefresh: Boolean) {

        if (!isRefresh)
        {
            val cache=Paper.book().read<String>("cache")
            if (cache !=null && !cache.isBlank() && cache!="null"){

                val website=Gson().fromJson<Website>(cache, Website::class.java)
                adapter= ListSourceAdapter(baseContext,website)
                adapter.notifyDataSetChanged()
                recyclerView.adapter=adapter

            }

            else{
                dialog.show()
                mService.sources.enqueue(object:retrofit2.Callback<Website>{
                    override fun onFailure(call: Call<Website>, t: Throwable) {
                        Toast.makeText(baseContext,"Failed",Toast.LENGTH_SHORT).show()
                    }

                    override fun onResponse(call: Call<Website>, response: Response<Website>) {

                        adapter= ListSourceAdapter(baseContext,response!!.body()!!)
                        adapter.notifyDataSetChanged()
                        recyclerView.adapter=adapter
                        Paper.book().write("cache",Gson().toJson(response!!.body()!!))
                        dialog.dismiss()
                    }
                })
            }
        }
        else{
            swipe_refresh.isRefreshing=true


            mService.sources.enqueue(object:retrofit2.Callback<Website>{
                override fun onFailure(call: Call<Website>, t: Throwable) {
                    Toast.makeText(baseContext,"Failed",Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(call: Call<Website>, response: Response<Website>) {

                    adapter= ListSourceAdapter(baseContext,response!!.body()!!)
                    adapter.notifyDataSetChanged()
                    recyclerView.adapter=adapter
                    Paper.book().write("cache",Gson().toJson(response!!.body()!!))
                    swipe_refresh.isRefreshing=false
                }
            })

        }

    }
}
