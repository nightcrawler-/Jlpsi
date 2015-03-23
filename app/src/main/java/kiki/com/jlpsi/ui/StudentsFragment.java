package kiki.com.jlpsi.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.view.ActionMode;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.manuelpeinado.fadingactionbar.FadingActionBarHelper;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import java.util.List;

import kiki.com.jlpsi.App;
import kiki.com.jlpsi.R;
import kiki.com.jlpsi.cloud.Backbone;
import kiki.com.jlpsi.utils.DbHelper;
import kiki.com.jlpsi.utils.Utils;
import singularity.com.jlpsi.entities.backboneendpoint.model.Student;


/**
 * Created by Frederick on 2/6/2015.
 */
public class StudentsFragment extends Fragment {
    public static final String ACTION_EDIT = "edit";

    private ListView list;
    private StudentAdapter adapter;
    private FadingActionBarHelper mFadingHelper;
    private ProgressBar progressBar;

    protected static ImageLoader imageLoader = ImageLoader.getInstance();
    static DisplayImageOptions options;

    private int selected = -1;

    public StudentsFragment() {
    }

    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static StudentsFragment newInstance(int sectionNumber) {
        StudentsFragment fragment = new StudentsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = mFadingHelper.createView(inflater);
        list = (ListView) root.findViewById(android.R.id.list);
        progressBar = (ProgressBar) root.findViewById(R.id.progressBar);

        adapter = new StudentAdapter(getActivity());
        list.setAdapter(adapter);

        View button = root.findViewById(R.id.frameLayout);
        button.setClickable(true);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), PupilRegActivity.class));
            }
        });
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.loading)
                .showImageForEmptyUri(R.drawable.w_empty)
                .showImageOnFail(R.drawable.loading_failed)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .considerExifParams(true)
                .displayer(new RoundedBitmapDisplayer(10))
                .build();


        new StudentTask().execute();


        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                ((ActionBarActivity) getActivity()).startSupportActionMode(new ActionMode.Callback() {
                    @Override
                    public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
                        actionMode.getMenuInflater().inflate(R.menu.pupils_action_mode, menu);
                        return true;
                    }

                    @Override
                    public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
                        return false;
                    }

                    @Override
                    public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.delete:
                                DialogFragment fragment = new ConfirmationFragment();
                                Bundle args = new Bundle();
                                args.putString(DbHelper.C_NAME, adapter.getItem(position - 1).getName());
                                args.putString(DbHelper.C_ID, adapter.getItem(position - 1).getId());
                                fragment.setArguments(args);
                                fragment.show(getActivity().getSupportFragmentManager(), "confirmation");
                                break;
                            case R.id.edit:
                                Intent intent = new Intent(getActivity(), PupilRegActivity.class);
                                intent.setAction(ACTION_EDIT);
                                kiki.com.jlpsi.entities.Student student = new kiki.com.jlpsi.entities.Student();
                                Student current = adapter.getItem(position - 1);

                                student.setCompDescription(current.getCompDescription());
                                student.setCompSerial(current.getCompSerial());
                                student.setAssigned(current.getAssigned());
                                student.setSchoolId(current.getSchoolId());
                                student.setAssignedBy(current.getAssignedBy());
                                student.setAssignedTime(current.getAssignedTime());
                                student.setId(current.getId());
                                student.setName(current.getName());
                                student.setPicUrl(current.getPicUrl());
                                student.setRegNo(current.getRegNo());
                                student.setStatus(current.getStatus());


                                intent.putExtra(DbHelper.TABLE_PUPILS, student);

                                getActivity().startActivity(intent);
                                break;
                        }


                        return false;
                    }

                    @Override
                    public void onDestroyActionMode(ActionMode actionMode) {

                    }
                });
            }
        });
        return root;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((HomeActivity) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));
        mFadingHelper = new FadingActionBarHelper()
                .actionBarBackground(R.drawable.ab_solid_kiki)
                .headerLayout(R.layout.header_dummy_pupils)
                .headerOverlayLayout(R.layout.header_pupils_overlay)
                .contentLayout(R.layout.fragment_home_list);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mFadingHelper.initActionBar((ActionBarActivity) getActivity());
    }

    private class StudentTask extends AsyncTask<Object, Object, List<Student>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected List<Student> doInBackground(Object[] params) {
            try {
                return Backbone.getInstance(getActivity()).listPupils(Utils.getCachedUser(getActivity()).getId());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<Student> o) {
            super.onPostExecute(o);
            progressBar.setVisibility(View.GONE);

            if (o != null)
                adapter.setStudents(o);
        }
    }

    private class StudentAdapter extends BaseAdapter {
        List<Student> students;
        Context context;
        LayoutInflater inflater;

        private StudentAdapter(Context context) {
            this.context = context;
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        }

        public void setStudents(List<Student> students) {
            this.students = students;
            this.notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return students != null ? students.size() : 0;
        }

        @Override
        public Student getItem(int position) {
            return students.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = new ViewHolder();

            if (convertView == null) {
                convertView = inflater.inflate(R.layout.list_item_student, null);
                convertView.setTag(holder);

                holder.imageView = (ImageView) convertView.findViewById(R.id.imageView12);
                holder.name = (TextView) convertView.findViewById(R.id.textView2);
                holder.summary = (TextView) convertView.findViewById(R.id.textView3);
                holder.complaints = (TextView) convertView.findViewById(R.id.textView4);

                Utils.applyFonts(convertView, App.getRobotoSlabLight());

            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.name.setText(getItem(position).getName());
            holder.summary.setText(getItem(position).getCompDescription() == null ? "unassigned" : getItem(position).getCompDescription() + " " + DateUtils.formatDateTime(context, getItem(position).getAssignedTime(), DateUtils.FORMAT_SHOW_DATE));
            imageLoader.displayImage(getItem(position).getPicUrl(), holder.imageView, options);

            return convertView;

        }

        private class ViewHolder {
            ImageView imageView;
            TextView name, summary, complaints;
        }
    }
}
