package com.rk.rkabc;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by ... on 04/04/16.
 */
public class Image implements Serializable {
    private String name;
    private String img;

    public Image() {
    }

    public Image(String name, String ig) {
        this.name = name;
        this.img = ig;
    }

    public String getName() {
        return name;
    }

    public String getImg(){
        return img;
    }

    public void setIMG(String i){
        this.img = i;
    }

    public void setName(String name) {
        this.name = name;
    }

}
