package com.bblinkout.bboutandroid.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bblinkout.bboutandroid.R;
import com.bblinkout.bboutandroid.entity.CartItem;
import com.bblinkout.bboutandroid.entity.OrderSummary;
import com.bblinkout.bboutandroid.entity.Product;
import com.bblinkout.bboutandroid.entity.SalesOrder;
import com.bblinkout.bboutandroid.util.BBConstants;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class OrderConfirmationActivity extends BaseActivity {
    private final String TAG="CP OrderConfirmation";
    View orderConfirmationView;
    TextView orderSubtotalView,orderTaxView,orderTotalView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        orderConfirmationView=this.getLayoutInflater().inflate(R.layout.activity_order_confirmation,null, true);
        drawer.addView(orderConfirmationView, 0);
        setTitle("Order Confirmation");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dafaultBackPressed();
            }
        });

        orderSubtotalView=findViewById(R.id.order_subtotal);
        orderTaxView=findViewById(R.id.order_tax);
        orderTotalView=findViewById(R.id.order_total);
        Intent intent = getIntent();
        SalesOrder salesOrder=(SalesOrder)intent.getSerializableExtra(BBConstants.SALES_ORDER);
        for(Product product:salesOrder.getProducts()){
            Log.d(TAG,product.getName());
        }
        OrderSummary orderSummary=calculateOrder(salesOrder.getProducts());
        orderSubtotalView.setText(String.valueOf(orderSummary.getOrderSubTotal()));
        orderTaxView.setText(String.valueOf(orderSummary.getOrderTax()));
        orderTotalView.setText(String.valueOf(orderSummary.getOrderTotal()));
    }

    private OrderSummary calculateOrder(List<Product> products) {
        Double orderSubtotal=0.0,orderTax=0.0,orderTotal=0.0;
        for (Product product:products){
            orderSubtotal+=product.getQuantity()*product.getPrice();
        }
        orderTax=orderSubtotal*8.75/100;
        orderTotal=orderSubtotal+orderTax;
        return new OrderSummary(orderSubtotal,orderTax,orderTotal);
    }


    public void placeOrder(View view){
        Log.d(TAG,"placeOrder");
        Toast.makeText(this,"Order Placed",Toast.LENGTH_LONG);
/*        String url= BBConstants.BASE_URL+"/order/";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            cartItem = new CartItem();
                            cartItem.setId(Long.parseLong(response.get("id").toString()));
                            cartItem.setName(response.get("name").toString());
                            cartItem.setDescription(response.get("description").toString());
                            cartItem.setPrice(response.getDouble("price"));
                            cartItem.setQuantity(1);
                            byte[] decodedString = Base64.decode(response.get("imageBlob").toString(), Base64.DEFAULT);
                            Bitmap productImage = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                            cartItem.setProductImage(productImage);
                            cartItems.add(cartItem);
                            myItemRecyclerViewAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue.add(jsonObjectRequest);*/
    }

}
