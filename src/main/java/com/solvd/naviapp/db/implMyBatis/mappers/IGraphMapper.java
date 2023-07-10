package com.solvd.naviapp.db.implMyBatis.mappers;
import com.solvd.naviapp.bin.Client;
import com.solvd.naviapp.bin.Graph;

import java.util.List;

public interface IGraphMapper extends IMapper<Graph> {
    List<Graph> selectByClientId(int id);
    void create(int  id);
}
