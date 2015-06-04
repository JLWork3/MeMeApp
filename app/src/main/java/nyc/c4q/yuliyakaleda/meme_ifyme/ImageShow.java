package nyc.c4q.yuliyakaleda.meme_ifyme;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.FileNotFoundException;

/**
 * Created by c4q-nali on 5/31/15.
 */
public class ImageShow extends Activity {
    private Uri u;
    Button buttonVanilla;
    SharedPreferences savednotes;
    private Bitmap userBitmap;

    //show the pic choose from Gallery to show on imageshow activity.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.image_view);

        Intent i = this.getIntent();
        u = this.getIntent().getParcelableExtra("imageURI");

        Log.d("URI", u.toString());
        ImageView img = (ImageView) findViewById(R.id.imgView);
        img.setImageURI(u);

        try {
            userBitmap = decodeUri(this, u, 300);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        //click the button and show vinalla effect.


//        buttonVanilla = (Button) findViewById(R.id.vanilla);
//        final EditText addText1 = (EditText) findViewById(R.id.addText1);
//        EditText addText2 = (EditText) findViewById(R.id.addText2);
//        EditText addText3 = (EditText) findViewById(R.id.addText3);


    }


    //adjust the size of pic which is too large to load in this activity.
    public static Bitmap decodeUri(Context c, Uri uri, final int requiredSize)
            throws FileNotFoundException {
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(c.getContentResolver().openInputStream(uri), null, o);

        int width_tmp = o.outWidth, height_tmp = o.outHeight;
        int scale = 1;

        while (true) {
            if (width_tmp / 2 < requiredSize || height_tmp / 2 < requiredSize)
                break;
            width_tmp /= 2;
            height_tmp /= 2;
            scale *= 2;
        }

        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o2.inSampleSize = scale;
        return BitmapFactory.decodeStream(c.getContentResolver().openInputStream(uri), null, o2);
    }
}

