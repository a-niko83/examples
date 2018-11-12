package com.ts.izh.lessons.domain;

import java.util.ArrayList;
import java.util.List;

public class User {
    int id;
    String name;
    List<Auto> autos;

    public User(int id) {
        this.id = id;
        autos = new ArrayList<>();
    }

    public User(int id, String name) {
        this.id = id;
        this.name = name;
        autos = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Auto> getAutos() {
        return autos;
    }

    public void setAutos(List<Auto> autos) {
        this.autos = autos;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", autos=" + autos +
                '}';
    }
}
