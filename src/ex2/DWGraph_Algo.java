package ex2;

import api.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.json.JSONStringer;




import java.io.FileReader;
import java.io.FileWriter;
import java.util.*;

public class DWGraph_Algo implements dw_graph_algorithms {
    private directed_weighted_graph gr;

    @Override
    public void init(directed_weighted_graph g) {
        this.gr = g;
    }

    @Override
    public directed_weighted_graph getGraph() {
        return gr;
    }

    @Override
    public directed_weighted_graph copy() {
        directed_weighted_graph temp = new DWGraph_DS();
        HashMap<Integer, Integer> keymaching = new HashMap<Integer, Integer>();
        for (node_data node : this.gr.getV()) {
            nodedata tempnode = new nodedata("", 0);
            temp.addNode(tempnode);
            keymaching.put(node.getKey(), tempnode.getKey());

        }
        for (node_data node : this.gr.getV()) {
            Iterator<edge_data> ite = gr.getE(node.getKey()).iterator();
            edge_data edge;
            while (ite.hasNext()) {
                edge = ite.next();
                temp.connect(keymaching.get(node.getKey()), keymaching.get(edge.getDest()), edge.getWeight());
            }
        }
        return temp;
    }

    @Override
    public boolean isConnected() {
        if (gr.nodeSize() < 2)
            return true;
        reset_tag();
        Queue<node_data> queue = new ArrayDeque<>();
        node_data N = this.gr.getV().iterator().next(), ni;
        int count = 0;
        queue.add(N);
        while (!queue.isEmpty()) {
            N = queue.remove();
            HashMap<Integer, edge_data> temp = ((nodedata) gr.getNode(N.getKey())).getNi();
            for (Integer keyofnei : temp.keySet()) {
                ni = gr.getNode(keyofnei);
                if (ni.getTag() == -1) {
                    queue.add(ni);
                    ni.setTag(1);
                    count++;
                }
            }
        }
        if (count == gr.nodeSize())
            return true;
        return false;
    }

    @Override
    public double shortestPathDist(int src, int dest) {
        if (gr.nodeSize() < 2)
            return -1;
        if (gr.nodeSize() == 2) {
            if (gr.getEdge(src, dest) != null)
                return gr.getEdge(src, dest).getWeight();
        }
        HashMap<Integer, Double> costpath = new HashMap<Integer, Double>();
        PriorityQueue<node_data> queue = new PriorityQueue<node_data>((Comparator<node_data>) new Comparator<node_data>() {
            @Override
            public int compare(node_data node1, node_data node2) {
                if (costpath.get(node1.getKey()) < +costpath.get(node2.getKey()))
                    return -1;
                else if (costpath.get(node1.getKey()) > costpath.get(node2.getKey()))
                    return 1;
                return 0;
            }
        }
        );
        node_data N = this.gr.getNode(src);
        node_data ni;
        queue.add(N);
        double cost = Double.MAX_VALUE;
        while (!queue.isEmpty()) {
            N = queue.remove();
            HashMap<Integer, edge_data> temp = ((nodedata) gr.getNode(N.getKey())).getNi();
            for (Integer keyofnei : temp.keySet()) {
                ni = gr.getNode(keyofnei);
                if (costpath.containsKey(ni.getKey())) {
                    costpath.put(ni.getKey(), costpath.get(N.getKey()) + temp.get(keyofnei).getWeight());
                    ni.setTag(1);
                    queue.add(ni);
                } else if (costpath.get(ni.getKey()) > costpath.get(N.getKey()) + temp.get(keyofnei).getWeight()) {
                    costpath.put(ni.getKey(), costpath.get(N.getKey()) + temp.get(keyofnei).getWeight());
                    queue.add(ni);
                }
                if (ni.getKey() == dest)
                    cost = Math.min(cost, ni.getTag());
            }
        }
        if (cost != Double.MAX_VALUE)
            return cost;
        return -1;
    }

    @Override
    public List<node_data> shortestPath(int src, int dest) {
        if (gr.nodeSize() < 2)
            return null;
        if (gr.nodeSize() == 2) {
            if (gr.getEdge(src, dest) == null)
                return null;
            List<node_data> path = new ArrayList<>();
            path.add(gr.getNode(src));
            path.add(gr.getNode(dest));
            return path;
        }
        HashMap<Integer, Double> costpath = new HashMap<Integer, Double>();
        HashMap<Integer, Integer> mappath = new HashMap<Integer, Integer>();
        PriorityQueue<node_data> queue = new PriorityQueue<node_data>((Comparator<node_data>) new Comparator<node_data>() {
            @Override
            public int compare(node_data node1, node_data node2) {
                if (costpath.get(node1.getKey()) < +costpath.get(node2.getKey()))
                    return -1;
                else if (costpath.get(node1.getKey()) > costpath.get(node2.getKey()))
                    return 1;
                return 0;
            }
        }
        );
        node_data N = this.gr.getNode(src);
        node_data ni;
        queue.add(N);
        boolean flag = false;
        while (!queue.isEmpty()) {
            N = queue.remove();
            HashMap<Integer, edge_data> temp = ((nodedata) gr.getNode(N.getKey())).getNi();
            for (Integer keyofnei : temp.keySet()) {
                ni = gr.getNode(keyofnei);
                if (costpath.containsKey(ni.getKey())) {
                    costpath.put(ni.getKey(), costpath.get(N.getKey()) + temp.get(keyofnei).getWeight());
                    ni.setTag(1);
                    queue.add(ni);
                    mappath.put(ni.getKey(), N.getKey());
                } else if (costpath.get(ni.getKey()) > costpath.get(N.getKey()) + temp.get(keyofnei).getWeight()) {
                    costpath.put(ni.getKey(), costpath.get(N.getKey()) + temp.get(keyofnei).getWeight());
                    queue.add(ni);
                    mappath.put(ni.getKey(), N.getKey());
                }
                if (ni.getKey() == dest)
                    flag = true;
            }
        }
        if (flag)
            return shortestPath(src, gr.getNode(dest), mappath);
        return null;
    }

