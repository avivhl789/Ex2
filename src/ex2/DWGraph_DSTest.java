package ex2;

import api.edge_data;
import api.node_data;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DWGraph_DSTest
{
    DWGraph_DS graph;

    @BeforeEach
    void setup()
    {
        graph = new DWGraph_DS();
        DWGraph_DS.nodedata firstNode = new DWGraph_DS.nodedata();
        DWGraph_DS.nodedata secondNode = new DWGraph_DS.nodedata();
        DWGraph_DS.nodedata thirdNode = new DWGraph_DS.nodedata();
        graph.addNode(firstNode);
        graph.addNode(secondNode);
        graph.addNode(thirdNode);
    }


    @Test
    void getNode()
    {
        DWGraph_DS.nodedata forthNode = new DWGraph_DS.nodedata();
        DWGraph_DS.nodedata expectedNode = forthNode;
        graph.addNode(forthNode);
        DWGraph_DS.nodedata actualNode = (DWGraph_DS.nodedata) graph.getNode(3);
        assert expectedNode == actualNode;
    }

    @Test
    void getNonExistingNode()
    {
        DWGraph_DS.nodedata actualNode = (DWGraph_DS.nodedata) graph.getNode(10);
        DWGraph_DS.nodedata expectedNode = null;
        assert actualNode == expectedNode;
    }



    @Test
    void getEdge()
    {
        graph.connect(1,2,15);
        edge_data actualEdgeData = graph.getEdge(1,2);
        assert actualEdgeData != null : "actual Edge Data: " +actualEdgeData +" , actual Edge Data shoudn't be null";
    }

    @Test
    void addNewNode()
    {
        DWGraph_DS.nodedata forthNode = new DWGraph_DS.nodedata();
        graph.addNode(forthNode);
        int actualMC = graph.getMC();
        int expectedMC = 4;
        assert actualMC == expectedMC;
    }

    @Test
    void addExistingNode()
    {
        DWGraph_DS.nodedata aSecondFirstNode = new DWGraph_DS.nodedata(1);
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
    }

    @Test
    void edgeSize()
    {
    }

    @Test
    void getMC()
    {
    }
}