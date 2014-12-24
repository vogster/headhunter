package com.unlogicon.headhunter.searchHistory;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.unlogicon.headhunter.R;

import java.util.ArrayList;

/**
 * Created by Valo on 16.12.14.
 */
public class AdapterQueries extends BaseAdapter {
    private ArrayList<SearchQuery> m_Queries;

    public AdapterQueries(ArrayList<SearchQuery> arrSearchQuery) {
        m_Queries = arrSearchQuery;
    }

    @Override
    public int getCount() {
        if (m_Queries != null) {
            return m_Queries.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (m_Queries != null) {
            return m_Queries.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        if (m_Queries != null) {
            return m_Queries.get(position).getId();
        }
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(parent.getContext(), R.layout.search_history_list_item, null);
            //   convertView.setOnClickListener(m_ClickListener);
          //  convertView.setOnLongClickListener(m_LongClickListener);
        }
        SearchQuery selectedSearch = m_Queries.get(position);

        ((TextView) convertView.findViewById(R.id.jobNameTV)).setText(selectedSearch.getJobName());
        ((TextView) convertView.findViewById(R.id.areaTV)).setText(selectedSearch.getArea() == null ? "Все регионы" : selectedSearch.getArea());

        if (selectedSearch.getSalary() != null) {
            (convertView.findViewById(R.id.salaryLL)).setVisibility(View.VISIBLE);
            ((TextView) convertView.findViewById(R.id.salaryTV)).setText((selectedSearch.getSalary() + " " + selectedSearch.getCurrency()));
        }

        if (selectedSearch.getProf() != null) {
            (convertView.findViewById(R.id.profLL)).setVisibility(View.VISIBLE);
            ((TextView) convertView.findViewById(R.id.profTV)).setText(selectedSearch.getProf());
        }

        if (selectedSearch.getSpecialization() != null) {
            (convertView.findViewById(R.id.specLL)).setVisibility(View.VISIBLE);
            ((TextView) convertView.findViewById(R.id.specTV)).setText(selectedSearch.getSpecialization());
        }

        if (selectedSearch.getExperience() != null) {
            (convertView.findViewById(R.id.expLL)).setVisibility(View.VISIBLE);
            ((TextView) convertView.findViewById(R.id.expTV)).setText(selectedSearch.getExperience());
        }

        if (selectedSearch.getEmployment() != null) {
            (convertView.findViewById(R.id.employmentLL)).setVisibility(View.VISIBLE);
            ((TextView) convertView.findViewById(R.id.employmentTV)).setText(selectedSearch.getEmployment());
        }

        if (selectedSearch.getSchedule() != null) {
            (convertView.findViewById(R.id.scheduleLL)).setVisibility(View.VISIBLE);
            ((TextView) convertView.findViewById(R.id.scheduleTV)).setText(selectedSearch.getSchedule());
        }

        if (selectedSearch.getPeriod() != null) {
            (convertView.findViewById(R.id.periodLL)).setVisibility(View.VISIBLE);
            ((TextView) convertView.findViewById(R.id.periodTV)).setText(selectedSearch.getPeriod());
        }

        if (selectedSearch.getOrderBy() != null) {
            (convertView.findViewById(R.id.orderByLL)).setVisibility(View.VISIBLE);
            ((TextView) convertView.findViewById(R.id.orderByTV)).setText(selectedSearch.getOrderBy());
        }
        //TODO put search details form checkboxes into readable view
        if (selectedSearch.getAgency() || selectedSearch.getHandicapped() || selectedSearch.getSalaryOnly()) {
            (convertView.findViewById(R.id.outputLL)).setVisibility(View.VISIBLE);
            String outputLimits = "";
            if (selectedSearch.getAgency()) {
                outputLimits = "Без вакансий агенств";
            }
            if (selectedSearch.getHandicapped()) {
                if (!outputLimits.equals("")) {
                    outputLimits = outputLimits + "; ";
                }
                outputLimits = outputLimits + "Только доступные для людей с инвалидностью";
            }
            if (selectedSearch.getSalaryOnly()) {
                if (!outputLimits.equals("")) {
                    outputLimits = outputLimits + "; ";
                }
                outputLimits = outputLimits + "Только с указанной зарплатой";
            }

            ((TextView) convertView.findViewById(R.id.outputTV)).setText(outputLimits);

        }
        return convertView;
    }

    public void setNewData(ArrayList<SearchQuery> arrNewData) {
        m_Queries.clear();
        m_Queries.addAll(arrNewData);
    }

}
