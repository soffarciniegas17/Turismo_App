package com.sophia1.turismo_app;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;



public class Inicio_Frag extends Fragment {

    ImageView imagen;
    TextView texto;
    public Inicio_Frag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_inicio_, container, false);
        imagen = view.findViewById(R.id.imagen);
        texto = view.findViewById(R.id.text);

        return view;
    }

}
