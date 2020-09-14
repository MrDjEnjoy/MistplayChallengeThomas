package com.tutarials.listoflist

import android.graphics.Bitmap
import android.graphics.Matrix
import android.graphics.Typeface
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.HorizontalScrollView
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.graphics.drawable.toBitmap
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.squareup.picasso.Picasso

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //we get the main layout as everything will be added programmatically
        //layout refers to the layout inside the Scroll View in the XML file
        val layout = findViewById<LinearLayout>(R.id.scrollLinLayout)

        //we start by reading the JSON file
        val lists = readJson()
        if (lists != null) {
            //we then add the lists to the display
            for(list in lists){
                layout.addView(list.buildViewTree(this.applicationContext))
            }
        }
    }

    fun readJson(): ListOfGame? {
        val gson = Gson()

        val listOfListOfGameType = object : TypeToken<ListOfGame>() {}.type

        //we create a JSON string from the file
        val jsonFileString = getJsonDataFromAsset(applicationContext, "lists.json")

        //we deserialize the JSON string and return the list of lists of games
        return gson.fromJson(jsonFileString, listOfListOfGameType)
    }
    
}