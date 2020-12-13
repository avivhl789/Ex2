package ex2;

import api.*;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Objects;

public class DWGraph_DS implements directed_weighted_graph
{
    private HashMap<Integer, node_data> graph = new HashMap<Integer, node_data>();
    private int edgeCounter;
    private int modeCounter;

    public DWGraph_DS()
    {
        nodedata.setCounterForKey(0);
    }

    @Override
    public node_data getNode(int key)
    {
        return graph.get(key);
    }

    @Override
    public edge_data getEdge(int src, int dest)
    {
        HashMap<Integer, edge_data> temp = (((nodedata) graph.get(src)).getNi());
        return temp.get(dest);
    }

    @Override
    public void addNode(node_data n)
    {
        if (graph.containsKey(n.getKey())) return;
        modeCounter++;
        graph.put(n.getKey(), n);
    }

    @Override
    public void connect(int src, int dest, double w)
    {
        if (w < 0) return;
        if (graph.get(src) == null || graph.get(dest) == null) return;
        if (src == dest) return;
        if (graph.containsKey(src) && graph.containsKey(dest))
        {
            if (getEdge(src, dest) == null)
            {
                modeCounter++;
                edgeCounter++;
                ((nodedata) graph.get(src)).addNi(graph.get(dest), w);
            }
            else if (getEdge(src, dest).getWeight() != w)
            {
                modeCounter++;
                ((nodedata) graph.get(src)).addNi(graph.get(dest), w);
            }
        }
    }

    @Override
    public Collection<node_data> getV()
    {
        return graph.values();
    }

    @Override
    public Collection<edge_data> getE(int node_id)
    {
        if (!graph.containsKey(node_id))
            return null;
        HashMap<Integer, edge_data> temp = ((nodedata) graph.get(node_id)).getNi();
        return temp.values();
    }

    @Override
    public node_data removeNode(int key)
    {
        if (graph.get(key) != null)
            if (graph.containsKey(key))
            {
                edgeCounter -= ((nodedata) graph.get(key)).getNi().size();
                modeCounter += ((nodedata) graph.get(key)).getNi().size() + 1;
                HashMap<Integer, edge_data> temp = ((nodedata) graph.get(key)).getNi();
                temp.forEach((k, v) -> ((nodedata) graph.get(k)).removeNode(graph.get(key)));
                ((nodedata) graph.get(key)).getNi().clear();
                return graph.remove(key);
            }
        return null;
    }

    @Override
    public edge_data removeEdge(int src, int dest)
    {
        if (graph.get(src) != null && graph.get(dest) != null)
            if (graph.containsKey(src) && graph.containsKey(dest) && getEdge(src,dest) != null)
            {
                edgeCounter--;
                modeCounter++;
                edge_data temp = ((nodedata) graph.get(src)).getNi().remove(dest);
                return temp;
            }
        return null;
    }

    @Override
    public int nodeSize()
    {
        return graph.size();
    }

    @Override
    public int edgeSize()
    {
        return edgeCounter;
    }

    @Override
    public int getMC()
    {
        return modeCounter;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof DWGraph_DS)) return false;
        DWGraph_DS that = (DWGraph_DS) o;
        if(edgeCounter != that.edgeCounter || this.nodeSize() != that.nodeSize() ) return false ;
        for (node_data node: this.getV())
        {
            nodedata onNode = (nodedata) node;
            if (!onNode.equals(that.getNode(node.getKey())))
                return false;
        }
        return true;
    }
}
