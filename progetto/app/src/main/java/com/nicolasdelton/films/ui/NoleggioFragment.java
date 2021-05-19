package com.nicolasdelton.films.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nicolasdelton.films.R;
import com.nicolasdelton.films.film.Film;

import java.util.ArrayList;

public class NoleggioFragment extends Fragment {

    private Spinner chooseSpinner;
    private EditText idcliente, giorni, giorniritardo;

    private ConstraintLayout loading;

    private ArrayList<Film> filmClassList;
    private ArrayList<String> listFilm;
    private ArrayAdapter<String> adapterFilm;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_noleggio, container, false);


        /**
         * inizializzo i vari attributi
         */
        listFilm = new ArrayList<>();
        loading = root.findViewById(R.id.loadingNol);
        adapterFilm = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_list_item_1, listFilm);
        filmClassList = new ArrayList<>();

        loading.setVisibility(View.VISIBLE);

        chooseSpinner = root.findViewById(R.id.chooseFilm);
        idcliente = root.findViewById(R.id.idcliente);
        giorni = root.findViewById(R.id.giorni);
        giorniritardo = root.findViewById(R.id.giorniritardo);

        chooseSpinner.setAdapter(adapterFilm);

        /**
         * sincronizzo i film con il database
         */
        syncFilms();

        Button send = root.findViewById(R.id.send2);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (idcliente.getText().toString().equals("") ||
                        giorni.getText().toString().equals("") ||
                        giorniritardo.getText().toString().equals("")) {
                    //System.out.println("###");
                    Toast.makeText(requireContext(), "Inserisci tutti i campi", Toast.LENGTH_SHORT).show();
                } else {
                    setNoleggio();
                    Toast.makeText(requireContext(), "Noleggio inserito", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return root;
    }

    public void syncFilms(){
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        DatabaseReference ref = database.child("Film");

        /**
         * scraica il valore massimo fino a cui cercare
         */
        ref.child("value").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    task.getException();
                }
                else {
                    int value = Integer.parseInt(task.getResult().getValue().toString());

                    if (value == 0) loading.setVisibility(View.GONE);
                    /**
                     * salva ogni film negli array
                     */
                    for (int i = 0; i <= value; i++){
                        DatabaseReference number = ref.child(String.valueOf(i));

                        int finalI = i;
                        number.child("codice").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DataSnapshot> task) {
                                Object object = task.getResult().getValue();
                                if (object != null) {
                                    String filmCode = String.valueOf(object);

                                    number.child("titolo").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DataSnapshot> task) {
                                            Object object = task.getResult().getValue();
                                            String filmTitle = String.valueOf(object);

                                            number.child("trama").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<DataSnapshot> task) {
                                                    Object object = task.getResult().getValue();
                                                    String filmTrama = String.valueOf(object);

                                                    /**
                                                     * aggiunge agli array il film e aggiorna l'adapter
                                                     */
                                                    filmClassList.add(new Film(Integer.parseInt(filmCode), filmTitle, filmTrama));
                                                    listFilm.add(filmTitle);

                                                    //System.out.println(listFilm + " - " + value + " " + finalI);
                                                    if (finalI == value) adapterFilm.notifyDataSetChanged();

                                                    loading.setVisibility(View.GONE);
                                                }
                                            });
                                        }
                                    });
                                }
                            }
                        });
                    }
                }
            }
        });
    }

    public void setNoleggio(){
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        DatabaseReference ref = database.child("Noleggio");

        /**
         * Legge il primo valore disponibile in cui inserire il noleggio
         */
        ref.child("value").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    task.getException();
                }
                else {
                    int value = Integer.parseInt(task.getResult().getValue().toString());

                    /**
                     * modifica il valore ed inserisce il noleggio
                     */
                    DatabaseReference ccDB = ref.child("value");
                    ccDB.setValue(value + 1);

                    Film film = findFilm(chooseSpinner.getSelectedItem().toString());

                    DatabaseReference codiceDB = ref.child(String.valueOf(value + 1)).child("codice");
                    codiceDB.setValue(film.getCodice());

                    DatabaseReference titoloDB = ref.child(String.valueOf(value + 1)).child("titolo");
                    titoloDB.setValue(film.getTitolo());

                    DatabaseReference tramaDB = ref.child(String.valueOf(value + 1)).child("trama");
                    tramaDB.setValue(film.getTrama());

                    DatabaseReference idclienteDB = ref.child(String.valueOf(value + 1)).child("idcliente");
                    idclienteDB.setValue(idcliente.getText().toString());

                    DatabaseReference giorniDB = ref.child(String.valueOf(value + 1)).child("giorni");
                    giorniDB.setValue(giorni.getText().toString());

                    DatabaseReference giorniritardoDB = ref.child(String.valueOf(value + 1)).child("giorniritardo");
                    giorniritardoDB.setValue(giorniritardo.getText().toString());

                    idcliente.setText("");
                    giorni.setText("");
                    giorniritardo.setText("");
                }
            }
        });
    }

    /**
     * trova il film in base al titolo
     * @param item Titolo
     * @return Film con il titolo inserito
     */
    private Film findFilm(String item){
        for (int i = 0; i < filmClassList.size(); i++){
            if (filmClassList.get(i).getTitolo().equals(item)) return filmClassList.get(i);
        }
        return null;
    }

}