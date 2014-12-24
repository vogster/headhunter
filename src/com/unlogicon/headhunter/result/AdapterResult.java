package com.unlogicon.headhunter.result;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;
import com.unlogicon.headhunter.DateHelper;
import com.unlogicon.headhunter.R;
import com.unlogicon.headhunter.Utils;
import com.unlogicon.headhunter.model.vacancies.Vacancies;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import static com.unlogicon.headhunter.UtilsApi.getCallBackFav;

/**
 * Created by nik on 02.10.14.
 */
public class AdapterResult extends BaseAdapter {

    private Activity activity;
    private final AQuery aQuery;
    private ArrayList<Vacancies> data;

    private ImageView logo;
    private TextView name;
    private TextView salary;
    private TextView corpName;
    private TextView dateCreated;

    public AdapterResult(Activity activity, ArrayList<Vacancies> data){
        this.activity = activity;
        this.data = data;

        aQuery = new AQuery(activity);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.result_row, null);
        }

        final Vacancies vacancies = data.get(position);
        final AQuery aq = aQuery.recycle(view);

        name = (TextView) view.findViewById(R.id.name);
        salary = (TextView) view.findViewById(R.id.salary);
        corpName = (TextView) view.findViewById(R.id.corpname);
        logo = (ImageView) view.findViewById(R.id.imageView);
        dateCreated = (TextView) view.findViewById(R.id.dateCreated);

        if (vacancies.getEmployer().getLogoUrls() != null) {
            aq.id(logo).image(vacancies.getEmployer().getLogoUrls().getUrl_original(), true, false, 0, 0, null, AQuery.FADE_IN_NETWORK);
        }

        aq.id(name).text(vacancies.getName());
        aq.id(salary).text(Utils.salaryForm(activity, vacancies.getSalary()));

        Calendar cal = Calendar.getInstance();
        try {
            cal.setTime(DateHelper.parseISO8601NoMilliseconds(vacancies.getCreated_at()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        aq.id(dateCreated).text(cal.get(Calendar.DATE) + " " + String.format(Locale.getDefault(),"%tb",cal));


        boolean fav = false;

        for (String s : vacancies.getRelations()){
            if (s.equals("favorited")){
                fav = true;
            }
        }

        aq.id(view.findViewById(R.id.imageView2)).image(fav ? R.drawable.ic_fav_active : R.drawable.ic_fav_deactive);

        aq.id(view.findViewById(R.id.imageView2)).clicked(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aq.progress(this).ajax(getCallBackFav(AdapterResult.this, "vacancies/favorited/" + vacancies.getId(), activity));
            }
        });

        aq.id(R.id.corpname).text(vacancies.getEmployer().getName());

        return view;
    }

    public void onResponse(String url, String s, AjaxStatus status) {
        if (status.getCode() == -101){
            Toast.makeText(activity, "ok", Toast.LENGTH_LONG).show();
        }


    }
}
