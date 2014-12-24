package com.unlogicon.headhunter.main;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.*;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;
import com.androidquery.util.AQUtility;
import com.unlogicon.headhunter.R;
import com.unlogicon.headhunter.Settings;
import com.unlogicon.headhunter.authorization.ActivityAuthorization;
import com.unlogicon.headhunter.model.user.User;
import org.json.JSONObject;

import static com.unlogicon.headhunter.UtilsApi.*;


public class ActivityMain extends ActionBarActivity {
    /**
     * Called when the activity is first created.
     */

    //TODO Clean code

    private String[] mScreenTitles;
    private String[] mScreenTitlesLoginned;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;

    private ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;

    private Activity activity;
    private AQuery aq;

    private Settings settings;

    private User user;

    //TODO very bad
    private boolean loginned;

    private ViewGroup header;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        activity = this;
        aq = new AQuery(activity);
        settings = new Settings(activity);

        mTitle = mDrawerTitle = getTitle();
        mScreenTitles = getResources().getStringArray(R.array.screen_array);
        mScreenTitlesLoginned = getResources().getStringArray(R.array.screen_array_loginned);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);



        LayoutInflater inflater = getLayoutInflater();
        header = (ViewGroup) inflater.inflate(R.layout.slidemenu_header, mDrawerList,false);


        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        aq.progress(this).ajax(getCallBack(this, "me", activity));

        mDrawerToggle = new ActionBarDrawerToggle(
                this,
                mDrawerLayout,
                R.drawable.ic_drawer,
                R.string.drawer_open,
                R.string.drawer_close
        ) {

            public void onDrawerClosed(View view) {
                getSupportActionBar().setTitle(mTitle);
                supportInvalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle(mDrawerTitle);
                supportInvalidateOptionsMenu();
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        if (savedInstanceState == null) {
            if (loginned) {
                selectItem(1);
            }
            else {
                selectItem(1);
            }
        }

        aq.id(R.id.clickable).clicked(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, ActivityAuthorization.class);
                startActivityForResult(intent, 1);
            }
        });

    }

    public void onResponse(String url, String s, AjaxStatus status) {
        AQUtility.debug(s);

        if (s != null){
            try {
                JSONObject result = new JSONObject(s);
                user = parseUser(result);
            } catch (Exception e) {
                AQUtility.debug("error Collections parser", e.toString());
                e.printStackTrace();
            }

            mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                    R.layout.drawer_list_item, mScreenTitlesLoginned));
            loginned = true;
        }
        else if (status.getMessage().equals("Forbidden") && settings.getRefreshToken() != null) {
            aq.progress(this).ajax(getCallBackRefresh(this, settings.getRefreshToken()));
        }
        else{
            mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                    R.layout.drawer_list_item, mScreenTitles));
            loginned = false;
        }

        if (loginned){
            aq.id(header.findViewById(R.id.imageView)).getImageView().setVisibility(View.VISIBLE);
            aq.id(header.findViewById(R.id.userName)).text(user.getFirst_name() + " " + user.getLast_name());
            mDrawerList.addHeaderView(header, null, false);
        }
        else {
            mDrawerList.removeHeaderView(header);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK){
            Toast.makeText(activity, "Авторизация выполнена успешно", Toast.LENGTH_LONG).show();
            aq.progress(this).ajax(getCallBack(this, "me", activity));
        }
        else {
            Toast.makeText(activity, "Ошибка авторизации", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu;
        MenuInflater inflater = getMenuInflater();
        //inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /* Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
       // menu.findItem(R.id.action_search).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle action buttons
        switch(item.getItemId()) {
            default:
                return super.onOptionsItemSelected(item);
        }
    }



    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    private void selectItem(int position) {

        Fragment fragment = null;
        if (loginned){
            switch (position - 1) {
                case 0:
                    fragment = new FragmentSearch();
                    break;
                case 1:
                    fragment = new FragmentHistory();
                    break;
                case 2:

                    break;
                case 3:

                    break;
                case 4:

                    break;
                case 5:
                    fragment = new FragmentFavorite();
                    break;
                case 6:

                    break;
                case 7:

                    break;
                default:
                    break;
            }
        }
        else {
            switch (position) {
                case 0:
                    Intent intent = new Intent(activity, ActivityAuthorization.class);
                    startActivityForResult(intent, 1);
                    break;
                case 1:
                    fragment = new FragmentSearch();
                    break;
                case 2:
                    fragment = new FragmentHistory();
                    break;
                default:
                    break;
            }
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, fragment).commit();

            mDrawerList.setItemChecked(position, true);

            if (loginned) {
                setTitle(mScreenTitlesLoginned[position - 1]);
            }
            else {
                setTitle(mScreenTitles[position - 1]);
            }

            mDrawerLayout.closeDrawer(mDrawerList);
        }
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

}
