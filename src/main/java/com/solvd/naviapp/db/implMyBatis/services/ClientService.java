package com.solvd.naviapp.db.implMyBatis.services;

import com.solvd.naviapp.bin.Client;
import com.solvd.naviapp.bin.Edge;
import com.solvd.naviapp.bin.Node;
import com.solvd.naviapp.db.IClientService;
import com.solvd.naviapp.db.IEdgeService;
import com.solvd.naviapp.db.implMyBatis.mappers.IClientMapper;
import com.solvd.naviapp.db.implMyBatis.mappers.IEdgeMapper;
import com.solvd.naviapp.db.implMyBatis.mappers.INodeMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;

public class ClientService implements IClientService {
    private static final String CONFIG = "mybatis-config.xml";
    private static final Logger LOGGER = LogManager.getLogger(ClientService.class);


    @Override
    public Client readFromDb(int id) {
        Client client = null;
        try (InputStream stream = Resources.getResourceAsStream(CONFIG);
             SqlSession session = new SqlSessionFactoryBuilder().build(stream).openSession()) {
            IClientMapper clientMapper = session.getMapper(IClientMapper.class);
            client = clientMapper.selectById(id);
        } catch (IOException e) {
            LOGGER.info(e.getMessage());
        }
        return client;
    }


    @Override
    public int removeFromDb(int id) {
        throw new UnsupportedOperationException("method not implemented");
    }

    @Override
    public int writeToDb(Client client) {
        int clientId = -1;
        try (InputStream stream = Resources.getResourceAsStream(CONFIG);
             SqlSession session = new SqlSessionFactoryBuilder().build(stream).openSession()) {
            IClientMapper clientMapper = session.getMapper(IClientMapper.class);
            clientMapper.create(client);
            session.commit();
            clientId = clientMapper.selectLastId();
        } catch (IOException e) {
            LOGGER.info(e.getMessage());
        }
        return clientId;
    }

    @Override
    public int updateInDb(Client client) {
        throw new UnsupportedOperationException("method not implemented");
    }
}




