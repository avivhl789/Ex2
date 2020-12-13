import api.edge_data;
import api.geo_location;
import api.node_data;
import ex2.DWGraph_DS;
import ex2.edgedata;
import ex2.geolocation;
import ex2.nodedata;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class DWGraph_DSTest
{
    DWGraph_DS graph;

    @BeforeEach
    void setup()
    {
        graph = new DWGraph_DS();
        node_data firstNode = new nodedata(0);
        node_data secondNode = new nodedata(1);
        node_data thirdNode = new nodedata(2);
        graph.addNode(firstNode);
        graph.addNode(secondNode);
        graph.addNode(thirdNode);
    }

    @Test
    public void proper_Graph_Init()
    {
        int actualMC = graph.getMC();
        int actualEdgeCounter = graph.edgeSize();
        int expectedMC = 3;
        int expectedEdgeCounter = 0;
        assert actualEdgeCounter == expectedEdgeCounter : "expected Edge Counter = " + expectedEdgeCounter
                + ", actual Edge Counter = " + actualEdgeCounter;
        assert actualMC == expectedMC : "expected Mode Counter = " + expectedMC
                + ", actual Mode Counter = " + actualMC;
    }

    @Test
    void getNode()
    {
        node_data forthNode = new nodedata(4);
        graph.addNode(forthNode);
        node_data actualNode = (nodedata) graph.getNode(forthNode.getKey());
        assert forthNode == actualNode : "actual node is: " + actualNode + " , expected node is: " + forthNode;
    }

    @Test
    public void valid_Add_Node()
    {
        node_data forthNode = new nodedata(4);
        graph.addNode(forthNode);
        int actualNodeSize = graph.nodeSize();
        int expectedNodeSize = 4;
        assert actualNodeSize == expectedNodeSize;
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
        graph.connect(1, 2, 15);
        edge_data actualEdgeData = graph.getEdge(1, 2);
        assertNotNull(actualEdgeData);
    }
    //TODO do i need both?
    @Test
    public void valid_Get_Edge()
    {
        edge_data expectedEdgeData = new edgedata(1, 2, 50);
        graph.connect(1, 2, 50);
        edge_data actualEdgeData = graph.getEdge(1, 2);
        assert actualEdgeData.equals(expectedEdgeData);
    }

//    @Test
//    public void invalid_Get_Edge_To_One_Non_Existing_Node()
//    {
//        graph.connect(1, 10, 100);
//
//        assert actualWeight == expectedWeight;
//    }

    @Test
    void getNonExistingEdge()
    {
        edge_data actualEdgeData = graph.getEdge(1, 2);
        assert actualEdgeData == null : "actual Edge Data: " + actualEdgeData + " , actual Edge Data should be null";
    }

    @Test
    void addNewNode()
    {
        nodedata forthNode = new nodedata(3);
        graph.addNode(forthNode);
        int actualMC = graph.getMC();
        int expectedMC = 4;
        assertEquals(expectedMC,actualMC);
    }

    @Test
    void addExistingNode()
    {
        nodedata aSecondFirstNode = new nodedata(1);
        graph.addNode(aSecondFirstNode);
        int actualMC = graph.getMC();
        int expectedMC = 3;
        assertEquals(expectedMC,actualMC);
    }


    @Test
    void getE()
    {
    }

    @Test
    public void valid_Remove_Existing_Node()
    {
        graph.connect(0, 1, 10);
        graph.connect(0, 2, 20);
        node_data expectedNode = graph.getNode(0);
        node_data actualNode = graph.removeNode(0);
        int actual_Neighbours_Of_Node_1 = graph.getE(1).size();
        int actual_Neighbours_Of_Node_2 = (graph.getE(2).size());
        int expected_Neighbours_Of_Node_1 = 0;
        int expected_Neighbours_Of_Node_2 = 0;
        assert actualNode.equals(expectedNode);
        assert actual_Neighbours_Of_Node_1 == expected_Neighbours_Of_Node_1;
        assert actual_Neighbours_Of_Node_2 == expected_Neighbours_Of_Node_2;
    }


    @Test
    void nodeSize()
    {
        nodedata forthNode = new nodedata(3);
        graph.addNode(forthNode);
        int expectedNodeSize = 4;
        int actualNodeSize = graph.nodeSize();
        assertEquals(expectedNodeSize,actualNodeSize);
    }

    @Test
    void edgeSize()
    {
        graph.connect(1, 2, 15);
        int expectedEdgeSize = 1;
        int actualNodeSize = graph.edgeSize();
        assert actualNodeSize == expectedEdgeSize;
    }

    @Test
    void getMC()
    {
        graph.connect(0, 1, 10);
        graph.connect(1, 2, 20);
        graph.connect(2, 0, 30);
        int actualMC = graph.getMC();
        int expectedMC = 6;
        assert actualMC == expectedMC;
    }

//    private class testNode implements node_data
//    {
//        private int Key;
//        private String Info;
//        private int Tag;
//        private double Weight;
//        private geo_location Location;
//
//
//        public testNode(int key, String Info, int tag)
//        {
//            this.Info = Info;
//            this.Key = key;
//            this.Tag = tag;
//            Location = new geolocation();
//        }
//
//        public testNode(int key)
//        {
//            this.Info = "empty";
//            this.Key = key;
//            this.Tag = 0;
//            Location = new geolocation();
//        }
//
//        @Override
//        public int getKey()
//        {
//            return Key;
//        }
//
//        @Override
//        public geo_location getLocation()
//        {
//            return Location;
//        }
//
//        @Override
//        public void setLocation(geo_location p)
//        {
//            Location = p;
//        }
//
//        @Override
//        public double getWeight()
//        {
//            return Weight;
//        }
//
//        @Override
//        public void setWeight(double w)
//        {
//            Weight = w;
//        }
//
//        @Override
//        public String getInfo()
//        {
//            return Info;
//        }
//
//        @Override
//        public void setInfo(String s)
//        {
//            Info = s;
//        }
//
//        @Override
//        public int getTag()
//        {
//            return Tag;
//        }
//
//        @Override
//        public void setTag(int t)
//        {
//            Tag = t;
//        }
//    }
}