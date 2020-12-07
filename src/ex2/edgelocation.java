package ex2;

import api.edge_data;
import api.edge_location;

public class edgelocation implements edge_location {
    private edge_data Data;
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
