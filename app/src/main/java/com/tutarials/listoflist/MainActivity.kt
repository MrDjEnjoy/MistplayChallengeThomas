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
                addList(list, layout)
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

    fun addList(list: GameListItem, layout: LinearLayout){
        //we create the parent views for our list
        val tempHorizontalScrollView = HorizontalScrollView(this.applicationContext)
        val verLinLayout = LinearLayout(this.applicationContext)
        verLinLayout.orientation = LinearLayout.VERTICAL

        //we create the list name view
        val listName = TextView(this.applicationContext)
        listName.text = list.list_title
        listName.setTypeface(Typeface.DEFAULT, Typeface.BOLD)
        listName.textSize = 24.0f

        //we had the children to the main layout of the list
        verLinLayout.addView(listName)
        verLinLayout.addView(tempHorizontalScrollView)

        //we add the list view tree to the App main layout
        layout.addView(verLinLayout)

        //we add the games to our HorizontalScrollView
        addGames(list.games, tempHorizontalScrollView)
    }

    fun addGames(games: List<Game>, horScrollView: HorizontalScrollView){
        //HorizontalScrollView can only have one direct child, we create the horizontal linear layout as the base to put out games
        val horLinLayout = LinearLayout(this.applicationContext)
        horLinLayout.orientation = LinearLayout.HORIZONTAL
        horScrollView.addView(horLinLayout)

        for(game in games){
            //each game needs an image and a title, we need a vertical linear layout to hold those
            val tempLinLayout = LinearLayout(this.applicationContext)
            tempLinLayout.orientation = LinearLayout.VERTICAL
            tempLinLayout.setPadding(10,10,10,10) //padding for aesthetic reason so that the images have some space between them

            val gameImage = ImageView(this.applicationContext)
            Picasso.get().load(game.img).into(gameImage) //with the Picasso framework, we load the image from the internet, framework added in the dependencies in the gradle and internet permission added in the manifest
            tempLinLayout.addView(gameImage)

            val tempTextView = TextView(this.applicationContext)
            tempTextView.text = game.title
            tempTextView.maxWidth = 250
            tempTextView.setHorizontallyScrolling(false)
            tempLinLayout.addView(tempTextView)

            horLinLayout.addView(tempLinLayout) //we had the game view tree to our scroll view
        }
    }
}