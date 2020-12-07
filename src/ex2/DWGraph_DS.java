package ex2;

import api.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class DWGraph_DS implements directed_weighted_graph {
    private HashMap<Integer, node_data> graph = new HashMap<Integer,node_data>();
    private int edgeCounter;
    private int modeCounter;
    @Override
    public node_data getNode(int key) {
        return graph.get(key);
    }

    @Override
    public edge_data getEdge(int src, int dest) {
        HashMap<Integer,edge_data> temp = ((nodedata) graph.get(src)).getNi();
        return temp.get(dest);
    }

    @Override
    public void addNode(node_data n) {
        if(graph.containsKey(n.getKey()))return;
        modeCounter++;
        graph.put(n.getKey(),n);
    }

    @Override
    public void connect(int src, int dest, double w) {
        if (graph.get(src) == null || graph.get(dest) == null) return;
        if (src == dest) return;
        //if(hasEdge(src, dest))return;
        if (graph.containsKey(src) && graph.containsKey(dest)) {
            modeCounter++;
            edgeCounter++;
              ((nodedata) graph.get(src)).addNi(graph.get(dest),w);
        }
    }

    @Override
    public Collection<node_data> getV() {
        return graph.values();
    }

    @Override
    public Collection<edge_data> getE(int node_id) {
        if(!graph.containsKey(node_id))
            return null;
        HashMap<Integer,edge_data> temp = ((nodedata) graph.get(node_id)).getNi();
        return temp.values();
    }

    @Override
    public node_data removeNode(int key) {
        if(graph.get(key)!=null)
            if(graph.containsKey(key)) {
                edgeCounter-=((nodedata) graph.get(key)).getNi().size();
                modeCounter+=((nodedata) graph.get(key)).getNi().size()+1;
                HashMap<Integer,edge_data> temp = ((nodedata) graph.get(key)).getNi();
                temp.forEach((k,v) ->((nodedata) graph.get(k)).removeNode(graph.get(key)));
                ((nodedata) graph.get(key)).getNi().clear();
                return graph.remove(key);
            }
        return null;
    }

    @Override
    public edge_data removeEdge(int src, int dest) {
        if(graph.get(src)!=null&&graph.get(dest)!=null)
            if(graph.containsKey(src)&&graph.containsKey(dest)) {
                edgeCounter--;
                modeCounter++;
                edge_data temp = ((nodedata) graph.get(src)).getNi().remove(dest);
                return temp;
            }
        return null;
    }

    @Override
    public int nodeSize() {
        return graph.size();
    }

    @Override
    public int edgeSize() {
        return edgeCounter;
    }

    @Override
    public int getMC() {
        return modeCounter;
    }

    public static class nodedata implements node_data {
        private static int CounterForKey;
        private int Key;
        private String Info;
        private int Tag;
        private double Weight;
        private geo_location Location;
        private  HashMap<Integer,edge_data> edgeinfo;
        public nodedata() {
            this.Info="empty";
            Key=CounterForKey;
            CounterForKey++;
            Tag=0;
        }
        public nodedata(String Info,int tag) {
            this.Info=Info;
            Key=CounterForKey;
            CounterForKey++;
            this.Tag=tag;
            Location=new geolocation();
        }
        public nodedata(int key,String Info,int tag) {
            this.Info=Info;
            this.Key=key;
            this.Tag=tag;
            Location=new geolocation();

        }
        public nodedata(int key) {
            this.Info="empty";
            this.Key=key;
            this.Tag=0;
            Location=new geolocation();
        }

        public boolean hasNi(int key) {
            return (edgeinfo.containsKey(key) && key != this.getKey());
        }
        public void addNi(node_data t, double w) {
            if(t!=null)
                if(!edgeinfo.containsKey(t.getKey()))
                    if(t.getKey()!=this.Key) {
                         edgedata temp= new edgedata(this.Key,t.getKey(),w);
                        edgeinfo.put(t.getKey(), temp);
                    }
        }
        public HashMap<Integer, edge_data> getNi() {
            return edgeinfo;
        }
        public void removeNode(node_data node) {
            if(node!=null)
                if(edgeinfo.containsKey(node.getKey()))
                    if(node.getKey()!=this.Key) {
                      edgeinfo.remove(node.getKey());
                    }

        }
        @Override
        public int getKey() {
            return Key;
        }

        @Override
        public geo_location getLocation() {
            return Location;
        }

        @Override
        public void setLocation(geo_location p) {
            Location = p;
        }

        @Override
        public double getWeight() {
            return Weight;
        }

        @Override
        public void setWeight(double w) {
            if(w>0)
            Weight = w;
        }

        @Override
        public String getInfo() {
            return Info;
        }

        @Override
        public void setInfo(String s) {
            Info = s;
        }

        @Override
        public int getTag() {
            return Tag;
        }

        @Override
        public void setTag(int t) {
            Tag = t;
        }


    }

    public static class geolocation implements geo_location {
        private double x;
        private double y;
        private double z;
        public geolocation(double x,double y,double z){
            this.x=x;
            this.y=y;
            this.z=z;
        }
        public geolocation(){
            this.x=0;
            this.y=0;
            this.z=0;
        }

        @Override
        public double x() {
            return x;
        }

        @Override
        public double y() {
            return y;
        }

        @Override
        public double z() {
            return z;
        }

        @Override
        public double distance(geo_location g) {
            double dis = Math.pow(x - g.x(), 2) + Math.pow(y - g.y(), 2) + Math.pow(z - g.z(), 2);
            dis = Math.sqrt(dis);
            return dis;
        }
    }

    public static class edgedata implements edge_data {
        private int Src;
        private int Dest;
        private double Weight;
        private String Info;
        private int Tag;

        public edgedata(int src,int dest,double w){
            //todo info,tag setup
            this.Src=src;
            this.Dest=dest;
            this.Weight=w;
            Info="";
            Tag=-1;
        }


        @Override
        public int getSrc() {
            return Src;
        }

        @Override
        public int getDest() {
            return Dest;
        }

        @Override
        public double getWeight() {
            return Weight;
        }

        @Override
        public String getInfo() {
            return Info;
        }

        @Override
        public void setInfo(String s) {
            Info = s;
        }

        @Override
        public int getTag() {
            return Tag;
        }

        @Override
        public void setTag(int t) {
            Tag = t;
        }
    }

    public class edgelocation implements edge_location {
        private  edge_data Data;
        private  double Ratio;
        @Override
        public edge_data getEdge() {
            return Data;
        }

        @Override
        public double getRatio() {
            return Ratio;
        }
    }
}
