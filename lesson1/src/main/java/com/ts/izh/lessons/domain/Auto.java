package com.ts.izh.lessons.domain;

public class Auto {
    int id;
    String model;
    User user;

    public Auto(int id, String model) {
        this.id = id;
        this.model = model;
        this.user = null;
    }

    public int getId() {
        return id;
    }

    public String getModel() {
        return model;
    }

    public User getUser() {
        return user;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Auto{");
        sb.append("id=").append(id);
        sb.append(", model='").append(model).append('\'');
        if (user!=null) sb.append(", user=").append(user.getName());
        sb.append('}');
        return sb.toString();
    }
}
