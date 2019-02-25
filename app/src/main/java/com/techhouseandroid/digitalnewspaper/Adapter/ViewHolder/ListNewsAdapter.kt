package com.techhouseandroid.digitalnewspaper.Adapter.ViewHolder

import android.content.Context
import android.content.Intent
import android.provider.ContactsContract
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import com.techhouseandroid.digitalnewspaper.Common.ISO8601Parser
import com.techhouseandroid.digitalnewspaper.DetailsNews
import com.techhouseandroid.digitalnewspaper.Interface.ItemClickListener

import com.techhouseandroid.digitalnewspaper.Model.Article
import java.text.ParseException
import java.util.*


class ListNewsAdapter (val articleList:MutableList<Article>,private val context: Context): RecyclerView.Adapter<ListNewsViewHolder>() {


    override fun onCreateViewHolder(parent:ViewGroup, viewType: Int): ListNewsViewHolder {


        val inflater= LayoutInflater.from(parent.context)
        val itemView=inflater.inflate(com.techhouseandroid.digitalnewspaper.R.layout.news_layout,parent,false)
        return ListNewsViewHolder(itemView)

    }


    override fun getItemCount(): Int {
        return articleList.size
    }

    override fun onBindViewHolder(holder: ListNewsViewHolder, position: Int) {

        Picasso.with(context)
            .load(articleList[position].urlToImage)
            .into(holder.article_image)
        if (articleList[position].title!!.length>65){

            holder.article_title.text=articleList[position].title!!.substring(0,65)+"..."

        }
     else
        {
            holder.article_title.text=articleList[position].title!!

        }

        if (articleList[position].publishedAt!=null){
            var  date:Date?=null

            try {
                date=ISO8601Parser.parse(articleList[position].publishedAt!!)

            }

            catch(ex:ParseException ){
                ex.printStackTrace()
            }
            holder.article_time.setReferenceTime(date!!.time)

        }


        holder.setItemClickListener(object:ItemClickListener{
            override fun onClick(view: View, position: Int) {

                var detail=Intent(context,DetailsNews::class.java)
                detail.putExtra("webURL",articleList[position].url)
                context.startActivity(detail)
            }


        })

    }



}