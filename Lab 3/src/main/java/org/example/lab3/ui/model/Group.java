package org.example.lab3.ui.model;

public class Group {
    private String name;
    private int capacity;
    private double fillPercentage;

    public Group(String name, int capacity, double fillPercentage) {
        this.name = name;
        this.capacity = capacity;
        this.fillPercentage = fillPercentage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public double getFillPercentage() {
        return fillPercentage;
    }

    public void setFillPercentage(double fillPercentage) {
        this.fillPercentage = fillPercentage;
    }
}
