package com.lkp.anylist;

import android.app.Activity;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;

import com.lkp.todolist.R;


public class FirstActivity extends FragmentActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

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
//        setContentView(R.layout.activity_first);
//        Intent intent = getIntent();
//        String message = intent.getStringExtra(SecondActivity.EXTRA_MESSAGE);
//        String priorityMessage = intent.getStringExtra(SecondActivity.PRIORITY);

        setContentView(R.layout.activity_first);
        Intent intent= new Intent(this,MyService.class);
        startService(intent);



            mNavigationDrawerFragment = (NavigationDrawerFragment)
                    getFragmentManager().findFragmentById(R.id.navigation_drawer);
            mTitle = getTitle();

            // Set up the drawer.
        //使用Fragment必须调用这个方法去设置抽屉式导航交互
            mNavigationDrawerFragment.setUp(
                    R.id.navigation_drawer,
                    (DrawerLayout) findViewById(R.id.drawer_layout));


    }


        @Override
        public  void onNavigationDrawerItemSelected ( final int position) {
            // update the main content by replacing fragments
//            LayoutInflater layout=this.getLayoutInflater();
//            View view=layout.inflate(R.layout.activity_second, null);
//            ImageButton addtwo=(ImageButton)view.findViewById(R.id.imageButton1);
//            if (addtwo.isClickable()) {
//                addtwo.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        FragmentManager fragmentManager = getFragmentManager();
//                        fragmentManager.beginTransaction()
//                                .replace(R.id.container, ContentFragment.newInstance(position + 1))
//                                .commit();
//                    }
//                });
//            }
//else {
                if (position == 0) {

                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.container, CalendarFragment.newInstance(position + 1))
                            .commit();
                } else if (position == 1) {
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.container, ContentFragment.newInstance(position + 1))
                            .commit();
                } else if (position == 2) {
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.container, TodayFragment.newInstance(position + 1))
                            .commit();
                } else if (position == 3) {

                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.container, TomorrowFragment.newInstance(position + 1))
                            .commit();
                } else if (position == 4) {
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.container, LaterFragment.newInstance(position + 1))
                            .commit();
                }

        }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:mTitle=getString(R.string.title_section0);
                break;
            case 2:
                mTitle = getString(R.string.title_section1);
                break;
            case 3:
                mTitle = getString(R.string.title_section2);
                break;
            case 4:
                mTitle = getString(R.string.title_section3);
                break;
            case 5:
                mTitle=getString(R.string.title_section4);
                        break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        if (!mNavigationDrawerFragment.isDrawerOpen()) {
//            // Only show items in the action bar relevant to this screen
//            // if the drawer is not showing. Otherwise, let the drawer
//            // decide what to show in the action bar.
//            getMenuInflater().inflate(R.menu.first, menu);
//            restoreActionBar();
//            return true;
//
//        }
//        return super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.first, menu);
        restoreActionBar();
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // 处理动作按钮的点击事件
        switch (item.getItemId()) {
            case R.id.add:

                Intent intent = new Intent(this, SecondActivity.class);
                startActivity(intent);

                return true;
////            case R.id.action_settings:
//                openSettings();
//                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_first, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((FirstActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }



}
