package kiki.com.jlpsi.ui;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.manuelpeinado.fadingactionbar.FadingActionBarHelper;

import java.util.List;

import kiki.com.jlpsi.App;
import kiki.com.jlpsi.R;
import kiki.com.jlpsi.cloud.Backbone;
import kiki.com.jlpsi.utils.Utils;
import singularity.com.jlpsi.entities.backboneendpoint.model.Computer;

/**
 * Created by Frederick on 2/7/2015.
 */
public class ComputersFragment extends Fragment {
    private FadingActionBarHelper mFadingHelper;
    private ComputersAdapter adapter;
    private ProgressBar progressBar;


    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static ComputersFragment newInstance(int sectionNumber) {
        ComputersFragment fragment = new ComputersFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public ComputersFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = mFadingHelper.createView(inflater);
        ListView list = (ListView) view.findViewById(android.R.id.list);
        View newOne = view.findViewById(R.id.frameLayout);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);

        adapter = new ComputersAdapter(getActivity());

        newOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), PackageRegActivity.class));
            }
        });

        list.setAdapter(adapter);
        new ComputersTask().execute();
        return view;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((HomeActivity) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));
        mFadingHelper = new FadingActionBarHelper()
                .actionBarBackground(R.drawable.ab_solid_kiki)
                .headerLayout(R.layout.header_dummy_computers)
                .headerOverlayLayout(R.layout.header_computers_overlay)
                .contentLayout(R.layout.fragment_home_list);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mFadingHelper.initActionBar((ActionBarActivity) getActivity());
    }

    public class ComputersTask extends AsyncTask<Object, Object, List<Computer>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);

        }

        @Override
        protected List<Computer> doInBackground(Object... params) {
            try {
                return Backbone.getInstance(getActivity()).listComputers(Utils.getCachedUser(getActivity()).getId(), false);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<Computer> computers) {
            super.onPostExecute(computers);
            progressBar.setVisibility(View.GONE);

            adapter.setComputers(computers);
        }
    }

    public class ComputersAdapter extends BaseAdapter {
        LayoutInflater inflater;
        Context context;
        List<Computer> computers;

        private ComputersAdapter(Context context) {
            this.context = context;
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        }

        public void setComputers(List<Computer> computers) {
            this.computers = computers;
            this.notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return computers != null ? computers.size() : 0;
        }

        @Override
        public Computer getItem(int position) {
            return computers.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = new ViewHolder();

            if (convertView == null) {
                convertView = inflater.inflate(R.layout.list_item_complaint, null);
                convertView.setTag(holder);

//                holder.imageView = (ImageView) convertView.findViewById(R.id.imageView);
                holder.summary = (TextView) convertView.findViewById(R.id.textView9);
                holder.time = (TextView) convertView.findViewById(R.id.textView10);
                holder.status = (TextView) convertView.findViewById(R.id.textView11);

                Utils.applyFonts(convertView, App.getRobotoSlabLight());

            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.summary.setText(getItem(position).getSerial());
            holder.time.setText(getItem(position).getModel() + " " + getItem(position).getCondition());
            holder.status.setText(getItem(position).getAssigned() ? "assigned" : "not assigned");

            return convertView;

        }

        private class ViewHolder {
            TextView summary, time, status;
        }
    }

}