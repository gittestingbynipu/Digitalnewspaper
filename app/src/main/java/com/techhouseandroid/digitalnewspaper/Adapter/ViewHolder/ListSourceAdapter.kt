package com.techhouseandroid.digitalnewspaper.Adapter.ViewHolder


import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.techhouseandroid.digitalnewspaper.Interface.ItemClickListener
import com.techhouseandroid.digitalnewspaper.ListNews
import com.techhouseandroid.digitalnewspaper.Model.Website


class ListSourceAdapter(private val context:Context,private val website: Website):RecyclerView.Adapter<ListSourceView>() {

    override fun onCreateViewHolder(parent:ViewGroup, viewType: Int): ListSourceView {


            val inflater=LayoutInflater.from(parent.context)
            val itemView=inflater.inflate(com.techhouseandroid.digitalnewspaper.R.layout.source_layout,parent,false)
            return ListSourceView(itemView)

    }

    override fun getItemCount(): Int {

        return website.sources!!.size

    }

    override fun onBindViewHolder(holder: ListSourceView, position: Int) {
        holder.source_title.text=website.sources!![position].name


        holder.setItemClickListener(object :ItemClickListener{
            override fun onClick(view: View, position: Int) {
                val intent = Intent(context, ListNews::class.java)
                intent.putExtra("source", website.sources!![position].id)
                context.startActivity(intent)
            }
        })

    }


}