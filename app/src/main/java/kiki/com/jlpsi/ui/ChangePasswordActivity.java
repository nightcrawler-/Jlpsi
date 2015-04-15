package kiki.com.jlpsi.ui;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import kiki.com.jlpsi.R;
import kiki.com.jlpsi.cloud.Backbone;
import kiki.com.jlpsi.utils.Utils;
import singularity.com.jlpsi.entities.backboneendpoint.model.User;

public class ChangePasswordActivity extends ActionBarActivity {
    private EditText oldPwd, newPwd, confirm;
    private TextView change;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        change = (TextView) findViewById(R.id.textView6);
        oldPwd = (EditText) findViewById(R.id.editText2);
        newPwd = (EditText) findViewById(R.id.editText4);
        confirm = (EditText) findViewById(R.id.editText);

        change.setClickable(true);
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prepSubmission();
            }
        });
    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_change_password, menu);
//        return true;
//    }

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

        if (Utils.getCachedUser(this).getPassword().equals(oldPwdString))
            new ChangePwdTask().execute(user);
        else
            oldPwd.setError("Invalid Password");

        //get pre-cached user with id
        //execute
    }

    private class ChangePwdTask extends AsyncTask<Object, Object, singularity.com.jlpsi.entities.userendpoint.model.User> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            change.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected singularity.com.jlpsi.entities.userendpoint.model.User doInBackground(Object[] params) {
            try {
                User user = Backbone.getInstance(getBaseContext()).login(Utils.getCachedUser(getBaseContext()));
                user.setPassword(((User) params[0]).getPassword());
                singularity.com.jlpsi.entities.userendpoint.model.User userE = new singularity.com.jlpsi.entities.userendpoint.model.User();

                // BeanUtils.copyProperties(userE, user);

                userE.setPassword(user.getPassword());
                userE.setUsername(user.getUsername());
                userE.setName(user.getName());
                userE.setContact(user.getContact());
                userE.setId(user.getId());
                userE.setLocation(user.getLocation());
                return Backbone.getInstance(getBaseContext()).changePassword(userE);


            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(singularity.com.jlpsi.entities.userendpoint.model.User o) {
            super.onPostExecute(o);
            change.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            if (o != null) {
                Toast.makeText(getBaseContext(), "Success", Toast.LENGTH_SHORT).show();
                finish();
            } else Toast.makeText(getBaseContext(), "Failled", Toast.LENGTH_SHORT).show();
        }
    }
}
