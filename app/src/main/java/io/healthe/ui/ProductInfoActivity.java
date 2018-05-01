package io.healthe.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.healthe.R;
import io.healthe.model.ImageResponse;
import io.healthe.model.NutritionalValue;
import io.healthe.model.Product;
import io.healthe.util.HealthePrefs;
import io.healthe.util.Utils;
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
    @BindView(R.id.recommended_text)
    TextView recommended;
    @BindView(R.id.not_recommended_text)
    TextView not_recommended;

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
                        ImageResponse imageResponse = (ImageResponse) response.body();
                        Log.e(TAG, "onResponse: " + imageResponse);
                        bindData(imageResponse);
                    }
                }

                @Override
                public void onFailure(Call<ImageResponse> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "No product found for " + productID, Toast.LENGTH_LONG).show();
                    finish();
                }
            });
        }
    }

    private void bindData(ImageResponse imageResponse) {
        Product product = imageResponse.getProduct();
        List<Product> recommendations = imageResponse.getRecommendations();
        List<NutritionalValue> nutritionalValues = imageResponse.getValues();

        name.setText(product.getName());
        manufacturer.setText(product.getManufacturer());
        if (product.getImage() != null) {
//            image.setImageURI(Uri.parse(Utils.IMAGE_BASE+product.getImage().trim()));


            Glide.with(ProductInfoActivity.this)
                    .load(Utils.IMAGE_BASE + product.getImage().trim())
                    .into(image)
            ;
        }

        if (nutritionalValues != null) {
            adapter.addNewContent(nutritionalValues);
            adapter.notifyDataSetChanged();
        } else {
            Snackbar.make(container, "We could not retrieve nutritional values for " + product.getName(), Snackbar.LENGTH_LONG).show();
        }

        // TODO: 26-Apr-18 Add recommendations to list
        if (recommendations != null) {
            RecommendationAdapter rAdapter = new RecommendationAdapter(recommendations, this);
            recommendationsList.setAdapter(rAdapter);
            LinearLayoutManager lm = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
            recommendationsList.setLayoutManager(lm);
            recommendationsList.setHasFixedSize(true);

            // This will check whether the incoming list of recommendations is empty
            //If it is, then hide the recyclerview and show that the item is recommended
            if (recommendations.isEmpty()) {
                TransitionManager.beginDelayedTransition(container);
                not_recommended.setVisibility(View.GONE);
                recommendationsList.setVisibility(View.GONE);
                recommended.setVisibility(View.VISIBLE);
            } else {
                TransitionManager.beginDelayedTransition(container);
                not_recommended.setVisibility(View.VISIBLE);
                recommendationsList.setVisibility(View.VISIBLE);
                recommended.setVisibility(View.GONE);
            }
        }
    }

    class RecommendationAdapter extends RecyclerView.Adapter<RecommendationsViewHolder> {
        private List<Product> products;
        private Activity host;

        public RecommendationAdapter(List<Product> products, Activity host) {
            this.products = products;
            this.host = host;
        }

        @NonNull
        @Override
        public RecommendationsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new RecommendationsViewHolder(getLayoutInflater().inflate(R.layout.item_recommendation, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull RecommendationsViewHolder holder, int position) {
            Product product = products.get(position);
            holder.name.setText(product.getName());
            Glide.with(ProductInfoActivity.this)
                    .load(Utils.IMAGE_BASE + product.getImage().trim())
                    .into(holder.image);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    Intent intent = new Intent(host, ProductDetails.class);
//                    intent.putExtra(ProductDetails.EXTRA_PRODUCT, product);
                    Intent intent = new Intent(host, ProductInfoActivity.class);
                    intent.putExtra(ProductInfoActivity.EXTRA_CONTENT, product.getId());
                    startActivity(intent);
                }
            });
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
