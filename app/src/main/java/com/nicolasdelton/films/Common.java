package com.nicolasdelton.films;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nicolasdelton.films.film.Film;
import com.nicolasdelton.films.film.Noleggio;

import java.util.ArrayList;
import java.util.List;

public class Common {

    public static ArrayAdapter<Film> adapterFilm;
    public static ArrayList<Film> films = new ArrayList<>();
    public static ArrayList<Noleggio> noleggio = new ArrayList<>();
    public static ArrayAdapter<Noleggio> adapterNoleggio;

    public static ArrayAdapter syncFilms(Context context){
        adapterFilm = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, films);

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
                                            //System.out.println("####" + listFilm);

                                            number.child("trama").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<DataSnapshot> task) {
                                                    Object object = task.getResult().getValue();
                                                        String filmTrama = String.valueOf(object);
                                                        films.add(new Film(Integer.parseInt(filmCode), filmTitle, filmTrama));
                                                        System.out.println(films);
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

        return adapterFilm;
    }

    public void syncNoleggi(){

    }

}
