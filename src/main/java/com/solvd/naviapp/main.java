package com.solvd.naviapp;
import com.solvd.naviapp.user.impl.UserInterface;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.Scanner;





public class main {
    private static final Logger LOGGER = LogManager.getLogger(main.class);

    public static void main(String[] args) {
        UserInterface userInterface = new UserInterface();

        userInterface.start();
    }
}