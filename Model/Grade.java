/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import ASM1.*;

/**
 *
 * @author PC
 */
public class Grade {
    int id;
    public String maSV;
    String HoTenSV;
    double Photoshop;
    double Java;
    double Web;
    double DiemTB;

    public Grade(int id, String maSV, String HoTenSV, double Photoshop, double Java, double Web, double DiemTB) {
        this.id = id;
        this.maSV = maSV;
        this.HoTenSV = HoTenSV;
        this.Photoshop = Photoshop;
        this.Java = Java;
        this.Web = Web;
        this.DiemTB = DiemTB;
    }

    public Grade() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMaSV() {
        return maSV;
    }

    public void setMaSV(String maSV) {
        this.maSV = maSV;
    }

    public String getHoTenSV() {
        return HoTenSV;
    }

    public void setHoTenSV(String HoTenSV) {
        this.HoTenSV = HoTenSV;
    }

    public double getPhotoshop() {
        return Photoshop;
    }

    public void setPhotoshop(double Photoshop) {
        this.Photoshop = Photoshop;
    }

    public double getJava() {
        return Java;
    }

    public void setJava(double Java) {
        this.Java = Java;
    }

    public double getWeb() {
        return Web;
    }

    public void setWeb(double Web) {
        this.Web = Web;
    }

    public double getDiemTB() {
        return DiemTB;
    }

    public void setDiemTB(double DiemTB) {
        this.DiemTB = DiemTB;
    }

    
}