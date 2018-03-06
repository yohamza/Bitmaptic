package us.technerd.bitmaptic;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.util.Log;
import android.widget.ImageView;

import java.io.IOException;

/**
 * Created by Hamza on 3/6/2018.
 */

public class Bitmaptic {

    public static Bitmap scaleToSizeKeepingAspectRatio(Bitmap realImage, float maxImageSize, boolean filter)    {
        float ratio = Math.min(
                (float) maxImageSize / realImage.getWidth(),
                (float) maxImageSize / realImage.getHeight());
        int width = Math.round((float) ratio * realImage.getWidth());
        int height = Math.round((float) ratio * realImage.getHeight());

        return Bitmap.createScaledBitmap(realImage, width,
                height, filter);
    }

    public static Bitmap scaleToPixelQuality(Bitmap bitmap, int width, int height){
        Bitmap scaled = Bitmap.createScaledBitmap(bitmap, width, height,true);
        return scaled;
    }

    public static Bitmap rotateBitmap(Bitmap source,float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }

    public static Bitmap blurBitmap(Bitmap src, Context context, float r) {
        if (r <= 0) {
            r = 0.1f;
        } else if (r > 25) {
            r = 25.0f;
        }

        Bitmap bitmap = Bitmap.createBitmap(src.getWidth(), src.getHeight(), Bitmap.Config.ARGB_8888);
        RenderScript renderScript = RenderScript.create(context);

        Allocation blurInput = Allocation.createFromBitmap(renderScript, src);
        Allocation blurOutput = Allocation.createFromBitmap(renderScript, bitmap);

        ScriptIntrinsicBlur blur = ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript));
        blur.setInput(blurInput);
        blur.setRadius(r);
        blur.forEach(blurOutput);

        blurOutput.copyTo(bitmap);
        renderScript.destroy();

        return bitmap;
    }

    public static Bitmap convertUritoBitmap(Uri imageUri, Context context){
        Bitmap bitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), imageUri);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    public static Bitmap newInstance(Bitmap param){
        return param.copy(param.getConfig(), true);
    }

    public static Bitmap getBitmapFromImageView(ImageView v){
        final ImageView view = (ImageView) v;

        Bitmap bm=((BitmapDrawable)view.getDrawable()).getBitmap();

        return bm;
    }

}
