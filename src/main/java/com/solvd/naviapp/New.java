package com.solvd.naviapp;

import com.solvd.naviapp.controller.services.INavService;
import com.solvd.naviapp.controller.services.impl.NavService;
import com.solvd.naviapp.db.IDbService;
import com.solvd.naviapp.db.Test;
import com.solvd.naviapp.db.implMyBatis.services.DbService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import user.IUserInterface;
import user.impl.UserInterface;
import java.util.Scanner;
import com.solvd.naviapp.bin.Client;
import com.solvd.naviapp.bin.Graph;
import com.solvd.naviapp.bin.Node;
import com.solvd.naviapp.bin.Path;
import com.solvd.naviapp.controller.services.INavService;




public class New {
    private static final Logger LOGGER = LogManager.getLogger(New.class);

    public static void main(String[] args) {
        UserInterface userInterface = new UserInterface();
        Scanner scanner = new Scanner(System.in);

        userInterface.start();
    }
}
