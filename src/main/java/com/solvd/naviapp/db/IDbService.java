package com.solvd.naviapp.db;

import com.solvd.naviapp.bin.Client;
import com.solvd.naviapp.bin.Graph;
import com.solvd.naviapp.bin.Path;

public interface IDbService {
    Client getClientById(int id);
    int saveClient(Client client);
    int removeClient(int id);
}
