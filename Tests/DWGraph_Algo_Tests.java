import api.directed_weighted_graph;
import api.node_data;
import ex2.DWGraph_Algo;
import ex2.DWGraph_DS;
import ex2.nodedata;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class DWGraph_Algo_Tests
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
        graph.connect(6, 7, 35);  // 6 -> 7 w:35 s
        graph.connect(7, 8, 40);  // 7 -> 8 w:40
        graph.connect(8, 9, 45);  // 8 -> 9 w:45
        graph.connect(5, 8, 2);   // 5 -> 8 w:2 s
        graph.connect(4, 7, 6);   // 4 -> 7 w:6 s
        graph.connect(6, 2, 17);  // 6 -> 2 w:17
        graph.connect(2, 8, 13);  // 2 -> 8 w:13 s
        tester.init(graph);
    }

    @Test
    public void successfulFileSave()
    {
        String fileName = "C:\\Users\\eliap\\IdeaProjects\\Ariel_OOP_2020\\Assignments\\Ex2\\src\\data\\test";
        boolean didItWork = tester.save(fileName);
        assert didItWork;
    }

    @Test
    public void successfulFileLoad()
    {
        String fileName = "C:\\Users\\eliap\\IdeaProjects\\Ariel_OOP_2020\\Assignments\\Ex2\\src\\data\\test";
        DWGraph_DS oldGraph = (DWGraph_DS) tester.copy();
        boolean didItWork = tester.load(fileName);
        if (oldGraph.equals(tester.getGraph()))
            assertTrue(didItWork);
        else
            fail();
    }

    @Test
    public void is_Saved_Graph_Identical_To_Loaded_Graph()
    {
        String fileName = "C:\\Users\\eliap\\IdeaProjects\\Ariel_OOP_2020\\Assignments\\Ex2\\src\\data\\test";
        tester.load(fileName);
        DWGraph_DS loadedGraph = (DWGraph_DS) tester.getGraph();
        assert loadedGraph.equals(graph) : "loaded graph is " + loadedGraph.toString() +
                " and " + "graph is " + graph.toString();
    }

    @Test
    public void proper_Deep_Copy()
    {
        directed_weighted_graph copyOfOrgGraph = tester.copy();
        copyOfOrgGraph.removeNode(0);
        assert !copyOfOrgGraph.equals(this) : "copy of graph contains: " + copyOfOrgGraph.getV() + ", original graph " +
                "contains: " + this.graph.getV();
    }

    @Test
    public void getGraph()
    {
        DWGraph_DS secondGraph = new DWGraph_DS();
        secondGraph.connect(0, 1, 5);
        secondGraph.connect(1, 2, 10);
        secondGraph.connect(2, 3, 15);
        tester.init(secondGraph);
        assert tester.getGraph() == secondGraph;
    }


    @Test
    public void valid_Is_Connected()
    {
        boolean actualAns = tester.isConnected();
        assertTrue(actualAns);
    }

    @Test
    public void invalid_Is_Connected()
    {
        graph.removeEdge(0, 1);
        boolean expectedAns = false;
        boolean actualAns = tester.isConnected();
        assert actualAns == expectedAns;
    }


    @Test
    public void valid_Shortest_Path_Distance()
    {
        double expectedAns = 15;
        double actualAns = tester.shortestPathDist(0, 2);
        assertEquals(expectedAns,actualAns);
    }

    @Test
    public void invalid_Shortest_Path_Distance_Non_Existing_Node()
    {
        double expectedAns = -1;
        double actualAns = tester.shortestPathDist(0, 50);
        assert actualAns == expectedAns;
    }

    @Test
    public void invalid_Shortest_Path_Distance_Disconnected_Graph()
    {
        graph.removeEdge(0, 1);
        double expectedAns = -1;
        double actualAns = tester.shortestPathDist(1, 0);
        assertEquals(expectedAns,actualAns);
    }

    @Test
    public void invalid_Shortest_Path_Distance_Two_Non_Existing_Nodes()
    {
        double expectedAns = -1;
        double actualAns = tester.shortestPathDist(100, 50);
        assert actualAns == expectedAns;
    }

    @Test
    public void valid_Shortest_Path()
    {
        ArrayList<node_data> expectedList = new ArrayList<node_data>();
        expectedList.add(graph.getNode(0));
        expectedList.add(graph.getNode(1));
        expectedList.add(graph.getNode(2));
        expectedList.add(graph.getNode(3));
        expectedList.add(graph.getNode(4));
        expectedList.add(graph.getNode(7));
        ArrayList<node_data> actualList = (ArrayList<node_data>) (tester.shortestPath(0, 7));
        assert actualList.equals(expectedList);
    }

    @Test
    public void invalid_Shortest_Path_Disconnected_Graph()
    {
        graph.removeEdge(0, 1);
        ArrayList<node_data> actualList = (ArrayList<node_data>) (tester.shortestPath(0, 7));
        assertNull(actualList);
    }

    @Test
    public void invalid_Shortest_Path_One_Non_Existing_Node()
    {
        ArrayList<node_data> actualList = (ArrayList<node_data>) (tester.shortestPath(0, 500));
        assertNull(actualList);
    }

    @Test
    public void invalid_Shortest_Path_Two_Non_Existing_Node()
    {
        ArrayList<node_data> actualList = (ArrayList<node_data>) (tester.shortestPath(10000, 500));
        assertNull(actualList);
    }

