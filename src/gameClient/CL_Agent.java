package gameClient;

import api.directed_weighted_graph;
import api.edge_data;
import api.geo_location;
import api.node_data;
import ex2.DWGraph_Algo;
import ex2.nodedata;
import gameClient.util.Point3D;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class CL_Agent {
    public static final double EPS = 0.0001;
    private static int _count = 0;
    private static int _seed = 3331;
    private int _id;
    //	private long _key;
    private geo_location _pos;
    private double _speed;
    private edge_data _curr_edge;
    private node_data _curr_node;
    private directed_weighted_graph _gg;
    private CL_Pokemon _curr_fruit;
    private long _sg_dt;
    private PathHelper help;
    private PriorityQueue<PathHelper> pathCompare;
    private ImageIcon agimg = new ImageIcon("images\\imgforag.jpg");
    Image scaledImg= agimg.getImage().getScaledInstance(70, 70, Image.SCALE_DEFAULT);
    private ImageIcon scaledagimg=new ImageIcon(scaledImg);
    private List<node_data>  bastpath;


    {
        new PriorityQueue<>((Comparator<PathHelper>) new Comparator<PathHelper>() {
            @Override
            public int compare(PathHelper help1, PathHelper help2) {
                if (help1.getTotalCost() < help2.getTotalCost())
                    return -1;
                else if (help1.getTotalCost() > help2.getTotalCost())
                    return 1;
                return 0;
            }
        }
        );
    }


    private double _value;


    public CL_Agent() {
        _curr_edge=null;
    }

    public CL_Agent(directed_weighted_graph g, int start_node) {
        _gg = g;
        setMoney(0);
        this._curr_node = _gg.getNode(start_node);
        _pos = _curr_node.getLocation();
        _id = -1;
        setSpeed(0);
        help = new PathHelper(Double.POSITIVE_INFINITY, null);
        pathCompare = new PriorityQueue<>();
        _curr_edge=null;
    }

    public void update(String json) {
        JSONObject line;
        try {
            // "GameServer":{"graph":"A0","pokemons":3,"agents":1}}
            line = new JSONObject(json);
            JSONObject ttt = line.getJSONObject("Agent");
            int id = ttt.getInt("id");
            if (id == this.getID() || this.getID() == -1) {
                if (this.getID() == -1) {
                    _id = id;
                }
                double speed = ttt.getDouble("speed");
                String p = ttt.getString("pos");
                Point3D pp = new Point3D(p);
                int src = ttt.getInt("src");
                int dest = ttt.getInt("dest");
                double value = ttt.getDouble("value");
                this._pos = pp;
                this.setCurrNode(src);
                this.setSpeed(speed);
                this.setNextNode(dest);
                this.setMoney(value);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //@Override
    public int getSrcNode() {
        return this._curr_node.getKey();
    }

    public String toJSON() {
        int d = this.getNextNode();
        String ans = "{\"Agent\":{"
                + "\"id\":" + this._id + ","
                + "\"value\":" + this._value + ","
                + "\"src\":" + this._curr_node.getKey() + ","
                + "\"dest\":" + d + ","
                + "\"speed\":" + this.getSpeed() + ","
                + "\"pos\":\"" + _pos.toString() + "\""
                + "}"
                + "}";
        return ans;
    }

    private void setMoney(double v) {
        _value = v;
    }

    public boolean setNextNode(int dest) {
        boolean ans = false;
        int src = this._curr_node.getKey();
        this._curr_edge = _gg.getEdge(src, dest);
        if (_curr_edge != null) {
            ans = true;
        } else {
            _curr_edge = null;
        }
        return ans;
    }

    public void setCurrNode(int src) {
        this._curr_node = _gg.getNode(src);
    }

    public boolean isMoving() {
        return this._curr_edge != null;
    }

    public String toString() {
        return toJSON();
    }

    public String toString1() {
        String ans = "" + this.getID() + "," + _pos + ", " + isMoving() + "," + this.getValue();
        return ans;
    }

    public int getID() {
        // TODO Auto-generated method stub
        return this._id;
    }

    public geo_location getLocation() {
        // TODO Auto-generated method stub
        return _pos;
    }


    public double getValue() {
        // TODO Auto-generated method stub
        return this._value;
    }


    public int getNextNode() {
        int ans = -2;
        if (this._curr_edge == null) {
            ans = -1;
        } else {
            ans = this._curr_edge.getDest();
        }
        return ans;
    }
    public void setBastpath(List<node_data> bastpath) {
        this.bastpath = bastpath;
    }

    public List<node_data> getBastpath() {
        return bastpath;
    }
    public double getSpeed() {
        return this._speed;
    }

    public void setSpeed(double v) {
        this._speed = v;
    }

    public CL_Pokemon get_curr_fruit() {
        return _curr_fruit;
    }

    public void set_curr_fruit(CL_Pokemon curr_fruit) {
        this._curr_fruit = curr_fruit;
    }

    public void set_SDT(long ddtt) {
        long ddt = ddtt;
        if (this._curr_edge != null) {
            double w = get_curr_edge().getWeight();
            geo_location dest = _gg.getNode(get_curr_edge().getDest()).getLocation();
            geo_location src = _gg.getNode(get_curr_edge().getSrc()).getLocation();
            double de = src.distance(dest);
            double dist = _pos.distance(dest);
            if (this.get_curr_fruit().get_edge() == this.get_curr_edge()) {
                dist = _curr_fruit.getLocation().distance(this._pos);
            }
            double norm = dist / de;
            double dt = w * norm / this.getSpeed();
            ddt = (long) (1000.0 * dt);
        }
        this.set_sg_dt(ddt);
    }

    public edge_data get_curr_edge() {
        return this._curr_edge;
    }

    public long get_sg_dt() {
        return _sg_dt;
    }

    public void set_sg_dt(long _sg_dt) {
        this._sg_dt = _sg_dt;
    }

    public PathHelper getHelp() {
        return help;
    }

    public PriorityQueue<PathHelper> getPathCompare() {
        return pathCompare;
    }
    public ImageIcon getScaledagimg() {
        return scaledagimg;
    }


    public static class PathHelper implements Comparable<PathHelper>{
        private double totalCost;
        private List<node_data> thePath;
        CL_Pokemon poke;
        int pokeDest;

        public PathHelper(double totalCost, List<node_data> thePath) {
            this.totalCost = totalCost;
            this.thePath = thePath;
            poke = null;
        }

        public double getTotalCost() {
            return totalCost;
        }

        public List<node_data> getThePath() {
            return thePath;
        }

        public void setTotalCost(double totalCost) {
            this.totalCost = totalCost;
        }

        public void setThePath(List<node_data> thePath) {
            this.thePath = thePath;
        }

        public void setpoke(CL_Pokemon somting) {
            this.poke = somting;
        }

        public CL_Pokemon getPoke() {
            return poke;
        }

        @Override
        public int compareTo(@NotNull PathHelper o) {
            if (this.getTotalCost() < o.getTotalCost())
                return -1;
            else if (this.getTotalCost() > o.getTotalCost())
                return 1;
            return 0;
        }
    }
}

