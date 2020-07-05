package com.holisticladies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.holisticladies.database.DatabaseHandler;
import com.holisticladies.model.Cart;
import com.holisticladies.model.Favourite;
import com.holisticladies.model.ListenerModel;

import static com.holisticladies.adapters.ProductAdapter.EXTRA_ITEM_ID;
import static com.holisticladies.adapters.ProductAdapter.EXTRA_ITEM_MAKER_ID;
import static com.holisticladies.adapters.ProductAdapter.EXTRA_ITEM_TITLE;
import static com.holisticladies.adapters.ProductAdapter.EXTRA_ITEM_DETAILS;
import static com.holisticladies.adapters.ProductAdapter.EXTRA_ITEM_PRICE;
import static com.holisticladies.adapters.ProductAdapter.EXTRA_ITEM_IMAGE;

public class ProductDetails extends AppCompatActivity {

    TextView textView_item_name, textView_item_description, textView_item_price;
    ImageView imageView_item_image;
    LinearLayout addTo_favourite;
    Button addTo_cart;

    DatabaseHandler db = new DatabaseHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        Intent intent = getIntent();
        String item_name = intent.getStringExtra(EXTRA_ITEM_TITLE);
        String item_description = intent.getStringExtra(EXTRA_ITEM_DETAILS);
        String item_price = intent.getStringExtra(EXTRA_ITEM_PRICE);
        String item_image = intent.getStringExtra(EXTRA_ITEM_IMAGE);

        textView_item_name = findViewById(R.id.item_name);
        textView_item_name.setText(item_name);
        textView_item_description = findViewById(R.id.item_description);
        textView_item_description.setText(item_description);
        textView_item_price = findViewById(R.id.item_price);
        String price = "Kshs " + item_price;
        textView_item_price.setText(price);
        imageView_item_image = findViewById(R.id.item_image);
        Glide.with(this)
                .load(item_image)
                .into(imageView_item_image);

        clicked();

    }

    void clicked(){
        addTo_cart = findViewById(R.id.layout_addTo_cart);
        addTo_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                String item_id = intent.getStringExtra(EXTRA_ITEM_ID);
                String item_name = intent.getStringExtra(EXTRA_ITEM_TITLE);
                String item_price = intent.getStringExtra(EXTRA_ITEM_PRICE);
                String item_image = intent.getStringExtra(EXTRA_ITEM_IMAGE);
                String item_description = intent.getStringExtra(EXTRA_ITEM_DETAILS);
                String item_maker_id = intent.getStringExtra(EXTRA_ITEM_MAKER_ID);

                Cart cart = new Cart(
                        item_id,
                        item_name,
                        item_image,
                        item_price,
                        1,
                        20,
                        item_description,
                        item_maker_id
                );

                boolean exists = db.checkIfProductExists(item_id);

                if (exists){
                    Toast.makeText(getApplicationContext(), "Product is already in your cart!", Toast.LENGTH_SHORT).show();
                } else {
                    db.addToCart(cart);
                    Toast.makeText(getApplicationContext(), "Product added to cart", Toast.LENGTH_SHORT).show();
                    ListenerModel.getInstance().changeRefresh(true);
                }

            }
        });

        addTo_favourite = findViewById(R.id.layout_addTo_favourite);
        addTo_favourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                String item_id = intent.getStringExtra(EXTRA_ITEM_ID);
                String item_name = intent.getStringExtra(EXTRA_ITEM_TITLE);
                String item_price = intent.getStringExtra(EXTRA_ITEM_PRICE);
                String item_image = intent.getStringExtra(EXTRA_ITEM_IMAGE);
                String item_description = intent.getStringExtra(EXTRA_ITEM_DETAILS);

                Favourite favourite = new Favourite(
                        item_id,
                        item_name,
                        item_image,
                        item_price,
                        item_description
                );

                boolean exists = db.checkIfProductExistsInFavourite(item_id);

                if (exists){
                    Toast.makeText(getApplicationContext(), "Product is already in your favourites!", Toast.LENGTH_SHORT).show();
                } else {
                    db.addToFavourite(favourite);
                    Toast.makeText(getApplicationContext(), "Product added to your favourites", Toast.LENGTH_SHORT).show();
                    ListenerModel.getInstance().changeFavouriteRefresh(true);
                }
            }
        });
    }


}
