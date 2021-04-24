package com.nicolasdelton.films.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.nicolasdelton.films.R;
import com.nicolasdelton.films.film.Film;
import com.nicolasdelton.films.film.Noleggio;

import java.util.ArrayList;

public class FilmFragment extends Fragment {

    private ArrayList<Film> films = new ArrayList<>();
    private ArrayList<Noleggio> noleggi = new ArrayList<>();

    private Button send;
    private TextView codice, titolo, trama;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_film, container, false);

        send = root.findViewById(R.id.send);
        codice = root.findViewById(R.id.codice);
        titolo = root.findViewById(R.id.titolo);
        trama = root.findViewById(R.id.trama);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                System.out.println("##########" + codice.getText() + "##");
//                System.out.println("##########" + titolo.getText() + "##");
//                System.out.println("##########" + trama.getText() + "##");

                if (codice.getText().toString().equals("") ||
                titolo.getText().toString().equals("") ||
                trama.getText().toString().equals("")) {
                    System.out.println("###");
                    Toast.makeText(requireContext(), "Inserisci tutti i campi", Toast.LENGTH_SHORT).show();
                } else {
                    inserisciFilm();
                    Toast.makeText(requireContext(), "Film inserito", Toast.LENGTH_SHORT).show();
                    codice.setText("");
                    titolo.setText("");
                    trama.setText("");
                }
            }
        });

        return root;
    }

    private void inserisciFilm(){
        films.add(new Film(Integer.parseInt(codice.getText().toString()), titolo.getText().toString(), trama.getText().toString()));
        databaseAdd();
    }

    Integer value = 1;

    private void databaseAdd(){
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

        DatabaseReference ccDB = database.getReference("Film/value");
        ccDB.setValue(value);

        DatabaseReference codiceDB = database.getReference("Film/" + value + "/codice");
        codiceDB.setValue(codice.getText().toString());

        DatabaseReference titoloDB = database.getReference("Film/" + value + "/titolo");
        titoloDB.setValue(titolo.getText().toString());

        DatabaseReference tramaDB = database.getReference("Film/" + value + "/trama");
        tramaDB.setValue(trama.getText().toString());


       // Film film = new Film(Integer.parseInt(codice.getText().toString()), titolo.getText().toString(), trama.getText().toString());
    }
}