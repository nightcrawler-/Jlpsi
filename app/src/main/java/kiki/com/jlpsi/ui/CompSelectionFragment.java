package kiki.com.jlpsi.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import kiki.com.jlpsi.App;
import kiki.com.jlpsi.R;
import kiki.com.jlpsi.cloud.Backbone;
import kiki.com.jlpsi.utils.Utils;
import singularity.com.jlpsi.entities.backboneendpoint.model.Computer;


public class CompSelectionFragment extends DialogFragment {
    MyAdapter adapter;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final PupilRegActivity activity = (PupilRegActivity) getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Choose one");

        adapter = new MyAdapter(getActivity());
        new ComputersTask().execute();

        builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                activity.assignComputer.setText(adapter.getItem(which).getSerial());
                dialog.dismiss();
            }
        });


        // Create the AlertDialog object and return it
        return builder.create();
    }


    public class ComputersTask extends AsyncTask<Object, Object, List<Computer>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //progressBar.setVisibility(View.VISIBLE);

        }

        @Override
        protected List<Computer> doInBackground(Object... params) {
            try {
                return Backbone.getInstance(getActivity()).listComputers(Utils.getCachedUser(getActivity()).getId(), true);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<Computer> computers) {
            super.onPostExecute(computers);
            //progressBar.setVisibility(View.GONE);

            adapter.setComputers(computers);
        }
    }

    public class MyAdapter extends BaseAdapter {
        Context context;
        List<Computer> computers;
        LayoutInflater inflater;


        public void setComputers(List<Computer> computers) {
            this.computers = computers;
            this.notifyDataSetChanged();
        }

        private MyAdapter(Context context) {
            this.context = context;
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);



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
                holder.packageCode = (TextView) convertView.findViewById(R.id.textView3);

                Utils.applyFonts(convertView, App.getRobotoSlabLight());

            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.name.setText(getItem(position).getSerial());
            holder.packageCode.setText(getItem(position).getPackageCode());


            return convertView;

        }

        private class ViewHolder {
            TextView name, packageCode;
        }
    }
}
