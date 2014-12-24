package com.unlogicon.headhunter.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.ListView;

import java.util.List;

/**
 * Created by nik on 13.10.14.
 */
public class EndlessListView  extends ListView implements OnScrollListener {

    private View footer;
    private boolean isLoading;
    private EndlessListener listener;

    public EndlessListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.setOnScrollListener(this);
    }

    public EndlessListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setOnScrollListener(this);
    }

    public EndlessListView(Context context) {
        super(context);
        this.setOnScrollListener(this);
    }

    public void setListener(EndlessListener listener) {
        this.listener = listener;
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {

        if (getAdapter() == null)
            return ;

        if (getAdapter().getCount() == 0)
            return ;

        int l = visibleItemCount + firstVisibleItem;
        if (l >= totalItemCount && !isLoading) {
            // It is time to add new data. We call the listener
            this.addFooterView(footer);
            isLoading = true;
            listener.loadData();
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {}

    public void setLoadingView(int resId) {
        LayoutInflater inflater = (LayoutInflater) super.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        footer = (View) inflater.inflate(resId, null);
        this.addFooterView(footer);

    }

    public void addNewData(List<String> data) {

        this.removeFooterView(footer);


        isLoading = false;
    }

    public void dataLoaded(boolean b){
        this.removeFooterView(footer);
        isLoading = b;
    }



    public EndlessListener setListener() {
        return listener;
    }

    public void setAdapter(BaseAdapter adapter) {
        super.setAdapter(adapter);
        this.removeFooterView(footer);
    }


    public static interface EndlessListener {
        public void loadData() ;
    }

}
