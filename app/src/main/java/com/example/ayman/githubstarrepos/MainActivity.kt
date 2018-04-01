package com.example.ayman.githubstarrepos

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.example.ayman.githubstarrepos.R.id.recyclerView_main
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        recyclerView_main.layoutManager = LinearLayoutManager(this)

        //load the JSon data and display it
        getData()
    }

    fun getData(){

        val Date = getDate(-30)

        val pageCount = 1

        val url = "https://api.github.com/search/repositories?q=created:%3E"+Date+"&sort=stars&order=desc"+"&page="+pageCount+""

        val request = Request.Builder().url(url).build()

        val client = OkHttpClient()
        client.newCall(request).enqueue(object: Callback{
            override fun onResponse(call: Call?, response: Response?) {

                val body = response?.body()?.string()
                val gson = GsonBuilder().create()
                val repoFeed = gson.fromJson(body, RepoFeed::class.java)

                runOnUiThread{
                    recyclerView_main.adapter = RepoAdapter(repoFeed)
                }
            }

            override fun onFailure(call: Call?, e: IOException?) {
                println("Failed to execute request")
            }
        })

    }

    //get todays date and add/substracts a number of days from it
    fun getDate(days: Int):String{
        val calender: Calendar
        val simpleDateFormat: SimpleDateFormat
        val Date: String
        calender = Calendar.getInstance()
        calender.add(Calendar.DAY_OF_MONTH, days)
        simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
        Date = simpleDateFormat.format(calender.time)
        return Date
    }

}
