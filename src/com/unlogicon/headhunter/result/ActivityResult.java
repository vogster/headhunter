package com.unlogicon.headhunter.result;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;
import com.androidquery.util.AQUtility;
import com.unlogicon.headhunter.R;
import com.unlogicon.headhunter.UtilsApi;
import com.unlogicon.headhunter.model.vacancies.Cluster;
import com.unlogicon.headhunter.model.vacancies.Vacancies;
import com.unlogicon.headhunter.vacancy.ActivityVacancy;
import com.unlogicon.headhunter.views.EndlessListView;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.unlogicon.headhunter.UtilsApi.getCallBack;
import static com.unlogicon.headhunter.UtilsApi.parseCluster;

/**
 * Created by nik on 04.09.14.
 */
public class ActivityResult extends ActionBarActivity implements EndlessListView.EndlessListener {

    public static final String KEY_REQUEST = "request";
    public static final String KEY_TITLE = "title";

    private Activity activity;
    public AQuery aq;
    private ArrayList<Vacancies> rows;
    private AdapterResult adapter;
    private Menu menu;
    private String request;
    private Cluster cluster;
    private int page;

    private EndlessListView listView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_activity);

        Intent intent = getIntent();

        if (intent.getStringExtra(KEY_TITLE) != null && !intent.getStringExtra(KEY_TITLE).equals("")) {
            getSupportActionBar().setTitle(intent.getStringExtra(KEY_TITLE));
        }
        else {
            getSupportActionBar().setTitle(getString(R.string.vacancies_title));
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        activity = this;
        aq = new AQuery(activity);
        rows = new ArrayList<Vacancies>();
        adapter = new AdapterResult(activity, rows);

        listView = (EndlessListView) findViewById(R.id.listView);

        listView.setLoadingView(R.layout.loading_layout);

        listView.setAdapter(adapter);
        listView.setListener(this);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(activity, ActivityVacancy.class);
                intent.putExtra(ActivityVacancy.KEY_ID, rows.get(position).getId());
                startActivity(intent);
            }

        });

        request = intent.getStringExtra(KEY_REQUEST);

        aq.progress(this).ajax(getCallBack(this, request, activity));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;

        MenuInflater inflater = getMenuInflater();
        //inflater.inflate(R.menu.channel_main, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onResponse(String url, String s, AjaxStatus status) {

        AQUtility.debug(s);


        JSONArray jsonArray = new JSONArray();
        adapter.notifyDataSetInvalidated();
      //  rows.clear();

         cluster = new Cluster();

        try {
            JSONObject result = new JSONObject(s);
            cluster = parseCluster(result);
            jsonArray = result.getJSONArray("items");
            for (int i = 0; i < jsonArray.length(); i++) {
                rows.add(UtilsApi.parseVacancies(jsonArray.getJSONObject(i)));
            }
        } catch (Exception e) {
            AQUtility.debug("error Collections parser", e.toString());
            e.printStackTrace();
        }

        adapter.notifyDataSetChanged();
        getSupportActionBar().setSubtitle(getString(R.string.found_vacancies).replace("%", cluster.getFound()));
        listView.dataLoaded(false);

        /*

        try {
            jsonResponse = new JSONObject(s);


            JSONArray jsonMainNode = jsonResponse.optJSONArray("items");

            int lengthJsonArr = jsonMainNode.length();

            for(int i=0; i < lengthJsonArr; i++)
            {

                JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
                JSONObject jsonSalary = jsonChildNode.getJSONObject("salary");


            String name = jsonChildNode.optString("url");
                String currency = jsonSalary.optString("currency");
                AQUtility.debug("item name", currency);

            }

            parseVacancies(jsonMainNode);

        } catch (Exception e) {
            AQUtility.debug("error List parser", e.toString());
            e.printStackTrace();
        }
        */


        }

    @Override
    public void loadData() {
        if (page <= Integer.valueOf(cluster.getPages())) {
            aq.progress(this).ajax(getCallBack(this, request + "&page=" + String.valueOf(page), activity));
            page++;
        }

    }
}
