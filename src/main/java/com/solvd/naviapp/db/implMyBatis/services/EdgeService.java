package com.solvd.naviapp.db.implMyBatis.services;

import com.solvd.naviapp.bin.Edge;
import com.solvd.naviapp.bin.Node;
import com.solvd.naviapp.db.IEdgeService;
import com.solvd.naviapp.db.INodeService;
import com.solvd.naviapp.db.implMyBatis.mappers.IEdgeMapper;
import com.solvd.naviapp.db.implMyBatis.mappers.INodeMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class EdgeService implements IEdgeService {
    private static final String CONFIG = "mybatis-config.xml";
    private static final Logger LOGGER = LogManager.getLogger(EdgeService.class);

    @Override
    public Edge readFromDb(int id) {
        Edge edge = null;
        try (InputStream stream = Resources.getResourceAsStream(CONFIG);
             SqlSession session = new SqlSessionFactoryBuilder().build(stream).openSession()) {
            IEdgeMapper edgeMapper = session.getMapper(IEdgeMapper.class);
            edge = edgeMapper.selectById(id);
        } catch (IOException e) {
            LOGGER.info(e.getMessage());
        }
        return edge;
    }

    @Override
    // takes edge id as parameter
    public int readFromDbDestinationId(int id) {
        int destinationId = -1;
        try (InputStream stream = Resources.getResourceAsStream(CONFIG);
             SqlSession session = new SqlSessionFactoryBuilder().build(stream).openSession()) {
            IEdgeMapper edgeMapper = session.getMapper(IEdgeMapper.class);
            destinationId = edgeMapper.selectDestinationIdById(id);
            LOGGER.info("edge retrieved");
        } catch (IOException e) {
            LOGGER.info(e.getMessage());
        }
        return destinationId;
    }

    @Override
    public int writeToDb(List<Edge> edgeList) {
        try (InputStream stream = Resources.getResourceAsStream(CONFIG);
             SqlSession session = new SqlSessionFactoryBuilder().build(stream).openSession()) {
            IEdgeMapper edgeMapper = session.getMapper(IEdgeMapper.class);
            edgeList.forEach(edge -> {
                edgeMapper.create(edge);
                session.commit();
                LOGGER.info("edge inserted");
            });

        } catch (IOException e) {
            LOGGER.info(e.getMessage());
        }
        return 1;
    }

    @Override
    public List<Edge> readFromDbBySourceNodeId(int id) {
        List<Edge> edgeList = null;
        try (InputStream stream = Resources.getResourceAsStream(CONFIG);
             SqlSession session = new SqlSessionFactoryBuilder().build(stream).openSession()) {
            IEdgeMapper edgeMapper = session.getMapper(IEdgeMapper.class);
            edgeList = edgeMapper.selectBySourceNodeId(id);
            LOGGER.info("edge retrieved");
        } catch (IOException e) {
            LOGGER.info(e.getMessage());
        }
        return edgeList;
    }

    @Override
    public int removeFromDb(int id) {
        throw new UnsupportedOperationException("method not implemented");

    }

    @Override
    public int writeToDb(Edge edge) {
        throw new UnsupportedOperationException("method not implemented, use overloaded version");
    }

    @Override
    public int updateInDb(Edge edge) {
        throw new UnsupportedOperationException("method not implemented");
    }

}



