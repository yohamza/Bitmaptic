package us.technerd.tn_bitmaptic_example;

import android.graphics.Bitmap;
import android.graphics.BitmapRegionDecoder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;

import us.technerd.bitmaptic.Bitmaptic;

public class MainActivity extends AppCompatActivity {

    ImageView specimen;
    Button rotate, blur, aspect, pixels;
    private SeekBar seekBar;
    Bitmap bitmap = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        specimen = findViewById(R.id.specimen);
        blur = findViewById(R.id.blur);
        rotate = findViewById(R.id.rotate);
        aspect = findViewById(R.id.aspect);
        pixels = findViewById(R.id.pixels);
        seekBar = findViewById(R.id.blur_radius);


        bitmap = Bitmaptic.getBitmapFromImageView(specimen);


        seekBar.setOnSeekBarChangeListener(onSeekBarChanged());

        rotate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bitmap = Bitmaptic.rotateBitmap(bitmap, 90);
                specimen.setImageBitmap(bitmap);
            }
        });

        aspect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bitmap = Bitmaptic.scaleToSizeKeepingAspectRatio(bitmap, 1000, true);
                specimen.setImageBitmap(bitmap);
            }
        });

        pixels.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bitmap = Bitmaptic.scaleToPixelQuality(bitmap, 400, 400);
                specimen.setImageBitmap(bitmap);
            }
        });


    }
    private SeekBar.OnSeekBarChangeListener onSeekBarChanged() {
        return new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                float radius = (float) MainActivity.this.seekBar.getProgress();
                specimen.setImageBitmap(Bitmaptic.blurBitmap(bitmap, MainActivity.this,radius));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        };
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
