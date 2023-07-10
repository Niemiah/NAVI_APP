package com.solvd.naviapp.db.implMyBatis.services;

import com.solvd.naviapp.bin.Edge;
import com.solvd.naviapp.bin.Node;
import com.solvd.naviapp.bin.Path;
import com.solvd.naviapp.db.IEdgeService;
import com.solvd.naviapp.db.INodeService;
import com.solvd.naviapp.db.implMyBatis.mappers.IEdgeMapper;
import com.solvd.naviapp.db.implMyBatis.mappers.IGraphMapper;
import com.solvd.naviapp.db.implMyBatis.mappers.INodeMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.sound.midi.Soundbank;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class NodeService implements INodeService {
    private static final String CONFIG = "mybatis-config.xml";
    private static final Logger LOGGER = LogManager.getLogger(NodeService.class);

    @Override
    public Node readFromDb(int id) {
        Node node = null;
        try (InputStream stream = Resources.getResourceAsStream(CONFIG);
             SqlSession session = new SqlSessionFactoryBuilder().build(stream).openSession()) {
            INodeMapper nodeMapper = session.getMapper(INodeMapper.class);
            IEdgeMapper edgeMapper = session.getMapper(IEdgeMapper.class);
            node = nodeMapper.selectById(id);
//            // new code
//            List <Edge> edgeList = edgeMapper.selectBySourceNodeId(node.getId());
//            Node finalNode = node;
//            edgeList.forEach(edge -> {
//                edge.setSource(finalNode);
//                edge.setDestination(readFromDb(edgeMapper.selectDestinationIdById(edge.getId())));
//            });
//            node.setEdges(edgeList);

        } catch (IOException e) {
            LOGGER.info(e.getMessage());
            LOGGER.info("node selected");
        }
        return node;
    }

    @Override
    public int removeFromDb(int id) {
        throw new UnsupportedOperationException("method not implemented");
    }

    @Override
    public int writeToDb(Node node) {
        throw new UnsupportedOperationException("method not implemented, use overloaded version");
    }

    @Override
    public int updateInDb(Node node) {
        throw new UnsupportedOperationException("method not implemented");
    }

    @Override
    public List<Node> readFromDbByGraphId(int id) {
        List<Node> nodeList = null;
        try (InputStream stream = Resources.getResourceAsStream(CONFIG);
             SqlSession session = new SqlSessionFactoryBuilder().build(stream).openSession()) {
            INodeMapper nodeMapper = session.getMapper(INodeMapper.class);
            IEdgeMapper edgeMapper = session.getMapper(IEdgeMapper.class);
            nodeList = nodeMapper.selectByGraphId(id);
            nodeList.forEach((node -> {
                List <Edge> edgeList = edgeMapper.selectBySourceNodeId(node.getId());
                edgeList.forEach(edge -> {
                    edge.setSource(node);
                    edge.setDestination(readFromDb(edgeMapper.selectDestinationIdById(edge.getId())));
                });
                node.setEdges(edgeList);
                LOGGER.info("node selected");
            }));
        } catch (IOException e) {
            LOGGER.info(e.getMessage());
        }
        return nodeList;
    }

    @Override
    public List<Integer> readFromDbByPathId(int id) {
        List<Integer> nodeList = new ArrayList<>();
        try (InputStream stream = Resources.getResourceAsStream(CONFIG);
             SqlSession session = new SqlSessionFactoryBuilder().build(stream).openSession()) {
            INodeMapper nodeMapper = session.getMapper(INodeMapper.class);
            nodeList = nodeMapper.selectByPathId(id);
            LOGGER.info("node selected");
        } catch (IOException e) {
            LOGGER.info(e.getMessage());
        }
        return nodeList;
    }

    @Override
    public int writeToDb(Node node, int graphsId) {
        int nodeId = -1;
        try (InputStream stream = Resources.getResourceAsStream(CONFIG);
             SqlSession session = new SqlSessionFactoryBuilder().build(stream).openSession()) {
            INodeMapper nodeMapper = session.getMapper(INodeMapper.class);
            nodeMapper.createWithGraphsId(node, graphsId);
            session.commit();
            nodeId = nodeMapper.selectLastId();
            LOGGER.info("node inserted");
        } catch (IOException e) {
            LOGGER.info(e.getMessage());
        }
        return nodeId;
    }

    @Override
    public int updateWithPath(Path path) {
        try (InputStream stream = Resources.getResourceAsStream(CONFIG);
             SqlSession session = new SqlSessionFactoryBuilder().build(stream).openSession()) {
            INodeMapper nodeMapper = session.getMapper(INodeMapper.class);
            List<Node> nodeList = path.getNodeList();
            for(int index=0; index<nodeList.size(); index++) {
                nodeMapper.updateWithPath(path.getId(), nodeList.get(index).getId(), index);
                session.commit();
                LOGGER.info("node updated");
            }
        } catch (IOException e) {
            LOGGER.info(e.getMessage());
        }
        return 1;
    }
}


