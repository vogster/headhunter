package com.unlogicon.headhunter.employer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;
import com.androidquery.util.AQUtility;
import com.unlogicon.headhunter.R;
import com.unlogicon.headhunter.model.vacancies.Employer;
import com.unlogicon.headhunter.result.ActivityResult;
import org.json.JSONObject;

import java.util.Map;

import static com.unlogicon.headhunter.Utils.getHashMapResource;
import static com.unlogicon.headhunter.UtilsApi.getCallBack;
import static com.unlogicon.headhunter.UtilsApi.parseEmployer;

/**
 * Created by Nik on 05.10.2014.
 */
public class ActivityEmployer extends ActionBarActivity {

    private Activity activity;
    private AQuery aq;

    private Menu menu;

    private ImageView logo;
    private TextView name;
    private TextView type;
    private WebView info;
    private RelativeLayout clickable;
    private ScrollView scrollView;

    Employer employer;

    public static final String KEY_ID = "id";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.employer_activity);

        aq = new AQuery(activity);
        activity = this;

        Intent intent = getIntent();

        getSupportActionBar().setTitle(getString(R.string.employer_vacancies));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        logo = (ImageView) findViewById(R.id.logo);
        name = (TextView) findViewById(R.id.name);
        type = (TextView) findViewById(R.id.type);
        info = (WebView) findViewById(R.id.description_web);
        clickable = (RelativeLayout) findViewById(R.id.clickableInfo);
        scrollView = (ScrollView) findViewById(R.id.scrollView);

        aq.id(clickable).clicked(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, ActivityResult.class);
                intent.putExtra(ActivityResult.KEY_REQUEST, "/vacancies/?employer_id=" + employer.getId());
                intent.putExtra(ActivityResult.KEY_TITLE, employer.getName());
                startActivity(intent);
            }
        });

        aq.progress(this).ajax(getCallBack(this, "employers/" + intent.getStringExtra(KEY_ID), activity));
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

        try {
            JSONObject result = new JSONObject(s);
            employer = parseEmployer(result);
        } catch (Exception e) {
            AQUtility.debug("error employer parser", e.toString());
            e.printStackTrace();
        }

        loadEmployer(employer);

    }

    private void loadEmployer(Employer employer) {
        Map map = getHashMapResource(activity, R.xml.employer_type, "employer_type");
        if (employer.getLogoUrls() != null)
            aq.id(logo).image(employer.getLogoUrls().getUrl_original());
        name.setText(employer.getName());
        type.setText(map.get(employer.getType()).toString());
        info.loadData(employer.getDescription(), "text/html; charset=UTF-8", null);
    }


}
