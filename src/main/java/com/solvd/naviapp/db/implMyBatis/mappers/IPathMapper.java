package com.solvd.naviapp.db.implMyBatis.mappers;
import com.solvd.naviapp.bin.Node;
import com.solvd.naviapp.bin.Path;
import org.apache.ibatis.annotations.Param;


public interface IPathMapper extends IMapper<Path> {
    Path selectByGraphId(int id);
    int selectTargetById(int id);
    int selectSourceById(int id);
    void createWithGraphsId(@Param("path") Path path, @Param("graphsId") int graphsId);
}
