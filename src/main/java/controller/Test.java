package controller;

import user.IUserInterface;
import user.impl.UserInterface;

// TODO
//  Check main algo for correctness +++
//  improve main algo to search for Source-Target Only (WIP)
//  review Factory algo +++
//  refactor all with SOLID  +++
//  patters (WIP)


public class Test {
    public static void main(String[] args) {
        IUserInterface userInterface = new UserInterface();
        userInterface.start();
    }
}