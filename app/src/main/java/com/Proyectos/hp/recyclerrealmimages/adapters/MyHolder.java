package com.Proyectos.hp.recyclerrealmimages.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.Proyectos.hp.recyclerrealmimages.models.Peliculas;
import com.tutorials.hp.recyclerrealmimages.R;

import java.util.ArrayList;


public class MyHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener,View.OnCreateContextMenuListener,View.OnClickListener{


    TextView nameTxt,descTxt;
    RatingBar ratingBar;
    ImageView img;
    public OnNoteListener onNoteListener;
    public LongClickListener longClickListener;

    public MyHolder(View itemView, OnNoteListener onNoteListener) {
        super(itemView);

        nameTxt= (TextView) itemView.findViewById(R.id.nameTxt);
        descTxt= (TextView) itemView.findViewById(R.id.descTxt);
        img= (ImageView) itemView.findViewById(R.id.spacecraftImage);
        ratingBar=(RatingBar) itemView.findViewById(R.id.ratingBar);
        this.onNoteListener = onNoteListener;
        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
        itemView.setOnCreateContextMenuListener(this);

    }

    public  void setLongClickListener(LongClickListener lc){
        this.longClickListener=lc;
    }
    public boolean onLongClick(View view){
        this.longClickListener.onItemLongClick(getLayoutPosition());
                return false;
    }

    @Override
    public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
        contextMenu.setHeaderTitle("Selecciona una accion");
        contextMenu.add(0,0,0,"Editar");
        contextMenu.add(0,1,0,"Eliminar");
    }
    public void onClick(View view){
        onNoteListener.onNoteClick(getAdapterPosition());
    }
    public interface OnNoteListener{
        void onNoteClick(int position);
    }
}
