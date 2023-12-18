package vn.edu.stu.do_an_mobile.model;

import java.io.Serializable;

public class MANGXAHOI implements Serializable {
    public String ten;
    public String link;

    public MANGXAHOI(){

    }
    public MANGXAHOI(String ten, String link) {
        this.ten = ten;
        this.link = link;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public String toString() {
        return "MANGXAHOI{" +
                "ten='" + ten + '\'' +
                ", link='" + link + '\'' +
                '}';
    }
}
