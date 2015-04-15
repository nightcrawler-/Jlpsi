package kiki.com.jlpsi.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.manuelpeinado.fadingactionbar.FadingActionBarHelper;

import java.io.IOException;
import java.util.List;

import kiki.com.jlpsi.App;
import kiki.com.jlpsi.R;
import kiki.com.jlpsi.cloud.Backbone;
import kiki.com.jlpsi.utils.Utils;
import singularity.com.jlpsi.entities.softwareendpoint.model.Software;

/**
 * Created by Frederick on 4/15/2015.
 */
public class SoftwareFragment extends Fragment


{
    private static final String ARG_SECTION_NUMBER = "section_number";

    private FadingActionBarHelper mFadingHelper;
    private SoftwareyAdapter adapter;
    private ProgressBar progressBar;


    public SoftwareFragment() {
    }

    public static SoftwareFragment newInstance(int sectionNumber) {
        SoftwareFragment fragment = new SoftwareFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = mFadingHelper.createView(inflater);
        ListView list = (ListView) view.findViewById(android.R.id.list);

        adapter = new SoftwareyAdapter(getActivity());
        list.setAdapter(adapter);

        Utils.applyFonts(view, App.getRobotoSlabLight());
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);

        new SoftwareTask().execute();

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String url = adapter.getItem(position).getDownloadUrl();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });


        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((HomeActivity) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));
        mFadingHelper = new FadingActionBarHelper()
                .actionBarBackground(R.drawable.ab_solid_kiki)
                .headerLayout(R.layout.header_apps)
                .headerOverlayLayout(R.layout.header_apps_overlay)
                .contentLayout(R.layout.fragment_home_list);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mFadingHelper.initActionBar((ActionBarActivity) getActivity());
    }

    public class SoftwareyAdapter extends BaseAdapter {
        LayoutInflater inflater;
        Context context;
        List<Software> content;

        public List<Software> getContent() {
            return content;
        }

        public void setContent(List<Software> content) {
            this.content = content;
            this.notifyDataSetChanged();
        }

        private SoftwareyAdapter(Context context) {
            this.context = context;
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        }

        @Override
        public int getCount() {
            return content != null ? content.size() : 0;
        }

        @Override
        public Software getItem(int position) {
            return content.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = new ViewHolder();

            if (convertView == null) {
                convertView = inflater.inflate(R.layout.list_item_student, null);
                convertView.setTag(holder);

//                holder.imageView = (ImageView) convertView.findViewById(R.id.imageView);
                holder.name = (TextView) convertView.findViewById(R.id.textView2);
                holder.summary = (TextView) convertView.findViewById(R.id.textView3);
                holder.complaints = (TextView) convertView.findViewById(R.id.textView4);

                Utils.applyFonts(convertView, App.getRobotoSlabLight());

            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.name.setText(getItem(position).getName());
            holder.summary.setText(getItem(position).getNotes());

            return convertView;

        }

        private class ViewHolder {
            ImageView imageView;
            TextView name, summary, complaints;
        }
    }

    public class SoftwareTask extends AsyncTask<Object, Object, List<Software>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }
        @Override
        protected List<Software> doInBackground(Object... params) {
            try {
                return Backbone.getInstance(getActivity()).listSoftware();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<Software> o) {
            super.onPostExecute(o);
            progressBar.setVisibility(View.GONE);

            if (o != null)
                adapter.setContent(o);
            else
                Toast.makeText(getActivity(), "Unable to fetch software", Toast.LENGTH_SHORT).show();
        }
    }
}
