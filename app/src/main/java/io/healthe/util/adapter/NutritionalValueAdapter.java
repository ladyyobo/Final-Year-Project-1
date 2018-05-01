package io.healthe.util.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.healthe.R;
import io.healthe.model.NutritionalValue;

/**
 * Created by Serwaa on 19-Feb-18.
 */


public class NutritionalValueAdapter extends RecyclerView.Adapter<NutritionalValueAdapter.NutritionViewHolder> {
    private Context context;
    private List<NutritionalValue> nutritionalValues;

    public NutritionalValueAdapter(Context context) {
        this.context = context;
        this.nutritionalValues = new ArrayList<>(0);
    }

    @Override
    public NutritionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NutritionViewHolder(LayoutInflater.from(context).inflate(R.layout.nutritional_value, parent, false));
    }

    @Override
    public void onBindViewHolder(NutritionViewHolder holder, int position) {
        NutritionalValue nutritionalValue = nutritionalValues.get(position);
        holder.tv_percentage.setText(nutritionalValue.getPercentage());
        holder.tv_nutrient.setText(nutritionalValue.getNutrient());
    }

    @Override
    public int getItemCount() {
        return nutritionalValues.size();
    }

    public void addNewContent(List<NutritionalValue> newContent) {
        if (newContent.isEmpty()) return;
        for (NutritionalValue val : newContent) {
            nutritionalValues.add(val);
            notifyItemRangeChanged(0, newContent.size());
        }
    }

    public static class NutritionViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_nutrient)
        TextView tv_nutrient;
        @BindView(R.id.tv_percentage)
        TextView tv_percentage;

        public NutritionViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}


