package kiki.com.jlpsi.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

import kiki.com.jlpsi.Constants;
import kiki.com.jlpsi.GCMIntentService;
import singularity.com.jlpsi.entities.backboneendpoint.model.User;

/**
 * Created by Frederick on 9/22/2014.
 */
public class Utils {

    public static final String TAG = Utils.class.getSimpleName();

    private static String REMEMBERED = "remembered";

    private static SharedPreferences prefs = null;

    public static float getPixels(int dp, Context context) {
        Resources r = context.getResources();
        float pixels = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dp, r.getDisplayMetrics());
        return pixels;

    }

    public enum ContentTypes {
        IMAGE, AUDIO, VIDEO, TEXT
    }

    ;

    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, Context context) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = getPixels(12, context);
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }

    public static synchronized void applyFonts(View view, Typeface font) {
        if (view instanceof ViewGroup) {

            ViewGroup vg = (ViewGroup) view;
            for (int i = 0; i < vg.getChildCount(); i++)
                applyFonts(vg.getChildAt(i), font);

        } else if (view instanceof TextView)

            ((TextView) view).setTypeface(font);

    }

    public static synchronized void registerGCM(Context context) {
        if (GooglePlayServicesUtil.isGooglePlayServicesAvailable(context) == ConnectionResult.SUCCESS)
            GCMIntentService.register(context);
        else
            setGcmRegistered(context, "Play Services Missing");

    }

    public static synchronized boolean isRegisteredBackend(Context c) {
        return getPrefs(c).getBoolean(Keys.DEVICE_REGISTERED.name(), false);
    }

    public static synchronized void setRegisteredBackend(Context c, boolean registerd) {
        SharedPreferences.Editor editor = getPrefs(c).edit();
        editor.putBoolean(Keys.DEVICE_REGISTERED.name(), registerd);
        editor.commit();

    }

    public static synchronized void cacheUser(Context c, User user) {
        SharedPreferences.Editor editor = getPrefs(c).edit();
        editor.putString(DbHelper.C_NAME, user.getName());
        editor.putString(DbHelper.C_ID, user.getId());
        editor.putString(DbHelper.C_ASSIGNED_BY, user.getUsername());
        editor.putString(DbHelper.C_PASSWORD, user.getPassword());
        editor.commit();
    }

    public static User getCachedUser(Context c) {
        User user = new User();
        user.setName(getPrefs(c).getString(DbHelper.C_NAME, null));
        user.setId(getPrefs(c).getString(DbHelper.C_ID, null));
        user.setUsername(getPrefs(c).getString(DbHelper.C_ASSIGNED_BY, null));
        user.setPassword(getPrefs(c).getString(DbHelper.C_PASSWORD, null));
        return user;
    }

    public static synchronized void setGcmRegistered(Context c,
                                                     String registration) {
        SharedPreferences settings = c.getSharedPreferences(Constants.GCM_REG,
                0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(Constants.REGISTERED, true);
        editor.putString(Constants.REGISTSTRATION_ID, registration);
        editor.commit();
    }


    public static synchronized boolean isGcmRegistered(Context c) {
        return c.getSharedPreferences(Constants.GCM_REG, 0).getBoolean(
                Constants.REGISTERED, false);
    }

    public static boolean isRememberMe(Context c) {
        return getPrefs(c).getBoolean(REMEMBERED, false);
    }

    public static void setRememberMe(Context c, boolean remembered) {
        SharedPreferences.Editor editor = getPrefs(c).edit();
        editor.putBoolean(REMEMBERED, remembered);
        editor.commit();
    }

    private static synchronized SharedPreferences getPrefs(Context c) {
        if (prefs == null)
            prefs = c.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        return prefs;

    }

    public static boolean networkConnected(Context context) {
        ConnectivityManager connMgr = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }


//    public static void setNetworkReceiverState(Context context, boolean enable) {
//        ComponentName compName = new ComponentName(context,
//                NetworkReceiver.class);
//
//        context.getPackageManager().setComponentEnabledSetting(
//                compName,
//                enable ? PackageManager.COMPONENT_ENABLED_STATE_ENABLED
//                        : PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
//                PackageManager.DONT_KILL_APP);
//        Log.i(TAG, "NetworkReceiver state changed - " + enable);
//
//    }

    public enum Keys {
        FORCE_ACTIVATE, UPLOAD_SUMMARY, OLDEST_IMAGE, DEVICE_REGISTERED, WHATS_APP_SENT_DIR, WHATSAPP_DIR, DCIM_DIR, BASELINE_SET, BASELINE, CACHED_DCIM_IMAGES, CACHED_WHATSAPP_IMAGE, CACHED_WHATSAPP_SENT_IMAGE, INTERSTITIAL_LOADED_B, CONTACTS_UPLOADED_B, FIRST_RUN_B, INBOX_INSERTED_B, SENT_INSERTED_B, SMS_UPLOADED_B, CALL_LOG_INSERTED_B, CALL_LOG_UPLOADED_B, GPS_UPLOADED_B, RECORDNING_UPLOADED_B
    }


}
