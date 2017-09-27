package com.example.android.myapplication.data;

/**
 * Created by Avilash on 27-09-2017.
 */

public class Destination {

    private String id , name , description , pic_url;

    public Destination(String id, String name, String description, String pic_url) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.pic_url = pic_url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPic_url() {
        return pic_url;
    }

    public void setPic_url(String pic_url) {
        this.pic_url = pic_url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
