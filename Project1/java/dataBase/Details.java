package com.example.task.dataBase;

import java.util.List;

public class Details {
    private int id;
    private String name;

    public Details(String name) {
        this.name = name;
    }
    public Details(){
        this.name=name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
