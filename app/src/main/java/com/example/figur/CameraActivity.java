package com.example.figur;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import Classes.Card;
import Classes.User;
import Classes.UserCollection;
import Database.AppDatabase;

import static java.util.Objects.isNull;

public class CameraActivity extends AppCompatActivity {

    private AppDatabase db;

    public void initializeDatabase(){
        //Instance of the initializeDatabase
        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "figur.db").allowMainThreadQueries().build();
    }

    public AppDatabase getDb(){
        return this.db;
    }

    /** Check if this device has a CameraActivity */
    private boolean checkCameraHardware(Context context) {
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
            // this device has a CameraActivity
            return true;
        } else {
            // no CameraActivity on this device
            return false;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        String userMail = getIntent().getStringExtra("userMail");

        initializeDatabase();

        User user = getDb().userDao().findByMail(userMail);

        Card card1 = new Card(1,"powerful_nature_national_geographic_qr", "powerfulNatureNG","Powerful Nature - National Geographic","https://www.nationalgeographic.com.es/medio/2019/03/01/powerful-nature_ab6a252f_1200x801.jpg");
        if(getDb().cardDao().getAll().isEmpty() || getDb().cardDao().getAll().stream().filter(card -> !isNull(card.getQrCode())).count() == 0 ||
                (getDb().cardDao().getAll().stream().filter(card -> !isNull(card.getQrCode())).count() > 0
                        && getDb().cardDao().getAll().stream().filter(card -> !isNull(card.getQrCode())).filter(card -> card.getQrCode().matches("powerful_nature_national_geographic_qr")).count() > 0)){

            getDb().cardDao().insertAll(card1);
        }
        //Wiring up the Button
        /*
            This sets up the event handler (onClick) for when the user presses the button.
            When they do that, we want to load the bar code, process it for data,
            and write that data to the TextView.
        */

        Button btn = (Button) findViewById(R.id.button);

        //In the guide, this code was placed in the Decode the Barcode section,
        // but since txtView is also used in the Setup the Barcode Detector section I decided tu place it here.

        TextView txtView = (TextView) findViewById(R.id.txtContent);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Load the Image
                /*
                We do this first by getting a reference to our image view, that we call ‘myImageView'
                 Then you use a BitMapFactory to decode the R.drawable.puppy resources into a Bitmap.
                 Then you set it to be the bitmap for myImageView.
                 */

                ImageView myImageView = (ImageView) findViewById(R.id.imgview);

                Context context = myImageView.getContext();
                int drawableId = context.getResources().getIdentifier("powerful_nature_national_geographic_qr", "drawable", context.getPackageName());

                /*Bitmap myBitmap2 = BitmapFactory.decodeResource(
                getApplicationContext().getResources(),
                R.drawable.powerful_nature_national_geographic_qr);*/

                Bitmap myBitmap = BitmapFactory.decodeResource(
                        getApplicationContext().getResources(),
                        drawableId);
                myImageView.setImageBitmap(myBitmap);


                //Setup the Barcode Detector
                /*
                We create our new BarcodeDetector using a builder,
                and tell it to look for QR codes and Data Matrices
                (there are a lot of other barcode types we could also look for).
                 */

                BarcodeDetector detector =
                        new BarcodeDetector.Builder(getApplicationContext())
                                .setBarcodeFormats(Barcode.DATA_MATRIX | Barcode.QR_CODE)
                                .build();
                if(!detector.isOperational()){
                    txtView.setText("Could not set up the detector. Please report this error.");
                    return;
                }else{
                    //Detect the Barcode
                /*
                This code creates a frame from the bitmap, and passes it to the detector.
                This returns a SparseArray of barcodes.
                The API is capable of detecting multiple bar codes in the same frame.
                 */

                    Frame frame = new Frame.Builder().setBitmap(myBitmap).build();
                    SparseArray<Barcode> barcodes = detector.detect(frame);

                    //Decode the Barcode
                /*
                Typically in this step you would iterate through the SparseArray,
                and process each bar code independently.
                Usually, we need to allow for the possibility that there won't be any barcodes, or there might be several.
                Because for this sample, I know I have 1 and only 1 bar code, I can hard code for it.
                To do this, I take the Barcode called ‘thisCode' to be the first element in the array.
                I then assign it's rawValue to the textView
                 */

                    Barcode thisCode = barcodes.valueAt(0);
                    //TextView txtView = (TextView) findViewById(R.id.txtContent);
                    txtView.setText(thisCode.rawValue);

                    if(getDb().userCollectionDao().getCardsForUser(user.getId()).isEmpty() || getDb().userCollectionDao().getCardsForUser(user.getId()).stream().filter(card -> !isNull(card.getQrCode())).count() == 0 ||
                            (getDb().userCollectionDao().getCardsForUser(user.getId()).stream().filter(card -> !isNull(card.getQrCode())).count() > 0
                                    && getDb().userCollectionDao().getCardsForUser(user.getId()).stream().filter(card -> !isNull(card.getQrCode())).filter(card -> card.getQrCode().matches("powerful_nature_national_geographic_qr")).count() == 0)) {

                        Card card = null;
                        if(!getDb().cardDao().getAll().isEmpty() && getDb().cardDao().getAll().stream().filter(cardStream -> !isNull(cardStream.getQrCode())).count() > 0){
                            card = getDb().cardDao().getAll().stream().filter(cardStream -> !isNull(cardStream.getQrCode())).filter(cardStream -> cardStream.getQrCode().matches("powerful_nature_national_geographic_qr")).findFirst().get();
                        }

                        if(!isNull(card)){
                            try{
                                UserCollection userCollection = new UserCollection(user.getId(),card.getId());
                                getDb().userCollectionDao().insertAll(userCollection);

                                showMessage("The card was successfully added to your collection!");

                                Intent mainActivity = new Intent(CameraActivity.this, MainActivity.class);
                                mainActivity.putExtra("userMail", userMail);
                                startActivity(mainActivity);
                                finish();
                            }catch(Exception e){
                                showMessage("The card could not be added to your collection");
                            }
                        }else{
                            showMessage("The card was not found. Please report this error.");
                        }
                    }else{
                        showMessage("The card is already in your collection.");
                    }
                }
            }
        });
    }

    private void showMessage(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}
