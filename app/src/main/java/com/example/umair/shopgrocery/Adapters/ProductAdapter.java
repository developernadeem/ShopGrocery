package com.example.umair.shopgrocery.Adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.umair.shopgrocery.Model.Product;
import com.example.umair.shopgrocery.R;
import com.google.gson.Gson;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    private List<Product> products;
    private Context mContext;
    private Gson gson;
    private Product p;
    private SharedPreferences myPref;
    private OnProductClickListener mListner;
    public interface OnProductClickListener{
        void onProductClick(Product product);
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        CardView productCardView;
        ImageView productImageView;
        TextView productNameView;
        TextView productPriceView;
        TextView productQuantityView;
        ImageView appCompatButton;
        TextView  quantity;
        ImageButton add_button,remove_btn;
        LinearLayout plusminusbtn;

        ProductViewHolder(View itemView) {
            super(itemView);
            this.productCardView = itemView.findViewById(R.id.productCardView);
            this.productNameView =  itemView.findViewById(R.id.productTitle);
            this.productPriceView = itemView.findViewById(R.id.productPrice);
            this.productQuantityView = itemView.findViewById(R.id.productQuantity);
            this.productImageView = itemView.findViewById(R.id.productImage);
            this.appCompatButton = itemView.findViewById(R.id.appCompatButton);
            this.add_button = itemView.findViewById(R.id.add_button);
            this.remove_btn = itemView.findViewById(R.id.minus_buton);
            this.quantity = itemView.findViewById(R.id.count);
            this.plusminusbtn = itemView.findViewById(R.id.plusminusbtn);

        }
    }

    public ProductAdapter(Context context, List<Product> products) {
        this.products = products;
        this.mContext = context;
        this.mListner = (OnProductClickListener)mContext;
        gson = new Gson();
        myPref = context.getApplicationContext().getSharedPreferences(context.getString(R.string.MY_APP_PREF),Context.MODE_PRIVATE);
    }

    @NonNull
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ProductViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.product_list_item, parent, false));
    }

    public void onBindViewHolder(@NonNull final ProductViewHolder holder, final int position) {
        p = products.get(position);
        if (myPref.contains(p.getId())){
            p = gson.fromJson(myPref.getString(p.getId(),""), Product.class);
            products.set(position,p);
            holder.appCompatButton.setVisibility(View.GONE);
            holder.plusminusbtn.setVisibility(View.VISIBLE);
        }
        Glide.with(mContext).load(p.getProductImageUrl()).into(holder.productImageView);
        holder.productNameView.setText(p.getProductName());
        holder.productPriceView.setText("RS. "+String.valueOf(p.getUnitPrice()));
        holder.productQuantityView.setText( "("+p.getQuantityUnit()+")");
        holder.quantity.setText(String.valueOf(p.getQuantity()));
        if (p.getQuantity()>1){
            holder.remove_btn.setBackgroundResource(R.drawable.ic_remove_circle_outline_black_24dp);
        }
        holder.productCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListner.onProductClick(products.get(position));
            }
        });
        holder.appCompatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = myPref.edit();
                //if(holder.appCompatButton.getTag().equals("add")){
                    //holder.appCompatButton.setTag("remove");
                    editor.putString(p.getId(),gson.toJson(p));
                    editor.apply();
                    holder.appCompatButton.setVisibility(View.GONE);
                    holder.plusminusbtn.setVisibility(View.VISIBLE);
                //}

            }
        });
        holder.add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quant = p.getQuantity();
                if (quant == 1){
                    holder.remove_btn.setBackgroundResource(R.drawable.ic_remove_circle_outline_black_24dp);
                }
                quant++;
                holder.quantity.setText(String.valueOf(quant));
                p.setQuantity(quant);
                myPref.edit().putString(p.getId(),gson.toJson(p)).apply();
            }
        });
        holder.remove_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quant = p.getQuantity();
                if (quant == 1){
                    holder.appCompatButton.setVisibility(View.VISIBLE);
                    holder.plusminusbtn.setVisibility(View.GONE);
                    myPref.edit().remove(p.getId()).apply();
                    return;
                }
                else if(quant == 2){
                    holder.remove_btn.setBackgroundResource(R.drawable.ic_delete_forever_black_24dp);
                }
                quant--;
                holder.quantity.setText(String.valueOf(quant));
                p.setQuantity(quant);
                myPref.edit().putString(p.getId(),gson.toJson(p)).apply();

            }
        });

    }

    public int getItemCount() {
        return this.products.size();
    }
}
