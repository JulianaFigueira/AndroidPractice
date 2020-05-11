package com.example.lists;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends Activity implements SearchView.OnQueryTextListener, Filter.FilterListener {

    private MenuItem searchMenuItem;
    private ArrayAdapter<String> strList;
    private StringListFilter strFilter;
    private ListView stringListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initStringList();
    }

    private void initStringList() {
        strFilter = new StringListFilter();
        strList = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, strFilter.FilteredWordList);
        stringListView = (ListView) findViewById(R.id.string_list);
        stringListView.setAdapter(strList);
    }

    /*
    * Show a list of items and a search box. 
    *
     * */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_box, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager)
                getSystemService(Context.SEARCH_SERVICE);
        searchMenuItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) searchMenuItem.getActionView();

        searchView.setSearchableInfo(searchManager.
                getSearchableInfo(getComponentName()));
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(this);

        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        strFilter.filter(s, this);
        return true;
    }

    @Override
    public void onFilterComplete(int i) {
        strList.clear();
        strList.addAll(strFilter.FilteredWordList);
        strList.notifyDataSetChanged();
    }
}
