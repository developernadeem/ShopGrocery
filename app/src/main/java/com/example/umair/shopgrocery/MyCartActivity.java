package com.example.umair.shopgrocery;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.umair.shopgrocery.Adapters.ProductAdapter;
import com.example.umair.shopgrocery.Adapters.ProductAdapter2;
import com.example.umair.shopgrocery.Model.Product;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MyCartActivity extends AppCompatActivity implements ProductAdapter2.CallBackListner {
    private List<Product> myProducts;
    private ProductAdapter2 adapter2;
    private TextView subTotal,grandTotal, grandTotalOnBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cart);
        setTitle(R.string.myCart);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        RecyclerView productRV = findViewById(R.id.product_rv);
        subTotal = findViewById(R.id.subTotal);
        grandTotal = findViewById(R.id.grandTotal);
        grandTotalOnBtn = findViewById(R.id.grandTotal2);
        Gson gson = new Gson();
        myProducts = new ArrayList<>();
        productRV.setLayoutManager(new LinearLayoutManager(this));
        adapter2= new ProductAdapter2(this,myProducts);
        productRV.setAdapter(adapter2);
        Type type = new TypeToken<Product>() {}.getType();

        SharedPreferences mPrefs  = this.getApplicationContext().getSharedPreferences(this.getString(R.string.MY_APP_PREF),Context.MODE_PRIVATE);
        Map<String, ?> allEntries = mPrefs.getAll();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            Product p = gson.fromJson(entry.getValue().toString(), Product.class);
            myProducts.add(p);
            adapter2.notifyDataSetChanged();
        }
        onPriceUpdated();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onProductRemoved(int position) {
        myProducts.remove(position);
        adapter2.notifyDataSetChanged();

    }

    @Override
    public void onPriceUpdated() {
        int sum = 0;
        for (Product p :myProducts){
            sum +=p.getQuantity()*p.getUnitPrice();
        }
        subTotal.setText("Rs. "+sum);
        sum = sum+29;
        grandTotal.setText("Rs. "+sum);
        grandTotalOnBtn.setText("Rs. "+sum);

    }
}
