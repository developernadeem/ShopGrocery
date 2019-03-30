package com.example.umair.shopgrocery;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.umair.shopgrocery.Model.Product;
import com.google.gson.Gson;


public class DetailsActivity extends ActionBarActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_details);
        final SharedPreferences preferences = getApplicationContext().getSharedPreferences(
                getString(R.string.MY_APP_PREF),
                Context.MODE_PRIVATE
        );
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final Product product = getIntent().getParcelableExtra("PA");
        setTitle(product.getCategory());
        ImageView imageView = findViewById(R.id.image_view);
        Glide.with(this).load(product.getProductImageUrl()).into(imageView);
        final ImageView cartIcon =findViewById(R.id.cart_icon);
        TextView name = findViewById(R.id.product_name);
        TextView price = findViewById(R.id.price_txt);
        TextView unit = findViewById(R.id.unit_txt);
        name.setText(product.getProductName());
        price.setText(new StringBuilder().append("RS: ").append(product.getUnitPrice()).toString());
        String u = "("+product.getQuantityUnit()+")";
        unit.setText(u);
        if (preferences.contains(product.getId())){
            cartIcon.setImageDrawable(getDrawable(R.drawable.ic_remove_crt));
            cartIcon.setTag("remove");
        }else{
            cartIcon.setImageDrawable(getDrawable(R.drawable.ic_add_crt));
            cartIcon.setTag("add");
        }
        cartIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = preferences.edit();
                if(cartIcon.getTag().equals("add")){
                    cartIcon.setTag("remove");
                    editor.putString(product.getId(),new Gson().toJson(product));
                    editor.apply();
                    cartIcon.setImageDrawable(getDrawable(R.drawable.ic_remove_crt));
                }
                else{
                    cartIcon.setTag("add");
                    editor.remove(product.getId());
                    editor.apply();
                    cartIcon.setImageDrawable(getDrawable(R.drawable.ic_add_crt));
                }
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
