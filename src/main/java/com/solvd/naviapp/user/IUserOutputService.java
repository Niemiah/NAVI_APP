package com.solvd.naviapp.user;
import com.solvd.naviapp.bin.Path;

public interface IUserOutputService {
    void displayWelcomeMessage(); //Displays a welcome message
    void displayGraph(); //Displays the graph

    void displayRoute(Path path); //Displays the shortest path from the source node to the destination node
    void displayGoodbyeMessage(); //Displays a goodbye message
}