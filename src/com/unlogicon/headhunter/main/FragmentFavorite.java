package com.unlogicon.headhunter.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;
import com.androidquery.util.AQUtility;
import com.unlogicon.headhunter.R;
import com.unlogicon.headhunter.UtilsApi;
import com.unlogicon.headhunter.model.vacancies.Cluster;
import com.unlogicon.headhunter.model.vacancies.Vacancies;
import com.unlogicon.headhunter.result.AdapterResult;
import com.unlogicon.headhunter.vacancy.ActivityVacancy;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.unlogicon.headhunter.UtilsApi.getCallBack;
import static com.unlogicon.headhunter.UtilsApi.parseCluster;

/**
 * Created by Nik on 14.12.2014.
 */
public class FragmentFavorite extends Fragment {

    private Activity activity;
    private AQuery aq;
    private ListView listView;

    private ArrayList<Vacancies> rows;
    private AdapterResult adapter;
    private Cluster cluster;

    public FragmentFavorite(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        activity = getActivity();
        aq = new AQuery(activity);

        View rootView = inflater.inflate(R.layout.favorite_fragment, container, false);

        rows = new ArrayList<Vacancies>();
        adapter = new AdapterResult(activity, rows);

        listView = (ListView) rootView.findViewById(R.id.listView);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(activity, ActivityVacancy.class);
                intent.putExtra(ActivityVacancy.KEY_ID, rows.get(position).getId());
                startActivity(intent);
            }

        });


        aq.progress(this).ajax(getCallBack(this, "vacancies/favorited", activity));

        return rootView;
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


    }

}
