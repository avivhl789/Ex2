package ex2;

import api.edge_data;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DWGraph_DSTest
{
    DWGraph_DS graph;

    @BeforeEach
    void setup()
    {
        graph = new DWGraph_DS();
        nodedata firstNode = new nodedata();
        nodedata secondNode = new nodedata();
        nodedata thirdNode = new nodedata();
        graph.addNode(firstNode);
        graph.addNode(secondNode);
        graph.addNode(thirdNode);
    }


    @Test
    void getNode()
    {
        nodedata forthNode = new nodedata();
        graph.addNode(forthNode);
        nodedata actualNode = (nodedata) graph.getNode(forthNode.getKey());
        assert forthNode == actualNode : "actual node is: " +actualNode +" , expected node is: " +forthNode;
    }

    @Test
    void getNonExistingNode()
    {
        nodedata actualNode = (nodedata) graph.getNode(10);
        nodedata expectedNode = null;
        assert actualNode == expectedNode;
    }


    @Test
    void getEdge()
    {
        graph.connect(1,2,15);
        edge_data actualEdgeData = graph.getEdge(1,2);
        assert actualEdgeData != null : "actual Edge Data: " +actualEdgeData +" , actual Edge Data shouldn't be null";
    }

    @Test
    void getNonExistingEdge()
    {
        edge_data actualEdgeData = graph.getEdge(1,2);
        assert actualEdgeData == null : "actual Edge Data: " +actualEdgeData +" , actual Edge Data should be null";
    }

    @Test
    void addNewNode()
    {
        nodedata forthNode = new nodedata();
        graph.addNode(forthNode);
        int actualMC = graph.getMC();
        int expectedMC = 4;
        assert actualMC == expectedMC;
    }

    @Test
    void addExistingNode()
    {
        nodedata aSecondFirstNode = new nodedata(1);
        graph.addNode(aSecondFirstNode);
        int actualMC = graph.getMC();
        int expectedMC = 3;
        assert actualMC == expectedMC;
    }

    @Test
    void validConnect()
    {
        graph.connect(1,2,15);
        int actualMC = graph.getMC();
        int expectedMC = 4;
        assert actualMC == expectedMC;
    }

    @Test
    void invalidConnectNegativeWeight()
    {
        graph.connect(1,2,-5);
        int actualMC = graph.getMC();
        int expectedMC = 3;
        assert actualMC == expectedMC : "actual MC = " +actualMC + " , expected MC = " +expectedMC;
    }

    @Test
    void invalidConnectNonExistingNode()
    {
        graph.connect(1,10,10);
        int actualMC = graph.getMC();
        int expectedMC = 3;
        assert actualMC == expectedMC : "actual MC = " +actualMC + " , expected MC = " +expectedMC;
    }

    @Test
    void invalidConnectTwoNonExistingNodes()
    {
        graph.connect(100,10,10);
        int actualMC = graph.getMC();
        int expectedMC = 3;
        assert actualMC == expectedMC : "actual MC = " +actualMC + " , expected MC = " +expectedMC;
    }

    @Test
    void invalidConnectExistingEdge()
    {

    }

    @Test
    void getE()
    {
    }

    @Test
    void removeNode()
    {
    }

    @Test
    void removeEdge()
    {
    }

    @Test
    void nodeSize()
    {
        nodedata forthNode = new nodedata();
        graph.addNode(forthNode);
        int expectedNodeSize = 4;
        int actualNodeSize = graph.nodeSize();
        assert expectedNodeSize == actualNodeSize;
    }

    @Test
    void edgeSize()
    {
        graph.connect(1,2,15);
        int expectedEdgeSize = 1;
        int actualNodeSize = graph.edgeSize();
        assert actualNodeSize == expectedEdgeSize;
    }

    @Test
    void getMC()
    {
        int expectedMC = 3;
        int actualMC = graph.getMC();
        assert  actualMC == expectedMC;
    }
}