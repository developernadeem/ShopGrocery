package com.example.umair.shopgrocery;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.os.Bundle;

import com.example.umair.shopgrocery.Adapters.MyAdapter;
import com.example.umair.shopgrocery.Adapters.ProductAdapter;
import com.example.umair.shopgrocery.Adapters.TabsPagerAdapter;
import com.example.umair.shopgrocery.Model.Product;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


public class SubCategoryActivity extends ActionBarActivity implements ProductAdapter.OnProductClickListener {
    private TabsPagerAdapter pagerAdapter;
    private ArrayList<String> secondSubCategories;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_category);

        String category = getIntent().getStringExtra(getString(R.string.category));
        String subCategory = getIntent().getStringExtra(getString(R.string.subCategory));

        setTitle(subCategory);
        secondSubCategories = new ArrayList<>();
        FirebaseFirestore mDB = FirebaseFirestore.getInstance();
        this.pagerAdapter = new TabsPagerAdapter(getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(this.pagerAdapter);
        final TabLayout tabLayout= findViewById(R.id.slidingTabs);
        tabLayout.setupWithViewPager(viewPager);
        Task<QuerySnapshot> querySnapshotTask = mDB.collection("2ndSubCat").whereEqualTo("Category", category).whereEqualTo("SubCategory", subCategory)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                secondSubCategories = (ArrayList<String>) document.get("2ndSubCategories");
                                addFragments(secondSubCategories);
                                pagerAdapter.notifyDataSetChanged();
                            }
                            if (secondSubCategories.size() < 3) {
                                tabLayout.setTabMode(TabLayout.MODE_FIXED);
                            }
                        }
                    }
                });


    }

    private void addFragments(ArrayList<String> secondSub) {
        for (int i=0 ; i<secondSub.size();i++){
            SubCategoryFragment sca = new SubCategoryFragment();
            Bundle bundle = new Bundle();
            bundle.putString(getString(R.string.secondsub),secondSub.get(i));
            sca.setArguments(bundle);
            this.pagerAdapter.addFragment(sca,secondSub.get(i));
        }
    }

    @Override
    public void onProductClick(Product product) {
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra("PA",product);
        startActivity(intent);

    }
}
