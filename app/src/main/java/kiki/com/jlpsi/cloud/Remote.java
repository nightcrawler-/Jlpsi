package kiki.com.jlpsi.cloud;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import kiki.com.jlpsi.ui.CloudNotification;
import kiki.com.jlpsi.utils.Utils;

/**
 * Created by Frederick on 3/4/2015.
 */
public class Remote {
    private static final String TAG = Remote.class.getName();


    public static void handleMessage(Context c, String message) throws JSONException {
        Log.i(TAG, "msg - " + message);
        JSONObject json = new JSONObject(message);
        String schoolId = json.getString("schoolId");
        Log.d(TAG, "message proccessed :)");
        if (schoolId.equals(Utils.getCachedUser(c).getId()) || schoolId.equals("software")) {
            CloudNotification.notify(c, "New messge from Admin", json.getString("content"), 0);
            Log.d(TAG, "message proccessed BEYOND:)");

        }


    }


}
