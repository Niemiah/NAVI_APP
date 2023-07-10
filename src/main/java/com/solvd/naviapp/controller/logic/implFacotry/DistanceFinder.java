package com.solvd.naviapp.controller.logic.implFacotry;
import com.solvd.naviapp.controller.logic.IDistanceFinder;
import com.solvd.naviapp.bin.Node;

public class DistanceFinder implements IDistanceFinder {
    @Override
    public int getDistance(Node a, Node b) {
        double x = a.getX() - b.getX();
        double y = a.getY() - b.getY();
        x = Math.pow(x, 2);
        y = Math.pow(y, 2);
        double z = Math.pow((x + y), 0.5);
        return (int)z;
    }
}