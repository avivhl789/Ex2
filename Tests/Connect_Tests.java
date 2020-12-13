import api.edge_data;
import ex2.DWGraph_DS;
import ex2.nodedata;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Connect_Tests
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
    void valid_Connect()
    {
        graph.connect(0,1,10);
        graph.connect(1,2,20);
        graph.connect(2,0,30);
        int actualMC = graph.getMC();
        int expectedMC = 6;
        assertEquals(expectedMC,actualMC);
    }

    @Test
    public void invalid_Connect_Same_Node()
    {
        graph.connect(1,1,20);
        int actualModeCounter = graph.edgeSize();
        int expectedModeCounter = 0;
        assert actualModeCounter == expectedModeCounter;
    }


    @Test
    public void invalid_Connect_Negative_Weight()
    {
        graph.connect(0,1,-8);
        int actualMC = graph.getMC();
        int expectedMC = 3;
        assert actualMC == expectedMC;
    }

    @Test
    public void invalid_Connect_With_NonExisting_Node()
    {
        graph.connect(0,8,12);
        int actualMC = graph.getMC();
        int expectedMC = 3;
        assert actualMC == expectedMC;
    }

    @Test
    public void connect_Updates_New_Weight()
    {
        graph.connect(1,2,10);
        graph.connect(1,2,50);
        nodedata scrNode = ((nodedata) (graph.getNode(1)));
        double actualWeight = scrNode.getNi().get(2).getWeight();
        double expectedWeight = 50;
        assert actualWeight == expectedWeight : "actual Weight: " +actualWeight +" , expected Weight: "+expectedWeight;
    }


    @Test
    void invalidConnectTwoNonExistingNodes()
    {
        graph.connect(100,10,10);
        int actualMC = graph.getMC();
        int expectedMC = 3;
        assert actualMC == expectedMC : "actual MC = " +actualMC + " , expected MC = " +expectedMC;
    }
}
