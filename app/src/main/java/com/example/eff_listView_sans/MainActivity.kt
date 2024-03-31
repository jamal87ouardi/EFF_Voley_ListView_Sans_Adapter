package com.example.eff_listView_sans

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.squareup.picasso.Picasso
import org.json.JSONArray
import org.json.JSONObject


data class Movie(val id:Int, val name:String, val price:Double, val image:String)
class MainActivity : AppCompatActivity() {

    var sa = JSONArray()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val url = "https://mocki.io/v1/c815b063-5d3a-4c5b-ad6f-8f457c52d6e1"

        val request = JsonArrayRequest(
            Request.Method.GET, url, null,
            { response ->
                sa = response

                val list1 = ArrayList<String>()

                val list2 = ArrayList<Movie>()



                for (i in 0 until sa.length()) {
                    val jsonObject: JSONObject = sa.getJSONObject(i)
                    val id = jsonObject.getInt("id")
                    val name = jsonObject.getString("name")
                    val price = jsonObject.getDouble("price")
                    val image = jsonObject.getString("image")

                    val displayed = name+" - "+price.toString()+" MAD"

                    list1.add(displayed)

                    val movie = Movie(id,name,price,image)

                    list2.add(movie)

                }

                val listView= findViewById<ListView>(R.id.listView)
                val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, list1)
                listView.adapter = adapter

                listView.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->

                    val selectedItem = list2[position]

                    findViewById<TextView>(R.id.tvName).text = selectedItem.name
                    findViewById<TextView>(R.id.tvPrice).text = selectedItem.price.toString()+" MAD"
                    Picasso.get().load(selectedItem.image).into(findViewById<ImageView>(R.id.imageView))
                }

            },
            { error ->

            }
        )


        val requestQueue = Volley.newRequestQueue(this)
        requestQueue.add(request)


    }
}