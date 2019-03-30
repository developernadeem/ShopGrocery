package com.example.umair.shopgrocery;



import android.app.SearchManager;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class SearchActivity extends AppCompatActivity implements SearchView.OnQueryTextListener,MenuItem.OnActionExpandListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu,menu);
        SearchManager sm = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        menu.findItem(R.id.search).setOnActionExpandListener(this);
        menu.findItem(R.id.search).expandActionView();
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        // Assumes current activity is the searchable activity
        searchView.setSearchableInfo(sm.getSearchableInfo(getComponentName()));
        searchView.setFocusable(true);
        searchView.setQueryHint(Html.fromHtml("<font color = #919389>" + getResources().getString(R.string.hintSearchMess) + "</font>"));
        searchView.setIconified(false);
        searchView.requestFocusFromTouch();
        searchView.setOnQueryTextListener(this);

        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        Toast.makeText(this, query, Toast.LENGTH_SHORT).show();
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        Toast.makeText(this, newText, Toast.LENGTH_SHORT).show();
        return false;
    }


    @Override
    public boolean onMenuItemActionExpand(MenuItem menuItem) {
        return true;
    }

    @Override
    public boolean onMenuItemActionCollapse(MenuItem menuItem) {
        finish();
        return false;
    }
}


