package pojo;

import java.util.Date;
/**
 * Created by YR on 2018/11/30.
 */
public class Product {
    private String id;
    private String cid;
    private String uid;
    private String pname;
    private double shop_price;
    private String pimage;
    private int num;
    private String pdesc;

    public Product() {
    }

    public Product(String id, String cid, String uid, String name, double price, String image, int num, String attribute) {
        this.id = id;
        this.cid = cid;
        this.uid = uid;
        this.pname = name;
        this.shop_price = price;
        this.pimage = image;
        this.num = num;
        this.pdesc = attribute;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String name) {
        this.pname = name;
    }

    public double getShop_price() {
        return shop_price;
    }

    public void setShop_price(double price) {
        this.shop_price = price;
    }

    public String getPimage() {
        return pimage;
    }

    public void setPimage(String image) {
        this.pimage = image;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getPdesc() {
        return pdesc;
    }

    public void setPdesc(String attribute) {
        this.pdesc = attribute;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", cid='" + cid + '\'' +
                ", uid='" + uid + '\'' +
                ", pname='" + pname + '\'' +
                ", show_price=" + shop_price +
                ", pimage='" + pimage + '\'' +
                ", num=" + num +
                ", pdesc='" + pdesc + '\'' +
                '}';
    }
}
