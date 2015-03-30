package kiki.com.jlpsi;

import android.app.Application;
import android.content.Context;
import android.graphics.Typeface;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import kiki.com.jlpsi.utils.Utils;

/**
 * Created by Doreen on 2/6/2015.
 */
public class App extends Application {
    private static Typeface robotoSlabRegular = null, robotoSlabLight = null, robotoThin = null;
    private static Context context;

    public static Typeface getRobotoSlabRegular() {
        if (robotoSlabRegular == null)
            robotoSlabRegular = Typeface.createFromAsset(context.getAssets(),
                    "robotoslab-regular.ttf");
        return robotoSlabRegular;
    }

    public static Typeface getRobotoSlabLight() {
        if (robotoSlabLight == null)
            robotoSlabLight = Typeface.createFromAsset(context.getAssets(),
                    "robotoslab-light.ttf");
        return robotoSlabLight;
    }

    public static Typeface getRobotoThin() {
        if (robotoThin == null)
            robotoThin = Typeface.createFromAsset(context.getAssets(),
                    "roboto-thin.ttf");
        return robotoThin;
    }

    private void initUIL() {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                .build();
        ImageLoader.getInstance().init(config);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        initUIL();
        if (Utils.networkConnected(context) && !Utils.isGcmRegistered(context))
            Utils.registerGCM(context);
    }
}
