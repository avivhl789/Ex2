package ex2;

import api.*;
import gameClient.CL_Agent;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.json.JSONStringer;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
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
        for (node_data node : this.gr.getV()) {
            nodedata tempnode = new nodedata(node.getKey(), node.getInfo(), node.getTag(), node.getWeight(), node.getLocation());
            temp.addNode(tempnode);
        }
        for (node_data node : this.gr.getV()) {
            Iterator<edge_data> ite = gr.getE(node.getKey()).iterator();
            edge_data edge;
            while (ite.hasNext()) {
                edge = ite.next();
                temp.connect(node.getKey(), edge.getDest(), edge.getWeight());
            }
        }
        return temp;
    }

    @Override
    public boolean isConnected() {
        if (gr.nodeSize() < 2)
            return true;
        Queue<node_data> queue = new ArrayDeque<>();
        HashMap<Integer, Integer> cheack = new HashMap<Integer, Integer>();
        node_data N = this.gr.getV().iterator().next(), ni;
        int count = 1;
        queue.add(N);
        cheack.put(N.getKey(), 0);
        while (!queue.isEmpty()) {
            N = queue.remove();
            HashMap<Integer, edge_data> temp = ((nodedata) gr.getNode(N.getKey())).getNi();
            for (Integer keyofnei : temp.keySet()) {
                ni = gr.getNode(keyofnei);
                if (!cheack.containsKey(keyofnei)) {
                    queue.add(ni);
                    cheack.put(keyofnei, 0);
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
        if (gr.getNode(src) == null || gr.getNode(dest) == null)
            return -1;
        if (src == dest)
            return 0;
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
        costpath.put(N.getKey(), 0.0);
        queue.add(N);
        double cost = Double.MAX_VALUE;
        while (!queue.isEmpty()) {
            N = queue.remove();
            HashMap<Integer, edge_data> temp = ((nodedata) gr.getNode(N.getKey())).getNi();
            for (Integer keyofnei : temp.keySet()) {
                node_data ni = gr.getNode(keyofnei);
                if (!costpath.containsKey(ni.getKey())) {
                    costpath.put(keyofnei, costpath.get(N.getKey()) + temp.get(keyofnei).getWeight());
                    queue.add(ni);
                } else if (costpath.get(ni.getKey()) > costpath.get(N.getKey()) + temp.get(keyofnei).getWeight()) {
                    costpath.put(keyofnei, costpath.get(N.getKey()) + temp.get(keyofnei).getWeight());
                    queue.add(ni);
                }
                if (keyofnei == dest)
                    cost = Math.min(cost, costpath.get(keyofnei));
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
        if (gr.getNode(src) == null || gr.getNode(dest) == null)
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
        costpath.put(N.getKey(), 0.0);
        queue.add(N);
        boolean flag = false;
        while (!queue.isEmpty()) {
            N = queue.remove();
            HashMap<Integer, edge_data> temp = ((nodedata) gr.getNode(N.getKey())).getNi();
            for (Integer keyofnei : temp.keySet()) {
                ni = gr.getNode(keyofnei);
                if (!costpath.containsKey(ni.getKey())) {
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
                StringBuilder pos = new StringBuilder();
                pos.append(correctnode.getLocation().x());
                pos.append(",");
                pos.append(correctnode.getLocation().y());
                pos.append(",");
                pos.append(correctnode.getLocation().z());
                obj.put("pos", pos.toString());
                obj.put("id", correctnode.getKey());
                nodesdata.put(obj);
                obj = new JSONObject();
                HashMap<Integer, edge_data> egdelist = ((nodedata) gr.getNode(correctnode.getKey())).getNi();
                for (Integer keyofnei : egdelist.keySet()) {
                    obj.put("src", correctnode.getKey());
                    obj.put("w", egdelist.get(keyofnei).getWeight());
                    obj.put("dest", (keyofnei));
                    egdedata.put(obj);
                    obj = new JSONObject();
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
        try {
            try {
                FileReader readenfile;
                readenfile = new FileReader(file);
                JSONTokener buff = new JSONTokener(readenfile);
                JSONObject holder = new JSONObject();

                holder.put("Graph", buff.nextValue());
                JSONObject json_object = (JSONObject) holder.get("Graph");

                JSONArray EdgesList = (JSONArray) json_object.get("Edges");
                JSONArray NodesList = (JSONArray) json_object.get("Nodes");

                DWGraph_DS temp = new DWGraph_DS();

                for (int i = 0; i < NodesList.length(); i++) {
                    int key = NodesList.getJSONObject(i).getInt("id");
                    node_data N = new nodedata(key);
                    geo_location p = new geolocation(NodesList.getJSONObject(i).getString("pos"));
                    N.setLocation(p);
                    temp.addNode(N);
                }
                for (int i = 0; i < EdgesList.length(); i++) {
                    int src = EdgesList.getJSONObject(i).getInt("src");
                    int dest = EdgesList.getJSONObject(i).getInt("dest");
                    double w = EdgesList.getJSONObject(i).getDouble("w");
                    temp.connect(src, dest, w);
                }
                readenfile.close();
                init(temp);
                return true;
            } catch (FileNotFoundException eoffile) {
                eoffile.printStackTrace();
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    public boolean loadfromstirng(String js) {
        try {
            JSONObject holder = new JSONObject();
            JSONTokener buff = new JSONTokener(js);
            holder.put("Graph", buff.nextValue());
            JSONObject json_object = (JSONObject) holder.get("Graph");

            JSONArray EdgesList = (JSONArray) json_object.get("Edges");
            JSONArray NodesList = (JSONArray) json_object.get("Nodes");

            DWGraph_DS temp = new DWGraph_DS();

            for (int i = 0; i < NodesList.length(); i++) {
                int key = NodesList.getJSONObject(i).getInt("id");
                node_data N = new nodedata(key);
                geo_location p = new geolocation(NodesList.getJSONObject(i).getString("pos"));
                N.setLocation(p);
                temp.addNode(N);
            }
            for (int i = 0; i < EdgesList.length(); i++) {
                int src = EdgesList.getJSONObject(i).getInt("src");
                int dest = EdgesList.getJSONObject(i).getInt("dest");
                double w = EdgesList.getJSONObject(i).getDouble("w");
                temp.connect(src, dest, w);
            }
            init(temp);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }


    @Override
    public boolean equals(Object o) {
        if (o.getClass() != this.getClass())
            return false;
        return ((DWGraph_DS) gr).equals(o);
    }


    public CL_Agent.PathHelper clientShortestPath(int src, int dest) {
        if (gr.nodeSize() < 2)
            return null;
        if (gr.getNode(src) == null || gr.getNode(dest) == null)
            return null;
        if (src == dest) {
            List<node_data> path = new ArrayList<>();
            double weight = 0.0;
            CL_Agent.PathHelper name = new CL_Agent.PathHelper(weight, path);
            return name;
        }
        if (gr.nodeSize() == 2) {
            if (gr.getEdge(src, dest) == null)
                return null;
            List<node_data> path = new ArrayList<>();
            path.add(gr.getNode(src));
            double weight = gr.getEdge(src, dest).getWeight();
            CL_Agent.PathHelper name = new CL_Agent.PathHelper(weight, path);
            return name;
        }
        HashMap<Integer, Double> costpath = new HashMap<Integer, Double>();
        HashMap<Integer, Integer> mappath = new HashMap<Integer, Integer>();
        double weight;
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
        costpath.put(N.getKey(), 0.0);
        queue.add(N);
        boolean flag = false;
        while (!queue.isEmpty()) {
            N = queue.remove();
            HashMap<Integer, edge_data> temp = ((nodedata) gr.getNode(N.getKey())).getNi();
            for (Integer keyofnei : temp.keySet()) {
                ni = gr.getNode(keyofnei);
                if (!costpath.containsKey(ni.getKey())) {
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
        if (flag) {
            List<node_data> path = shortestPath(src, gr.getNode(dest), mappath);
            weight = costpath.get(dest);
            CL_Agent.PathHelper name = new CL_Agent.PathHelper(weight, path);
            return name;
        }
        return null;
    }


}
