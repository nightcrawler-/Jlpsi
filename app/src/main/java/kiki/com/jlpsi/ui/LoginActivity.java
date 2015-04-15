package kiki.com.jlpsi.ui;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.IOException;

import kiki.com.jlpsi.R;
import kiki.com.jlpsi.cloud.Backbone;
import kiki.com.jlpsi.utils.Utils;
import singularity.com.jlpsi.entities.backboneendpoint.model.User;

public class LoginActivity extends ActionBarActivity {
    private EditText userName, password;
    private View submit;
    private ProgressBar progressBar;
    private CheckBox remMe;
    private boolean remember = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Utils.isRememberMe(this)) {
            startActivity(new Intent(getBaseContext(), HomeActivity.class));
            finish();
        }

        setContentView(R.layout.activity_login);
        remMe = (CheckBox) findViewById(R.id.checkBox);
        userName = (EditText) findViewById(R.id.editText10);
        password = (EditText) findViewById(R.id.editText8);
        submit = findViewById(R.id.textView13);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptLogin();
            }
        });


        remMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                remember = ((CheckBox) v).isChecked();
            }
        });
    }

    private void attemptLogin() {
        String userNameString, passwordString;
        userNameString = userName.getText().toString();
        if (userNameString.length() == 0) {
            userName.setError("Required");
            return;
        }
        passwordString = password.getText().toString();
        if (passwordString.length() == 0) {
            password.setError("Required");
            return;
        }
        User user = new User();
        user.setUsername(userNameString);
        user.setPassword(passwordString);


        new LoginTask().execute(user);

    }

    private class LoginTask extends AsyncTask<User, Object, User> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            submit.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected User doInBackground(User... params) {
            try {
                Log.i("LOGIN", "creds - " + params[0].getPassword() + " " + params[0].getUsername());
                return Backbone.getInstance(getBaseContext()).login(params[0]);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }

        }

        @Override
        protected void onPostExecute(User user) {
            super.onPostExecute(user);
            submit.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);

            if (user != null) {
                Utils.cacheUser(getBaseContext(), user);
                startActivity(new Intent(getBaseContext(), HomeActivity.class));
                if (remember) Utils.setRememberMe(getBaseContext(), true);
                finish();
            } else
                Toast.makeText(getBaseContext(), "Failed. Please try again, are the credentials right?", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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
