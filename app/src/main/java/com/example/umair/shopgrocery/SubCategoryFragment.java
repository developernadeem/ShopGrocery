package com.example.umair.shopgrocery;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.umair.shopgrocery.Adapters.ProductAdapter;
import com.example.umair.shopgrocery.Model.Product;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class SubCategoryFragment extends Fragment {
    private OnFragmentInteractionListener mListener;
    private ProductAdapter productAdapter;
    private RecyclerView rv;
    private FirebaseFirestore mDb;
    private List<Product> products;

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        products = new ArrayList<>();
        String search = getArguments().getString(getString(R.string.secondsub));
        mDb = FirebaseFirestore.getInstance();
        this.productAdapter = new ProductAdapter(getContext(),products);
        mDb.collection("Products").whereEqualTo("subSubCat",search)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for(QueryDocumentSnapshot ds :task.getResult()){
                    Product product = ds.toObject(Product.class);
                    product.setId(ds.getId());
                    products.add(product);
                }
                productAdapter.notifyDataSetChanged();

            }
        });
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_sub_category, container, false);
        this.rv =  v.findViewById(R.id.recyclerViewProduct);
        this.rv.setLayoutManager(new LinearLayoutManager(getContext()));
        this.rv.setAdapter(this.productAdapter);
        return v;
    }
    public void onAttach(Context context) {
        super.onAttach(context);
    }


    public void onDetach() {
        super.onDetach();
        this.mListener = null;
    }
}
