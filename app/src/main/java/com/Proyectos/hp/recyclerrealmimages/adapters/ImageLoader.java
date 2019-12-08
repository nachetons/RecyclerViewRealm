package com.Proyectos.hp.recyclerrealmimages.adapters;

import android.content.Context;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.tutorials.hp.recyclerrealmimages.R;


public class ImageLoader {

    public static void downloadImage(Context c,String imageUrl,ImageView img)
    {
        if(imageUrl != null && imageUrl.length()>0)
        {
            Picasso.with(c).load(imageUrl).placeholder(R.drawable.placeholder).into(img);

        }else {
            Picasso.with(c).load(R.drawable.placeholder).into(img);
        }
    }
}
