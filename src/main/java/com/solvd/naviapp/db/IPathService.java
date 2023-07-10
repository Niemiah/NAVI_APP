package com.solvd.naviapp.db;

import com.solvd.naviapp.bin.Node;
import com.solvd.naviapp.bin.Path;

import java.util.List;

public interface IPathService extends IService<Path> {
    Path readFromDbByGraphId(int id);
    int readFromDbSourceId(int id);
    int readFromDbTargetId(int id);
    int writeToDb(Path path, int graphId);
}

