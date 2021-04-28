package com.nicolasdelton.films.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nicolasdelton.films.R;
import com.nicolasdelton.films.film.Film;

import java.util.ArrayList;

public class ResocontoFragment extends Fragment {

    private ListView films, noleggi;
    private ArrayList<Film> filmArray = new ArrayList<>(), noleggiArray = new ArrayList<>();
    private ArrayList<String> listFilm = new ArrayList<>(), listNoleggi = new ArrayList<>();
    private ArrayAdapter<String> adapterFilm, adapterNoleggi;
    private ConstraintLayout loading;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_resoconto, container, false);

        films = root.findViewById(R.id.filmList);
        noleggi = root.findViewById(R.id.noleggList);
        loading = root.findViewById(R.id.loading);

        adapterFilm = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_list_item_1, listFilm);
        adapterNoleggi = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_list_item_1, listNoleggi);

        films.setAdapter(adapterFilm);

        /**
         * sincronizza le due liste
         */
        syncFilms();
        syncNoleggi();

        /**
         * controlla se ci sono click negli item
         */
        films.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /**
                 * apre l'alert
                 */
                new AlertDialog.Builder(requireContext())
                        .setTitle("Trama")
                        .setMessage(filmArray.get(position).getTrama())

                        // Specifying a listener allows you to take an action before dismissing the dialog.
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        .setPositiveButton("Elimina", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Continue with delete operation
                                /**
                                 * se premuto "Elimina" elimina il film dal database e dagli array
                                 */
                                removeFilm(filmArray.get(position).getTitolo());
                            }
                        })

                        // A null listener allows the button to dismiss the dialog and take no further action.
                        .setNegativeButton(android.R.string.ok, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });

        noleggi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                new AlertDialog.Builder(requireContext())
                        .setTitle("Elimina")
                        .setMessage("Sicuro di voler eliminare il noleggio?")

                        // Specifying a listener allows you to take an action before dismissing the dialog.
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Continue with delete operation
                                databaseNoleggiRemove(position);
                            }
                        })

                        // A null listener allows the button to dismiss the dialog and take no further action.
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
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
                                                    filmArray.add(new Film(Integer.parseInt(filmCode), filmTitle, filmTrama));
                                                    listFilm.add(filmTitle);
                                                    if (finalI == value) adapterFilm.notifyDataSetChanged();
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

    private void removeFilm(String titolo){
        DatabaseReference databaseRead = FirebaseDatabase.getInstance().getReference();
        DatabaseReference ref = databaseRead.child("Film");

        /**
         * legge il valore massimo sul DATABASE fino a cui cercare
         */
        ref.child("value").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    task.getException();
                }
                else {
                    /**
                     * legge valore dal DARTABASE
                     */
                    int value = Integer.parseInt(task.getResult().getValue().toString());

                    /**
                     * cerca nel database il titolo da cancellare
                     */
                    for (int i = 0; i <= value; i++) {
                        int finalI = i;
                        ref.child(String.valueOf(i)).child("titolo").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DataSnapshot> task) {
                                Object object = task.getResult().getValue();

                                if (object != null && String.valueOf(object).equals(titolo)) {
                                    /**
                                     * rimuove film dal database
                                     */
                                    DatabaseReference removeFilm = ref.child(String.valueOf(finalI));
                                    removeFilm.setValue(null);

                                    /**
                                     * rimuove film dalle liste e aggiorna lo spinner
                                     */
                                    //System.out.println(listFilm);
                                    filmArray.remove(titolo);
                                    listFilm.remove(titolo);
                                    adapterFilm.notifyDataSetChanged();
                                    //System.out.println(listFilm);
                                }
                            }
                        });
                    }
                    //System.out.println("####" + position);
                }
            }
        });
    }

    private void syncNoleggi(){
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        DatabaseReference ref = database.child("Noleggio");

        ref.child("value").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    task.getException();
                }
                else {
                    int value = Integer.parseInt(task.getResult().getValue().toString());

                    for (int i = 0; i <= value; i++){
                        DatabaseReference number = ref.child(String.valueOf(i));

                        number.child("titolo").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DataSnapshot> task) {
                                Object object = task.getResult().getValue();
                                if (object != null) {
                                    String filmTitle = String.valueOf(object);
                                    listNoleggi.add(filmTitle);
                                    adapterNoleggi.notifyDataSetChanged();
                                    //System.out.println("####" + listFilm);

                                    noleggi.setAdapter(adapterNoleggi);
                                }
                            }
                        });
                    }

                    loading.setVisibility(View.GONE);
                }
            }
        });
    }

    private void databaseNoleggiRemove(int position){
        DatabaseReference databaseRead = FirebaseDatabase.getInstance().getReference();
        DatabaseReference ref = databaseRead.child("Noleggio");

        ref.child("value").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    task.getException();
                }
                else {
                    int value = Integer.parseInt(task.getResult().getValue().toString());

                    DatabaseReference ccDB = ref.child("value");
                    ccDB.setValue(value - 1);

                    //System.out.println("####" + position);

                    DatabaseReference removeNoleggio = ref.child(String.valueOf(position + 1));
                    removeNoleggio.setValue(null);
                }
            }
        });

        //System.out.println("####"+listFilm);
        listNoleggi.remove(position);
        adapterNoleggi.notifyDataSetChanged();
    }
}