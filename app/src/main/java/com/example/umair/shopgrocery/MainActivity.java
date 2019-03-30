package com.example.umair.shopgrocery;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.example.umair.shopgrocery.Adapters.CategoryAdapter;
import com.example.umair.shopgrocery.Adapters.MyAdapter;
import com.example.umair.shopgrocery.Adapters.SubCategoryAdapter;
import com.example.umair.shopgrocery.Animations.ZoomOutPageTransformer;
import com.example.umair.shopgrocery.Model.Category;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static android.support.design.widget.NavigationView.*;

public class MainActivity extends ActionBarActivity
        implements OnNavigationItemSelectedListener, SubCategoryAdapter.OnSubCategoriesClickListner {

    private static final String TAG = MainActivity.class.getSimpleName();

    private ExpandableListView expandableListview;
    private ViewPager mImageViewPager;
    private static int currentPage = 0;
    private ArrayList<String> images;

    private CategoryAdapter adapter;

    private FirebaseFirestore mDB;

    private List<Category> mCategories;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Setting custom toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mImageViewPager = findViewById(R.id.pager);
        TabLayout tabLayout = findViewById(R.id.tabDots);
        tabLayout.setupWithViewPager(mImageViewPager, true);
        images = new ArrayList<>();
        images.add("https://firebasestorage.googleapis.com/v0/b/yo-mart.appspot.com/o/banner%2Fleft_banner_mobile.png?alt=media&token=149a8491-d1ce-4530-9c7e-ba9ab00a324b");
        images.add("https://firebasestorage.googleapis.com/v0/b/yo-mart.appspot.com/o/banner%2Flipton-ice-tea.jpeg?alt=media&token=797e1385-0bdf-444e-bfa4-fcfffe9b4b6c");
        images.add("https://firebasestorage.googleapis.com/v0/b/yo-mart.appspot.com/o/banner%2FNESTLE-FRUITA-VITALS-Banner.png?alt=media&token=3e63372c-378e-4260-ac1a-284f4d0d37be");
        MyAdapter slider = new MyAdapter(this,images );
        mImageViewPager.setAdapter(slider);
        mImageViewPager.setPageTransformer(true,new ZoomOutPageTransformer());
        autoSwiper();
        //Reference to firestore database
        mDB = FirebaseFirestore.getInstance();
        mCategories = new ArrayList<>();
        expandableListview =  findViewById(R.id.simple_expandable_listview);
        expandableListview.setGroupIndicator(null);
        expandableListview.setDividerHeight(0);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        // Synchronizing states of Navigation drawer
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        ((NavigationView) findViewById(R.id.nav_view)).setNavigationItemSelectedListener(this);
        //Setting recycler view
        adapter = new CategoryAdapter(
                this,
                mCategories);
        this.expandableListview.setAdapter(adapter);
        //firebase data events
        CollectionReference mCollectionRef = mDB.collection("Categories");
        // Source can be CACHE, SERVER, or DEFAULT.
        //Source source = Source.CACHE;
        mCollectionRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Category cat = document.toObject(Category.class);
                        mCategories.add(cat);
                        adapter.notifyDataSetChanged();
                    }
                }
                else{
                    Log.d(TAG, "Failed");
                }
            }
        });
        setListener();
    }

    public void onBackPressed() {
        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen( GravityCompat.START)) {
            drawer.closeDrawer( GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {

        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {

        }
        ((DrawerLayout) findViewById(R.id.drawer_layout)).closeDrawer((int) GravityCompat.START);
        return true;
    }
    void setListener() {

        // This listener will show toast on group click
        expandableListview.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView listview, View view,
                                        int group_pos, long id) {


                return false;
            }
        });

        // This listener will expand one group at one time
        // You can remove this listener for expanding all groups
        expandableListview
                .setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

                    // Default position
                    int previousGroup = -1;

                    @Override
                    public void onGroupExpand(int groupPosition) {
                        if (groupPosition != previousGroup)

                            // Collapse the expanded group
                            expandableListview.collapseGroup(previousGroup);
                        previousGroup = groupPosition;
                    }

                });

        // This listener will show toast on child click
        expandableListview.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView listview, View view,
                                        int groupPos, int childPos, long id) {
                Toast.makeText(
                        MainActivity.this,
                        "You clicked : " + adapter.getChild(groupPos, childPos),
                        Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }


    @Override
    public void onSubcategoryClick(int groupPosition, int childPostion) {
        Intent intent = new Intent(this,SubCategoryActivity.class);
        intent.putExtra(getString(R.string.category),mCategories.get(groupPosition).getCategoryTitle());
        intent.putExtra(getString(R.string.subCategory),mCategories.get(groupPosition).getSubCategories().get(childPostion).getSubCategoryName());

        startActivity(intent);
    }
    public void autoSwiper(){
        mImageViewPager.addOnPageChangeListener(new  ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float v, int i1) {
                currentPage = position;
            }

            @Override
            public void onPageSelected(int i) {

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {

                if (currentPage == images.size()) {
                    currentPage = 0;
                }
                mImageViewPager.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 3000, 5000);
    }
}