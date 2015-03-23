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

import kiki.com.jlpsi.R;
import kiki.com.jlpsi.cloud.Backbone;
import singularity.com.jlpsi.entities.backboneendpoint.model.Computer;


public class PackageRegActivity extends ActionBarActivity {
    private EditText name, serialNo, code, receivedBy, condition;
    private TextView submit;
    private ProgressBar progressBar;

    private String nameString, serialNoString, codeString, receivedByString, conditionString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_package_reg);
        name = (EditText) findViewById(R.id.editText2);
        serialNo = (EditText) findViewById(R.id.editText6);
        code = (EditText) findViewById(R.id.editText9);
        receivedBy = (EditText) findViewById(R.id.editText3);
        condition = (EditText) findViewById(R.id.editText4);
        submit = (TextView) findViewById(R.id.textView7);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prepSubmission();
            }
        });


    }

    private void prepSubmission() {
        nameString = name.getText().toString().trim();
        if (nameString.length() == 0) {
            name.setError("Required");
            return;
        }

        serialNoString = serialNo.getText().toString().trim();
        if (serialNoString.length() == 0) {
            serialNo.setError("Required");
            return;
        }

        codeString = code.getText().toString().trim();
        if (codeString.length() == 0) {
            code.setError("Required");
            return;

        }

        receivedByString = receivedBy.getText().toString().trim();
        if (receivedByString.length() == 0) {
            receivedBy.setError("Required");
            return;
        }

        conditionString = condition.getText().toString().trim();
        if (conditionString.length() == 0) {
            condition.setError("Required");
            return;
        }


        Computer comp = new Computer();
        comp.setId(serialNoString);
        comp.setSerial(serialNoString);
        comp.setTime(System.currentTimeMillis());
        comp.setModel(nameString);
        comp.setCondition(conditionString);
        comp.setNotes(receivedByString);
        comp.setPackageCode(codeString);

        new RegTask().execute(comp);
    }

    private void reset() {
        name.setText(null);
        serialNo.setText(null);
        code.setText(null);
        receivedBy.setText(null);
        condition.setText(null);

    }

    public class RegTask extends AsyncTask<Computer, Object, Computer> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
            submit.setVisibility(View.GONE);
        }

        @Override
        protected Computer doInBackground(Computer... params) {
            try {
                return Backbone.getInstance(getBaseContext()).insertComputer(params[0]);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(Computer o) {
            super.onPostExecute(o);
            progressBar.setVisibility(View.GONE);
            submit.setVisibility(View.VISIBLE);

            if (o != null) {
                Toast.makeText(getBaseContext(), "Successful", Toast.LENGTH_SHORT).show();
                reset();
            } else
                Toast.makeText(getBaseContext(), "Something went terribly wrong", Toast.LENGTH_SHORT).show();
        }
    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_package_reg, menu);
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
}
