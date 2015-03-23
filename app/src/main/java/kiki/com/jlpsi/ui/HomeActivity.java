package kiki.com.jlpsi.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.manuelpeinado.fadingactionbar.FadingActionBarHelper;

import kiki.com.jlpsi.App;
import kiki.com.jlpsi.R;
import kiki.com.jlpsi.utils.Utils;
import singularity.com.jlpsi.entities.backboneendpoint.model.User;


public class HomeActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    private int currPage = 0;
    private MenuItem menuItem;

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home2);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        currPage = position;
        switch (position) {
            case 0:
                fragmentManager.beginTransaction()
                        .replace(R.id.container, HomeFragment.newInstance(position + 1))
                        .commit();
                break;
            case 1:
                fragmentManager.beginTransaction()
                        .replace(R.id.container, StudentsFragment.newInstance(2))
                        .commit();
                break;
            case 2:
                fragmentManager.beginTransaction()
                        .replace(R.id.container, ComputersFragment.newInstance(3))
                        .commit();
                break;
            case 3:
                fragmentManager.beginTransaction()
                        .replace(R.id.container, ComplaintsFragment.newInstance(4))
                        .commit();
                break;
            default:
                fragmentManager.beginTransaction()
                        .replace(R.id.container, HomeFragment.newInstance(position + 1))
                        .commit();
                break;

        }


    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
            case 4:
                mTitle = getString(R.string.title_section4);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.home, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.change_password:
                startActivity(new Intent(this, ChangePasswordActivity.class));
                break;
            case R.id.action_help:
                startActivity(new Intent(this, HelpActivity.class));
                break;
            case R.id.logout:
                Utils.setRememberMe(this, false);
                startActivity(new Intent(this, LoginActivity.class));
                finish();
        }

        return super.onOptionsItemSelected(item);
    }



    public static class HomeFragment extends Fragment {
        private FadingActionBarHelper mFadingHelper;
        TextView school;
        TextView username;

        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static HomeFragment newInstance(int sectionNumber) {
            HomeFragment fragment = new HomeFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public HomeFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View view = mFadingHelper.createView(inflater);
            ListView list = (ListView) view.findViewById(android.R.id.list);
            list.setAdapter(new HistoryAdapter(getActivity()));

            View newOne = view.findViewById(R.id.frameLayout);
            View newOne2 = view.findViewById(R.id.frameLayout2);

            school = (TextView) view.findViewById(R.id.textView15);
            username = (TextView) view.findViewById(R.id.textView16);

            Utils.applyFonts(view, App.getRobotoSlabLight());


            newOne2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getActivity(), PupilRegActivity.class));
                }
            });


            newOne.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getActivity(), ComplainsActivity.class));
                }
            });

            fillTitle();
            return view;
        }

        private void fillTitle() {
            User user = Utils.getCachedUser(getActivity());
            school.setText(user.getName());
            username.setText(user.getUsername());
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((HomeActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
            mFadingHelper = new FadingActionBarHelper()
                    .actionBarBackground(R.drawable.ab_solid_kiki)
                    .headerLayout(R.layout.header_home)
                    .headerOverlayLayout(R.layout.header_home_overlay)
                    .contentLayout(R.layout.fragment_home_list);
        }

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            mFadingHelper.initActionBar((ActionBarActivity) getActivity());
        }

        public class HistoryAdapter extends BaseAdapter {
            LayoutInflater inflater;
            Context context;

            private HistoryAdapter(Context context) {
                this.context = context;
                inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            }

            @Override
            public int getCount() {
                return 22;
            }

            @Override
            public Object getItem(int position) {
                return null;
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

//                holder.imageView = (ImageView) convertView.findViewById(R.id.imageView);
                    holder.name = (TextView) convertView.findViewById(R.id.textView2);
                    holder.summary = (TextView) convertView.findViewById(R.id.textView3);
                    holder.complaints = (TextView) convertView.findViewById(R.id.textView4);

                    Utils.applyFonts(convertView, App.getRobotoSlabLight());

                } else {
                    holder = (ViewHolder) convertView.getTag();
                }

                holder.name.setText("false");

                return convertView;

            }

            private class ViewHolder {
                ImageView imageView;
                TextView name, summary, complaints;
            }
        }

    }

}
