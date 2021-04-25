package com.nicolasdelton.films.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nicolasdelton.films.R;
import com.nicolasdelton.films.film.Film;

import java.util.ArrayList;

public class NoleggioFragment extends Fragment {

    private ArrayList<Film> films;
    private Spinner chooseSpinner;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_noleggio, container, false);

        films = new ArrayList<>();

        //syncDB();

        for (int i = 0; i < films.size(); i++) {
//            System.out.println("############");
//            System.out.println(films.get(i).getCodice());
//            System.out.println(films.get(i).getTitolo());
//            System.out.println(films.get(i).getTrama());
        }

//        chooseSpinner = root.findViewById(R.id.chooseFilm);
//        ArrayAdapter<Film> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_dropdown_item, films);
//        chooseSpinner.setAdapter(adapter);



        return root;
    }

    private Integer value = 1;
    private String codice, titolo, trama;

    private void syncDB() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Film/value");
        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                value = ((dataSnapshot.getValue(Integer.class)) + 1);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });

        System.out.println("####" + value);

        DatabaseReference refCodice = database.getReference("Film/1/codice");
            // Read from the database
            refCodice.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                     codice = dataSnapshot.getValue(String.class);
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                }
            });

        DatabaseReference refTitolo = database.getReference("Film/1/titolo");
            // Read from the database
            refTitolo.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    titolo = dataSnapshot.getValue(String.class);
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                }
            });

            DatabaseReference refTrama = database.getReference("Film/1/trama");
            // Read from the database
            refTrama.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    trama = dataSnapshot.getValue(String.class);
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                }
            });

        System.out.println("####" + codice + "; " + titolo + "; " + trama);

//        for (int i = 1; i <= value; i++) {
//            DatabaseReference refCodice = database.getReference("Film/" + i + "/codice");
//            // Read from the database
//            refCodice.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(DataSnapshot dataSnapshot) {
//                    // This method is called once with the initial value and again
//                    // whenever data at this location is updated.
//                     codice = dataSnapshot.getValue(Integer.class);
//                }
//
//                @Override
//                public void onCancelled(DatabaseError error) {
//                    // Failed to read value
//                }
//            });
//
//            DatabaseReference refTitolo = database.getReference("Film/" + i + "/titolo");
//            // Read from the database
//            refTitolo.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(DataSnapshot dataSnapshot) {
//                    // This method is called once with the initial value and again
//                    // whenever data at this location is updated.
//                    titolo = dataSnapshot.getValue(String.class);
//                }
//
//                @Override
//                public void onCancelled(DatabaseError error) {
//                    // Failed to read value
//                }
//            });
//
//            DatabaseReference refTrama = database.getReference("Film/" + i + "/trama");
//            // Read from the database
//            refTrama.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(DataSnapshot dataSnapshot) {
//                    // This method is called once with the initial value and again
//                    // whenever data at this location is updated.
//                    trama = dataSnapshot.getValue(String.class);
//                }
//
//                @Override
//                public void onCancelled(DatabaseError error) {
//                    // Failed to read value
//                }
//            });
//
//            //films.add(new Film(Integer.parseInt(codice), titolo, trama));
//
//            System.out.println("####" + codice + "; " + titolo + "; " + trama);
//        }
    }


}