package com.sophia1.turismo_app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBase extends SQLiteOpenHelper {

    Context context;
    private static final int version= 1;
    private static final String dataName= "registro.db";
    private static final String table_name = "CREATE TABLE LUGARES (ID INTEGER  PRIMARY KEY AUTOINCREMENT, IMAGEN INTEGER, " +
            "TITULO TEXT, DESCRIPCIONCORTA TEXT, DESCRIPCION TEXT, LATITUD DOUBLE, LONGITUD DOUBLE, CATEGORIA INTEGER)";


    public DataBase(Context context){
        super(context, dataName, null, version);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(table_name);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS CREATE"+table_name);
        db.execSQL(table_name);
    }

    public void guardar(int imagen, String titulo,String descripcioncorta, String descripcion,
                        double latitud, double longitud, int categoria){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("IMAGEN", imagen);
        values.put("TITULO", titulo);
        values.put("DESCRIPCIONCORTA", descripcioncorta);
        values.put("DESCRPCION", descripcion);
        values.put("LATITUD", latitud);
        values.put("LONGITUD", longitud);
        values.put("CATEGORIA", categoria);

        db.insert("LUGARES", null, values);

    }

    public Cursor cargar (){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor;

        String find[] = {"IMAGEN", "TITULO", "DESCRPCIONCORTA", "DESCRIPCION", "LATITUD", "LONGITUD"};

        try {

        }
    }

}
