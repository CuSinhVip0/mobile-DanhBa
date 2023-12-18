package vn.edu.stu.do_an_mobile.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class BANBE implements Serializable {
    public String ma;
    public String bietdanh;
    public String ho;
    public String ten;
    public String mail;
    public Date ngaysinh;
    public String phone;

    public byte[] photo;


    public ArrayList<MANGXAHOI> mxh;


    public BANBE(){

    }
    public BANBE(String ma, String bietdanh, String ho, String ten, String mail, Date ngaysinh, String phone, ArrayList<MANGXAHOI> mxh,byte[] photo) {
        this.ma = ma;
        this.bietdanh = bietdanh;
        this.ho = ho;
        this.ten = ten;
        this.mail = mail;
        this.ngaysinh = ngaysinh;
        this.phone = phone;
        this.mxh = mxh;
        this.photo = photo;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    @Override
    public String toString() {
        return "BANBE{" +
                "ma='" + ma + '\'' +
                ", bietdanh='" + bietdanh + '\'' +
                ", ho='" + ho + '\'' +
                ", ten='" + ten + '\'' +
                ", mail='" + mail + '\'' +
                ", ngaysinh=" + ngaysinh +
                ", phone='" + phone + '\'' +
                ", photo=" + Arrays.toString(photo) +
                ", mxh=" + mxh +
                '}';
    }

    public String getMa() {
        return ma;
    }

    public void setMa(String ma) {
        this.ma = ma;
    }

    public String getBietdanh() {
        return bietdanh;
    }

    public void setBietdanh(String bietdanh) {
        this.bietdanh = bietdanh;
    }

    public String getHo() {
        return ho;
    }

    public void setHo(String ho) {
        this.ho = ho;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Date getNgaysinh() {
        return ngaysinh;
    }

    public void setNgaysinh(Date ngaysinh) {
        this.ngaysinh = ngaysinh;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public ArrayList<MANGXAHOI> getMxh() {
        return mxh;
    }

    public void setMxh(ArrayList<MANGXAHOI> mxh) {
        this.mxh = mxh;
    }
}
