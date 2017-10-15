package com.xemss.mypizzaapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ShareActionProvider;

public class MainActivity extends Activity {
    private ShareActionProvider shareActionProvider;
    private DrawerLayout drawerLayout;
    private String[] titles;
    private ListView drawerList;
    private ActionBarDrawerToggle drawerToggle;
    private int currentPos = 0;
//    private final static String LOG = "log";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Log.d(LOG, "Create main activity");

        // TODO: 11.10.2017 find needed views
        titles = getResources().getStringArray(R.array.title);
        drawerList = (ListView) findViewById(R.id.drawer);

        // TODO: 11.10.2017
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout); 
        
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_activated_1,
                titles
        );
        // TODO: 11.10.2017 set click listener to list item

        drawerList.setOnClickListener(new DrawerItemClickListener());

        // TODO: 11.10.2017
//        if (savedInstanceState == null) {
//            selectItem(0);
//        }

        // TODO: 12.10.2017 rebuild to save position
        if (savedInstanceState != null) {
            currentPos = savedInstanceState.getInt("position");
            setActionBarTitle(currentPos);
        } else {
            selectItem(0);
        }
        
        // TODO: 11.10.2017 draw listview
        drawerList.setAdapter(adapter);

        setContentView(R.layout.activity_main);

        // TODO: 12.10.2017
        drawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                R.string.open_drawer,
                R.string.close_drawer
        ) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
            }
        };

        drawerLayout.setDrawerListener(drawerToggle);

        // TODO: 12.10.2017 enable Up icon
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        // TODO: 12.10.2017 add back track
        getFragmentManager().addOnBackStackChangedListener(
                new FragmentManager.OnBackStackChangedListener() {
                    @Override
                    public void onBackStackChanged() {
                        FragmentManager fragmentManager = getFragmentManager();
                        Fragment fragment = fragmentManager.findFragmentByTag("visible_fragment");

                        if (fragment instanceof TopFragment) {
                            currentPos = 0;
                        }
                        if (fragment instanceof PizzaFragment) {
                            currentPos = 1;
                        }
                        if (fragment instanceof PastaFragment) {
                            currentPos = 2;
                        }
                        if (fragment instanceof StoresFragment) {
                            currentPos = 3;
                        }

                        setActionBarTitle(currentPos);
                        drawerList.setItemChecked(currentPos, true);
                    }
                }
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem menuItem = menu.findItem(R.id.action_share);
        shareActionProvider = (ShareActionProvider) menuItem.getActionProvider();
        setIntent("This is example text");

        return super.onCreateOptionsMenu(menu);
    }

    private void setIntent(String text) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, text);
        shareActionProvider.setShareIntent(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO: 12.10.2017
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        switch (item.getItemId()) {
            case R.id.action_create_order:
                // TODO: 11.10.2017
//                Log.d(LOG, "Create order activity");
                Intent intent = new Intent(this, OrderActivity.class);
//                Log.d(LOG, "Start order activity");
                startActivity(intent);
                return true;
//                break;
            case R.id.action_setting:
                // TODO: 11.10.2017
                return true;
//                break;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
            // TODO: 11.10.2017 write method
            selectItem(pos);

        }
    }

    // TODO: 11.10.2017 check what list item clicked and draw fragment 
    private void selectItem(int pos) {
        currentPos = pos;
        Fragment fragment;

        switch (pos) {
            case 1:
                fragment = new PizzaFragment();
                break;
            case 2:
                fragment = new PastaFragment();
                break;
            case 3:
                fragment = new StoresFragment();
                break;
            default:
                fragment = new TopFragment();
                break;
        }

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

        fragmentTransaction.replace(R.id.frameLayout,  fragment, "visible_fragment");
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commit();
        
        setActionBarTitle(pos);
        
        drawerLayout.closeDrawer(drawerList);
    }

    // TODO: 11.10.2017 to show in action bar what activity in use 
    public void setActionBarTitle(int position) {
        String title;
        if (position == 0) {
            title = getResources().getString(R.string.app_name);
        } else  {
            title = titles[position];
        }
        
        // TODO: 11.10.2017 set title 
        getActionBar().setTitle(title);

        // TODO: 11.10.2017 close drawer
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        drawerLayout.closeDrawer(drawerList);
        
    }

//    ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(
//            this,
//            drawerLayout,
//            R.string.open_drawer,
//            R.string.close_drawer
//    ) {
//        @Override
//        public void onDrawerOpened(View drawerView) {
//            super.onDrawerOpened(drawerView);
//            invalidateOptionsMenu();
//        }
//
//        @Override
//        public void onDrawerClosed(View drawerView) {
//            super.onDrawerClosed(drawerView);
//            invalidateOptionsMenu();
//        }
//
//        @Override
//        public void onDrawerSlide(View drawerView, float slideOffset) {
//            super.onDrawerSlide(drawerView, slideOffset);
//        }
//    };

    // TODO: 12.10.2017 hide share in menu when drawer open 
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        
        boolean drawerOpen = drawerLayout.isDrawerOpen(drawerList);
        menu.findItem(R.id.action_share).setVisible(!drawerOpen);
        
        return super.onPrepareOptionsMenu(menu);
    }

    // TODO: 12.10.2017 sync state between actionbar and drawer
    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    // TODO: 12.10.2017 pass config to drawer toggler
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    // TODO: 12.10.2017 save position
    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putInt("position", currentPos);
    }
}

