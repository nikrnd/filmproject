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
import com.nicolasdelton.films.Common;
import com.nicolasdelton.films.R;
import com.nicolasdelton.films.film.Film;

import java.util.ArrayList;

public class ResocontoFragment extends Fragment {

    private ListView films, noleggi;
    private ArrayList<String> listNoleggi;
    private ArrayAdapter<String> adapterNoleggi;
    private ConstraintLayout loading;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_resoconto, container, false);

        noleggi = root.findViewById(R.id.noleggList);
        loading = root.findViewById(R.id.loading);
        loading.setVisibility(View.VISIBLE);
        listNoleggi = new ArrayList<>();
        adapterNoleggi = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_list_item_1, listNoleggi);

        films = root.findViewById(R.id.filmList);
        films.setAdapter(Common.syncFilms(requireContext()));

        syncNoleggi();

        films.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                new AlertDialog.Builder(requireContext())
                        .setTitle("Elimina")
                        .setMessage("Sicuro di voler eliminare il film: " + Common.films.get(position))

                        // Specifying a listener allows you to take an action before dismissing the dialog.
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Continue with delete operation
                                databaseFilmRemove(position);
                            }
                        })

                        // A null listener allows the button to dismiss the dialog and take no further action.
                        .setNegativeButton(android.R.string.no, null)
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

    private void databaseFilmRemove(int position){
        DatabaseReference databaseRead = FirebaseDatabase.getInstance().getReference();
        DatabaseReference ref = databaseRead.child("Film");

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

                    DatabaseReference removeFilm = ref.child(String.valueOf(position + 1));
                    removeFilm.setValue(null);

                    Common.films.remove(position);
                    Common.adapterFilm.notifyDataSetChanged();
                }
            }
        });

        //System.out.println("####"+listFilm);
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
}