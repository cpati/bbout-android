package com.bblinkout.bboutandroid.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.util.Log;
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
import com.bblinkout.bboutandroid.util.BaseUrl;
import com.bblinkout.bboutandroid.util.RestClientQueue;
import com.google.gson.JsonObject;
import com.google.zxing.Result;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class CartActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener , ItemFragment.OnListFragmentInteractionListener{

    FragmentManager manager;
    private ZXingScannerView scannerView;
    private RestClientQueue restClientQueue;
    RequestQueue requestQueue;
    CartItem cartItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        restClientQueue=RestClientQueue.getInstance(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //        .setAction("Action", null).show();

                scannerView=new ZXingScannerView(CartActivity.this);
                requestQueue = restClientQueue.getRequestQueue();
                scannerView.setResultHandler(new ZxingScannerResultHandler());
                setContentView(scannerView);
                scannerView.startCamera();

            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ItemFragment itemFragment=new ItemFragment();

        manager = getSupportFragmentManager();
        manager.beginTransaction()
                .replace(R.id.container, itemFragment, "Cartitem")
                .commit();
        setTitle("Shopping cart");
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
                    cartItem.setProductName(response.get("name").toString());
                    cartItem.setProductDescription(response.get("description").toString());
                    cartItem.setPrice(response.getDouble("price"));
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

    class ZxingScannerResultHandler implements ZXingScannerView.ResultHandler
    {
        @Override
        public void handleResult(Result result) {
            String barcode=result.getText();
            Toast.makeText(getApplicationContext(),barcode,Toast.LENGTH_LONG).show();
            getProductInfo(barcode);
            setContentView(R.layout.activity_cart);
            scannerView.stopCamera();
        }
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.cart, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onListFragmentInteraction(CartItem cartItem) {

    }
}
