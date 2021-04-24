package com.nicolasdelton.films.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

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

        return root;
    }

    private void inserisciFilm(){
        films.add(new Film(Integer.parseInt(codice.getText().toString()), titolo.getText().toString(), trama.getText().toString()));
    }

    private void inserisciNoleggio(Film film){

    }

    private void databaseAdd(Film film){

    }
}