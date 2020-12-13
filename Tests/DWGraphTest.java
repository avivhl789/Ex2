//import ex2.DWGraph_DS;
//import ex2.nodedata;
//import org.junit.jupiter.api.*;
//
//import java.util.ArrayList;
//
//public class DWGraphTest
//{
//    DWGraph_DS graph;
//
//    @BeforeEach
//    void setup()
//    {
//        graph = new DWGraph_DS();
//        nodedata firstNode = new nodedata();
//        nodedata secondNode = new nodedata();
//        nodedata thirdNode = new nodedata();
//        graph.addNode(firstNode);
//        graph.addNode(secondNode);
//        graph.addNode(thirdNode);
//    }
//
//
//
//
//
//
//
//
//
//
//    @Test
//    public void valid_Get_All_Graph_Nodes()
//    {
//        graph.connect(0, 1, 10);
//        graph.connect(0, 2, 20);
//        ArrayList<node_info> actual_Neighbours_Of_Given_Node = (ArrayList<node_info>) (graph.getV(0));
//        ArrayList<node_info> expected_Neighbours_Of_Given_Node = new ArrayList<node_info>();
//        expected_Neighbours_Of_Given_Node.add(graph.getNode(1));
//        expected_Neighbours_Of_Given_Node.add(graph.getNode(2));
//        assert actual_Neighbours_Of_Given_Node.equals(expected_Neighbours_Of_Given_Node);
//    }
//
//    @Test
//    public void invalid_Get_All_Graph_Nodes()
//    {
//        graph.connect(0, 1, 10);
//        graph.connect(0, 2, 20);
//        ArrayList<node_info> actual_Neighbours_Of_Given_Node = (ArrayList<node_info>) (graph.getV(0));
//        ArrayList<node_info> expected_Neighbours_Of_Given_Node = new ArrayList<node_info>();
//        expected_Neighbours_Of_Given_Node.add(graph.getNode(1));
//        assert !actual_Neighbours_Of_Given_Node.equals(expected_Neighbours_Of_Given_Node);
//    }
//
//
//
//    @Test
//    public void remove_Non_Existing_Node()
//    {
//        node_info actualNode = graph.removeNode(10);
//        node_info expectedNode = null;
//        assert actualNode == expectedNode;
//    }
//
//    @Test
//    public void Are_Graphs_Equal()
//    {
//        WGraph_DS secondGraph = new WGraph_DS(graph);
//        System.out.println(graph);
//        secondGraph.connect(1, 2, 10);
//        secondGraph.removeEdge(1, 2);
//        System.out.println(secondGraph);
//        assert secondGraph.equals(graph);
//    }
//
//    @Test
//    public void valid_Specific_Node_Neighbours()
//    {
//        graph.connect(0, 1, 12);
//        graph.connect(1, 2, 10);
//        ArrayList<node_info> actualNeighbours = new ArrayList<>();
//        actualNeighbours = (ArrayList<node_info>) graph.getV(1);
//        ArrayList<node_info> expectedNeighbours = new ArrayList<>();
//        expectedNeighbours.add(graph.getNode(0));
//        expectedNeighbours.add(graph.getNode(2));
//        assert actualNeighbours.size() == expectedNeighbours.size();
//    }
//
//}
