# simple-kalah-game

The simplest implementation of online Kalah-game.

The main successful scenario is implemented:

*player_1 opens page (localhost:8080 if running locally), enters his/her name and submits for new game
*player_2 opens page in another browser (or browser window in private mode), enters his/her name and submits
*while player_2 is not submited player_1 page is showing waiting for opponent
*players registered
*at the page of each player there is a tip whose turn is now
*at the page of each player there is a link to get last state of the game - it was supposed that while one player makes his/her turn another player's client polls server for the current state and refreshes when the state is recieved; pressing link Get State is simulation of polling
*the turn is made by clicking on number link in the table

There is many limitations such as:
*no errors handling
*no page reload handling
*no handling if the user input url directly

The game was tested locally running directly via IDE with java 8, tomcat 8, FireFox
