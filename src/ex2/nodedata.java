package ex2;

import api.edge_data;
import api.geo_location;
import api.node_data;

import java.util.HashMap;

public  class nodedata implements node_data {
    private static int CounterForKey;
    private int Key;
    private String Info;
    private int Tag;
    private double Weight;
    private geo_location Location;
    private HashMap<Integer, edge_data> edgeinfo;
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



