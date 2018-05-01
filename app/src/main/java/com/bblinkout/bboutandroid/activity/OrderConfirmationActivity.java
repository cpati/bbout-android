package com.bblinkout.bboutandroid.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bblinkout.bboutandroid.R;
import com.bblinkout.bboutandroid.entity.CartItem;
import com.bblinkout.bboutandroid.entity.OrderSummary;
import com.bblinkout.bboutandroid.entity.Product;
import com.bblinkout.bboutandroid.entity.SalesOrder;
import com.bblinkout.bboutandroid.util.BBConstants;
import com.bblinkout.bboutandroid.util.RestClientQueue;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

public class OrderConfirmationActivity extends BaseActivity {
    private final String TAG="CP OrderConfirmation";
    View orderConfirmationView;
    TextView orderSubtotalView,orderTaxView,orderTotalView;
    RequestQueue requestQueue;
    private RestClientQueue restClientQueue;
    SalesOrder salesOrder;
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
        salesOrder=(SalesOrder)intent.getSerializableExtra(BBConstants.SALES_ORDER);
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
        try {
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("name", "chida");
            jsonObj.put("surname", "pati");
            Log.d(TAG,"chida"+jsonObj.toString());
            Log.d(TAG,"chida"+(new GsonBuilder().setPrettyPrinting().create()).toJson(jsonObj));
        }catch (JSONException e) {
            e.printStackTrace();
        }


        JSONObject salesOrderJSON = new JSONObject();
        try {
            salesOrderJSON.put("OrderDetail",salesOrder);
            JSONObject salesOrderJSON1 = new JSONObject(salesOrderJSON.toString());
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            Log.d(TAG,salesOrderJSON1.toString());
            Log.d(TAG,gson.toJson(salesOrderJSON1));
        } catch (JSONException e) {
            e.printStackTrace();
        }


        restClientQueue=RestClientQueue.getInstance(this);
        requestQueue = restClientQueue.getRequestQueue();
        Toast.makeText(this,"Order Placed",Toast.LENGTH_LONG);
        String url= BBConstants.BASE_URL+"order/";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, salesOrderJSON, new Response.Listener<JSONObject>(){
            @Override
            public void onResponse(JSONObject response) {
                try {
                            Log.d(TAG,"create order:"+response.get("orderId").toString());
                            String smsString="Order Number:"+salesOrder.getOrderId()+" "+
                                    "No of Products:"+salesOrder.getProducts().size() +" "+
                                    "Order Total:"+salesOrder.getOrderTotal() ;
                            sendSMS(smsString);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
            }
        },new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG,"create order erro:"+error.getMessage());
            }
        });
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, salesOrder,
//                new Response.Listener<JSONObject>()
//                {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        try {
//                            Log.d(TAG,"create order:"+response.get("orderId").toString());
//                            String smsString="Order Number:"+salesOrder.getOrderId()+" "+
//                                    "No of Products:"+salesOrder.getProducts().size() +" "+
//                                    "Order Total:"+salesOrder.getOrderTotal() ;
//                            sendSMS(smsString);
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//            }
//        });

                requestQueue.add(jsonObjectRequest);
    }

    public void sendSMS(String message){
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(BBConstants.PHONE_NUMBER, null, message, null, null);
        Toast.makeText(getApplicationContext(), "SMS sent.",
                Toast.LENGTH_LONG).show();
    }

}
