package com.unlogicon.headhunter.main;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import com.unlogicon.headhunter.R;
import com.unlogicon.headhunter.result.ActivityResult;
import com.unlogicon.headhunter.searchHistory.AdapterQueries;
import com.unlogicon.headhunter.searchHistory.SearchQuery;
import com.unlogicon.headhunter.searchHistory.SearchQueryDAO;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by nik on 03.10.14.
 */
public class FragmentHistory extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener {
    private ListView m_SearchListView;
    private Button m_ClearHistoryButton;
    private SearchQueryDAO m_searchDAO;
    private ArrayList<SearchQuery> m_searchQueries;
    private AdapterQueries m_Adapter;

    public FragmentHistory(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.main_fragment_history, container,
                false);
        m_SearchListView = (ListView) rootView.findViewById(R.id.search_history_ListView);
        m_SearchListView.setOnItemClickListener(this);
        m_ClearHistoryButton = (Button) rootView.findViewById(R.id.clear_historyBT);
        m_ClearHistoryButton.setOnClickListener(this);

        m_searchDAO = new SearchQueryDAO();
        m_searchQueries = m_searchDAO.readAllQueries(getActivity());
        //reversing the items (latest search should be the first in listView)
        Collections.reverse(m_searchQueries);

        m_Adapter = new AdapterQueries(m_searchQueries);

        m_SearchListView.setAdapter(m_Adapter);

        return rootView;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.clear_historyBT) {
            //TODO add confirmation dialog
            clearHistory();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        SearchQuery selectedSearch = m_searchDAO.readQueryByLongId(getActivity(), id);
        m_searchDAO.deleteQueryById(getActivity(), id);
        m_searchDAO.addQuery(selectedSearch, getActivity());
        updateSearchListView();

        sendRequest(selectedSearch);

      //  Log.d("myLog", "string query -> " + (m_searchDAO.readQueryByLongId(getActivity(), id)).getQuery());
    }

    //TODO implement OnItemLongClickListener to open search for editing

    private void clearHistory() {
        m_searchDAO.deleteAllQueries(getActivity());
        updateSearchListView();
    }

    private void updateSearchListView() {
        m_searchQueries = null;
        m_searchQueries = m_searchDAO.readAllQueries(getActivity());
        Collections.reverse(m_searchQueries);
        m_Adapter.setNewData(m_searchQueries);
        m_Adapter.notifyDataSetChanged();
    }

    //TODO rework copy-paste
    // make original method public
    private void sendRequest(SearchQuery searchQuery) {
        Intent intent = new Intent(getActivity(), ActivityResult.class);
        intent.putExtra(ActivityResult.KEY_TITLE, searchQuery.getJobName());
        intent.putExtra(ActivityResult.KEY_REQUEST, searchQuery.getQuery());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
