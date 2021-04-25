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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.nicolasdelton.films.R;
import com.nicolasdelton.films.film.Film;
import com.nicolasdelton.films.film.Noleggio;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class FilmFragment extends Fragment {

    private Button send;
    private TextView codice, titolo, trama;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_film, container, false);

        send = root.findViewById(R.id.send);
        codice = root.findViewById(R.id.codice);
        titolo = root.findViewById(R.id.titolo);
        trama = root.findViewById(R.id.trama);

        //removeFilm(2);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                System.out.println("##########" + codice.getText() + "##");
//                System.out.println("##########" + titolo.getText() + "##");
//                System.out.println("##########" + trama.getText() + "##");

                if (codice.getText().toString().equals("") ||
                        titolo.getText().toString().equals("") ||
                        trama.getText().toString().equals("")) {
                    //System.out.println("###");
                    Toast.makeText(requireContext(), "Inserisci tutti i campi", Toast.LENGTH_SHORT).show();
                } else {
                    databaseAdd();
                    Toast.makeText(requireContext(), "Film inserito", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return root;
    }

    private void databaseAdd(){
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

                    DatabaseReference ccDB = ref.child("value");
                    ccDB.setValue(value + 1);

                    DatabaseReference codiceDB = ref.child(String.valueOf(value + 1)).child("codice");
                    codiceDB.setValue(codice.getText().toString());

                    DatabaseReference titoloDB = ref.child(String.valueOf(value + 1)).child("titolo");
                    titoloDB.setValue(titolo.getText().toString());

                    DatabaseReference tramaDB = ref.child(String.valueOf(value + 1)).child("trama");
                    tramaDB.setValue(trama.getText().toString());

                    codice.setText("");
                    titolo.setText("");
                    trama.setText("");
                }
            }
        });
//


        // Film film = new Film(Integer.parseInt(codice.getText().toString()), titolo.getText().toString(), trama.getText().toString());
    }
}