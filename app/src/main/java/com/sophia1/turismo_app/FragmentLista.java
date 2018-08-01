package com.sophia1.turismo_app;


import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentLista extends Fragment {

    private ArrayList<ItemLugar> lugares;
    private AdapterLugares adapter;
    private GridView gridView;
    private DataBase dataBase;
    private int categoria;
    private boolean actionVisualizacion;

    public FragmentLista() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View row=inflater.inflate(R.layout.fragment_lista, container, false);

        gridView=row.findViewById(R.id.gridView);

        if (getArguments()!=null){
            categoria=getArguments().getInt("CATEG");
            actionVisualizacion=getArguments().getBoolean("VIS");
        }


        cargarLista(actionVisualizacion);

        return row;
    }
    public void cargarLista(boolean vista){
        try{
            lugares=new ArrayList<>();

            int orientation=this.getResources().getConfiguration().orientation;
            if(orientation== Configuration.ORIENTATION_PORTRAIT){
                if (vista){
                    adapter=new AdapterLugares(getActivity(),lugares,R.layout.item_lugares_lista);
                }else{
                    adapter=new AdapterLugares(getActivity(),lugares,R.layout.item_lugar_grid);
                    gridView.setNumColumns(GridView.AUTO_FIT);
                }

            }else{
                adapter=new AdapterLugares(getActivity(),lugares,R.layout.item_lugar_lista_land);
            }
            gridView.setAdapter(adapter);


            cargarDatos(categoria);


        }catch (Exception e){}

        adapter.notifyDataSetChanged();
        clickItem();
    }


    public void cargarDatos(int categ){
        dataBase=new DataBase(getActivity());
        Cursor cursor;

        if(categ==0) cursor=dataBase.cargar();
        else cursor=dataBase.cargarCategoria(categ+"");
        if (cursor==null)return;

        if(cursor.moveToFirst()){
            do{
                lugares.add(new ItemLugar(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getDouble(4),
                        cursor.getDouble(5),
                        categ+""));

            }while (cursor.moveToNext());
        }


    }
    public void clickItem(){

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ((ComunicaFragment)getActivity()).itemPresionado(position);
            }
        });
    }

}
