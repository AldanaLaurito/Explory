package com.example.figur;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.figur.ui.ImageAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import Classes.Card;
import Classes.CollectorAlbum;
import Classes.User;
import Classes.UserCollection;
import Database.AppDatabase;

public class MainActivity extends AppCompatActivity {

    private AppDatabase db;

    public void initializeDatabase(){
        //Instance of the initializeDatabase
        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "figur.db").allowMainThreadQueries().build();
    }

    public AppDatabase getDb(){
        return this.db;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final FloatingActionButton cameraButton = findViewById(R.id.btn_camera);

        String userMail = getIntent().getStringExtra("userMail");

        initializeDatabase();

        User user = getDb().userDao().findByMail(userMail);

        if(getDb().collectorAlbumDao().getAll().isEmpty()){
            CollectorAlbum album = new CollectorAlbum("National Geographic - Landscapes");
            getDb().collectorAlbumDao().insertAll(album);
        }
        if(getDb().cardDao().getAll().isEmpty()){
            Card card1 = new Card(1,"acrossTheSkyNG","Across the sky of History - National Geographic","https://www.nationalgeographic.com.es/medio/2019/09/19/across-the-sky-of-history_82c9fd85_800x533.jpg");
            Card card2 = new Card(1,"theWatcher","The Watcher - National Geographic","https://www.nationalgeographic.com.es/medio/2019/09/19/the-watcher_4c5b7692_540x351.jpg");
            Card card3 = new Card(1,"galaticLightHouse","Galactic Lighthouse - National Geographic","https://www.nationalgeographic.com.es/medio/2019/09/19/galactic-lighthouse_ea927e18_1109x1200.jpg");
            Card card4 = new Card(1,"flowePower","Flower Power - National Geographic","https://www.nationalgeographic.com.es/medio/2019/09/19/flower-power_7da0a74e_1335x2000.jpg");
            Card card5 = new Card(1,"montesFoja","Montes Foja - National Geographic","https://www.nationalgeographic.com.es/medio/2019/10/31/montesfoja61_497b4943_800x800.jpg");
            getDb().cardDao().insertAll(card1,card2,card3,card4,card5);
        }
        if(getDb().userCollectionDao().getCardsForUser(user.getId()).isEmpty()){
            switch(user.getId()){
                case 1:
                    UserCollection userCollection1 = new UserCollection(1,1);
                    UserCollection userCollection2 = new UserCollection(1,2);
                    UserCollection userCollection3 = new UserCollection(1,3);
                    UserCollection userCollection4 = new UserCollection(1,4);
                    UserCollection userCollection5 = new UserCollection(1,5);
                    getDb().userCollectionDao().insertAll(userCollection1,userCollection2,userCollection3,userCollection4,userCollection5);
                    break;
                case 2:
                    UserCollection userCollection6 = new UserCollection(2,1);
                    UserCollection userCollection7 = new UserCollection(2,3);
                    UserCollection userCollection8 = new UserCollection(2,4);
                    getDb().userCollectionDao().insertAll(userCollection6,userCollection7,userCollection8);
                    break;
                case 3:
                    UserCollection userCollection9 = new UserCollection(3,1);
                    UserCollection userCollection10 = new UserCollection(3,2);
                    UserCollection userCollection11 = new UserCollection(3,3);
                    UserCollection userCollection12 = new UserCollection(3,4);
                    getDb().userCollectionDao().insertAll(userCollection9,userCollection10,userCollection11,userCollection12);
                    break;

            }
        }

        //Image Grid View Example
        final GridView gridview = findViewById(R.id.gridview);

        ImageAdapter imageAdapter = new ImageAdapter(this);

         List<Card> cards = getDb().userCollectionDao().getCardsForUser(user.getId());
        ArrayList<String> cardsAux = new ArrayList<String>();
         if(!cards.isEmpty()) {
             cards.forEach(card -> imageAdapter.setItem(card.getLink()));
             cards.forEach(card -> cardsAux.add(card.getLink()));
         }

        gridview.setAdapter(imageAdapter);
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Intent i = new Intent(getApplicationContext(), FullImageActivity.class);
                i.putExtra("id", position);
                i.putStringArrayListExtra("userCards",cardsAux);
                startActivity(i);
            }
        });

        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraActivity = new Intent(MainActivity.this, CameraActivity.class);
                cameraActivity.putExtra("userMail", userMail);
                startActivity(cameraActivity);
                finish();
            }
        });
    }
}
