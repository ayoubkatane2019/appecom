package com.example.appecom.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.appecom.R;
import com.example.appecom.models.NewProductsModel;
import com.example.appecom.models.PopularProductsModel;
import com.example.appecom.models.ShowAllModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Set;

public class DetailedActivity extends AppCompatActivity {


    ImageView detailedImg;
    TextView rating,name,description,price,quantity;
    Button addToCarte,buyNow;
    ImageView addItems,removeItems;



Toolbar toolbar;


    int totalQuantity = 1;
    int totalPrice = 0;


    NewProductsModel newProductsModel=null;

    PopularProductsModel popularProductsModel=null;

    ShowAllModel showAllModel=null;

    FirebaseAuth auth;

    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);

        toolbar=findViewById(R.id.detailed_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        firestore = FirebaseFirestore.getInstance();

        auth = FirebaseAuth.getInstance();

        final Object obj = getIntent().getSerializableExtra("detailed");

        if (obj instanceof NewProductsModel){

            newProductsModel = (NewProductsModel) obj;

        }else if (obj instanceof PopularProductsModel){

            popularProductsModel =(PopularProductsModel) obj;
        }else if (obj instanceof ShowAllModel){

            showAllModel =(ShowAllModel) obj;
        }

        detailedImg = findViewById(R.id.detailed_img);
        quantity = findViewById(R.id.quantity);
        name = findViewById(R.id.detailed_name);
        rating = findViewById(R.id.rating);
        description=findViewById(R.id.detailed_desc);
        price = findViewById(R.id.detailed_price);
        addToCarte = findViewById(R.id.add_to_cart);
        buyNow = findViewById(R.id.buy_now);
        addItems = findViewById(R.id.add_item);
        removeItems = findViewById(R.id.remove_item);




        if (showAllModel != null){

            Glide.with(getApplicationContext()).load(showAllModel.getImg_url()).into(detailedImg);
            name.setText(showAllModel.getName());
            rating.setText(showAllModel.getRating());
            description.setText(showAllModel.getDescription());
            price.setText(String.valueOf(showAllModel.getPrice()));


            //totalPrice= Integer.parseInt(showAllModel.getPrice())*totalQuantity;





        }

        if (popularProductsModel != null){

            Glide.with(getApplicationContext()).load(popularProductsModel.getImg_url()).into(detailedImg);
            name.setText(popularProductsModel.getName());
            rating.setText(popularProductsModel.getRating());
            description.setText(popularProductsModel.getDescription());
            price.setText(String.valueOf(popularProductsModel.getPrice()));}


//buy now acheter maint

        buyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailedActivity.this,AddressActivity.class);

                if (newProductsModel != null){
                    intent.putExtra("item",newProductsModel);
                } if  (popularProductsModel !=null){
                    intent.putExtra("item",popularProductsModel);
                }if  (showAllModel !=null){
                    intent.putExtra("item",showAllModel);
                }
                startActivity(intent);
            }
        });



// add to cart
        addToCarte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                addToCarte();
            }
        });

        addItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              additemm();

            }
        });

        removeItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              removeittem();

            }
        });


        addprii();

    }

    private int additemm() {
        if (totalQuantity<10){
             quantity.setText(String.valueOf(totalQuantity));

            if (newProductsModel !=null ){

                // totalPrice=newProductsModel.getPrice().compareTo(String.valueOf(totalQuantity)) ;
            }
            if (popularProductsModel !=null){

                //  totalPrice=popularProductsModel.getPrice().compareTo(String.valueOf(totalQuantity)) ;

            }
            if (showAllModel !=null){

                //   totalPrice=showAllModel.getPrice().compareTo(String.valueOf(totalQuantity)) ;
            }
        }
        return totalQuantity++;
    }

private  int removeittem() {
    if (totalQuantity>1){
          quantity.setText(String.valueOf(totalQuantity));
    }
    return totalQuantity--;

}

private   void addprii () {
    if (newProductsModel != null) {

        Glide.with(getApplicationContext()).load(newProductsModel.getImg_url()).into(detailedImg);
        name.setText(newProductsModel.getName());
        rating.setText(newProductsModel.getRating());
        description.setText(newProductsModel.getDescription());
        price.setText(String.valueOf(newProductsModel.getPrice()));
        name.setText(newProductsModel.getName());


        // totalPrice= Integer.parseInt(newProductsModel.getPrice())*totalQuantity;
        totalPrice = Integer.parseInt(quantity.getText().toString());


        // Toast.makeText(DetailedActivity.this,   , Toast.LENGTH_SHORT).show();
    }
}
        private void addToCarte() {


        String saveCurrentTime,saveCurrentDate;
        Calendar calForDate = Calendar.getInstance();

        SimpleDateFormat currentDate =  new SimpleDateFormat("MM dd, yyyy");
        saveCurrentDate = currentDate.format(calForDate.getTime());

        SimpleDateFormat currentTime =  new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calForDate.getTime());

        int pr,quatt,total;
        pr=Integer.parseInt(price.getText().toString());
        quatt=Integer.parseInt(quantity.getText().toString());
                total=pr*quatt;
                String tt = String.valueOf(total);

        final HashMap<String,Object> cartMap =new HashMap<>();
        cartMap.put("productName",name.getText().toString());
        cartMap.put("productPrice",price.getText().toString());
        cartMap.put("currentTime",saveCurrentTime.toString());
        cartMap.put("currentDate",saveCurrentDate.toString());
        cartMap.put("totalQuantity",quantity.getText().toString());
        cartMap.put("totalPrice",tt);

        firestore.collection("AddToCart").document(auth.getCurrentUser().getUid())
                .collection("User").add(cartMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {

                Toast.makeText(DetailedActivity.this," AJOUTÉ AU PANIER",Toast.LENGTH_SHORT).show();
                finish();
            }
        });


    }
}