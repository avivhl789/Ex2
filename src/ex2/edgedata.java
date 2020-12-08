package ex2;

import api.edge_data;

public class edgedata implements edge_data {
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