package ex2;

import api.edge_data;

import java.util.Objects;

public class edgedata implements edge_data {
    private int Src;
    private int Dest;
    private double Weight;
    private String Info;
    private int Tag;

    public edgedata(int src,int dest,double w){
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

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof edgedata)) return false;
        edgedata edgedata = (edgedata) o;
        return Src == edgedata.Src && Dest == edgedata.Dest && Double.compare(edgedata.Weight, Weight) == 0 && Tag == edgedata.Tag && Info.equals(edgedata.Info);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(Src, Dest, Weight, Info, Tag);
    }
}