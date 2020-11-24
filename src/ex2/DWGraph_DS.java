package ex2;

import api.*;

import java.util.Collection;
import java.util.HashMap;

public class DWGraph_DS implements directed_weighted_graph {
    @Override
    public node_data getNode(int key) {
        return null;
    }

    @Override
    public edge_data getEdge(int src, int dest) {
        return null;
    }

    @Override
    public void addNode(node_data n) {

    }

    @Override
    public void connect(int src, int dest, double w) {

    }

    @Override
    public Collection<node_data> getV() {
        return null;
    }

    @Override
    public Collection<edge_data> getE(int node_id) {
        return null;
    }

    @Override
    public node_data removeNode(int key) {
        return null;
    }

    @Override
    public edge_data removeEdge(int src, int dest) {
        return null;
    }

    @Override
    public int nodeSize() {
        return 0;
    }

    @Override
    public int edgeSize() {
        return 0;
    }

    @Override
    public int getMC() {
        return 0;
    }

    public static class nodedata implements node_data {
        private static int CounterForKey;
        private int Key;
        private String Info;
        private int Tag;
        private double Weight;
        private geo_location Location;
        private HashMap<Integer, Double> weighted_neighbors = new HashMap<Integer, Double>();

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

    public class geolocation implements geo_location {
        private double x;
        private double y;
        private double z;

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
            return x;
        }

        @Override
        public double distance(geo_location g) {
            double dis = Math.pow(x - g.x(), 2) + Math.pow(y - g.y(), 2) + Math.pow(z - g.z(), 2);
            dis = Math.pow(dis, 0.5);
            return dis;
        }
    }

    public class edgedata implements edge_data {
        private int Src;
        private int Dest;
        private double Weight;
        private String Info;
        private int Tag;

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