    private List<node_data> shortestPath(int src, node_data nei, HashMap<Integer, Integer> mappath) {
        List<node_data> path2rev = new ArrayList<node_data>();
        node_data N = nei;
        while (N != gr.getNode(src)) {
            path2rev.add(N);
            N = gr.getNode(mappath.get(N.getKey()));
        }
        path2rev.add(gr.getNode(src));
        // reverse order to top to bottom:
        List<node_data> path2send = new ArrayList<node_data>();
        for (int i = path2rev.size() - 1; i >= 0; i--)
            path2send.add(path2rev.get(i));
        return path2send;
    }

    @Override
    public boolean save(String file) {
        if ((gr.nodeSize() == 0) || (gr == null))
            return false;
        JSONArray nodesdata = new JSONArray();
        JSONArray egdedata = new JSONArray();
        Iterator<node_data> temp = this.gr.getV().iterator();
        while (temp.hasNext()) {
            node_data correctnode = temp.next();
            JSONObject obj = new JSONObject();
            try {
                obj.put("pos", correctnode.getLocation());
                obj.put("id", correctnode.getKey());
                nodesdata.put(obj);
                obj = new JSONObject();
                HashMap<Integer, edge_data> egdelist = ((nodedata) gr.getNode(correctnode.getKey())).getNi();
                for (Integer keyofnei : egdelist.keySet()) {
                    obj.put("src", correctnode.getKey());
                    obj.put("w", egdelist.get(keyofnei));
                    obj.put("dest", (keyofnei));
                    egdedata.put(obj);
                }

            } catch (JSONException e) {
                e.printStackTrace();
                return false;
            }
        }
        JSONObject alldata = new JSONObject();
        try {
            alldata.put("Edges", egdedata);
            alldata.put("Nodes", nodesdata);
        } catch (JSONException e) {
            e.printStackTrace();
            return false;

        }

        try (FileWriter fw = new FileWriter(file)) {
            fw.write(alldata.toString());
            System.out.println("Successfully Copied JSON Object to File...");
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;

        }
    }

    @Override
    public boolean load(String file) {
        try (FileReader reader = new FileReader(file)) {
            JSONArray data = new JSONArray(reader);
            System.out.println(data);
            System.out.println(data.toString());
//            data.get();
           /*if(filein.hasNextLine()) {
               line=filein.nextLine();
               int last=line.indexOf("pos")+2;
               int next=line.indexOf(",",last);
               while (filein.hasNextLine()) {
                   while(next!=-1&&last!=-1) {
                       posx.add(Double.parseDouble(line.substring(last+1, next)));
                       last=next;
                       next=line.indexOf(",",last+1);
                       posy.add(Double.parseDouble(line.substring(last+1, next)));
                       last=next;
                       next=line.indexOf(",",last+1);
                       posz.add(Double.parseDouble(line.substring(last+1, next)));
                       last=line.indexOf("id",next)+2;
                       next=line.indexOf("}",last);
                       keys.add(Integer.parseInt(line.substring(last+1,next)));
                       last=line.indexOf("pos",next)+2;
                       next=line.indexOf(",",last);
                   }
                   last=line.indexOf("src")+2;
                   next=line.indexOf(",",last);
                   int src;
                   int dest;
                   double w;
                   while(next!=-1&&last!=-1) {
                   src=Integer.parseInt(line.substring(last+1,next));
                   w=Double.parseDouble(line.substring(last+1, next));
                    dest=Integer.parseInt(line.substring(last+1,next));
                       temp.cocncet(
                   }

                   */


            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    private void reset_tag() {
        Iterator<node_data> ite = this.gr.getV().iterator();
        node_data node;
        while (ite.hasNext()) {
            node = ite.next();
            node.setTag(-1);
        }
    }
    public static void main(String[]args)
    {
        DWGraph_Algo algo=new DWGraph_Algo();
        DWGraph_DS graph=new DWGraph_DS();
        algo.init(graph);
        boolean successfulLoad=algo.load("C:\\A0");
        System.out.println(successfulLoad);
    }

}
