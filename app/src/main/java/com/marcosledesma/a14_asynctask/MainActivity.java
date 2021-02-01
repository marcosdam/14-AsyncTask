package com.marcosledesma.a14_asynctask;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private TextView textView;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        progressBar = findViewById(R.id.progressBar);
        textView = findViewById(R.id.txtContador);

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Lanzar un HILO que cuente de 0 a 100
                // Se activará el loader y se desactivará al finalizar
                new Contador().execute();
            }
        });
    }

    // TAREAS EN SEGUNDO PLANO (Heredan de la Clase AsyncTask<Params, Progress, Result>

    /**
     * 1º -> Tipo de datos del constructor
     * 2º -> Tipo de datos a publicar
     * 3º -> Tipo de datos a devolver
     */
    public class Contador extends AsyncTask<Void, Integer, Void>{
        // 1. Se ejecuta antes de iniciar la Tarea (el primero  que se ejecuta)
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setMin(0);
            progressBar.setMax(100);
            fab.setEnabled(false);
        }

        // 2. Ejecuta la Tarea
        @Override
        protected Void doInBackground(Void... voids) {
            // Parar el hilo 1 segundo en cada vuelta para que dure 100 segundos
            for (int i = 1; i <= 100; i++) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // para llamar a onProgressUpdate
                publishProgress(i);
            }

            return null;
        }

        // 3. Se ejecuta si doInBackground publica un resultado
        // (Recibe el Integer que publica el doInBackground)
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            //
            int numero = values[0];
            textView.setText(String.valueOf(numero));
            progressBar.setProgress(numero);
        }

        // 4. Las tareas a realizar el doInBackground (la Tarea real) ha terminado
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            progressBar.setVisibility(View.GONE);
            fab.setEnabled(true);
        }
    }
}