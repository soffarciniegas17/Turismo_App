
package com.sophia1.turismo_app;


import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


public class Contenido_Frag extends Fragment {

    ArrayList<ItemLugar> lista;
    DataBase db;
    int categoria, item;
    ImageView imagen;
    TextView titulo, descripcion;
    public Contenido_Frag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_contenido_, container, false);

        imagen= view.findViewById(R.id.imagenViewRR);
        titulo= view.findViewById(R.id.titulo);
        descripcion= view.findViewById(R.id.descrip);

        item=0;
        categoria=0;
        if(getArguments()!= null){
            item=getArguments().getInt("ITEM");
            categoria=getArguments().getInt("CATEG");
        }

        Activity activity = getActivity();
        db = new DataBase(activity);
        lista= new ArrayList<>();

        if(categoria==0){
            Item();
        } else {ItemCategoria();}


        mostrar();
        return view;
    }


    public void Item(){

        Cursor cursor=db.cargar();

        if(cursor.moveToFirst()){

            do{
                //imageRes, String titulo, String descripcionCorta, String descripcionLarga, double latitud, double longitud, String categoria
                lista.add(new ItemLugar(cursor.getInt(0), cursor.getString(1),cursor.getString(2),
                        cursor.getString(3),cursor.getDouble(4), cursor.getDouble(5),
                        "1"));

            }while (cursor.moveToNext());
        }
    }
    public void ItemCategoria(){
        Cursor cursor=db.cargarCategoria(categoria+"");

        if(cursor.moveToFirst()){

            do{
                lista.add(new ItemLugar(cursor.getInt(0),
                        cursor.getString(1),cursor.getString(2),
                        cursor.getString(3),cursor.getDouble(4),
                        cursor.getDouble(5), "1"));

            }while (cursor.moveToNext());
        }
    }

    public void mostrar(){
        ItemLugar lugar=lista.get(item);

        imagen.setImageResource(lugar.getImageRes());
        titulo.setText(lugar.getTitulo());
        descripcion.setText(lugar.getDescripcionLarga());
    }

}
