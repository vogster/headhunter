package com.unlogicon.headhunter.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.*;
import android.widget.*;
import com.androidquery.AQuery;
import com.unlogicon.headhunter.R;
import com.unlogicon.headhunter.Settings;
import com.unlogicon.headhunter.model.vacancies.Proff;
import com.unlogicon.headhunter.model.vacancies.Region;
import com.unlogicon.headhunter.model.vacancies.Specializations;
import com.unlogicon.headhunter.result.ActivityResult;
import com.unlogicon.headhunter.searchHistory.SearchQuery;
import com.unlogicon.headhunter.searchHistory.SearchQueryDAO;

import java.util.ArrayList;
import java.util.Map;

import static com.unlogicon.headhunter.Utils.*;

/**
 * Created by nik on 03.10.14.
 */
public class FragmentSearch extends Fragment {

    public FragmentSearch() {

    }

    private Activity activity;
    private AQuery aq;

    private EditText search;
    private AutoCompleteTextView regionSearch;
    private Spinner spinnerCurrency;
    private View rootView;
    private ArrayList<Region> regions = new ArrayList();

    private ArrayList<Integer> employmentSelected = new ArrayList<Integer>();
    private ArrayList<Integer> scheduleSelected = new ArrayList<Integer>();

    private RelativeLayout specializationsClickable;
    private TextView specializationsSelected;

    private Map mapCurrency;
    private Map mapExp;
    private Map mapPeriod;
    private Map mapSort;
    private Map mapEmployment;
    private Map mapSchedule;

    private Spinner spinnerProff;
    private Spinner spinnerSpec;
    private Spinner spinnerExp;
    private Spinner spinnerPeriod;
    private Spinner spinnerSort;

    private SearchQuery searchQuery = new SearchQuery();

    private Settings settings;

    private final String TAG_PROFF = "PROFF";
    private final String TAG_SPECIALIZATION = "SPECIALIZATION";
    private final String TAG_EXPERIENCE = "EXPERIENCE";
    private final String TAG_EMPLOYMENT = "EMPLOYMENT";
    private final String TAG_SCHEDULE = "SCHEDULE";
    private final String TAG_PUBLISH = "PUBLISH";
    private final String TAG_SORT = "SORT";

    private final int CODE_EMPLOYMENT = 0;
    private final int CODE_SCHEDULE = 1;

    private StringBuilder query;

    public static final String KEY_SELECTED = "selected";

    private DialogFragmentSelect selectDialog;

    private Proff[] arraySepc;

    private String regionText;

    private Region cinemaSelected;

