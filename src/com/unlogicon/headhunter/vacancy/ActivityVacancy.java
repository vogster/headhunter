package com.unlogicon.headhunter.vacancy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.util.Linkify;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;
import com.androidquery.util.AQUtility;
import com.unlogicon.headhunter.R;
import com.unlogicon.headhunter.Utils;
import com.unlogicon.headhunter.employer.ActivityEmployer;
import com.unlogicon.headhunter.model.vacancies.Vacancy;
import org.json.JSONObject;

import static com.unlogicon.headhunter.UtilsApi.getCallBack;
import static com.unlogicon.headhunter.UtilsApi.parseVacancy;

/**
 * Created by nik on 04.09.14.
 */
public class ActivityVacancy extends ActionBarActivity {

    private Activity activity;
    private AQuery aq;
    private Menu menu;
    private Vacancy vacancy;

    private TextView name;
    private TextView salary;
    private TextView address;
    private TextView experience;
    private TextView employment;
    private TextView company_name;
    private TextView company_info;
    private WebView description;

    private RelativeLayout clickable_info;
    private ScrollView scrollView;

    private LinearLayout contactsView;
    private LinearLayout contactsContainer;

    public static final String KEY_ID = "id";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vacancy_activity);

        Intent intent = getIntent();

        getSupportActionBar().setTitle(getString(R.string.vacancy_title));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        activity = this;
        aq = new AQuery(activity);

        vacancy = new Vacancy();

        name = (TextView) findViewById(R.id.name);
        salary = (TextView) findViewById(R.id.salary);
        address = (TextView) findViewById(R.id.address);
        experience = (TextView) findViewById(R.id.experience);
        employment = (TextView) findViewById(R.id.employment);
        company_name = (TextView) findViewById(R.id.company_name);
        company_info = (TextView) findViewById(R.id.company_info);
        description = (WebView) findViewById(R.id.description_web);
        description.getSettings().setDefaultFontSize(15);

        clickable_info = (RelativeLayout) findViewById(R.id.clickableInfo);
        scrollView = (ScrollView) findViewById(R.id.scrollView);

        contactsView = (LinearLayout) findViewById(R.id.contacts_view);
        contactsContainer = (LinearLayout) findViewById(R.id.contacts_container);

        String id = intent.getStringExtra(KEY_ID);

        aq.id(clickable_info).clicked(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, ActivityEmployer.class);
              //  intent.putExtra(ActivityResult.KEY_REQUEST, "/vacancies/?employer_id=" + vacancy.getEmployer().getId());
               // intent.putExtra(ActivityResult.KEY_TITLE, vacancy.getEmployer().getName());
                intent.putExtra(ActivityEmployer.KEY_ID, vacancy.getEmployer().getId());
                startActivity(intent);
            }
        });

        aq.progress(this).ajax(getCallBack(this, "vacancies/" + id, activity));

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
            vacancy = parseVacancy(result);
        } catch (Exception e) {
            AQUtility.debug("error vacancy parser", e.toString());
            e.printStackTrace();
        }
        vacancyLoad(vacancy);


    }

    public void vacancyLoad(Vacancy vacancy){
        name.setText(vacancy.getName());
        salary.setText(Utils.salaryForm(activity,vacancy.getSalary()));
        address.setText(vacancy.getArea().getName());
        experience.setText(vacancy.getExperience().getName());
        employment.setText(vacancy.getEmployment().getName());
        company_name.setText(vacancy.getEmployer().getName());
        company_info.setText("Инофрмация о компании");
        description.loadData(vacancy.getDescription(), "text/html; charset=UTF-8", null);
        scrollView.setVisibility(View.VISIBLE);

        if(vacancy.getContacts() != null) {

            contactsView.setVisibility(View.VISIBLE);
            aq.id(R.id.contact_name).text(vacancy.getContacts().getName());

            if (!vacancy.getContacts().getEmail().equals("null")){
                aq.id(R.id.contact_email).text(vacancy.getContacts().getEmail());
            }
            else {
                aq.id(R.id.contact_email).visibility(View.GONE);
            }


            for (int i = 0; i < vacancy.getContacts().getPhones().length; i++) {
                View child = getLayoutInflater().inflate(R.layout.contacts_row, null);
                final TextView comment = (TextView) child.findViewById(R.id.comment);
                final TextView phone = (TextView) child.findViewById(R.id.phone);
                phone.setAutoLinkMask(Linkify.PHONE_NUMBERS);
                if (!vacancy.getContacts().getPhones()[i].getComment().equals("null")) {
                    comment.setText(vacancy.getContacts().getPhones()[i].getComment());
                }
                else {
                    comment.setVisibility(View.GONE);
                }
                phone.setText("+"+vacancy.getContacts().getPhones()[i].getCountry() + vacancy.getContacts().getPhones()[i].getCity() + vacancy.getContacts().getPhones()[i].getNumber());
                contactsContainer.addView(child);
            }
        }
        else {
            contactsView.setVisibility(View.GONE);
        }

    }


}
