package com.example.umair.shopgrocery.Adapters;


import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.umair.shopgrocery.Decorator;
import com.example.umair.shopgrocery.Model.Category;
import com.example.umair.shopgrocery.R;

import java.util.List;

public class CategoryAdapter
        extends BaseExpandableListAdapter {
    private Context context;
    private List<Category> categories;

    public CategoryAdapter(Context context, List<Category> categories) {
        this.context = context;
        this.categories = categories;
    }

    @Override
    public int getGroupCount() {
        return categories.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {

        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return categories.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return categories.get(groupPosition).getSubCategories().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        // Inflating header layout and setting text
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            assert infalInflater != null;
            convertView = infalInflater.inflate(R.layout.header_exp, parent, false);
        }
        CardView card = convertView.findViewById(R.id.cardView);
        TextView btn = convertView.findViewById(R.id.categoryExpandBtn);
        TextView title = convertView.findViewById(R.id.categoryTitle);
        TextView subHeading = convertView.findViewById(R.id.sub_headings);
        ImageView imageView = convertView.findViewById(R.id.category_image);
        Category category = categories.get(groupPosition);
        title.setText(category.getCategoryTitle());
        subHeading.setText(category.getCategorySubTitle());
        Glide.with(context).load(category.getCategoryImageUrl()).into(imageView);
        ViewGroup.MarginLayoutParams layoutParams =
                (ViewGroup.MarginLayoutParams) card.getLayoutParams();


        if (isExpanded) {
            card.setCardElevation(0);
            card.setCardBackgroundColor(context.getResources().getColor(R.color.colorItemBackground));
            layoutParams.setMargins(4, 4, 4, -DptoPx(8));
            card.requestLayout();
            btn.setCompoundDrawablesWithIntrinsicBounds(0, 0,
                    R.drawable.ic_keyboard_arrow_down_black_24dp, 0);
        } else {
            // If group is not expanded then change the text back into normal
            // and change the icon
            card.setCardElevation(4);
            card.setCardBackgroundColor(context.getResources().getColor(android.R.color.white));
            layoutParams.setMargins(4, 4, 4, 4);
            card.requestLayout();
            btn.setCompoundDrawablesWithIntrinsicBounds(0, 0,
                    R.drawable.ic_keyboard_arrow_up_black_24dp, 0);
        }
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        View v=convertView;
        RecyclerView recyclerView;
        if (v == null) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.child_exp, parent,
                    false);
            Category groupname = categories.get(groupPosition);
            recyclerView = v.findViewById(R.id.inner_rv);
            SubCategoryAdapter sbc=new SubCategoryAdapter(context,
                    groupname.getSubCategories(),groupPosition);
            recyclerView.setLayoutManager(new GridLayoutManager(context,3));
            recyclerView.setAdapter(sbc);
        }else
        {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.child_exp, parent,
                    false);
            Category groupname = categories.get(groupPosition);
            recyclerView = v.findViewById(R.id.inner_rv);
            SubCategoryAdapter sbc=new SubCategoryAdapter(context,
                    groupname.getSubCategories(),groupPosition);
            recyclerView.setLayoutManager(new GridLayoutManager(context,3));
            recyclerView.setAdapter(sbc);

        }

        recyclerView.addItemDecoration(new Decorator(context,DptoPx(2),3));

        return v;
    }


    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    private int DptoPx(float a){
        Resources r = context.getResources();
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                a,
                r.getDisplayMetrics()
        );
    }
}
