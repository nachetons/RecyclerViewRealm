package com.Proyectos.hp.recyclerrealmimages.activities;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;


public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //SETUP REALM
        RealmConfiguration config=new RealmConfiguration.Builder(this).schemaVersion(4).deleteRealmIfMigrationNeeded().build();
        Realm.setDefaultConfiguration(config);
    }
}
