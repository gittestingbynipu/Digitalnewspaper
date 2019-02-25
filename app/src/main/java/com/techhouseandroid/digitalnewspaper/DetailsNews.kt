package com.techhouseandroid.digitalnewspaper

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.app.AlertDialog
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import dmax.dialog.SpotsDialog
import kotlinx.android.synthetic.main.activity_details_news.*

class DetailsNews : AppCompatActivity() {

    lateinit var alterDialog:AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details_news)

        alterDialog=SpotsDialog(this)
        alterDialog.show()


        webView.settings.javaScriptEnabled=true
        webView.webChromeClient= WebChromeClient()
        webView.webViewClient=object: WebViewClient() {


            override fun onPageFinished(view: WebView?, url: String?) {

                alterDialog.dismiss()
            }


        }

             if (intent!=null)
                 if (!intent.getStringExtra("webURL").isEmpty())

                     webView.loadUrl(intent.getStringExtra("webURL"))



    }
}
