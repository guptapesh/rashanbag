package com.rightarrows.blinkbasket;

public class RowPojo {
    String name,url,id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public  RowPojo()
    {

    }
    public void setName(String name) {
        this.name = name;
    }

    public RowPojo(String name, String url,String id) {
        this.name = name;
        this.url = url;
        this.id=id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
