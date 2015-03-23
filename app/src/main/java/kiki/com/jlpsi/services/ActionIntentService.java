package kiki.com.jlpsi.services;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import java.io.IOException;

import kiki.com.jlpsi.cloud.Backbone;


public class ActionIntentService extends IntentService {
    private static final String ACTION_DELETE_STUDENT = "kiki.com.jlpsi.services.action.DELETE_STUDENT";
    private static final String ACTION_UPDATE_STUDENT= "kiki.com.jlpsi.services.action.UPDATE_STUDENT";

    private static final String EXTRA_ID = "kiki.com.jlpsi.services.extra.ID";
    private static final String EXTRA_PARAM2 = "kiki.com.jlpsi.services.extra.PARAM2";


    public static void startActionDeleteStudent(Context context, String id) {
        Intent intent = new Intent(context, ActionIntentService.class);
        intent.setAction(ACTION_DELETE_STUDENT);
        intent.putExtra(EXTRA_ID, id);
        context.startService(intent);
    }

//    /**
//     * Starts this service to perform action Baz with the given parameters. If
//     * the service is already performing a task this action will be queued.
//     *
//     * @see IntentService
//     */
//    // TODO: Customize helper method
//    public static void startActionBaz(Context context, String param1, String param2) {
//        Intent intent = new Intent(context, ActionIntentService.class);
//        intent.setAction(ACTION_BAZ);
//        intent.putExtra(EXTRA_PARAM1, param1);
//        intent.putExtra(EXTRA_PARAM2, param2);
//        context.startService(intent);
//    }

    public ActionIntentService() {
        super("ActionIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_DELETE_STUDENT.equals(action)) {
                final String id = intent.getStringExtra(EXTRA_ID);
                try {
                    handleActionDeleteStudent(id);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
//            } else if (ACTION_BAZ.equals(action)) {
//                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
//                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
//                handleActionBaz(param1, param2);
//            }
        }
    }


    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void  handleActionDeleteStudent(String id) throws IOException {
        Backbone.getInstance(this).deletePupil(id);
    }
}
