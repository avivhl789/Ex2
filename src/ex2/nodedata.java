package ex2;

import api.edge_data;
import api.geo_location;
import api.node_data;

import java.util.ArrayList;
import java.util.HashMap;

public  class nodedata implements node_data {
    private static int CounterForKey;
    private int Key;
    private String Info;
    private int Tag;
    private double Weight;
    private geo_location Location;
    private HashMap<Integer, edge_data> inedgeinfo;
    private HashMap<Integer, edge_data> outedgeinfo;

    public nodedata() {
        this.Info="empty";
        Key=CounterForKey;
        CounterForKey++;
        Tag=0;
        inedgeinfo = new HashMap<>();
        Location = new geolocation();

    }
    public nodedata(int key, String Info,int Tag, double Weight, geo_location Location) {
        this.Key=key;
        this.Info=Info;
        this.Tag=Tag;
        this.Weight = Weight;
        this.Location=Location;
        inedgeinfo = new HashMap<>();
        outedgeinfo = new HashMap<>();
    }
    public nodedata(int key,String Info,int tag) {
        this.Info=Info;
        this.Key=key;
        this.Tag=tag;
        Location=new geolocation();
        inedgeinfo = new HashMap<>();
        outedgeinfo = new HashMap<>();

    }
    public nodedata(int key) {
        this.Info="empty";
        this.Key=key;
        this.Tag=0;
        Location=new geolocation();
        inedgeinfo = new HashMap<>();
        outedgeinfo = new HashMap<>();
    }

    public static int getCounterForKey()
    {
        return CounterForKey;
    }

    public static void setCounterForKey(int counterForKey)
    {
        CounterForKey = counterForKey;
    }

    public boolean hasNi(int key) {
        return (inedgeinfo.containsKey(key) && key != this.getKey());
    }
    public void addNi(node_data t, double w) {
        if(t!=null)
            if(!inedgeinfo.containsKey(t.getKey()) || inedgeinfo.get(t.getKey()).getWeight() != w)
                if(t.getKey()!=this.Key) {
                    edgedata temp= new edgedata(this.Key,t.getKey(),w);
                    inedgeinfo.put(t.getKey(), temp);
                }
    }
    public void addin(node_data t, double w) {
        if(t!=null)
            if(!outedgeinfo.containsKey(t.getKey()) || outedgeinfo.get(t.getKey()).getWeight() != w)
                if(t.getKey()!=this.Key) {
                    edgedata temp= new edgedata(this.Key,t.getKey(),w);
                    outedgeinfo.put(t.getKey(), temp);
                }
    }
    public HashMap<Integer, edge_data> getNi() {
        return outedgeinfo;
    }

    public HashMap<Integer, edge_data> getinedgeinfo() {
        return inedgeinfo;
    }

    public void removeNode(node_data node) {
        if(node!=null)
            if(outedgeinfo.containsKey(node.getKey()))
                if(node.getKey()!=this.Key) {
                    outedgeinfo.remove(node.getKey());
                }

    }
    public void removeinNode(node_data node) {
        if(node!=null)
            if(inedgeinfo.containsKey(node.getKey()))
                if(node.getKey()!=this.Key) {
                    inedgeinfo.remove(node.getKey());
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

    @Override
    public boolean equals(Object o) {
        nodedata compNode = (nodedata) o;
        if ( compNode.getClass() != this.getClass()) {
            return false;
        }
        if(compNode.getKey() != this.getKey())
            return false;

        if ( compNode.getInfo() != this.getInfo()) {
            return  false;

        }
        if ( compNode.getTag() != this.getTag()) {
            return  false;

        }
        for (Integer keyofedge: inedgeinfo.keySet())
        {
            edgedata currEdgeData = (edgedata) inedgeinfo.get(keyofedge);
            edgedata compEdgeData = ((edgedata)(compNode.getNi()).get(keyofedge));
            if(!currEdgeData.equals(compEdgeData))
                return false;
        }
        return true;
    }

}



