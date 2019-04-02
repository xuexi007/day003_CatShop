package com.offcn.po;

public class Sp {
    private String name;
    private Double price;
    private String pic;
    private String spUrl;

    public Sp() {
    }

    public Sp(String name, Double price, String pic, String spUrl) {
        this.name = name;
        this.price = price;
        this.pic = pic;
        this.spUrl = spUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getSpUrl() {
        return spUrl;
    }

    public void setSpUrl(String spUrl) {
        this.spUrl = spUrl;
    }
}
