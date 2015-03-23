package kiki.com.jlpsi.ui;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.text.format.DateUtils;
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
import singularity.com.jlpsi.entities.backboneendpoint.model.Complaint;

/**
 * Created by Frederick on 2/7/2015.
 */
public class ComplaintsFragment extends Fragment {
    private FadingActionBarHelper mFadingHelper;
    private ComplaintsAdapter adapter;
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
    public static ComplaintsFragment newInstance(int sectionNumber) {
        ComplaintsFragment fragment = new ComplaintsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public ComplaintsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = mFadingHelper.createView(inflater);
        ListView list = (ListView) view.findViewById(android.R.id.list);
        View lodgeNew =  view.findViewById(R.id.frameLayout);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);



        adapter = new ComplaintsAdapter(getActivity());

        lodgeNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ComplainsActivity.class));
            }
        });

        list.setAdapter(adapter);
        new ComplaintTask().execute();
        return view;
    }



    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((HomeActivity) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));
        mFadingHelper = new FadingActionBarHelper()
                .actionBarBackground(R.drawable.ab_solid_kiki)
                .headerLayout(R.layout.header_dummy_complaints)
                .headerOverlayLayout(R.layout.header_overlay_complaints)
                .contentLayout(R.layout.fragment_home_list);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mFadingHelper.initActionBar((ActionBarActivity) getActivity());
    }

    public class ComplaintTask extends AsyncTask<Object, Object, List<Complaint>>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);

        }

        @Override
        protected List<Complaint> doInBackground(Object... params) {
            try {
                return Backbone.getInstance(getActivity()).listComlaints(Utils.getCachedUser(getActivity()).getId());
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<Complaint> o) {
            super.onPostExecute(o);
            progressBar.setVisibility(View.GONE);

            adapter.setComplaintList(o);
        }
    }

    public class ComplaintsAdapter extends BaseAdapter {
        LayoutInflater inflater;
        Context context;
        List<Complaint> complaintList;

        private ComplaintsAdapter(Context context) {
            this.context = context;
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        }


        public void setComplaintList(List<Complaint> complaintList) {
            this.complaintList = complaintList;
            this.notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return complaintList != null ? complaintList.size() : 0;
        }

        @Override
        public Complaint getItem(int position) {
            return complaintList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = new ViewHolder();

            if (convertView == null) {
                convertView = inflater.inflate(R.layout.list_item_complaint, null);
                convertView.setTag(holder);

                holder.summary = (TextView) convertView.findViewById(R.id.textView9);
                holder.time = (TextView) convertView.findViewById(R.id.textView10);
                holder.status = (TextView) convertView.findViewById(R.id.textView11);

                Utils.applyFonts(convertView, App.getRobotoSlabLight());

            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.summary.setText(Html.fromHtml(getItem(position).getDescription()));
            holder.time.setText(DateUtils.formatDateTime(context, getItem(position).getTime(), DateUtils.FORMAT_SHOW_TIME));

            return convertView;

        }

        private class ViewHolder {
            TextView summary, time, status;
        }
    }

}