package com.solvd.naviapp.db.implMyBatis.mappers;
import com.solvd.naviapp.bin.Node;
import com.solvd.naviapp.bin.Path;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface INodeMapper extends IMapper<Node> {
    List<Node> selectByGraphId(int id);
    List<Integer> selectByPathId(int id);
    void createWithGraphsId(@Param("node") Node node, @Param("graphsId") int graphsId);
    void updateWithPath(@Param("pathId") int pathId, @Param("nodeId") int nodeId, @Param("routeIndex") int routeIndex);
}

