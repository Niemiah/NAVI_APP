package com.solvd.naviapp.db;

import com.solvd.naviapp.bin.Graph;
import com.solvd.naviapp.bin.Node;
import com.solvd.naviapp.bin.Path;

import java.util.List;

public interface INodeService extends IService<Node> {
    List<Node> readFromDbByGraphId(int id);
    List<Integer> readFromDbByPathId(int id);
    int writeToDb(Node node, int graphsId);
    int updateWithPath(Path path);
}

