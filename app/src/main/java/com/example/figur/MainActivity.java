package com.example.figur;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import Database.AppDatabase;

public class MainActivity extends AppCompatActivity {

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

        //Wiring up the Button
        /*
            This sets up the event handler (onClick) for when the user presses the button.
            When they do that, we want to load the bar code, process it for data,
            and write that data to the TextView.
        */
        Button btn = (Button) findViewById(R.id.button);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        //Load the Image
        /*
        We do this first by getting a reference to our image view, that we call ‘myImageView'
         Then you use a BitMapFactory to decode the R.drawable.puppy resources into a Bitmap.
         Then you set it to be the bitmap for myImageView.
         */
        ImageView myImageView = (ImageView) findViewById(R.id.imgview);
        Bitmap myBitmap = BitmapFactory.decodeResource(
                getApplicationContext().getResources(),
                R.drawable.puppy);
        myImageView.setImageBitmap(myBitmap);

        //In the guide, this code was placed in the Decode the Barcode section,
        // but since txtView is also used in the Setup the Barcode Detector section I decided tu place it here.
        TextView txtView = (TextView) findViewById(R.id.txtContent);


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
            txtView.setText("Could not set up the detector!");
            return;
        }

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

    }
}
