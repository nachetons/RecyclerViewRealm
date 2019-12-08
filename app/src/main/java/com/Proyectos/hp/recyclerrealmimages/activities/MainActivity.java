package com.Proyectos.hp.recyclerrealmimages.activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.Proyectos.hp.recyclerrealmimages.adapters.MyAdapter;
import com.Proyectos.hp.recyclerrealmimages.adapters.MyHolder;
import com.Proyectos.hp.recyclerrealmimages.models.Peliculas;
import com.Proyectos.hp.recyclerrealmimages.models.RealmHelper;
import com.tutorials.hp.recyclerrealmimages.R;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;


public class MainActivity extends AppCompatActivity implements MyHolder.OnNoteListener {

    private Realm realm;
    private RealmChangeListener realmChangeListener;
    private MyAdapter adapter;
    private RecyclerView rv;
    private ArrayList<Peliculas> peliculas = new ArrayList<>();
    private EditText nameEditText,descEditTxt,urlEditTxt;
    private RatingBar ratingBar2;
    private RecyclerView.LayoutManager layoutManager;
    RealmResults <Peliculas>mResults;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //registerForContextMenu(rv);
        layoutManager = new LinearLayoutManager(this);
        //SETUP RECYCLER
        rv = (RecyclerView) findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this));

        /*adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                        }
        });*/
        //INITIALIZE REALM
        realm=Realm.getDefaultInstance();

        //RETRIEVE
        final RealmHelper helper=new RealmHelper(realm);
        helper.retrieveFromDB();

        //ADAPTER
        adapter=new MyAdapter(this,helper.justRefresh(),this);
        rv.setAdapter(adapter);
        //DATA CHANGE

        realmChangeListener=new RealmChangeListener() {
            @Override
                    public void onChange() {



                adapter=new MyAdapter(MainActivity.this,helper.justRefresh(),MainActivity.this);
               /* adapter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //showAlertForEditingBoard("Editar tablero", "Cambia el nombre del tablero", peliculas.get(0));
                    //    layoutManager.scrollToPosition(position);


                    }
                });*/
                rv.setAdapter(adapter);
            }

        };

        //ADD IT TO REALM
        realm.addChangeListener(realmChangeListener);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                  displayInputDialog();
            }
        });
    }


    //DISPLAY INPUT DIALOG
    public void displayInputDialog()
    {

        final Dialog d=new Dialog(this);
        d.setTitle("Guardado en Realm");
        d.setContentView(R.layout.input_dialog);

        //EDITTEXTS
        nameEditText= (EditText) d.findViewById(R.id.nameEditText);
        descEditTxt= (EditText) d.findViewById(R.id.descEditText);
        urlEditTxt= (EditText) d.findViewById(R.id.urlEditText);
        ratingBar2= (RatingBar) d.findViewById(R.id.ratingBar2);
        ratingBar2.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

            }
        });
        Button saveBtn= (Button) d.findViewById(R.id.saveBtn);

        //SAVE
        saveBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                //GET DATA
                String name=nameEditText.getText().toString();
                String desc=descEditTxt.getText().toString();
                String imageUrl=urlEditTxt.getText().toString();
                float valoracion=ratingBar2.getRating();



                //VERIFY
                if(name != null && name.length()>0)
                {

                     //SET DATA
                    Peliculas s=new Peliculas();
                    s.setName(name);
                    s.setDescription(desc);
                    s.setImageUrl(imageUrl);
                    s.setValoracion(valoracion);

                    //SAVE
                    RealmHelper helper=new RealmHelper(realm);

                    if(helper.save(s))
                    {
                        nameEditText.setText("");
                        descEditTxt.setText("");
                        urlEditTxt.setText("");
                        ratingBar2.setRating(0);
                    }else {
                        Toast.makeText(MainActivity.this, "Entrada invalida", Toast.LENGTH_SHORT).show();

                    }

                }else {
                    Toast.makeText(MainActivity.this, "Campos vacios", Toast.LENGTH_SHORT).show();
                }

                d.hide();

            }
        });




        d.show();
    }

    private void editPeliculas(String newName,String newDesc,String newImage,float newvalorar, Peliculas peliculas) {
        realm.beginTransaction();
        peliculas.setName(newName);
        peliculas.setDescription(newDesc);
        peliculas.setImageUrl(newImage);
        peliculas.setValoracion(newvalorar);
        realm.copyToRealmOrUpdate(peliculas);
        realm.commitTransaction();
    }

    private void deletePeliculas(Peliculas peliculas) {
        realm.beginTransaction();
        peliculas.removeFromRealm();
        realm.commitTransaction();
    }





    public void showAlertForEditingBoard(final Peliculas peliculas) {

        final Dialog d=new Dialog(this);
        d.setTitle("Guardado en Realm");
        d.setContentView(R.layout.edit_dialog);

        //EDITTEXTS
        nameEditText= (EditText) d.findViewById(R.id.nameEditText);
        nameEditText.setText(peliculas.getName());
        descEditTxt= (EditText) d.findViewById(R.id.descEditText);
        descEditTxt.setText(peliculas.getDescription());
        urlEditTxt= (EditText) d.findViewById(R.id.urlEditText);
        urlEditTxt.setText(peliculas.getImageUrl());
        ratingBar2= (RatingBar) d.findViewById(R.id.ratingBar2);
        ratingBar2.setRating(peliculas.getValoracion());
        ratingBar2.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

            }
        });
        Button saveBtn= (Button) d.findViewById(R.id.saveBtn);
        Button delBtn= (Button) d.findViewById(R.id.deleteBtn);
        //SAVE
        saveBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                //GET DATA
                String name=nameEditText.getText().toString();
                String desc=descEditTxt.getText().toString();
                String imageUrl=urlEditTxt.getText().toString();
                Float valoracion=ratingBar2.getRating();



                //VERIFY
                if(name != null && name.length()>0)
                {

                    //SET DATA

                    editPeliculas(name,desc,imageUrl,valoracion, peliculas);

                    //SAVE



                    RealmHelper helper=new RealmHelper(realm);
                    helper.save(peliculas);
                    d.hide();
                }



            }
        });
        delBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {


                deletePeliculas(peliculas);
                d.hide();

            }
            });




        d.show();
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);



        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_item:
                Realm realm = Realm.getDefaultInstance();
                realm.beginTransaction();
                realm.clear(Peliculas.class);
                realm.commitTransaction();
                realm.close();

                return true;
            default:

            return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        menu.setHeaderTitle(peliculas.get(info.position).getName());
        getMenuInflater().inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {


        adapter.getItemSelected(item);
                return super.onContextItemSelected(item);
        }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        realm.removeChangeListener(realmChangeListener);
        realm.close();
    }

    @Override
    public void onNoteClick(int position) {
        showAlertForEditingBoard(adapter.peliculas.get(position));
        //Toast.makeText(MainActivity.this, "Pulsastes el: "+position+"Con el nombre"+adapter.peliculas.get(position).getName(), Toast.LENGTH_SHORT).show();
    }
}












