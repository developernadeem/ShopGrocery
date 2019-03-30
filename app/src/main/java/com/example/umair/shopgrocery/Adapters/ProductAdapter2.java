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
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.umair.shopgrocery.Model.Product;
import com.example.umair.shopgrocery.R;
import com.google.gson.Gson;

import java.util.List;

public class ProductAdapter2 extends RecyclerView.Adapter<ProductAdapter2.ProductViewHolder> {
    private List<Product> products;
    private Context mContext;
    private Gson gson;
    private SharedPreferences myPref;
    private CallBackListner mListner;
    public interface CallBackListner{
        void onProductRemoved(int position);
        void onPriceUpdated();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView productImageView;
        TextView productNameView;
        TextView productPriceView;
        TextView productQuantityView, quantity;
        ImageButton add_button,remove_btn;

        ProductViewHolder(View itemView) {
            super(itemView);
            this.productNameView =  itemView.findViewById(R.id.productTitle);
            this.productPriceView = itemView.findViewById(R.id.productPrice);
            this.productQuantityView = itemView.findViewById(R.id.productQuantity);
            this.productImageView = itemView.findViewById(R.id.productImage);
            this.add_button = itemView.findViewById(R.id.add_button);
            this.remove_btn = itemView.findViewById(R.id.minus_buton);
            this.quantity = itemView.findViewById(R.id.count);

        }
    }

    public ProductAdapter2(Context context, List<Product> products) {
        this.products = products;
        this.mContext = context;
        this.mListner = (CallBackListner)mContext;
        gson = new Gson();
        myPref = context.getApplicationContext().getSharedPreferences(context.getString(R.string.MY_APP_PREF),Context.MODE_PRIVATE);
    }

    @NonNull
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ProductViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.product_list_item2, parent, false));
    }

    public void onBindViewHolder(@NonNull final ProductViewHolder holder, final int position) {
        final Product p = products.get(position);
        Glide.with(mContext).load(p.getProductImageUrl()).into(holder.productImageView);
        holder.productNameView.setText(p.getProductName());
        holder.productPriceView.setText("RS. "+String.valueOf(p.getUnitPrice()));
        holder.productQuantityView.setText( "("+p.getQuantityUnit()+")");
        holder.quantity.setText(String.valueOf(p.getQuantity()));
        if (p.getQuantity()>1){
            holder.remove_btn.setBackgroundResource(R.drawable.ic_remove_circle_outline_black_24dp);
        }
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
                mListner.onPriceUpdated();
                myPref.edit().putString(p.getId(),gson.toJson(p)).apply();
            }
        });
        holder.remove_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quant = p.getQuantity();
                if (quant == 1){
                    myPref.edit().remove(p.getId()).apply();
                    mListner.onProductRemoved(position);
                    return;
                }
                else if(quant == 2){
                    holder.remove_btn.setBackgroundResource(R.drawable.ic_delete_forever_black_24dp);
                }
                quant--;
                holder.quantity.setText(String.valueOf(quant));
                p.setQuantity(quant);
                mListner.onPriceUpdated();
                myPref.edit().putString(p.getId(),gson.toJson(p)).apply();

            }
        });


    }

    public int getItemCount() {
        return this.products.size();
    }
}
