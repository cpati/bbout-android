package com.bblinkout.bboutandroid.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.View;

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
import com.bblinkout.bboutandroid.fragment.ItemFragment;
import com.bblinkout.bboutandroid.fragment.ItemRecyclerViewAdapter;
import com.bblinkout.bboutandroid.util.BBConstants;
import com.bblinkout.bboutandroid.util.RestClientQueue;
import com.google.zxing.Result;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class CartActivity extends BaseActivity
        implements  ItemFragment.OnListFragmentInteractionListener, ZXingScannerView.ResultHandler,View.OnClickListener{
    private final String TAG="CP CartActivity";
    FragmentManager manager;
    private ZXingScannerView scannerView;
    private RestClientQueue restClientQueue;
    RequestQueue requestQueue;
    CartItem cartItem;
    ArrayList<CartItem> cartItems=new ArrayList<>();
    private RecyclerView mRecyclerView;
    private ItemRecyclerViewAdapter myItemRecyclerViewAdapter;
    View cartView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cartView=this.getLayoutInflater().inflate(R.layout.activity_cart,null, true);
        drawer.addView(cartView, 0);
        restClientQueue=RestClientQueue.getInstance(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);
        FloatingActionButton proceedCheckout = (FloatingActionButton) findViewById(R.id.proceed_checkout);
        proceedCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SalesOrder salesOrder=createOrder();
                Intent intent=new Intent(getApplicationContext(),OrderConfirmationActivity.class);
                intent.putExtra(BBConstants.SALES_ORDER,salesOrder);
                startActivity(intent);
            }
        });
        ItemFragment itemFragment=new ItemFragment();

        manager = getSupportFragmentManager();
        manager.beginTransaction()
                .replace(R.id.container, itemFragment, "Cartitem")
                .commit();
        setTitle("Shopping cart");
    }

    private SalesOrder createOrder() {
        SalesOrder salesOrder=new SalesOrder();
        salesOrder.setUserId(1L);
        List<Product> products=new ArrayList<>();
        for(CartItem cartItem:cartItems){
            products.add(new Product(cartItem.getId(),cartItem.getName(),cartItem.getDescription(),cartItem.getPrice(),cartItem.getQuantity(),cartItem.getBarCode()));
        }
        salesOrder.setProducts(products);
        return salesOrder;
    }

    public void onClick(View view) {
        scannerView=new ZXingScannerView(CartActivity.this);
        requestQueue = restClientQueue.getRequestQueue();
        scannerView.setResultHandler(this);
        drawer.removeViewAt(0);
        drawer.addView(scannerView,0);
        scannerView.startCamera();

    }

    public void getProductInfo(String barcode)
    {
        String url= BBConstants.BASE_URL+"/product/"+barcode+"/";
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

        requestQueue.add(jsonObjectRequest);

}

    @Override
    public void handleResult(Result result) {
        String barcode=result.getText();
        barcode="AAAAAAAA";
        getProductInfo(barcode);
        drawer.removeViewAt(0);
        drawer.addView(cartView,0);
        scannerView.stopCamera();
    }

    @Override
    public void onListFragmentInteraction(CartItem cartItem) {

    }

    public void setAdapter(View view) {
        myItemRecyclerViewAdapter=new ItemRecyclerViewAdapter(cartItems,this);
        mRecyclerView = (RecyclerView) view;
        mRecyclerView.setAdapter(myItemRecyclerViewAdapter);
    }

    public void deleteItem(int position){
        Log.d(TAG,String.valueOf(position));
        cartItems.remove(position);
        myItemRecyclerViewAdapter.notifyDataSetChanged();
    }

    public void updateItem(Long id,int quantity){
        Log.d(TAG,String.valueOf(id));
        for(CartItem cartItem:cartItems){
            if (id == cartItem.getId())
                cartItem.setQuantity(quantity);
        }
        //myItemRecyclerViewAdapter.notifyDataSetChanged();
    }
}
