package com.nicolasdelton.films.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import java.util.ArrayList;

public class ResocontoFragment extends Fragment {

    ListView films, noleggi;
    ArrayList<String> listFilm, listNoleggi;
    ArrayAdapter<String> adapterFilm, adapterNoleggi;
    ConstraintLayout loading;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_resoconto, container, false);

        films = root.findViewById(R.id.filmList);
        noleggi = root.findViewById(R.id.noleggList);
        loading = root.findViewById(R.id.loading);

        listFilm = new ArrayList<String>();

        adapterFilm = new ArrayAdapter<String>( requireActivity(), android.R.layout.simple_list_item_1, listFilm);

        syncFilms();
        syncNoleggi();

        return root;
    }

    public void syncFilms(){
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        DatabaseReference ref = database.child("Film");

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
                                    listFilm.add(filmTitle);
                                    adapterFilm.notifyDataSetChanged();
                                    //System.out.println("####" + listFilm);

                                    films.setAdapter(adapterFilm);
                                }
                            }
                        });
                    }

                    loading.setVisibility(View.GONE);
                }
            }
        });
    }

    public void syncNoleggi(){
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