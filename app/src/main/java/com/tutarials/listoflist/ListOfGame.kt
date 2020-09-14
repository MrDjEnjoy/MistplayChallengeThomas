package com.tutarials.listoflist

//classes made to parse the JSON file

class ListOfGame : ArrayList<GameListItem>()

//list class that stores the name of the list and the game it contains
data class GameListItem(
    val games: List<Game>,
    val list_title: String
)
// game class that stores the name and URL to the image as a string (image will be loaded at runtime individually)
data class Game(
    val img: String,
    val title: String
)