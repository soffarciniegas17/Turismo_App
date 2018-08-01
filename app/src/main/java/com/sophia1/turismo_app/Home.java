package com.sophia1.turismo_app;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, ComunicaFragment {

    private int fragmentActivo, categoria, itemPress;
    private boolean visualizacion;
    private FloatingActionButton fab;
    private MenuItem actionVisualizacion;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
         toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

         fab = (FloatingActionButton) findViewById(R.id.fab);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if(fragmentActivo==3) {

            abreLista();
        }else{
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        actionVisualizacion=menu.findItem(R.id.action_visualizacion);

        if (visualizacion){
            actionVisualizacion.setIcon(R.drawable.view_list);
        }else{
            actionVisualizacion.setIcon(R.drawable.view_grid);

        }


        //se inicia los fragment desde el metodo del menu para evitar errores del actionVisualizacion null
        if(fragmentActivo==1){
            abreInicio();
        }else if(fragmentActivo==2){
            abreLista();
        }else{
            itemPresionado(itemPress);
        }



        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        if (id == R.id.action_exit) {

            finish();

            return true;
        }else if(id==R.id.action_visualizacion){

            if(visualizacion){
                visualizacion=false;
                actionVisualizacion.setIcon(R.drawable.view_list);
                abreLista();
            }else{
                visualizacion=true;
                actionVisualizacion.setIcon(R.drawable.view_grid);
                abreLista();

            }




            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.bton_inicio) {
            abreInicio();
        } else if (id == R.id.bton_lugar) {
            categoria=0;
            abreLista();

        } else if (id == R.id.bton_hotel) {
            categoria=1;
            abreLista();

        } else if (id == R.id.bton_restaurante) {
            categoria=2;
            abreLista();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void abreInicio(){

        Toast.makeText(this,"inicio",Toast.LENGTH_LONG).show();

        fragmentActivo=1;
        actionVisualizacion.setVisible(false);
        fab.setVisibility(View.INVISIBLE);
        toolbar.setTitle("Inicio");

    }
    public void abreLista(){
        FragmentManager manager=getFragmentManager();
        FragmentTransaction transaction=manager.beginTransaction();

        Fragment fragLista=new FragmentLista();
        Bundle datos=new Bundle();
        datos.putInt("CATEG",categoria);
        datos.putBoolean("VIS",visualizacion);
        fragLista.setArguments(datos);

        transaction.replace(R.id.contenedor_fragment_1,fragLista);
        transaction.commit();

        Toast.makeText(this,"lista",Toast.LENGTH_LONG).show();




        fragmentActivo=2;
        actionVisualizacion.setVisible(true);
        if (categoria==0)fab.setImageResource(R.drawable.icon_places);
        else if (categoria==1)fab.setImageResource(R.drawable.icon_hotel);
        else if (categoria==2)fab.setImageResource(R.drawable.icon_restaurant);

        int orientation=this.getResources().getConfiguration().orientation;
        if(orientation==Configuration.ORIENTATION_LANDSCAPE)actionVisualizacion.setVisible(false);
        else fab.setVisibility(View.VISIBLE);

        toolbar.setTitle("Sitios");

    }


    @Override
    public void itemPresionado(int item) {

        Toast.makeText(this,"contenido_lugar",Toast.LENGTH_LONG).show();

        itemPress=item;
        fragmentActivo=3;
        actionVisualizacion.setVisible(false);
        fab.setImageResource(R.drawable.icon_car);
        fab.setVisibility(View.VISIBLE);
        toolbar.setTitle("Detalle Sitio");

    }
    public void onResume(){
        super.onResume();
        SharedPreferences datos= PreferenceManager.getDefaultSharedPreferences(this);

        visualizacion=datos.getBoolean("VISUALIZACION",true);
        fragmentActivo=datos.getInt("FRAGMENT_ACTIVO",1);
        categoria=datos.getInt("CATEGORIA",0);
        itemPress=datos.getInt("ITEM_PRESIONADO",0);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               //abrir mapa
                if(fragmentActivo==2){

                }else if(fragmentActivo==3){

                }
            }
        });

        guardaDatos=datos.getInt("nada",0);
        if(guardaDatos==0)guardaDatos();



    }
    public void onPause(){
        super.onPause();

        SharedPreferences datos= PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor guarda=datos.edit();

        guarda.putBoolean("VISUALIZACION",visualizacion);
        guarda.putInt("FRAGMENT_ACTIVO",fragmentActivo);
        guarda.putInt("CATEGORIA",categoria);
        guarda.putInt("ITEM_PRESIONADO",itemPress);
        guarda.putInt("nada",guardaDatos);


        guarda.apply();
    }
    int guardaDatos;
    public void guardaDatos(){
        DataBase dataBase=new DataBase(this);

        for (int i=0;i<10;i++){
            dataBase.guardar(R.drawable.icono,"DEFECTO0","alexander casanova",".",2.2,2.2,
                    0);
        }
        for (int i=0;i<10;i++){
            dataBase.guardar(R.drawable.icono,"DEFECTO11","alexander casanova",".",2.2,2.2,
                    1);
        }
        for (int i=0;i<10;i++){
            dataBase.guardar(R.drawable.icono,"DEFECTO22","alexander casanova",".",2.2,2.2,
                    2);
        }
        guardaDatos=1;
    }
}
