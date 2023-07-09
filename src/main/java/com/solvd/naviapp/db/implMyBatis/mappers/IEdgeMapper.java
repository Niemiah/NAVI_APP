package com.solvd.naviapp.db.implMyBatis.mappers;

import com.solvd.naviapp.bin.Edge;

import java.util.List;

public interface IEdgeMapper extends IMapper<Edge>{
    List<Edge> selectBySourceNodeId(int id);
    int selectDestinationIdById(int id);
}
