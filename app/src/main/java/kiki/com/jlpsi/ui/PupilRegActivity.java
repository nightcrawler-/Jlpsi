package kiki.com.jlpsi.ui;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBarActivity;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import kiki.com.jlpsi.AlbumStorageDirFactory;
import kiki.com.jlpsi.App;
import kiki.com.jlpsi.BaseAlbumDirFactory;
import kiki.com.jlpsi.FroyoAlbumDirFactory;
import kiki.com.jlpsi.R;
import kiki.com.jlpsi.cloud.Backbone;
import kiki.com.jlpsi.cloud.UploadHelper;
import kiki.com.jlpsi.utils.DbHelper;
import kiki.com.jlpsi.utils.Utils;
import singularity.com.jlpsi.entities.studentendpoint.model.Student;


public class PupilRegActivity extends ActionBarActivity {
    EditText name, regNo, assignedBy;
    TextView time, assignComputer, submit;
    ProgressBar progressBar;
    private boolean edit;

    private ImageView imageView;
    private String mCurrentPhotoPath;
    private static final String JPEG_FILE_PREFIX = "IMG_";
    private static final String JPEG_FILE_SUFFIX = ".jpg";
    private static final int ACTION_TAKE_PHOTO_B = 2;
    private String contentUri;
    private Utils.ContentTypes contentType = Utils.ContentTypes.TEXT;

