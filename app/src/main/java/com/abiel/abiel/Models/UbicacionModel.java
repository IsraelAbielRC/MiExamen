package com.abiel.abiel.Models;

public class UbicacionModel {
    double altitutud;
    double longitud;
    public  UbicacionModel(){

    }
    public Double GetAltitud(){
        return  altitutud;
    }
    public  void SetAltitud(Double alt){
        altitutud = alt;
    }
    public  Double GetLongitud(){
        return  longitud;
    }
    public  void SetLongitud(Double longi){
        longitud = longi;
    }
}
