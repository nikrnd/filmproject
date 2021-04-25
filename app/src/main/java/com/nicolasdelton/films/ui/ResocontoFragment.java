package com.nicolasdelton.films.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nicolasdelton.films.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ResocontoFragment extends Fragment {

    ListView films;
    ArrayList<String> listFilm;
    ArrayAdapter<String> itemsAdapter;
    ConstraintLayout loading;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_resoconto, container, false);

        films = root.findViewById(R.id.filmList);
        loading = root.findViewById(R.id.loading);

        listFilm = new ArrayList<String>();

        itemsAdapter = new ArrayAdapter<String>( requireActivity(), android.R.layout.simple_list_item_1, listFilm);

        syncFilms();


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
                                    itemsAdapter.notifyDataSetChanged();
                                    //System.out.println("####" + listFilm);

                                    films.setAdapter(itemsAdapter);
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