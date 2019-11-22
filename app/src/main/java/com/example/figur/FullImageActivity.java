package com.example.figur;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.figur.R;
import com.example.figur.ui.ImageAdapter;

import java.io.InputStream;
import java.util.ArrayList;


public class FullImageActivity extends Activity {

    ProgressDialog pDialog;
    ImageView img;
    Bitmap bitmap;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fullimageview);

        Intent i = getIntent();
        int position = i.getExtras().getInt("id");
        ArrayList<String> cardsAux = i.getStringArrayListExtra("userCards");

        ImageAdapter imageAdapter = new ImageAdapter(this);

        if(!cardsAux.isEmpty()) {
            cardsAux.forEach(link -> imageAdapter.setItem(link));

            img = findViewById(R.id.image);
            String url = imageAdapter.getItem(position);

            new DownloadImage().execute(url);
        }else{
            Toast.makeText(getApplicationContext(), "No cards found", Toast.LENGTH_SHORT).show();
        }
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
}