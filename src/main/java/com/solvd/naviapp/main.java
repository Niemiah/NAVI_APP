package com.solvd.naviapp;
import com.solvd.naviapp.user.impl.View;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class main {
    private static final Logger LOGGER = LogManager.getLogger(main.class);

    public static void main(String[] args) {
        View userInterface = new View();

        userInterface.start();
    }
}