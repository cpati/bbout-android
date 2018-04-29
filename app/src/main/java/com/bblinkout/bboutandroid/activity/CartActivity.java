package com.bblinkout.bboutandroid.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bblinkout.bboutandroid.R;
import com.bblinkout.bboutandroid.entity.CartItem;
import com.bblinkout.bboutandroid.fragment.ItemFragment;
import com.bblinkout.bboutandroid.fragment.MyItemRecyclerViewAdapter;
import com.bblinkout.bboutandroid.fragment.OrderRecyclerViewAdapter;
import com.bblinkout.bboutandroid.util.BaseUrl;
import com.bblinkout.bboutandroid.util.RestClientQueue;
import com.google.gson.JsonObject;
import com.google.zxing.Result;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class CartActivity extends BaseActivity
        implements  ItemFragment.OnListFragmentInteractionListener, ZXingScannerView.ResultHandler,View.OnClickListener{

    FragmentManager manager;
    private ZXingScannerView scannerView;
    private RestClientQueue restClientQueue;
    RequestQueue requestQueue;
    CartItem cartItem;
    List<CartItem> cartItems=new ArrayList<>();
    private RecyclerView mRecyclerView;
    private MyItemRecyclerViewAdapter myItemRecyclerViewAdapter;
    View cartView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cartView=this.getLayoutInflater().inflate(R.layout.activity_cart,null, true);
        drawer.addView(cartView, 0);
        restClientQueue=RestClientQueue.getInstance(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);

        ItemFragment itemFragment=new ItemFragment();

        manager = getSupportFragmentManager();
        manager.beginTransaction()
                .replace(R.id.container, itemFragment, "Cartitem")
                .commit();
        setTitle("Shopping cart");
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
        Toast.makeText(getApplicationContext(),"Barcode no:"+barcode,Toast.LENGTH_SHORT).show();
        String url=BaseUrl.BASE_URL+"/product/"+barcode+"/";
           JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>()
                {
            @Override
            public void onResponse(JSONObject response) {
            try {
                    cartItem = new CartItem();
                    cartItem.setName(response.get("name").toString());
                    cartItem.setDescription(response.get("description").toString());
                    cartItem.setPrice(response.getDouble("price"));
                    cartItems.add(cartItem);
                    myItemRecyclerViewAdapter.notifyDataSetChanged();
                    Toast.makeText(getApplicationContext(),response.get("name").toString(),Toast.LENGTH_SHORT).show();
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
        Toast.makeText(getApplicationContext(),barcode,Toast.LENGTH_LONG).show();
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
        myItemRecyclerViewAdapter=new MyItemRecyclerViewAdapter(cartItems,this);
        mRecyclerView = (RecyclerView) view;
        mRecyclerView.setAdapter(myItemRecyclerViewAdapter);
    }
}
