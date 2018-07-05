package com.example.jonathas.acadvol;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    Button btSalvar, btMaisrep, btMenosRep, btMaisCarga, btMenosCarga;
    EditText edRep, edcarga;
    TextView tvTotalSerie;

    Spinner spGpMuscular;
    String[] gpMusc = new String[] {"Abdome","Bíceps","Costas","Ombros", "Peito","Pernas", "Tríceps"};
    String gpMuscSelec = "";

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        finds();
        
        inicializarFirebase();

        popularSpinner();


    }

    private void popularSpinner() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_spinner_item,gpMusc);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spGpMuscular.setAdapter(adapter);
        spGpMuscular.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                Log.v("item", (String) parent.getItemAtPosition(position));
                gpMuscSelec = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(MainActivity.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
//            firebaseDatabase.setPersistenceEnabled(true);
        databaseReference = firebaseDatabase.getReference();
    }


    private void finds() {
        btSalvar = findViewById(R.id.btSalvar);
        btMaisCarga = findViewById(R.id.btMaisCarga);
        btMenosCarga= findViewById(R.id.btMenosCarga);
        btMaisrep= findViewById(R.id.btMaisRep);
        btMenosRep= findViewById(R.id.btMenosRep);
        spGpMuscular = findViewById(R.id.spGrupoMusc);
        edcarga = findViewById(R.id.edCarga);
        edRep = findViewById(R.id.edNumRep);
        tvTotalSerie = findViewById(R.id.tvVolumeSerie);

    }

    public void botoesMaisEMenos(View view) {
        switch (view.getId()) {
            case R.id.btMaisCarga:
                edcarga.setText(String.valueOf(Integer.parseInt(edcarga.getText().toString())+1));
                break;
            case R.id.btMenosCarga:
                edcarga.setText(String.valueOf(Integer.parseInt(edcarga.getText().toString())-1));
                break;
            case R.id.btMaisRep:
                edRep.setText(String.valueOf(Integer.parseInt(edRep.getText().toString())+1));
                break;
            case R.id.btMenosRep:
                edRep.setText(String.valueOf(Integer.parseInt(edRep.getText().toString())-1));
                break;
            case R.id.btSalvar:
               // tvTotalSerie.setText(String.valueOf());

                break;
        }


    }
}


//TODO considerar mudar spinner por radio para diminuir clicques
//TODO salvar dias e dias da semana e extrair relatórios como carga da semana, etc;
//TODO fazer o firebase

