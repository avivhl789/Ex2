import ex2.DWGraph_DS;
import ex2.nodedata;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class Remove_Edge_Tests
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
    public void remove_Existing_Edge()
    {
        graph.connect(0, 1, 10);
        graph.connect(1, 2, 20);
        graph.removeEdge(1, 2);
        int actualEdgeCounter = graph.edgeSize();
        int expectedEdgeCounter = 1;
        assert actualEdgeCounter == expectedEdgeCounter;
    }

    @Test
    public void remove_Non_Existing_Edge_Between_Existing_Nodes()
    {
        graph.connect(1, 2, 20);
        graph.removeEdge(0, 1);
        int actualEdgeCounter = graph.edgeSize();
        int expectedEdgeCounter = 1;
        assert actualEdgeCounter == expectedEdgeCounter : "expected 1, actual: " + graph.edgeSize();
    }

    @Test
    public void remove_Non_Existing_Edge_Between_Existing_And_Non_Existing_Node()
    {
        graph.connect(1, 2, 20);
        graph.removeEdge(1,100);
        int actualEdgeCounter = graph.edgeSize();
        int expectedEdgeCounter = 1;
        assert actualEdgeCounter == expectedEdgeCounter : "expected 1, actual: " + graph.edgeSize();
    }

    @Test
    public void remove_Non_Existing_Edge_Between_Non_Existing_Nodes()
    {
        graph.connect(1, 2, 20);
        graph.removeEdge(3, 1);
        graph.removeEdge(7, 10);
        int actualEdgeCounter = graph.edgeSize();
        int expectedEdgeCounter = 1;
        assert actualEdgeCounter == expectedEdgeCounter;
    }
}
