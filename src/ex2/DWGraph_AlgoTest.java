package ex2;

import api.directed_weighted_graph;
import api.node_data;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.fail;

class DWGraph_AlgoTest
{
    private DWGraph_Algo tester;
    private directed_weighted_graph graph;

    @BeforeEach
    public void init()
    {
        tester = new DWGraph_Algo();
        graph = new DWGraph_DS();
        for (int i = 0; i < 10; i++)
        {
            node_data newNode = new nodedata(i);
            graph.addNode(newNode);
        }
        graph.connect(0, 1, 5);   // 0 -> 1 w:5
        graph.connect(1, 2, 10);  // 1 -> 2 w:10
        graph.connect(9, 1, 7);   // 9 -> 1 w:7
        graph.connect(2, 3, 15);  // 2 -> 3 w:15
        graph.connect(3, 4, 20);  // 3 -> 4 w:20
        graph.connect(4, 5, 25);  // 4 -> 5 w:25
        graph.connect(5, 6, 30);  // 5 -> 6 w:30
        graph.connect(6, 7, 35);  // 6 -> 7 w:35
        graph.connect(7, 8, 40);  // 7 -> 8 w:40
        graph.connect(8, 9, 45);  // 8 -> 9 w:45
        graph.connect(5, 8, 2);   // 5 -> 8 w:2
        graph.connect(4, 7, 6);   // 4 -> 3 w:6
        graph.connect(6, 2, 17);  // 6 -> 2 w:17
        graph.connect(2, 8, 13);  // 2 -> 8 w:13
        tester.init(graph);
    }

    @Test
    void getGraph()
    {
    }

    @Test
    void copy()
    {
    }

    @Test
    void isConnected()
    {
    }

    @Test
    void shortestPathDist()
    {
    }

    @Test
    void shortestPath()
    {
    }

    @Test
    public void successfulFileSave()
    {
        String fileName = "C:\\Users\\eliap\\IdeaProjects\\Ex2\\data\\test";
        boolean didItWork = tester.save(fileName);
        assert didItWork;
    }

    @Test
    public void successfulFileLoad()
    {
        String fileName = "C:\\Users\\eliap\\IdeaProjects\\Ex2\\data\\A0";
        directed_weighted_graph oldGraph = tester.copy();
        boolean didItWork = tester.load(fileName);
        if (oldGraph.equals(tester.getGraph()))
            assert didItWork;
        else
            fail();
    }
}