//    @Test
    // Using your code to create that graph.
//    public void are_Million_Vertices_Connected_Properly()
//    {
//        directed_weighted_graph hugeGraph = graphCreator(1000000, 10000000, 1);
//        tester.init(hugeGraph);
//        boolean actualAns = tester.isConnected();
//        assert actualAns;
//    }


    @Test
    // My test to create the graph only using your random function.
    public void are_Million_Vertices_Connected_Properly_V2()
    {
        int numOfNodes = 1_000_000;
        int numOfEdges = 10_000_000;
        int numOfConnectedEdges = 0;
        _rnd = new Random(1);
        directed_weighted_graph hugeGraph = new DWGraph_DS();
        tester.init(hugeGraph);
        node_data newNode = new nodedata(0);
        hugeGraph.addNode(newNode);
        for (int i = 1; i < numOfNodes; i++)
        {
            newNode = new nodedata(i);
            hugeGraph.addNode(newNode);
            hugeGraph.connect(i - 1, i, nextRnd(0, numOfNodes));
            numOfConnectedEdges++;
        }
        while (hugeGraph.edgeSize() < numOfEdges - numOfConnectedEdges)
        {
            int node1 = nextRnd(0, numOfNodes);
            int node2 = nextRnd(0, numOfNodes);
            hugeGraph.connect(node1, node2, nextRnd(0, numOfNodes));
        }
        boolean actualAns = tester.isConnected();
        assert actualAns;
    }

    private static Random _rnd = null;

    public static directed_weighted_graph graphCreator(int v_size, int e_size, int seed)
    {
        directed_weighted_graph g = new DWGraph_DS();
        _rnd = new Random(seed);
        for (int i = 0; i < v_size; i++)
        {
            nodedata newNode = new nodedata(i);
            g.addNode(newNode);
        }
        // Iterator<node_data> itr = V.iterator(); // Iterator is a more elegant and generic way, but KIS is more important
        while (g.edgeSize() < e_size)
        {
            int node1 = nextRnd(0, v_size);
            int node2 = nextRnd(0, v_size);
            if (node1 != node2)
                g.connect(node1, node2, nextRnd(0, v_size));
        }
        return g;
    }


    private static int nextRnd(int min, int max)
    {
        double v = nextRnd(0.0 + min, (double) max);
        int ans = (int) v;
        return ans;
    }

    private static double nextRnd(double min, double max)
    {
        double d = _rnd.nextDouble();
        double dx = max - min;
        double ans = d * dx + min;
        return ans;
    }

}