    String proff;
    private String spec;
    int experience;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.main_fragment_search, container, false);
        activity = getActivity();
        aq = new AQuery(activity);
        settings = new Settings(activity);
        search = (EditText) rootView.findViewById(R.id.searchText);
        specializationsClickable = (RelativeLayout) rootView.findViewById(R.id.specializationsClickable);

        spinnerProff = (Spinner) rootView.findViewById(R.id.spinnerProff);
        spinnerSpec = (Spinner) rootView.findViewById(R.id.spinnerSpec);
        spinnerExp = (Spinner) rootView.findViewById(R.id.spinnerExperience);
        spinnerPeriod = (Spinner) rootView.findViewById(R.id.spinnerPeriod);
        spinnerSort = (Spinner) rootView.findViewById(R.id.spinnerSort);

        regionSearch = (AutoCompleteTextView) rootView.findViewById(R.id.region);
        setHasOptionsMenu(true);

        spinnerProff.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(activity, String.valueOf(position - 1), Toast.LENGTH_LONG).show();
                if (position == 0){
                    specializationsClickable.setVisibility(View.GONE);
                }
                else {
                    specializationsClickable.setVisibility(View.VISIBLE);
                    fillSpinnerSpec(position - 1);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        aq.id(rootView.findViewById(R.id.proffClickable)).clicked(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinnerProff.performClick();
            }
        });

        aq.id(rootView.findViewById(R.id.experienceClickable)).clicked(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinnerExp.performClick();
            }
        });

        aq.id(specializationsClickable).clicked(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinnerSpec.performClick();
            }
        });

        aq.id(rootView.findViewById(R.id.periodClickable)).clicked(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinnerPeriod.performClick();
            }
        });

        aq.id(rootView.findViewById(R.id.orderClickable)).clicked(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinnerSort.performClick();
            }
        });

        aq.id(rootView.findViewById(R.id.employmentClickable)).clicked(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectDialog = DialogFragmentSelect.newInstance(employmentSelected);
                selectDialog.setTargetFragment(FragmentSearch.this, CODE_EMPLOYMENT);
                selectDialog.show(getFragmentManager().beginTransaction(), TAG_EMPLOYMENT);

            }
        });

        aq.id(rootView.findViewById(R.id.scheduleClickable)).clicked(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectDialog = DialogFragmentSelect.newInstance(scheduleSelected);
                selectDialog.setTargetFragment(FragmentSearch.this, CODE_SCHEDULE);
                selectDialog.show(getFragmentManager().beginTransaction(), TAG_SCHEDULE);
            }
        });

        arraySepc = (Proff[]) getObjectFromJson(Proff[].class, readAssets(activity, "specializations.json"));
        mapEmployment = getHashMapResource(getActivity(), R.xml.employment, "employment");
        mapSchedule = getHashMapResource(getActivity(), R.xml.schedule, "schedule");

        fillRegion();

        fillCurrency();

        fillProffSpinner();

        fillSpinnerExp();

        fillSpinnerPeriod();

        fillSpinnerSort();

        return rootView;
    }

    private void fillSpinnerPeriod() {
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(activity, android.R.layout.simple_spinner_item, android.R.id.text1);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPeriod.setAdapter(spinnerAdapter);

        mapPeriod = getHashMapResource(getActivity(), R.xml.period, "period");

        for (Object key : mapPeriod.keySet()) {
            spinnerAdapter.add(mapPeriod.get(key).toString());
        }
        spinnerAdapter.notifyDataSetChanged();
        spinnerPeriod.setSelection(1);
    }

    private void fillSpinnerSort() {
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(activity, android.R.layout.simple_spinner_item, android.R.id.text1);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSort.setAdapter(spinnerAdapter);

        mapSort = getHashMapResource(getActivity(), R.xml.sort, "sort");

        for (Object key : mapSort.keySet()) {
            spinnerAdapter.add(mapSort.get(key).toString());
        }
        spinnerAdapter.notifyDataSetChanged();
        spinnerSort.setSelection(3);
    }

    private void fillSpinnerExp() {
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(activity, android.R.layout.simple_spinner_item, android.R.id.text1);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerExp.setAdapter(spinnerAdapter);

        mapExp = getHashMapResource(getActivity(), R.xml.experience, "experience");
        spinnerAdapter.add(getActivity().getString(R.string.experience_any));

        for (Object key : mapExp.keySet()) {
            spinnerAdapter.add(mapExp.get(key).toString());
        }
        spinnerAdapter.notifyDataSetChanged();
    }

    private void fillSpinnerSpec(int position) {
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(activity, android.R.layout.simple_spinner_item, android.R.id.text1);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSpec.setAdapter(spinnerAdapter);

        spinnerAdapter.add(activity.getString(R.string.professional_any));
        for (Specializations proff1 : arraySepc[position].getSpecializations()){
            spinnerAdapter.add(proff1.getName());
            spinnerAdapter.notifyDataSetChanged();
        }
    }

    private void fillProffSpinner() {

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(activity, android.R.layout.simple_spinner_item, android.R.id.text1);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerProff.setAdapter(spinnerAdapter);

            spinnerAdapter.add(activity.getString(R.string.professional_any));
        for (Proff proff1 : arraySepc){
            spinnerAdapter.add(proff1.getName());
            spinnerAdapter.notifyDataSetChanged();
        }
    }

    private void fillRegion() {
        Region[] arrayOfRegion = (Region[]) getObjectFromJson(Region[].class, readAssets(activity, "areas.json"));
        getRegionRecursiveList(arrayOfRegion);




        final ArrayAdapter<Region> adapter = new ArrayAdapter<Region>(getActivity(),
                android.R.layout.simple_dropdown_item_1line, regions);
        regionSearch.setAdapter(adapter);


        regionSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(
                    AdapterView<?> adapterView, View view,
                    int position, long id) {
                if (position < 0 || position > regions.size() - 1) {
                    return;
                }
                /*
                int lookingForId = 0;

                for (int i = 0; i < regions.size(); i++) {
                    if (regions.get(i).getName().contains(regionSearch.getText().toString())) {
                        lookingForId = i;
                        Log.d("xxx", "looking for id is " + i);
                        break;
                    }
                }
*/
                cinemaSelected = (Region) adapterView.getAdapter().getItem(position);

            }
        });

        regionSearch.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }

            @Override
            public void afterTextChanged(Editable s) {

            }

        });
    }

    private void fillCurrency() {
        spinnerCurrency = (Spinner) rootView.findViewById(R.id.currency);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(activity, android.R.layout.simple_spinner_item, android.R.id.text1);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCurrency.setAdapter(spinnerAdapter);
        mapCurrency = getHashMapResource(activity, R.xml.currency, "currency");

        for (Object key : mapCurrency.keySet()) {
            spinnerAdapter.add(mapCurrency.get(key).toString());
            spinnerAdapter.notifyDataSetChanged();
        }
    }

    private void getRegionRecursiveList(Region[] paramArrayOfRegion) {

        for (Region region : paramArrayOfRegion){
            regions.add(region);
            getRegionRecursiveList(region.getAreas());
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        Intent i = new Intent();
        Map map = null;
        StringBuilder stringBuilder = new StringBuilder();
        String prefix = "";
        ArrayList<Integer> selected = new ArrayList<Integer>();

                if (resultCode == Activity.RESULT_OK) {
                    switch (requestCode){
                        case CODE_EMPLOYMENT:
                            selected = (ArrayList<Integer>) data.getExtras().get(KEY_SELECTED);
                            map = getHashMapResource(getActivity(), R.xml.employment, "employment");
                            employmentSelected.clear();


                            for (Integer s : selected){
                                stringBuilder.append(prefix);
                                prefix = ", ";
                                employmentSelected.add(s);
                                stringBuilder.append(map.get(map.keySet().toArray()[s]).toString());
                                Toast.makeText(activity, map.get(map.keySet().toArray()[s]).toString(), Toast.LENGTH_LONG).show();
                            }

                            if (selected.size() != 0){
                                aq.id(rootView.findViewById(R.id.employmentSelected)).text(stringBuilder.toString());
                            }
                            else {
                                aq.id(rootView.findViewById(R.id.employmentSelected)).text(activity.getString(R.string.employment_any));
                            }
                            break;
                        case CODE_SCHEDULE:
                            selected = (ArrayList<Integer>) data.getExtras().get(KEY_SELECTED);
                            map = getHashMapResource(getActivity(), R.xml.schedule, "schedule");
                            scheduleSelected.clear();
                            for (Integer s : selected){
                                stringBuilder.append(prefix);
                                prefix = ", ";
                                scheduleSelected.add(s);
                                stringBuilder.append(map.get(map.keySet().toArray()[s]).toString());
                                Toast.makeText(activity, map.get(map.keySet().toArray()[s]).toString(), Toast.LENGTH_LONG).show();
                            }

                            if (selected.size() != 0){
                                aq.id(rootView.findViewById(R.id.scheduleSelected)).text(stringBuilder.toString());
                            }
                            else {
                                aq.id(rootView.findViewById(R.id.scheduleSelected)).text(activity.getString(R.string.schedule_any));
                            }
                            break;

                    }

                } else if (resultCode == Activity.RESULT_CANCELED){
                    // Nothing =(
                }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_fragment_search_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_refresh:
                sendRequest();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void sendRequest() {
        Intent intent = new Intent(getActivity(), ActivityResult.class);
        intent.putExtra(ActivityResult.KEY_TITLE, search.getText().toString());
        intent.putExtra(ActivityResult.KEY_REQUEST, formationQuery());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);

        SearchQueryDAO searchQueryDAO = new SearchQueryDAO();
        //should be called after formationQuery(), to provide filled SearchQuery object
        searchQueryDAO.addQuery(searchQuery, getActivity());
    }

    public String formationQuery(){
        query = new StringBuilder();
        query.append("vacancies/?");
        if (!search.getText().toString().equals("")){
            query.append("text=");
            query.append(search.getText().toString());

            searchQuery.setJobName(search.getText().toString()); // TODO add variable for searchTitle?
        } else {
            searchQuery.setJobName("All vacancies");
        }
        if(!aq.id(R.id.salary).getText().toString().equals("")) {
            query.append("&currenncy=" + mapCurrency.keySet().toArray()[spinnerCurrency.getSelectedItemPosition()]);
            query.append("&salary=" + aq.id(R.id.salary).getText().toString());

            searchQuery.setCurrency(spinnerCurrency.getSelectedItem().toString());
            searchQuery.setSalary(aq.id(R.id.salary).getText().toString());
        }
        int proff = spinnerProff.getSelectedItemPosition() - 1;
        int spec = spinnerSpec.getSelectedItemPosition() - 1;
        int exp = spinnerExp.getSelectedItemPosition()  -1;

        if(proff > -1){
            if (spec > -1){
                query.append("&specialization=" + arraySepc[proff].getSpecializations()[spec].getId());
                searchQuery.setProf(arraySepc[proff].getName());
                searchQuery.setSpecialization(arraySepc[proff].getSpecializations()[spec].getName());
            }
            else {
                query.append("&specialization=" + arraySepc[proff].getId());
                searchQuery.setProf(arraySepc[proff].getName());
            }
        }

        if (exp > -1){
            query.append("&experience=" + mapExp.keySet().toArray()[spinnerExp.getSelectedItemPosition() - 1]);
            searchQuery.setExperience(spinnerExp.getSelectedItem().toString());
        }

        for (Integer pos : employmentSelected){
            query.append("&employment=" + mapEmployment.keySet().toArray()[pos]);
            if (searchQuery.getEmployment() != null) {
                String temp = searchQuery.getEmployment() + ", ";
                searchQuery.setEmployment(temp + (mapEmployment.get(mapEmployment.keySet().toArray()[pos])).toString());
            } else {
                searchQuery.setEmployment((mapEmployment.get(mapEmployment.keySet().toArray()[pos])).toString());
            }
        }

        for (Integer pos : scheduleSelected){
            query.append("&schedule=" + mapSchedule.keySet().toArray()[pos]);
            if (searchQuery.getSchedule() != null) {
                String temp = searchQuery.getSchedule() + ", ";
                searchQuery.setSchedule(temp + (mapSchedule.get(mapSchedule.keySet().toArray()[pos])).toString());
            } else {
                searchQuery.setSchedule((mapSchedule.get(mapSchedule.keySet().toArray()[pos])).toString());
            }
        }

        query.append("&period=" + mapPeriod.keySet().toArray()[spinnerPeriod.getSelectedItemPosition()]);
        searchQuery.setPeriod(spinnerPeriod.getSelectedItem().toString());

        query.append("&order_by=" + mapSort.keySet().toArray()[spinnerSort.getSelectedItemPosition()]);
        searchQuery.setOrderBy(spinnerSort.getSelectedItem().toString());

        if (aq.id(R.id.agencyCheckbox).isChecked()){
            query.append("&label=not_from_agency");
            searchQuery.setAgency(true);
        }

        if (aq.id(R.id.handicappedCheckBox).isChecked()){
            query.append("&label=accept_handicapped");
            searchQuery.setHandicapped(true);
        }

        query.append("&only_with_salary=" + (aq.id(R.id.noSalaryCheckbox).isChecked() ? "true" : "false"));
        searchQuery.setSalaryOnly(aq.id(R.id.noSalaryCheckbox).isChecked());

        activity.runOnUiThread(new Runnable() {
            public void run() {
                if (!regionSearch.getText().toString().equals("")) {

                    for (int i = 0; i < regions.size(); i++) {
                        if (regions.get(i).getName().equalsIgnoreCase(regionSearch.getText().toString())) {
                            query.append("&area=" + regions.get(i).getId());
                            searchQuery.setArea(regions.get(i).getName());
                        } else {
                            //Toast.makeText(activity, "Нет такого региона", Toast.LENGTH_LONG).show();
                        }
                    }

                }
            }
        });
        searchQuery.setQuery(query.toString());
        return query.toString();
    }

}
