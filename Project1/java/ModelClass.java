package com.example.task;

public class ModelClass {

    private int imageview;
    private String textview="Null";


    public ModelClass(int imageview, String textview) {
        this.imageview = imageview;
        this.textview = textview;
    }

    public int getImageview() {
        return imageview;
    }

    public String getTextview() {


        return textview;
    }

}
