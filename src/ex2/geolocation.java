package ex2;

import api.geo_location;

public  class geolocation implements geo_location {
    private double x;
    private double y;
    private double z;

    public geolocation(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public geolocation() {
        this.x = 0;
        this.y = 0;
        this.z = 0;
    }

    public geolocation(String pos)
    {
        String[] splittedStr  = pos.split(",");
        this.x = Double.parseDouble(splittedStr[0]);
        this.y = Double.parseDouble(splittedStr[1]);
        this.z = Double.parseDouble(splittedStr[2]);
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
