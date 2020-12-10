package com.abiel.abiel.Models;

public class MovieModel {
    String Titulo;
    String Desc;
    String Link;

    public MovieModel() {
    }

    public MovieModel(String titulo, String desc, String link) {
        Titulo = titulo;
        Desc = desc;
        Link = link;
    }

    public String getDetail() {
        return Titulo + " " + Desc;
    }

    public void setTitulo(String titulo) {
        Titulo = titulo;
    }

    public void setDesc(String desc) {
        Desc = desc;
    }

    public String getLink() {
        return Link;
    }

    public void setLink(String link) {
        Link = link;
    }
}
