package kiki.com.jlpsi.ui;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.beanutils.BeanUtils;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import kiki.com.jlpsi.R;
import kiki.com.jlpsi.cloud.Backbone;
import kiki.com.jlpsi.utils.Utils;
import singularity.com.jlpsi.entities.backboneendpoint.model.User;

public class ChangePasswordActivity extends ActionBarActivity {
    private EditText oldPwd, newPwd, confirm;
    private TextView submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_change_password, menu);
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

    private void prepSubmission() {
        String oldPwdString = oldPwd.getText().toString();
        if (oldPwdString.length() == 0) {
            oldPwd.setError("Required");
            return;
        }
        String newPwdString = newPwd.getText().toString();
        if (newPwdString.length() == 0) {
            newPwd.setError("Required");
            return;
        }
        String confirmString = confirm.getText().toString();
        if (confirmString.length() == 0) {
            confirm.setError("Required");
            return;
        }

        if (!newPwdString.equals(confirmString)) {
            confirm.setError("Mismatched");
            return;
        }
        User user = new User();
        user.setPassword(newPwdString);

        new ChangePwdTask().execute(user);

        //get pre-cached user with id
        //execute
    }

    private class ChangePwdTask extends AsyncTask<Object, Object, singularity.com.jlpsi.entities.userendpoint.model.User > {

        @Override
        protected singularity.com.jlpsi.entities.userendpoint.model.User  doInBackground(Object[] params) {
            try {
                User user = Backbone.getInstance(getBaseContext()).login(Utils.getCachedUser(getBaseContext()));
                user.setPassword(((User) params[0]).getPassword());
                singularity.com.jlpsi.entities.userendpoint.model.User userE = new singularity.com.jlpsi.entities.userendpoint.model.User();

                BeanUtils.copyProperties(userE, user);
                return Backbone.getInstance(getBaseContext()).changePassword(userE);


            } catch (IOException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(singularity.com.jlpsi.entities.userendpoint.model.User  o) {
            super.onPostExecute(o);
            if(o !=null) Toast.makeText(getBaseContext(), "Success", Toast.LENGTH_SHORT).show();
            else Toast.makeText(getBaseContext(), "Failled", Toast.LENGTH_SHORT).show();
        }
    }
}
