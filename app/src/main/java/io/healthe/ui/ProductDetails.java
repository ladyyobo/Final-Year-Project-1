package io.healthe.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import io.healthe.R;
import io.healthe.model.Product;
import io.healthe.util.GlideApp;

public class ProductDetails extends AppCompatActivity {

    public static final String EXTRA_PRODUCT = "EXTRA_PRODUCT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_PRODUCT)) {
            Product product = intent.getParcelableExtra(EXTRA_PRODUCT);
            showProductDetails(product);
        }
    }

    private void showProductDetails(Product product) {
        if (product == null) {
            Toast.makeText(getApplicationContext(), "Your product cannot be verified", Toast.LENGTH_SHORT).show();
            finish();
        }

        // TODO: 30-Apr-18 continue from here . Show product props

    }

}
