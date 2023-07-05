package controller;

import bin.Graph;
import bin.Node;
import bin.Path;
import controller.service.INavService;
import controller.service.impl.NavService;
import user.IUserInterface;
import user.UserInterface;


// TODO Check main algo for correctness, review Factory algo, refactor all with SOLID and patterns

public class Test {
    public static void main(String[] args) {
        IUserInterface userInterface = new UserInterface();
        userInterface.start();
    }
}
