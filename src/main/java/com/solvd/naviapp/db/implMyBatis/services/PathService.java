package com.solvd.naviapp.db.implMyBatis.services;

import com.solvd.naviapp.bin.Client;
import com.solvd.naviapp.bin.Node;
import com.solvd.naviapp.bin.Path;
import com.solvd.naviapp.db.IClientService;
import com.solvd.naviapp.db.IPathService;
import com.solvd.naviapp.db.implMyBatis.mappers.IClientMapper;
import com.solvd.naviapp.db.implMyBatis.mappers.IGraphMapper;
import com.solvd.naviapp.db.implMyBatis.mappers.INodeMapper;
import com.solvd.naviapp.db.implMyBatis.mappers.IPathMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;

public class PathService implements IPathService {
    private static final String CONFIG = "mybatis-config.xml";
    private static final Logger LOGGER = LogManager.getLogger(PathService.class);


    @Override
    public Path readFromDbByGraphId(int id) {
        Path path = null;
        try (InputStream stream = Resources.getResourceAsStream(CONFIG);
             SqlSession session = new SqlSessionFactoryBuilder().build(stream).openSession()) {
            IPathMapper pathMapper = session.getMapper(IPathMapper.class);
            path = pathMapper.selectByGraphId(id);
            LOGGER.info("path selected");
        } catch (IOException e) {
            LOGGER.info(e.getMessage());
        }
        return path;
    }

    @Override
    public int readFromDbSourceId(int id) {
        int sourceId = -1;
        try (InputStream stream = Resources.getResourceAsStream(CONFIG);
             SqlSession session = new SqlSessionFactoryBuilder().build(stream).openSession()) {
            IPathMapper pathMapper = session.getMapper(IPathMapper.class);
            sourceId = pathMapper.selectSourceById(id);
            LOGGER.info("path selected");
        } catch (IOException e) {
            LOGGER.info(e.getMessage());
        }
        return sourceId;
    }

    @Override
    public int readFromDbTargetId(int id){
            int targetId = -1;
            try (InputStream stream = Resources.getResourceAsStream(CONFIG);
                 SqlSession session = new SqlSessionFactoryBuilder().build(stream).openSession()) {
                IPathMapper pathMapper = session.getMapper(IPathMapper.class);
                targetId = pathMapper.selectTargetById(id);
                LOGGER.info("path selected");
            } catch (IOException e) {
                LOGGER.info(e.getMessage());
            }
            return targetId;
        }

    @Override
    public int writeToDb(Path path, int graphId) {
        int pathId = -1;
        try (InputStream stream = Resources.getResourceAsStream(CONFIG);
             SqlSession session = new SqlSessionFactoryBuilder().build(stream).openSession()) {
            IPathMapper pathMapper = session.getMapper(IPathMapper.class);
            pathMapper.createWithGraphsId(path, graphId);
            session.commit();
            pathId = pathMapper.selectLastId();
            LOGGER.info("path inserted");
        } catch (IOException e) {
            LOGGER.info(e.getMessage());
        }
        return pathId;
    }

    @Override
    public Path readFromDb(int id) {
        return null;
    }

    @Override
    public int removeFromDb(int id) {
        return 0;
    }

    @Override
    public int writeToDb(Path path) {
        throw new UnsupportedOperationException("method not implemented, use overloaded version");
    }


    @Override
    public int updateInDb(Path path) {
        return 0;
    }
}
