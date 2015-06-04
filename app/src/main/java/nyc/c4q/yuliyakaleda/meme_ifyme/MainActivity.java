package nyc.c4q.yuliyakaleda.meme_ifyme;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.widget.ImageView;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;


public class MainActivity extends ActionBarActivity {

    Button buttonLoadPic;
    Button buttonTakePic;
    static final int REQUEST_IMAGE_LOAD = 1;
    Uri u;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("onActivityResult", "Something came back " + requestCode);
        u = data.getData();

        Intent imgView = new Intent(this, ImageShow.class);
        imgView.putExtra("imageURI", u);
        Log.d("URI", u.toString());
        startActivity(imgView);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("onCreate", "start!");
//    This is the button for load and choose a pic from gallery.
        buttonLoadPic = (Button) findViewById(R.id.buttonLoadPic);
        buttonTakePic = (Button) findViewById(R.id.buttonTakePic);


        buttonLoadPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent loadPic = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(Intent.createChooser(loadPic, "Select file"), REQUEST_IMAGE_LOAD);

            }
        });



    }

    //    function that invokes an intent to capture a photo.
    private void dispatchLoadPictureIntent() {
        Intent loadPicIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (loadPicIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(loadPicIntent, REQUEST_IMAGE_LOAD);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
