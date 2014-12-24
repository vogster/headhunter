package com.unlogicon.headhunter.searchHistory;

/**
 * Created by Valo on 15.12.14.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class SearchQueryDAO {
    private DataBaseHelper m_DataBaseHelper;
    private SQLiteDatabase m_SQLiteDatabase;
    private String m_strTableName = "queries";
    private String m_strDatabaseName = "hhdatabase.db";

    public long addQuery(SearchQuery searchQuery, Context context) {
        ContentValues newValues = new ContentValues();

        newValues.put(DataBaseHelper.JOB_NAME, searchQuery.getJobName());
        newValues.put(DataBaseHelper.SEARCH_QUERY, searchQuery.getQuery());

        m_DataBaseHelper = new DataBaseHelper(context, m_strDatabaseName, null, 1);
        m_SQLiteDatabase = m_DataBaseHelper.getWritableDatabase();
        long longAddedRawId = m_SQLiteDatabase.insert(m_strTableName, null, newValues);
        m_DataBaseHelper.close();
        return longAddedRawId;
    }

    public SearchQuery readQueryByLongId(Context context, long nId) {
        m_DataBaseHelper = new DataBaseHelper(context, m_strDatabaseName, null, 1);
        m_SQLiteDatabase = m_DataBaseHelper.getWritableDatabase();

        Cursor cursor = m_SQLiteDatabase.query(m_strTableName, new String[]{DataBaseHelper.ID, DataBaseHelper.JOB_NAME, DataBaseHelper.SEARCH_QUERY},
                "_id " + "=" + nId, null,
                null, null, null);

        cursor.moveToFirst();
        SearchQuery searchQuery = new SearchQuery();
        searchQuery.setId(cursor.getLong(cursor.getColumnIndex(DataBaseHelper.ID)));
        searchQuery.setJobName(cursor.getString(cursor.getColumnIndex(DataBaseHelper.JOB_NAME)));
        searchQuery.setQuery(cursor.getString(cursor.getColumnIndex(DataBaseHelper.SEARCH_QUERY)));

        m_DataBaseHelper.close();
        return searchQuery;
    }


    public ArrayList<SearchQuery> readAllQueries(Context v) {

        ArrayList<SearchQuery> arrQueries = new ArrayList<SearchQuery>();
        m_DataBaseHelper = new DataBaseHelper(v, m_strDatabaseName, null, 1);

        m_SQLiteDatabase = m_DataBaseHelper.getWritableDatabase();

        Cursor cursor = m_SQLiteDatabase.query(m_strTableName, new String[]{DataBaseHelper.ID, DataBaseHelper.JOB_NAME, DataBaseHelper.SEARCH_QUERY},
                null, null,
                null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            SearchQuery searchQuery = new SearchQuery();
            searchQuery.setId(cursor.getLong(cursor.getColumnIndex(DataBaseHelper.ID)));
            searchQuery.setJobName(cursor.getString(cursor.getColumnIndex(DataBaseHelper.JOB_NAME)));
            searchQuery.setQuery(cursor.getString(cursor.getColumnIndex(DataBaseHelper.SEARCH_QUERY)));

            arrQueries.add(searchQuery);
            cursor.moveToNext();
        }
        m_DataBaseHelper.close();
        return arrQueries;
    }

    public void deleteAllQueries(Context context) {

        m_DataBaseHelper = new DataBaseHelper(context, m_strDatabaseName, null, 1);
        m_SQLiteDatabase = m_DataBaseHelper.getWritableDatabase();
        m_DataBaseHelper.deleteAllRows(m_SQLiteDatabase);
        m_DataBaseHelper.close();
    }

    public void deleteQueryById(Context context, long nId) {
        String strQuery = "DELETE FROM " + m_strTableName + " WHERE _id = " + nId;
        m_DataBaseHelper = new DataBaseHelper(context, m_strDatabaseName, null, 1);
        m_SQLiteDatabase = m_DataBaseHelper.getWritableDatabase();
        m_DataBaseHelper.executeQuery(m_SQLiteDatabase, strQuery);
        m_DataBaseHelper.close();
    }


}