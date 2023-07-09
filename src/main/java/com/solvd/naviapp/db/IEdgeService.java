package com.solvd.naviapp.db;

import com.solvd.naviapp.bin.Edge;

import java.util.List;


public interface IEdgeService extends IService<Edge> {
    List<Edge> readFromDbBySourceNodeId(int id);
    int readFromDbDestinationId(int id);
    int writeToDb(List<Edge> edgeList);
}

