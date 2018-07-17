package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @BindView(R.id.image_iv)
    ImageView ingredientsIv;
    @BindView(R.id.also_known_tv)
    TextView alsoKnownAsText;
    @BindView(R.id.ingredients_tv)
    TextView ingredientsText;
    @BindView(R.id.origin_tv)
    TextView placeOfOriginText;
    @BindView(R.id.description_tv)
    TextView descriptionText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .error(R.drawable.noimage)
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {

        List<String> listAka = sandwich.getAlsoKnownAs();
        List<String> ingredientsList = sandwich.getIngredients();

        StringBuilder Akabuilder = new StringBuilder();
        StringBuilder ingredientsBuilder = new StringBuilder();

        for (int i = 0; i < listAka.size(); i++) {
            Akabuilder.append(listAka.get(i));
            Akabuilder.append("\n");
        }
        alsoKnownAsText.setText(Akabuilder.toString());

        for (int i = 0; i < ingredientsList.size(); i++) {
            ingredientsBuilder.append(ingredientsList.get(i));
            ingredientsBuilder.append("\n");
        }
        ingredientsText.setText(ingredientsBuilder.toString());

        placeOfOriginText.setText(sandwich.getPlaceOfOrigin());

        descriptionText.setText(sandwich.getDescription());
    }
}
