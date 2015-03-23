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

import java.util.UUID;

import kiki.com.jlpsi.R;
import kiki.com.jlpsi.cloud.Backbone;
import singularity.com.jlpsi.entities.complaintendpoint.model.Complaint;


public class ComplainsActivity extends ActionBarActivity {
    private EditText serialNo, description;
    private TextView submit;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complains);

        serialNo = (EditText) findViewById(R.id.editText7);
        description = (EditText) findViewById(R.id.editText5);
        submit = (TextView) findViewById(R.id.textView7);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prepSubmision();
            }
        });


    }

    private void prepSubmision() {
        String serialNoString, descString;

        serialNoString = serialNo.getText().toString().trim();
        if (serialNoString.length() == 0 || serialNoString == null) {
            serialNo.setError("Required");
            return;
        }

        descString = description.getText().toString().trim();
        if (descString.length() == 0 || descString == null) {
            description.setError("Required");
            return;
        }

        Complaint complaint = new Complaint();
        complaint.setCompSerial(serialNoString);
        complaint.setDescription(descString);
        complaint.setId(UUID.randomUUID().toString());
        complaint.setTime(System.currentTimeMillis());
        complaint.setStatus("new");

        new ComplaintTask().execute(complaint);

    }

    private void reset() {
        serialNo.setText(null);
        description.setText(null);
    }

    public class ComplaintTask extends AsyncTask<Object, Object, Complaint> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
            submit.setVisibility(View.GONE);
        }

        @Override
        protected Complaint doInBackground(Object... params) {
            try {
                return Backbone.getInstance(getBaseContext()).insertComplaint((singularity.com.jlpsi.entities.complaintendpoint.model.Complaint) params[0]);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(Complaint result) {
            super.onPostExecute(result);
            progressBar.setVisibility(View.GONE);
            submit.setVisibility(View.VISIBLE);

            if (result != null) {
                reset();
                Toast.makeText(getBaseContext(), "Submitted successfuly", Toast.LENGTH_SHORT).show();
            } else
                Toast.makeText(getBaseContext(), "Something went terribly wrong", Toast.LENGTH_SHORT).show();

        }
    }

//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_complains, menu);
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
