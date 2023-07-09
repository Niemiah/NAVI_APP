package com.solvd.naviapp.db.implMyBatis.mappers;

import com.solvd.naviapp.bin.Client;

public interface IMapper <T>{
    T selectById(int id);
    int selectLastId();
    void create(T t);
    void update(T t);
    void delete(int id);
}
