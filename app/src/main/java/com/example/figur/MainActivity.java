package com.example.figur;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.SparseArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.figur.data.model.LoggedInUser;
import com.example.figur.ui.ImageAdapter;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

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

    /** Check if this device has a camera */
    private boolean checkCameraHardware(Context context) {
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
            // this device has a camera
            return true;
        } else {
            // no camera on this device
            return false;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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



        //TODO
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

        //TODO
        //I commented the barcoder part

        //Wiring up the Button
        /*
            This sets up the event handler (onClick) for when the user presses the button.
            When they do that, we want to load the bar code, process it for data,
            and write that data to the TextView.
        */

        //TODO
        //Button btn = (Button) findViewById(R.id.button);

        /*btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });*/

        //Load the Image
        /*
        We do this first by getting a reference to our image view, that we call ‘myImageView'
         Then you use a BitMapFactory to decode the R.drawable.puppy resources into a Bitmap.
         Then you set it to be the bitmap for myImageView.
         */

        //TODO
        /*ImageView myImageView = (ImageView) findViewById(R.id.imgview);
        Bitmap myBitmap = BitmapFactory.decodeResource(
                getApplicationContext().getResources(),
                R.drawable.puppy);
        myImageView.setImageBitmap(myBitmap);*/

        //In the guide, this code was placed in the Decode the Barcode section,
        // but since txtView is also used in the Setup the Barcode Detector section I decided tu place it here.

        //TODO
        //TextView txtView = (TextView) findViewById(R.id.txtContent);


        //Setup the Barcode Detector
        /*
        We create our new BarcodeDetector using a builder,
        and tell it to look for QR codes and Data Matrices
        (there are a lot of other barcode types we could also look for).
         */

        //TODO
        /*
        BarcodeDetector detector =
                new BarcodeDetector.Builder(getApplicationContext())
                        .setBarcodeFormats(Barcode.DATA_MATRIX | Barcode.QR_CODE)
                        .build();
        if(!detector.isOperational()){
            txtView.setText("Could not set up the detector!");
            return;
        }*/

        //Detect the Barcode
        /*
        This code creates a frame from the bitmap, and passes it to the detector.
        This returns a SparseArray of barcodes.
        The API is capable of detecting multiple bar codes in the same frame.
         */

        //TODO
        //Frame frame = new Frame.Builder().setBitmap(myBitmap).build();
        //SparseArray<Barcode> barcodes = detector.detect(frame);

        //Decode the Barcode
        /*
        Typically in this step you would iterate through the SparseArray,
        and process each bar code independently.
        Usually, we need to allow for the possibility that there won't be any barcodes, or there might be several.
        Because for this sample, I know I have 1 and only 1 bar code, I can hard code for it.
        To do this, I take the Barcode called ‘thisCode' to be the first element in the array.
        I then assign it's rawValue to the textView
         */

        //TODO
        /*Barcode thisCode = barcodes.valueAt(0);
        //TextView txtView = (TextView) findViewById(R.id.txtContent);
        txtView.setText(thisCode.rawValue);
        */

    }
}
