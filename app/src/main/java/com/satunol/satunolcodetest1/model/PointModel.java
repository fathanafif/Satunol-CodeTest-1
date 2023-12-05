package com.satunol.satunolcodetest1.model;

public class PointModel {
    int id;
    float x;
    float y;

    public PointModel(int id, float x, float y) {
        this.id = id;
        this.x = x;
        this.y = y;
    }

    public int getId() { return id; }
    public float getX() { return x; }
    public float getY() { return y; }

}
