package nyc.c4q.yuliyakaleda.meme_ifyme;

import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by July on 5/31/15.
 */
public class SecondActivity extends Activity {


    String mCurrentPhotoPath;
    ImageView image;
    TextView tv;
    Button share;
    Uri myUri;
    Bitmap bm;
    File jpgFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(MainActivity.TAG, "SecondActivity.onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image);

        image = (ImageView) findViewById(R.id.image1);
        tv = (TextView) findViewById(R.id.text);
        share = (Button) findViewById(R.id.share_button);

        //get the uri from the intent sent from MainActivity
        Intent intent = getIntent();



        Log.d(MainActivity.TAG, String.format("SecondActivity.onCreate() intent:", intent));
        bm = intent.getParcelableExtra("bitmap");
        image.setImageBitmap(bm);




        share.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                File shareFile = null;
                try {
                    shareFile = createJpgFile(bm);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                shareVia(shareFile);
            }
        });

    }

    private File createJpgFile (Bitmap b) throws IOException {
        OutputStream fOut = null;
        jpgFile = createImageFile();
        try{
            fOut = new FileOutputStream(jpgFile);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        b.compress(Bitmap.CompressFormat.JPEG, 85, fOut);
        try {
            fOut.flush();
        } catch (IOException ex){
            ex.printStackTrace();
        }
        try {
            fOut.close();
        } catch (IOException ex){
            ex.printStackTrace();
        }

        try {
            MediaStore.Images.Media.insertImage(getContentResolver(), jpgFile.getAbsolutePath(), jpgFile.getName(), jpgFile.getName());
        } catch (IOException ex){
            ex.printStackTrace();
        }


        return jpgFile;
    }


    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
    }

    //method to share an image via social networks
    public void shareVia(File attachment) {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        attachment = null;
        try {
            attachment = createImageFile();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        if (attachment != null) {
            sharingIntent.setType("image/jpeg");
            sharingIntent.putExtra(Intent.EXTRA_STREAM, attachment.toURI());
            startActivity(Intent.createChooser(sharingIntent, "Share image using"));
        }
    }
}



