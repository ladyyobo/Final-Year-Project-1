package io.healthe.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.healthe.R;
import io.healthe.model.ImageResponse;
import io.healthe.model.NutritionValues;
import io.healthe.model.Product;
import io.healthe.util.HealthePrefs;
import io.healthe.util.adapter.NutritionalValueAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductInfoActivity extends AppCompatActivity {
    public static final String EXTRA_CONTENT = "EXTRA_CONTENT";
    private static final String TAG = "ProductInfoActivity";

    @BindView(R.id.container)
    ViewGroup container;
    @BindView(R.id.tv_prod_name)
    TextView name;
    @BindView(R.id.tv_prod_manu)
    TextView manufacturer;
    @BindView(R.id.tv_prod_nutrient)
    TextView nutrient;
    @BindView(R.id.tv_prod_percentage)
    TextView percentage;
    @BindView(R.id.product_image)
    ImageView image;
    @BindView(R.id.lv_nutri_value)
    RecyclerView nutritionList;
    @BindView(R.id.lv_recommendations)
    RecyclerView recommendationsList;

    private HealthePrefs prefs;
    private NutritionalValueAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_info);
        ButterKnife.bind(this);

        init();
        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_CONTENT)) {
            String productID = intent.getStringExtra(EXTRA_CONTENT);
            displayProductInfo(productID);
        }
    }

    private void init() {
        prefs = HealthePrefs.get(this);
        adapter = new NutritionalValueAdapter(getApplicationContext());

        //RecyclerView
        nutritionList.setAdapter(adapter);
        nutritionList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        nutritionList.setHasFixedSize(true);
        nutritionList.setItemAnimator(new DefaultItemAnimator());
    }

    private void displayProductInfo(String productID) {
        if (productID != null) {

            prefs.getApi().getProductDetails(String.valueOf(prefs.getId()), productID).enqueue(new Callback<ImageResponse>() {
                @Override
                public void onResponse(Call<ImageResponse> call, Response<ImageResponse> response) {
                    if (response == null) {
                        Snackbar.make(container, response.message(), Snackbar.LENGTH_INDEFINITE).show();
                    } else {
                        ImageResponse imageResponse = response.body();
                        bindData(imageResponse);
                    }
                }

                @Override
                public void onFailure(Call<ImageResponse> call, Throwable t) {
                    Snackbar.make(container, t.getLocalizedMessage(), Snackbar.LENGTH_INDEFINITE).show();
                }
            });
        }
    }

    private void bindData(ImageResponse imageResponse) {
        Product product = imageResponse.getProduct();
        List<Product> recommendations = imageResponse.getRecommendations();
        List<NutritionValues> nutritionValues = imageResponse.getValues();

        name.setText(product.getName());
        manufacturer.setText(product.getManufacturer());
        if (product.getImage() != null) {
            image.setImageURI(Uri.parse(product.getImage()));
        }

        if (nutritionValues != null) {
            adapter.addNewContent(nutritionValues);
        } else {
            Snackbar.make(container, "We could not retrieve nutritional values for " + product.getName(), Snackbar.LENGTH_LONG).show();
        }

        // TODO: 26-Apr-18 Add recommendations to list
        if (recommendations != null) {
            RecommendationAdapter rAdapter = new RecommendationAdapter(recommendations);
            recommendationsList.setAdapter(rAdapter);
            LinearLayoutManager lm = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
            recommendationsList.setLayoutManager(lm);
            recommendationsList.setHasFixedSize(true);
        }
    }

    class RecommendationAdapter extends RecyclerView.Adapter<RecommendationsViewHolder> {
        private List<Product> products;

        public RecommendationAdapter(List<Product> products) {
            this.products = products;
        }

        @NonNull
        @Override
        public RecommendationsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new RecommendationsViewHolder(getLayoutInflater().inflate(R.layout.item_recommendation, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull RecommendationsViewHolder holder, int position) {
            Product product = products.get(position);
            holder.image.setImageURI(Uri.parse(product.getImage()));
            holder.name.setText(product.getName());
        }

        @Override
        public int getItemCount() {
            return products.size();
        }
    }

    public static class RecommendationsViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.r_name)
        TextView name;
        @BindView(R.id.r_image)
        ImageView image;

        public RecommendationsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
