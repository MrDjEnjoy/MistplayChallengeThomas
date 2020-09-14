package com.tutarials.listoflist

import android.content.Context
import android.graphics.Typeface
import android.view.View
import android.widget.HorizontalScrollView
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.squareup.picasso.Picasso

//classes made to parse the JSON file

class ListOfGame : ArrayList<GameListItem>()

//list class that stores the name of the list and the game it contains
class GameListItem(val games: List<Game>, val list_title: String){

    //function that builds the view tree for the list and returns it for it to be added to the main layout
    fun buildViewTree(appContext: Context): View{
        val tempHorizontalScrollView = HorizontalScrollView(appContext)
        val verLinLayout = LinearLayout(appContext)
        verLinLayout.orientation = LinearLayout.VERTICAL

        //we create the list name view
        val listName = TextView(appContext)
        listName.text = list_title
        listName.setTypeface(Typeface.DEFAULT, Typeface.BOLD)
        listName.textSize = 24.0f

        //we had the children to the main layout of the list
        verLinLayout.addView(listName)
        verLinLayout.addView(tempHorizontalScrollView)

        //we add the list view tree to the App main layout
        //HorizontalScrollView can only have one direct child, we create the horizontal linear layout as the base to put out games
        val horLinLayout = LinearLayout(appContext)
        horLinLayout.orientation = LinearLayout.HORIZONTAL
        tempHorizontalScrollView.addView(horLinLayout)

        //we add the games to our HorizontalScrollView
        for(game in games){
            horLinLayout.addView(game.buildViewTree(appContext))
        }

        return verLinLayout
    }
}
// game class that stores the name and URL to the image as a string (image will be loaded at runtime individually)
class Game(val img: String, val title: String){

    //function that builds the view tree for a game, called by buildViewTree in GameListItem but it could technically be called on its own
    fun buildViewTree(appContext: Context): View{
        val tempLinLayout = LinearLayout(appContext)
        tempLinLayout.orientation = LinearLayout.VERTICAL
        tempLinLayout.setPadding(10,10,10,10) //padding for aesthetic reason so that the images have some space between them

        val gameImage = ImageView(appContext)
        Picasso.get().load(img).into(gameImage) //with the Picasso framework, we load the image from the internet, framework added in the dependencies in the gradle and internet permission added in the manifest
        tempLinLayout.addView(gameImage)

        val tempTextView = TextView(appContext)
        tempTextView.text = title
        tempTextView.maxWidth = 250
        tempTextView.setHorizontallyScrolling(false)
        tempLinLayout.addView(tempTextView)

        return tempLinLayout
    }
}