    private AlbumStorageDirFactory mAlbumStorageDirFactory = null;
    protected static ImageLoader imageLoader = ImageLoader.getInstance();
    static DisplayImageOptions options;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pupil_reg);

        name = (EditText) findViewById(R.id.editText2);
        regNo = (EditText) findViewById(R.id.editText4);
        time = (TextView) findViewById(R.id.textView);
        assignedBy = (EditText) findViewById(R.id.editText);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        assignComputer = (TextView) findViewById(R.id.textView5);
        imageView = (ImageView) findViewById(R.id.imageView2);
        time.setText(DateUtils.formatDateTime(this, System.currentTimeMillis(), DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_TIME));

        submit = (TextView) findViewById(R.id.textView6);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prepSubmission();
            }
        });
        assignComputer.setClickable(true);
        assignComputer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder alertDiag = new AlertDialog.Builder(getBaseContext());
                DialogFragment fragment = new CompSelectionFragment();
                fragment.show(getSupportFragmentManager(), "select");

            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent(ACTION_TAKE_PHOTO_B);

            }
        });
        Utils.applyFonts(submit, App.getRobotoSlabLight());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO) {
            mAlbumStorageDirFactory = new FroyoAlbumDirFactory();
        } else {
            mAlbumStorageDirFactory = new BaseAlbumDirFactory();
        }

        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.loading)
                .showImageForEmptyUri(R.drawable.w_empty)
                .showImageOnFail(R.drawable.loading_failed)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .considerExifParams(true)
                .displayer(new FadeInBitmapDisplayer(200))
                .displayer(new RoundedBitmapDisplayer(5))
                .build();

        try {
            if (getIntent().getAction().equals(StudentsFragment.ACTION_EDIT)) {
                edit = true;
                setupEdit((kiki.com.jlpsi.entities.Student) getIntent().getSerializableExtra(DbHelper.TABLE_PUPILS));


            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onBackPressed() {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);
        // set dialog message
        alertDialogBuilder
                .setMessage("Do you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        PupilRegActivity.this.finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();

        alertDialog.show();

    }

    private void prepSubmission() {
        String nameString = name.getText().toString().trim();
        if (nameString.length() == 0) {
            name.setError("Required");
            return;

        }
        String regString = regNo.getText().toString().trim();
        if (regString.length() == 0) {
            regNo.setError("Required");
            return;

        }

        String assignedByString = assignedBy.getText().toString().trim();
        if (assignedByString.length() == 0) {
            assignedBy.setError("Required");
            return;

        }

        if (assignComputer.getText().toString().equals(getString(R.string.tap_for_computers))) {
            Toast.makeText(this, "Please select a computer", Toast.LENGTH_SHORT).show();
            return;
        }

        Student student = new Student();
        student.setName(nameString);
        student.setRegNo(regString);
        student.setId(regString);
        student.setAssignedBy(assignedByString);
        student.setCompSerial(assignComputer.getText().toString());
        student.setPicUrl(contentUri);
        student.setAssignedTime(System.currentTimeMillis());
        student.setAssigned(true);
        student.setCompDescription(assignComputer.getText().toString());

        new RegTask(edit).execute(student);

    }

    private void setupEdit(kiki.com.jlpsi.entities.Student student) {
        name.setText(student.getName());
        regNo.setText(student.getRegNo());
        assignedBy.setText(student.getAssignedBy());
        assignComputer.setText(student.getCompSerial());
        submit.setText("Done");
        imageLoader.displayImage(student.getPicUrl(), imageView, options);

    }

    private void resetUi() {
        name.setText(null);
        regNo.setText(null);
        assignedBy.setText(null);
        assignComputer.setText(getString(R.string.tap_for_computers));
        imageLoader.displayImage("drawable://" + R.drawable.ic_image_photo, imageView, options);

    }

    public class RegTask extends AsyncTask<Object, Object, Object> {
        boolean edit = false;

        public RegTask(boolean edit) {
            this.edit = edit;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
            submit.setVisibility(View.GONE);
        }

        @Override
        protected singularity.com.jlpsi.entities.studentendpoint.model.Student doInBackground(Object[] params) {
            singularity.com.jlpsi.entities.studentendpoint.model.Student student = (singularity.com.jlpsi.entities.studentendpoint.model.Student) params[0];
            if (edit) {
                try {
                    return Backbone.getInstance(getBaseContext()).updatePupil((singularity.com.jlpsi.entities.studentendpoint.model.Student) params[0]);
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }

            } else {
                try {
                    Student resp = Backbone.getInstance(getBaseContext()).insertStudent(student);
                    if (student.getPicUrl() != null && student.getPicUrl().length() > 0)
                        UploadHelper.uploadPic(student.getRegNo(), student.getRegNo(), new File(student.getPicUrl()));
                    return resp;

                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }

//                try {
//                    return CloudWork.updateStudent((Student) params[0]);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    return null;
//                }
            }
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            progressBar.setVisibility(View.GONE);
            submit.setVisibility(View.VISIBLE);
            if (o != null) {
                Toast.makeText(getBaseContext(), "Updated successfully", Toast.LENGTH_SHORT).show();
                //finish();
                resetUi();

            } else if (o == null) {
                Toast.makeText(getBaseContext(), "Failed", Toast.LENGTH_SHORT).show();

            } else
                Toast.makeText(getBaseContext(), "Something went terribly wrong", Toast.LENGTH_SHORT).show();

        }
    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_pupil_reg, menu);
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

    private File setUpPhotoFile() throws IOException {

        File f = createImageFile();
        mCurrentPhotoPath = f.getAbsolutePath();

        return f;
    }

    @SuppressLint("SimpleDateFormat")
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
                .format(new Date());
        String imageFileName = JPEG_FILE_PREFIX + timeStamp + "_";
        File albumF = getAlbumDir();
        File imageF = File.createTempFile(imageFileName, JPEG_FILE_SUFFIX,
                albumF);
        return imageF;
    }

    private String getAlbumName() {
        return getString(R.string.app_name);
    }

    private File getAlbumDir() {
        File storageDir = null;

        if (Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState())) {

            storageDir = mAlbumStorageDirFactory
                    .getAlbumStorageDir(getAlbumName());

            if (storageDir != null) {
                if (!storageDir.mkdirs()) {
                    if (!storageDir.exists()) {
                        Log.d("CameraSample", "failed to create directory");
                        return null;
                    }
                }
            }

        } else {
            Log.v(getString(R.string.app_name),
                    "External storage is not mounted READ/WRITE.");
        }

        return storageDir;
    }

    private void handleCameraPhoto(Intent intent) {
//        Uri mPicUri = intent.getData();
        contentUri = mCurrentPhotoPath;
        contentType = Utils.ContentTypes.IMAGE;
        Log.i("", "uri - " + contentUri + " current " + mCurrentPhotoPath);
        imageLoader.displayImage("file://" + contentUri, imageView, options);

        mCurrentPhotoPath = null;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case ACTION_TAKE_PHOTO_B: {
                if (resultCode == RESULT_OK) {
                    handleCameraPhoto(data);
                }
                break;
            } // ACTION_TAKE_PHOTO_B


        } // switch
    }

    private void dispatchTakePictureIntent(int actionCode) {
        File f = null;

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            f = setUpPhotoFile();
            mCurrentPhotoPath = f.getAbsolutePath();
            takePictureIntent
                    .putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
            Log.i("geeeg", "uriat dispatch - " + contentUri + " current "
                    + mCurrentPhotoPath);

        } catch (IOException e) {
            e.printStackTrace();
            f = null;
            mCurrentPhotoPath = null;
        }
        startActivityForResult(takePictureIntent, actionCode);
    }


}
