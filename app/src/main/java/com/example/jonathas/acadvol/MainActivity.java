package com.example.jonathas.acadvol;

import android.content.Intent;
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
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.UUID;

public class MainActivity extends AppCompatActivity{

    Button btSalvar, btMaisrep, btMenosRep, btMaisCarga, btMenosCarga, btIngestProt;
    EditText edRep, edcarga;
    TextView tvTotalSerie, tvMedia, tvMax, tvVolumeHoje, tvQtdSerie;

    Spinner spGpMuscular;
    String[] gpMusc = new String[] {"Abdome","Bíceps","Costas","Ombros", "Peito","Pernas", "Tríceps"};
    String gpMuscSelec = "";
    String dia_atual = "";

    int carga, numRep, totalExerc=0;
    float volMedio;

    ArrayList<Serie> serieArrayList = new ArrayList<>();

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    Boolean chamei = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        finds();
        
        inicializarFirebase();

        popularSpinner();


    }

    private void popularSpinner() {
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.gruposMusc,
                android.R.layout.simple_spinner_item);

        spGpMuscular.setAdapter(adapter);
        spGpMuscular.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                Log.v("item", (String) parent.getItemAtPosition(position));
                gpMuscSelec = (String) parent.getItemAtPosition(position);

                //item do spinner selecionado, buscar informações no banco sobre ele e preencher os dois tvs
                eventoDataBase(gpMuscSelec);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void eventoDataBase(final String gpMuscSelec) {

        tvVolumeHoje.setText("");
        tvMedia.setText("");
        tvMax.setText("");
        tvTotalSerie.setText("");
        tvQtdSerie.setText("");

        Query query = databaseReference.child("Series").child(gpMuscSelec);

        Calendar calendar = Calendar.getInstance();
        dia_atual = calendar.get(Calendar.DAY_OF_MONTH) + "-" + (calendar.get(Calendar.MONTH)+1) + "-" + calendar.get(Calendar.YEAR);
        Query query2 = databaseReference.child("Series").child(gpMuscSelec).orderByChild("data").equalTo(dia_atual);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                totalExerc=0;
                serieArrayList.clear();

                for(DataSnapshot objSnapshot: dataSnapshot.getChildren()){
                    Serie s = objSnapshot.getValue(Serie.class);
                    totalExerc += s.getVolume();
                    serieArrayList.add(s);
                }

                if (totalExerc > 0.0) {
                    volMedio = totalExerc / serieArrayList.size();
                    tvMedia.setText(String.valueOf(volMedio));
                }
                tvQtdSerie.setText(String.valueOf(serieArrayList.size()));

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        query2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                totalExerc=0;

                for(DataSnapshot objSnapshot: dataSnapshot.getChildren()){
                    Serie s = objSnapshot.getValue(Serie.class);
                    totalExerc += s.getVolume();
                }

                if (totalExerc > 0.0) {
                    tvVolumeHoje.setText(String.valueOf(totalExerc));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void inicializarFirebase() {
            FirebaseApp.initializeApp(MainActivity.this);
            FirebaseDatabase firebaseDatabase = Utils.getDatabase();
            databaseReference = firebaseDatabase.getReference();
    }

    private void finds() {
        btSalvar = findViewById(R.id.btSalvar);
        btMaisrep= findViewById(R.id.btMaisRep);
        btMenosRep= findViewById(R.id.btMenosRep);
        spGpMuscular = findViewById(R.id.spGrupoMusc);
        edcarga = findViewById(R.id.edCarga);
        edRep = findViewById(R.id.edNumRep);
        tvTotalSerie = findViewById(R.id.tvVolumeSerie);
        tvMedia = findViewById(R.id.tvMedia);
        tvMax = findViewById(R.id.tvMax);
        tvVolumeHoje = findViewById(R.id.tvVolumeHoje);
        tvQtdSerie = findViewById(R.id.tvQtdSerie);
        btIngestProt = findViewById(R.id.btIngestProt);
    }

    public void botoesMaisEMenos(View view) {

        switch (view.getId()) {
            case R.id.btMaisCarga1:
                edcarga.setText(String.valueOf(Integer.parseInt(edcarga.getText().toString())+1));
                break;
            case R.id.btMaisCarga2:
                edcarga.setText(String.valueOf(Integer.parseInt(edcarga.getText().toString())+2));
                break;
            case R.id.btMaisCarga5:
                edcarga.setText(String.valueOf(Integer.parseInt(edcarga.getText().toString())+5));
                break;
            case R.id.btMaisCarga10:
                edcarga.setText(String.valueOf(Integer.parseInt(edcarga.getText().toString())+10));
                break;
            case R.id.btMaisCarga15:
                edcarga.setText(String.valueOf(Integer.parseInt(edcarga.getText().toString())+15));
                break;
            case R.id.btMaisCarga20:
                edcarga.setText(String.valueOf(Integer.parseInt(edcarga.getText().toString())+20));
                break;
            case R.id.btMenosCarga1:
                edcarga.setText(String.valueOf(Integer.parseInt(edcarga.getText().toString())-1));
                break;
            case R.id.btMenosCarga2:
                edcarga.setText(String.valueOf(Integer.parseInt(edcarga.getText().toString())-2));
                break;
            case R.id.btMenosCarga5:
                edcarga.setText(String.valueOf(Integer.parseInt(edcarga.getText().toString())-5));
                break;
            case R.id.btMenosCarga10:
                edcarga.setText(String.valueOf(Integer.parseInt(edcarga.getText().toString())-10));
                break;
            case R.id.btMenosCarga15:
                edcarga.setText(String.valueOf(Integer.parseInt(edcarga.getText().toString())-15));
                break;
            case R.id.btMenosCarga20:
                edcarga.setText(String.valueOf(Integer.parseInt(edcarga.getText().toString())-20));
                break;
            case R.id.btMaisRep:
                edRep.setText(String.valueOf(Integer.parseInt(edRep.getText().toString())+1));
                break;
            case R.id.btMenosRep:
                edRep.setText(String.valueOf(Integer.parseInt(edRep.getText().toString())-1));
                break;
            case R.id.btIngestProt:
                Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                startActivity(intent);
                finish();
            case R.id.btSalvar:
               // tvTotalSerie.setText(String.valueOf());

                carga = Integer.parseInt(edcarga.getText().toString());
                numRep = Integer.parseInt(edRep.getText().toString());

                Serie serie = new Serie();
                serie.setId(UUID.randomUUID().toString());
                serie.setGpMusc(gpMuscSelec);
                serie.setCarga(carga);
                serie.setNumRep(numRep);
                serie.setVolume(numRep*carga);

                Calendar calendar = Calendar.getInstance();
                 dia_atual = calendar.get(Calendar.DAY_OF_MONTH) + "-" + (calendar.get(Calendar.MONTH)+1) + "-" + calendar.get(Calendar.YEAR);

                 serie.setData(dia_atual);

                //databaseReference.child("Series").child(dia_atual).child(serie.getGpMusc()).child(serie.getId()).setValue(serie);
                databaseReference.child("Series").child(serie.getGpMusc()).child(serie.getId()).setValue(serie);


                Toast.makeText(this, "Série de " + serie.getGpMusc() + " salva", Toast.LENGTH_SHORT).show();
                Intent intent2 = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent2);
                finish();

                break;
        }
        tvTotalSerie.setText(String.valueOf(Integer.parseInt(edcarga.getText().toString()) * Integer.parseInt(edRep.getText().toString())));

    }

}


//TODO salvar dias e dias da semana e extrair relatórios como carga da semana, etc;
//TODO tela de ingestão proteica

