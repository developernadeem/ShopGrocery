package com.example.umair.shopgrocery.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.umair.shopgrocery.Model.SubCategory;
import com.example.umair.shopgrocery.R;

import java.util.List;

public class SubCategoryAdapter extends RecyclerView.Adapter<SubCategoryAdapter.ViewHolder> {
    private List<SubCategory> subCategories;
    private Context mContext;
    private  int gropPostion;
    private OnSubCategoriesClickListner mListener;
    public interface OnSubCategoriesClickListner{
        void onSubcategoryClick(int groupPosition, int childPostion);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout cardView;
        TextView name;
        ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            this.name = (TextView) itemView.findViewById(R.id.itemText);
            this.cardView =  itemView.findViewById(R.id.cardItem);
            imageView = itemView.findViewById(R.id.item_image);
        }

    }

    public SubCategoryAdapter(Context context, List<SubCategory> subCategories, int groupPosition) {
        this.subCategories = subCategories;
        this.mContext = context;
        this.gropPostion = groupPosition;
        mListener = (OnSubCategoriesClickListner)mContext;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.card_expand_item_view, parent, false));
    }

    public void onBindViewHolder(ViewHolder holder, final int position) {
        SubCategory sb = subCategories.get(position);
        holder.name.setText(subCategories.get(position).getSubCategoryName());
        Glide.with(mContext).load(sb.getSubCategoryImageUrl()).into(holder.imageView);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    mListener.onSubcategoryClick(gropPostion,position);
            }
        });
    }

    public int getItemCount() {
        return subCategories.size();
    }
}
