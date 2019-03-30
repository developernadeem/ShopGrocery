package com.example.umair.shopgrocery;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class ActionBarActivity extends AppCompatActivity {

    TextView textCartItemCount;
    int mCartItemCount = 0;
    SharedPreferences.OnSharedPreferenceChangeListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        SharedPreferences preferences = getApplication().getSharedPreferences(getString(R.string.MY_APP_PREF), Context.MODE_PRIVATE);
        mCartItemCount = preferences.getAll().size();
        preferences.registerOnSharedPreferenceChangeListener(
                listener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                mCartItemCount = sharedPreferences.getAll().size();
                //Toast.makeText(ActionBarActivity.this, ""+mCartItemCount, Toast.LENGTH_SHORT).show();
                invalidateOptionsMenu();

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        final MenuItem menuItem = menu.findItem(R.id.action_cart);

        View actionView = menuItem.getActionView();
        textCartItemCount = (TextView) actionView.findViewById(R.id.cart_badge);

        setupBadge();

        actionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsItemSelected(menuItem);
            }
        });



        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if ( item.getItemId() == R.id.action_cart){
            Intent myCartIntent = new Intent(this, MyCartActivity.class);
            startActivity(myCartIntent);
        }
        else if (item.getItemId() == R.id.search){
            Intent mySearchIntent = new Intent(this, SearchActivity.class);
            startActivity(mySearchIntent);
        }
        return super.onOptionsItemSelected(item);
    }
    private void setupBadge() {

        if (textCartItemCount != null) {
            if (mCartItemCount == 0) {
                if (textCartItemCount.getVisibility() != View.GONE) {
                    textCartItemCount.setVisibility(View.GONE);
                }
            } else {
                textCartItemCount.setText(String.valueOf(Math.min(mCartItemCount, 99)));
                if (textCartItemCount.getVisibility() != View.VISIBLE) {
                    textCartItemCount.setVisibility(View.VISIBLE);
                }
            }
        }
    }



}
