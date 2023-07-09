package com.solvd.naviapp.db;

import com.solvd.naviapp.bin.Client;
import com.solvd.naviapp.bin.Graph;

import java.util.List;

public interface IGraphService extends IService<Graph> {
    List<Graph> readFromDbByClientId(int id);
    int writeToDb(int clientId);

}

