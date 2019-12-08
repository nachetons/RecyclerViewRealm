package com.Proyectos.hp.recyclerrealmimages.models;

import android.view.View;

import com.Proyectos.hp.recyclerrealmimages.adapters.MyHolder;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.exceptions.RealmException;


public class RealmHelper{

    Realm realm;
    RealmResults<Peliculas> peliculas;
    Boolean saved=null;

    public RealmHelper(Realm realm
    ) {
        this.realm = realm;


    }

    //SAVE
    public Boolean save(final Peliculas peliculas)

    {
        if(peliculas ==null)
        {
            saved=false;
        }else
        {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {

                    try
                    {
                        Peliculas s=realm.copyToRealm(peliculas);
                        saved=true;

                    }catch (RealmException e)
                    {
                        e.printStackTrace();
                        saved=false;
                    }
                }
            });
        }

        return saved;
    }



    //RETRIEVE
    public void retrieveFromDB()
    {
        peliculas =realm.where(Peliculas.class).findAll();
    }


    //REFRESH
    public ArrayList<Peliculas> justRefresh()
    {
        ArrayList<Peliculas> latest=new ArrayList<>();
        for(Peliculas s : peliculas)
        {
            latest.add(s);
        }

        return latest;
    }



}












