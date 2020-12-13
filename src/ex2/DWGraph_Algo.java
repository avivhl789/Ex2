package ex2;

import api.*;
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
            nodedata tempnode = new nodedata(node.getKey(),node.getInfo(),node.getTag(),node.getWeight(),node.getLocation());
            temp.addNode(tempnode);
        }
        for (node_data node : this.gr.getV()) {
            Iterator<edge_data> ite = gr.getE(node.getKey()).iterator();
            edge_data edge;
            while (ite.hasNext()) {
                edge = ite.next();
                temp.connect(node.getKey(),edge.getDest(),edge.getWeight());
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
        cheack.put(N.getKey(),0);
        while (!queue.isEmpty()) {
            N = queue.remove();
            HashMap<Integer, edge_data> temp = ((nodedata) gr.getNode(N.getKey())).getNi();
            for (Integer keyofnei : temp.keySet()) {
                ni = gr.getNode(keyofnei);
                if (!cheack.containsKey(keyofnei)) {
                    queue.add(ni);
                    cheack.put(keyofnei,0);
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
        if(gr.getNode(src) == null || gr.getNode(dest) == null)
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
                    cost = Math.min(cost,costpath.get(keyofnei));
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
        costpath.put(N.getKey(),0.0);
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
                StringBuilder pos= new StringBuilder();
                pos.append(correctnode.getLocation().x());
                pos.append(",");
                pos.append(correctnode.getLocation().y());
                pos.append(",");
                pos.append(correctnode.getLocation().z());
                obj.put("pos",pos.toString());
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
        JSONTokener buffer; // config json tokenizer so i can convert file to json tokenizer

        try
        {
            FileReader file_;
            try
            {
                file_ = new FileReader(file);
                buffer = new JSONTokener(file_); //converting file to json tokenizer
                JSONObject json_object_temp = new JSONObject(); //config temp json object to manipulate the buffer readings

                json_object_temp.put("graph", buffer.nextValue()); // add buffer tokenizer and graph string as a key
                JSONObject json_object = new JSONObject(); // config an original json object
                json_object=(JSONObject) json_object_temp.get("graph"); // extracting the value that appends graph key and insert into json object and casting

                JSONArray edgesList =new  JSONArray(); // configuring edge list and node list json arrays
                JSONArray nodesList =new  JSONArray();

                edgesList=(JSONArray) json_object.get("Edges"); // inserting values into both arrays using the original jason object
                nodesList=(JSONArray) json_object.get("Nodes");

                directed_weighted_graph g_copy = new DWGraph_DS(); // constructing new graph DWGraph_DS

                for(int i=0;i<nodesList.length();i++) // going over nodes list creating the nodes inserting positions and adding later to new graph
                {
                    node_data N=new nodedata(nodesList.getJSONObject(i).getInt("id"));
                    geo_location p=new geolocation(nodesList.getJSONObject(i).getString("pos"));
                    N.setLocation(p);
                    g_copy.addNode(N);
                }
                for(int i=0;i<edgesList.length();i++) // going over edge list and connecting existing nodes to together
                {
                    g_copy.connect(edgesList.getJSONObject(i).getInt("src"), edgesList.getJSONObject(i).getInt("dest"), edgesList.getJSONObject(i).getDouble("w"));

                }

                init(g_copy); // init graph to be the new graph
                file_.close(); //closing file
            }
            catch (FileNotFoundException e2)
            {
                e2.printStackTrace();
                return false;
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return false;
        }
        return true;
//        try (FileReader reader = new FileReader(file)) {
//            String data = Files.readString(Path.of(file));
//            System.out.println(data);
//            ArrayList<Double> posx = new ArrayList<Double>();
//            ArrayList<Double> posy = new ArrayList<Double>();
//            ArrayList<Double> posz = new ArrayList<Double>();
//            ArrayList<Integer> keys = new ArrayList<Integer>();
//            DWGraph_DS temp = new DWGraph_DS();
//            if (data != null)
//            {
//               int last=data.indexOf("pos")+5;
//               int next=data.indexOf(",",last);
//                   while(next!=-1&&last!=-1) {
//                       posx.add(Double.parseDouble(data.substring(last+1, next)));
//                       last=next;
//                       next=data.indexOf(",",last+1);
//                       posy.add(Double.parseDouble(data.substring(last+1, next)));
//                       last=next;
//                       next=data.indexOf(",",last+1);
//                       posz.add(Double.parseDouble(data.substring(last+1, next)));
//                       last=data.indexOf("id",next)+4;
//                       next=data.indexOf("}",last);
//                       keys.add(Integer.parseInt(data.substring(last+1,next)));
//                       last=data.indexOf("pos",next)+5;
//                       next=data.indexOf(",",last);
//                   }
//                for (int i = 0; i < keys.size(); i++)
//                {
//                    node_data newNode = new nodedata(keys.get(i));
//                    geolocation location = new geolocation(posx.get(i), posy.get(i), posz.get(i));
//                    newNode.setLocation(location);
//                    temp.addNode(newNode);
//                }
//                   last=data.indexOf("src")+5;
//                   next=data.indexOf(",",last);
//                   int src;
//                   int dest;
//                   double w;
//                   while(next!=-1&&last!=-1) {
//                   src=Integer.parseInt(data.substring(last+1,next));
//                       last=data.indexOf("w")+2;
//                       next=data.indexOf(",",last);
//                   w=Double.parseDouble(data.substring(last+1, next));
//                       last=data.indexOf("w")+2;
//                       next=data.indexOf("}",last);
//                    dest=Integer.parseInt(data.substring(last+1,next));
//                      temp.connect(src,dest,w);
//                   }
//               }
//            gr = temp; //TODO will this work?
//            return true;
//        } catch (Exception e) {
//            System.out.println(e);
//            return false;
//        }
    }

    private void reset_tag() {
        Iterator<node_data> ite = this.gr.getV().iterator();
        node_data node;
        while (ite.hasNext()) {
            node = ite.next();
            node.setTag(-1);
        }
    }


    @Override
    public boolean equals(Object o) {
        if ( o.getClass() != this.getClass())
            return false;
        return ((DWGraph_DS)gr).equals(o);
    }


    public static void main(String[]args)
    {
        DWGraph_Algo algo=new DWGraph_Algo();
        DWGraph_DS graph=new DWGraph_DS();
        algo.init(graph);
        boolean successfulLoad=algo.load("C:\\Users\\eliap\\IdeaProjects\\Ex2\\data\\A0");
        System.out.println(successfulLoad);
    }


}
