package com.solvd.naviapp.db.implMyBatis.services;

import com.solvd.naviapp.bin.Client;
import com.solvd.naviapp.bin.Graph;
import com.solvd.naviapp.db.IClientService;
import com.solvd.naviapp.db.IGraphService;
import com.solvd.naviapp.db.implMyBatis.mappers.IClientMapper;
import com.solvd.naviapp.db.implMyBatis.mappers.IGraphMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class GraphService implements IGraphService {
    private static final String CONFIG = "mybatis-config.xml";
    private static final Logger LOGGER = LogManager.getLogger(GraphService.class);


    public List<Graph> readFromDbByClientId(int id){
        List<Graph> graphList = null;
        try (InputStream stream = Resources.getResourceAsStream(CONFIG);
             SqlSession session = new SqlSessionFactoryBuilder().build(stream).openSession()) {
            IGraphMapper graphMapper = session.getMapper(IGraphMapper.class);
            graphList = graphMapper.selectByClientId(id);
            LOGGER.info("graph selected");
        } catch (IOException e) {
            LOGGER.info(e.getMessage());
        }
        return graphList;
    }

    @Override
    public int writeToDb(int clientId) {
        int graphId = -1;
        try (InputStream stream = Resources.getResourceAsStream(CONFIG);
             SqlSession session = new SqlSessionFactoryBuilder().build(stream).openSession()) {
            IGraphMapper graphMapper = session.getMapper(IGraphMapper.class);
            graphMapper.create(clientId);
            session.commit();
            graphId = graphMapper.selectLastId();
            LOGGER.info("graph inserted");
        } catch (IOException e) {
            LOGGER.info(e.getMessage());
        }
        return graphId;
    }

    @Override
    public Graph readFromDb(int id) {
        return null;
    }

    @Override
    public int removeFromDb(int id) {
        throw new UnsupportedOperationException("method not implemented");
    }

    @Override
    public int writeToDb(Graph graph) {
        throw new UnsupportedOperationException("method not implemented, use create (int id ) method");
    }

    @Override
    public int updateInDb(Graph graph) {
        throw new UnsupportedOperationException("method not implemented");
    }
}




