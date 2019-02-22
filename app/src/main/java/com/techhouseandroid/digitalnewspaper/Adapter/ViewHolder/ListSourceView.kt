package com.techhouseandroid.digitalnewspaper.Adapter.ViewHolder


import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.synthetic.main.source_layout.view.*
import com.techhouseandroid.digitalnewspaper.Interface.ItemClickListener

class ListSourceView(itemView:View):RecyclerView.ViewHolder(itemView),View.OnClickListener{


    private lateinit var itemClickListener:ItemClickListener

    var source_title=itemView.source_name


    init {

        itemView.setOnClickListener(this)

    }



    fun setItemClickListener(itemClickListener:ItemClickListener){


        this.itemClickListener=itemClickListener
    }

    override fun onClick(v: View?) {

        itemClickListener.onClick(v!!,adapterPosition)

    }


    
    

   
    

}




