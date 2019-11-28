package com.example.figur;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.room.Room;

import com.example.figur.R;
import com.example.figur.ui.ImageAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.InputStream;
import java.util.ArrayList;

import Classes.Card;
import Classes.User;
import Classes.UserCollection;
import Database.AppDatabase;

import static java.util.Objects.isNull;


public class FullImageActivity extends Activity {

    private AppDatabase db;

    public void initializeDatabase(){
        //Instance of the initializeDatabase
        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "figur.db").allowMainThreadQueries().build();
    }

    public AppDatabase getDb(){
        return this.db;
    }

    ProgressDialog pDialog;
    ImageView img;
    Bitmap bitmap;
    String url;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fullimageview);

        final FloatingActionButton deleteCardButton = findViewById(R.id.btn_deleteCard);

        String userMail = getIntent().getStringExtra("userMail");

        initializeDatabase();

        User user = getDb().userDao().findByMail(userMail);

        Intent i = getIntent();
        int position = i.getExtras().getInt("id");
        ArrayList<String> cardsAux = i.getStringArrayListExtra("userCards");

        ImageAdapter imageAdapter = new ImageAdapter(this);

        if(!cardsAux.isEmpty()) {
            cardsAux.forEach(link -> {imageAdapter.setItem(link);});

            img = findViewById(R.id.image);
            url = imageAdapter.getItem(position);

            new DownloadImage().execute(url);
        }else{
            Toast.makeText(getApplicationContext(), "No cards found", Toast.LENGTH_SHORT).show();
        }

        deleteCardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Card card = null;

                if(!cardsAux.isEmpty()) {
                    cardsAux.forEach(link -> {imageAdapter.setItem(link);});

                    img = findViewById(R.id.image);
                    url = imageAdapter.getItem(position);

                    if(!getDb().cardDao().getAll().isEmpty() && !isNull(url)){
                        if(getDb().cardDao().getAll().stream().filter(card1 -> card1.getLink().equalsIgnoreCase(url)).count() > 0){
                            card = getDb().cardDao().getAll().stream().filter(card1 -> card1.getLink().equalsIgnoreCase(url)).findFirst().get();
                        }
                    }

                }

                if(!(getDb().userCollectionDao().getCardsForUser(user.getId()).isEmpty())){
                    if(!isNull(card)){

                        final int cardId = card.getId();

                        UserCollection userCollection = getDb().userCollectionDao().getAll().stream().filter(userColl -> userColl.getIdCard() == cardId).findFirst().get();

                        if(!isNull(userCollection)){
                            getDb().userCollectionDao().delete(userCollection);

                            showDeleteMessage("The image was successfully deleted from the collection");

                            Intent cameraActivity = new Intent(FullImageActivity.this, MainActivity.class);
                            cameraActivity.putExtra("userMail", userMail);
                            startActivity(cameraActivity);
                            finish();
                        }else{
                            showDeleteMessage("The image was not found in the user collection");
                        }
                    }else{
                        showDeleteMessage("The image was not found");
                    }
                }else{
                    showDeleteMessage("No image was not found in the user collection");
                }
            }
        });
    }
    private class DownloadImage extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... URL) {
            String imageURL = URL[0];
            Bitmap bitmap = null;
            try {
                InputStream input = new java.net.URL(imageURL).openStream();
                bitmap = BitmapFactory.decodeStream(input);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            img.setImageBitmap(result);
        }
    }

    private void showDeleteMessage(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}