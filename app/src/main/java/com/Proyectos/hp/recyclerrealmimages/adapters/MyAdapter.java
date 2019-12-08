package com.Proyectos.hp.recyclerrealmimages.adapters;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.Proyectos.hp.recyclerrealmimages.activities.MainActivity;
import com.Proyectos.hp.recyclerrealmimages.models.Peliculas;
import com.tutorials.hp.recyclerrealmimages.R;

import java.util.ArrayList;


public class MyAdapter extends RecyclerView.Adapter<MyHolder> implements View.OnClickListener {

    public Context c;
    public ArrayList<Peliculas> peliculas;
    public View.OnClickListener listener;
    private String name;
    private String desc;
    private String image;
    private float valoracion;
    private MyHolder.OnNoteListener mOnListener;

    public MyAdapter(Context c, ArrayList<Peliculas> peliculas, MyHolder.OnNoteListener onNoteListener) {
        this.c = c;
        this.peliculas = peliculas;
        this.mOnListener=onNoteListener;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(c).inflate(R.layout.model,parent,false);
        v.setOnClickListener(this);
        return new MyHolder(v, mOnListener);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {

        Peliculas s= peliculas.get(position);

        holder.nameTxt.setText(s.getName());
        holder.descTxt.setText(s.getDescription());
        holder.ratingBar.setRating(s.getValoracion());
        holder.setLongClickListener(new LongClickListener() {
            @Override
            public void onItemLongClick(int position) {
                name=peliculas.get(position).getName();
                desc=peliculas.get(position).getDescription();
                image=peliculas.get(position).getImageUrl();
                valoracion=peliculas.get(position).getValoracion();



                Toast.makeText(c,peliculas.get(position).getName(), Toast.LENGTH_SHORT).show();

            }
        });
        String imageUrl=s.getImageUrl().replace("localhost","10.0.2.2");

        ImageLoader.downloadImage(c,imageUrl,holder.img);
    }

    @Override
    public int getItemCount() {
        return peliculas.size();
    }




   @Override
    public void onClick(View view) {
if (listener!=null){
    listener.onClick(view);
}
    }

    public void getItemSelected(MenuItem item){
        Toast.makeText(c,name+" : "+item.getTitle(),Toast.LENGTH_SHORT).show();

    }



}











