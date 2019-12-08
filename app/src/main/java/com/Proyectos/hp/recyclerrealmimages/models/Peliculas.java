package com.Proyectos.hp.recyclerrealmimages.models;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;


public class Peliculas extends RealmObject {


    @PrimaryKey
    private String name;

    private String description;
   
    private String imageUrl;
    private float valoracion;


    public Peliculas() {
    }


    public Peliculas(String name, String description, String imageUrl, float valoracion) {
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.valoracion = valoracion;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public float getValoracion() {
        return valoracion;
    }

    public void setValoracion(float valoracion) {
        this.valoracion = valoracion;
    }



}
