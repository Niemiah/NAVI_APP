package com.solvd.naviapp.db;

    public interface IService<T> {
        T readFromDb(int id);
        int removeFromDb(int id);
        int writeToDb(T t);
        int updateInDb(T t);
    }
