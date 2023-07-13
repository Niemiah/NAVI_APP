package com.solvd.naviapp.db.implMyBatis.services;

import com.solvd.naviapp.bin.Edge;
import com.solvd.naviapp.bin.Node;
import com.solvd.naviapp.bin.Path;
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
            // get node with edges = null
            node = nodeMapper.selectById(id);
            // get edges of the node with nodes = null
            List<Edge> edgeList = edgeMapper.selectBySourceNodeId(node.getId());
            Node finalNode1 = node;
            // set source node of the edges
            edgeList.forEach((edge) ->{
             edge.setSource(finalNode1);
            });
            // get ids of destination nodes
            List<Integer> destinationIdList = edgeMapper.selectDestinationIdBySourceId(node.getId());
            for(int index = 0; index<edgeList.size(); index ++){
                Edge edge = edgeList.get(index);
                int destinationNodeId = destinationIdList.get(index);
                edge.setDestination(nodeMapper.selectById(destinationNodeId));
            }
            node.setEdges(edgeList);
        } catch (IOException e) {
            LOGGER.info(e.getMessage());
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
                    int destinationNodeId = edgeMapper.selectDestinationIdById(edge.getId());
                    edge.setDestination(readFromDb(destinationNodeId));
                });
                node.setEdges(edgeList);
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
            }
        } catch (IOException e) {
            LOGGER.info(e.getMessage());
        }
        return 1;
    }
}


