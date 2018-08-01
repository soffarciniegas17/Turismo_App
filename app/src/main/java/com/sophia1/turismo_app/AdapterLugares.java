package com.sophia1.turismo_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class AdapterLugares extends BaseAdapter {

    private Context context;
    private ArrayList<ItemLugar> lugares;
    private int layoutItem;

    public AdapterLugares(Context context, ArrayList<ItemLugar> lugares, int layoutItem) {
        this.context = context;
        this.lugares = lugares;
        this.layoutItem = layoutItem;
    }

    @Override
    public int getCount() {
        return lugares.size();
    }

    @Override
    public Object getItem(int position) {
        return lugares.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row=convertView;
        Holder holder= new Holder();

        if(row==null){

            LayoutInflater inflater=(LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            row=inflater.inflate(layoutItem,null);

            holder.lugarImagen=row.findViewById(R.id.item_lugar_imagen);
            holder.lugarTitulo=row.findViewById(R.id.item_lugar_titulo);
            holder.lugarDescripC=row.findViewById(R.id.item_lugar_descripcionC);
            holder.lugarUbicacion=row.findViewById(R.id.item_lugar_ubicacion);

            row.setTag(holder);
        }else{
            holder=(Holder)row.getTag();

        }

        ItemLugar lugar=lugares.get(position);

        holder.lugarImagen.setBackgroundResource(lugar.getImageRes());
        holder.lugarTitulo.setText(lugar.getTitulo());
        holder.lugarDescripC.setText(lugar.getDescripcionCorta());
        holder.lugarUbicacion.setText(lugar.getLatitud()+"");

        return row;
    }

    public class Holder{
        ImageView lugarImagen;
        TextView lugarTitulo, lugarDescripC, lugarUbicacion;
    }
}
