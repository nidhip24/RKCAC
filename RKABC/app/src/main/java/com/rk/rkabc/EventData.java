package com.rk.rkabc;

public class EventData {

    String ename,edes,id,date;

    public EventData(String en, String ed,String id,String date){
        ename = en;
        edes = ed;
        this.id = id;
        this.date = date;
    }

    public String getEname(){
        return ename;
    }

    public String getEdes(){
        return edes;
    }

    public String getID(){ return id; }

    public String getDat(){ return  date; }
}
