package us.technerd.bitmaptic;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import java.io.IOException;
import java.net.URI;

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

    public Bitmap scaleToPixelQuality(Bitmap bitmap, int width, int height){
        Bitmap scaled = Bitmap.createScaledBitmap(bitmap, width, height,true);
        return scaled;
    }

    public Bitmap rotateBitmap(Bitmap source,float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }

    public Bitmap blurBitmap(Bitmap bitmapParam, float scale, int radius) {

        int width = Math.round(bitmapParam.getWidth() * scale);
        int height = Math.round(bitmapParam.getHeight() * scale);
        bitmapParam = Bitmap.createScaledBitmap(bitmapParam, width, height, false);

        Bitmap bitmap = bitmapParam.copy(bitmapParam.getConfig(), true);

        if (radius < 1) { return (null); } int w = bitmap.getWidth(); int h = bitmap.getHeight(); int[] pix = new int[w * h]; Log.e("pix", w + " " + h + " " + pix.length); bitmap.getPixels(pix, 0, w, 0, 0, w, h); int wm = w - 1; int hm = h - 1; int wh = w * h; int div = radius + radius + 1; int r[] = new int[wh]; int g[] = new int[wh]; int b[] = new int[wh]; int rsum, gsum, bsum, x, y, i, p, yp, yi, yw; int vmin[] = new int[Math.max(w, h)]; int divsum = (div + 1) >> 1;
        divsum *= divsum;
        int dv[] = new int[256 * divsum];
        for (i = 0; i < 256 * divsum; i++) {
            dv[i] = (i / divsum);
        }

        yw = yi = 0;

        int[][] stack = new int[div][3];
        int stackpointer;
        int stackstart;
        int[] sir;
        int rbs;
        int r1 = radius + 1;
        int routsum, goutsum, boutsum;
        int rinsum, ginsum, binsum;

        for (y = 0; y < h; y++) {
            rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
            for (i = -radius; i <= radius; i++) { p = pix[yi + Math.min(wm, Math.max(i, 0))]; sir = stack[i + radius]; sir[0] = (p & 0xff0000) >> 16;
                sir[1] = (p & 0x00ff00) >> 8;
                sir[2] = (p & 0x0000ff);
                rbs = r1 - Math.abs(i);
                rsum += sir[0] * rbs;
                gsum += sir[1] * rbs;
                bsum += sir[2] * rbs;
                if (i > 0) {
                    rinsum += sir[0];
                    ginsum += sir[1];
                    binsum += sir[2];
                } else {
                    routsum += sir[0];
                    goutsum += sir[1];
                    boutsum += sir[2];
                }
            }
            stackpointer = radius;

            for (x = 0; x < w; x++) { r[yi] = dv[rsum]; g[yi] = dv[gsum]; b[yi] = dv[bsum]; rsum -= routsum; gsum -= goutsum; bsum -= boutsum; stackstart = stackpointer - radius + div; sir = stack[stackstart % div]; routsum -= sir[0]; goutsum -= sir[1]; boutsum -= sir[2]; if (y == 0) { vmin[x] = Math.min(x + radius + 1, wm); } p = pix[yw + vmin[x]]; sir[0] = (p & 0xff0000) >> 16;
                sir[1] = (p & 0x00ff00) >> 8;
                sir[2] = (p & 0x0000ff);

                rinsum += sir[0];
                ginsum += sir[1];
                binsum += sir[2];

                rsum += rinsum;
                gsum += ginsum;
                bsum += binsum;

                stackpointer = (stackpointer + 1) % div;
                sir = stack[(stackpointer) % div];

                routsum += sir[0];
                goutsum += sir[1];
                boutsum += sir[2];

                rinsum -= sir[0];
                ginsum -= sir[1];
                binsum -= sir[2];

                yi++;
            }
            yw += w;
        }
        for (x = 0; x < w; x++) {
            rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
            yp = -radius * w;
            for (i = -radius; i <= radius; i++) { yi = Math.max(0, yp) + x; sir = stack[i + radius]; sir[0] = r[yi]; sir[1] = g[yi]; sir[2] = b[yi]; rbs = r1 - Math.abs(i); rsum += r[yi] * rbs; gsum += g[yi] * rbs; bsum += b[yi] * rbs; if (i > 0) {
                rinsum += sir[0];
                ginsum += sir[1];
                binsum += sir[2];
            } else {
                routsum += sir[0];
                goutsum += sir[1];
                boutsum += sir[2];
            }

                if (i < hm) {
                    yp += w;
                }
            }
            yi = x;
            stackpointer = radius;
            for (y = 0; y < h; y++) {
                // Preserve alpha channel: ( 0xff000000 & pix[yi] )
                pix[yi] = ( 0xff000000 & pix[yi] ) | ( dv[rsum] << 16 ) | ( dv[gsum] << 8 ) | dv[bsum];

                rsum -= routsum;
                gsum -= goutsum;
                bsum -= boutsum;

                stackstart = stackpointer - radius + div;
                sir = stack[stackstart % div];

                routsum -= sir[0];
                goutsum -= sir[1];
                boutsum -= sir[2];

                if (x == 0) {
                    vmin[y] = Math.min(y + r1, hm) * w;
                }
                p = x + vmin[y];

                sir[0] = r[p];
                sir[1] = g[p];
                sir[2] = b[p];

                rinsum += sir[0];
                ginsum += sir[1];
                binsum += sir[2];

                rsum += rinsum;
                gsum += ginsum;
                bsum += binsum;

                stackpointer = (stackpointer + 1) % div;
                sir = stack[stackpointer];

                routsum += sir[0];
                goutsum += sir[1];
                boutsum += sir[2];

                rinsum -= sir[0];
                ginsum -= sir[1];
                binsum -= sir[2];

                yi += w;
            }
        }

        Log.e("pix", w + " " + h + " " + pix.length);
        bitmap.setPixels(pix, 0, w, 0, 0, w, h);

        return (bitmap);
    }

    public Bitmap convertUritoBitmap(Uri imageUri, Context context){
        Bitmap bitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), imageUri);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    private Bitmap newInstance(Bitmap param){
        return param.copy(param.getConfig(), true);
    }

}
