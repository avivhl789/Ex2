package ex2;

import api.*;

import java.util.*;

public class DWGraph_Algo implements dw_graph_algorithms {
    private directed_weighted_graph gr;

    @Override
    public void init(directed_weighted_graph g) {
        this.gr=g;
    }

    @Override
    public directed_weighted_graph getGraph() {
        return gr;
    }

    @Override
    public directed_weighted_graph copy() {
        //todo
        directed_weighted_graph temp=new DWGraph_DS();
        HashMap<Integer,Integer> keymaching = new HashMap <Integer,Integer>();
        for (node_data node :this.gr.getV()) {
            DWGraph_DS.nodedata tempnode=new DWGraph_DS.nodedata("",0);
            temp.addNode(tempnode);
            keymaching.put(node.getKey(), tempnode.getKey());

        }
        for (node_data node :this.gr.getV()) {
            Iterator<edge_data> ite = gr.getE(node.getKey()).iterator();
            edge_data edge;
            while (ite.hasNext()) {
                edge= ite.next();
                temp.connect(keymaching.get(node.getKey()), keymaching.get(edge.getDest()),edge.getWeight());
            }
        }
        return temp;
    }

    @Override
    public boolean isConnected() {
        if(gr.nodeSize()<2)
            return true;
        reset_tag();
        Queue<node_data> queue = new ArrayDeque<>();
        node_data N =this.gr.getV().iterator().next(), ni;
        int count=0;
        queue.add(N);
        while(!queue.isEmpty()) {
            N = queue.remove();
            HashMap<Integer,edge_data> temp = ((DWGraph_DS.nodedata) gr.getNode(N.getKey())).getNi();
            for (Integer keyofnei : temp.keySet()) {
                ni=gr.getNode(keyofnei);
                if(ni.getTag()==-1) {
                    queue.add(ni);
                    ni.setTag(1);
                    count++;
                }
            }
        }
        if(count==gr.nodeSize())
            return true;
        return false;
    }

    @Override
    public double shortestPathDist(int src, int dest) {
        //todo

        return 0;
    }

    @Override
    public List<node_data> shortestPath(int src, int dest) {
        return null;
    }

    @Override
    public boolean save(String file) {
        return false;
    }

    @Override
    public boolean load(String file) {
        return false;
    }
    private void reset_tag() {
        Iterator<node_data> ite = this.gr.getV().iterator();
        node_data node;
        while (ite.hasNext()) {
            node = ite.next();
            node.setTag(-1);
        }
    }
}
