package com.bblinkout.bboutandroid.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bblinkout.bboutandroid.R;
import com.bblinkout.bboutandroid.entity.CartItem;
import com.bblinkout.bboutandroid.entity.Product;
import com.bblinkout.bboutandroid.entity.SalesOrder;
import com.bblinkout.bboutandroid.fragment.ItemFragment;
import com.bblinkout.bboutandroid.fragment.ItemRecyclerViewAdapter;
import com.bblinkout.bboutandroid.util.BBConstants;
import com.bblinkout.bboutandroid.util.BBWebSocketClient;
import com.bblinkout.bboutandroid.util.RestClientQueue;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.zxing.Result;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.CompletableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.dm7.barcodescanner.zxing.ZXingScannerView;
import ua.naiksoftware.stomp.Stomp;
import ua.naiksoftware.stomp.client.StompClient;

public class CartActivity extends BaseActivity
        implements ItemFragment.OnListFragmentInteractionListener, ZXingScannerView.ResultHandler, View.OnClickListener {
    private final String TAG = "CP CartActivity";
    public static Boolean isOrderPlaced = false;
    FragmentManager manager;
    private ZXingScannerView scannerView;
    private RestClientQueue restClientQueue;
    RequestQueue requestQueue;
    CartItem cartItem;
    ArrayList<CartItem> cartItems = new ArrayList<>();
    ArrayList<CartItem> tempCartItems = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private ItemRecyclerViewAdapter myItemRecyclerViewAdapter;
    View cartView;
    private StompClient mStompClient;
    Long userId = 1L;
    Long shoppingCartOrderId = userId * -999;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG,"onCreate");
        super.onCreate(savedInstanceState);
        cartView = this.getLayoutInflater().inflate(R.layout.activity_cart, null, true);
        drawer.addView(cartView, 0);
        restClientQueue = RestClientQueue.getInstance(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);
        FloatingActionButton proceedCheckout = (FloatingActionButton) findViewById(R.id.proceed_checkout);
        proceedCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SalesOrder salesOrder = createOrder();
                if (cartItems.size() >0) {
                    Intent intent = new Intent(getApplicationContext(), OrderConfirmationActivity.class);
                    intent.putExtra(BBConstants.SALES_ORDER, salesOrder);
                    startActivity(intent);
                }
            }
        });
        ItemFragment itemFragment = new ItemFragment();

        manager = getSupportFragmentManager();
        manager.beginTransaction()
                .replace(R.id.container, itemFragment, "Cartitem")
                .commit();
        setTitle("Shopping cart");
        getOrder(shoppingCartOrderId);
    }

    @Override
    protected void onResume() {
        super.onResume();
        connectStomp();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disconnectStomp();
    }

    //Bar Code Scanning
    public void onClick(View view) {
        scannerView = new ZXingScannerView(CartActivity.this);
        requestQueue = restClientQueue.getRequestQueue();
        scannerView.setResultHandler(this);
        drawer.removeViewAt(0);
        drawer.addView(scannerView, 0);
        scannerView.startCamera();


    }

    public void getProductInfo(String barcode) {
        String url = BBConstants.BASE_URL + "/product/" + barcode + "/";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
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
                            placeOrder(shoppingCartOrderId);
                            sendEchoViaStomp();
                            //myItemRecyclerViewAdapter.notifyDataSetChanged();
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
        String barcode = result.getText();
        getProductInfo(barcode);
        drawer.removeViewAt(0);
        drawer.addView(cartView, 0);
        scannerView.stopCamera();
    }

    @Override
    public void onListFragmentInteraction(CartItem cartItem) {

    }

    public void setAdapter(View view) {
        myItemRecyclerViewAdapter = new ItemRecyclerViewAdapter(cartItems, this);
        mRecyclerView = (RecyclerView) view;
        mRecyclerView.setAdapter(myItemRecyclerViewAdapter);
    }

    public void deleteItem(int position) {
        Log.d(TAG, String.valueOf(position));
        cartItems.remove(position);
        myItemRecyclerViewAdapter.notifyDataSetChanged();
    }

    public void updateItem(Long id, int quantity) {
        Log.d(TAG, String.valueOf(id));
        for (CartItem cartItem : cartItems) {
            if (id == cartItem.getId())
                cartItem.setQuantity(quantity);
        }
        //myItemRecyclerViewAdapter.notifyDataSetChanged();
    }

    //Create Order
    private SalesOrder createOrder(Long orderId) {
        SalesOrder salesOrder = new SalesOrder();
        if (orderId != null)
            salesOrder.setOrderId(orderId);
        salesOrder.setUserId(userId);
        List<Product> products = new ArrayList<>();
        for (CartItem cartItem : cartItems) {
            products.add(new Product(cartItem.getId(), cartItem.getName(), cartItem.getDescription(), cartItem.getPrice(), cartItem.getQuantity(), cartItem.getBarCode()));
        }
        salesOrder.setProducts(products);
        return salesOrder;
    }

    private SalesOrder createOrder() {
        return createOrder(null);
    }

    // Get Order for Shopping Cart when multiple users are present
    public void getOrder(Long orderId) {
        final RestClientQueue instance = RestClientQueue.getInstance(getApplicationContext());
        String url = BBConstants.BASE_URL + "/order/" + orderId;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                Log.d(TAG, response.get("orderId").toString());
                                SalesOrder order = new SalesOrder();
                                order.setOrderId(Long.parseLong(response.get("orderId").toString()));
                                Gson gson = new Gson();
                                List<Product> products = gson.fromJson(response.get("products").toString(), new TypeToken<List<Product>>() {
                                }.getType());
                                order.setProducts(products);
                                cartItems.clear();
                                for (Product product : products) {
                                    Log.d(TAG, "product name:" + product.getName());
                                    cartItem = new CartItem();
                                    cartItem.setId(product.getId());
                                    cartItem.setName(product.getName());
                                    cartItem.setDescription(product.getDescription());
                                    cartItem.setPrice(product.getPrice());
                                    Log.d(TAG,"price "+product.getPrice());
                                    cartItem.setQuantity(1);
                                    byte[] decodedString = Base64.decode(product.getImageBlob(), Base64.DEFAULT);
                                    Bitmap productImage = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                                    cartItem.setProductImage(productImage);
                                    cartItems.add(cartItem);
                                }
                                myItemRecyclerViewAdapter.notifyDataSetChanged();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        //setAdapter();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        instance.addToRequestQueue(jsonObjectRequest);

    }

    // Place Order
    public void placeOrder(Long orderId) {
        Log.d(TAG, "placeOrder");
        JSONObject salesOrderJSON = null;
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            salesOrderJSON = new JSONObject(gson.toJson(createOrder(orderId)));
            Log.d(TAG, salesOrderJSON.toString());
            Log.d(TAG, gson.toJson(salesOrderJSON));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        restClientQueue = RestClientQueue.getInstance(this);
        requestQueue = restClientQueue.getRequestQueue();
        String url = BBConstants.BASE_URL + "order/";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, salesOrderJSON, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.d(TAG, "create order:" + response.get("orderId").toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    Log.d(TAG, "create order error:" + new String(error.networkResponse.data, "UTF-8"));
                } catch (Exception e) {

                }
                error.printStackTrace();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json");
                return params;
            }
        };
        requestQueue.add(jsonObjectRequest);
    }

    // Web Socket Connection
    // Stomp Client
    public void connectStomp() {
        mStompClient = Stomp.over(Stomp.ConnectionProvider.JWS, BBConstants.BASE_WS_URL);

        mStompClient.lifecycle()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(lifecycleEvent -> {
                    switch (lifecycleEvent.getType()) {
                        case OPENED:
                            Log.d(TAG, "Stomp connection opened");
                            break;
                        case ERROR:
                            Log.e(TAG, "Stomp connection error", lifecycleEvent.getException());
                            Log.d(TAG, "Stomp connection error");
                            break;
                        case CLOSED:
                            if (!isOrderPlaced)
                                connectStomp();
                            Log.d(TAG, "Stomp connection closed");
                    }
                });

        // Receive greetings
        mStompClient.topic("/topic/lineitemadded")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(topicMessage -> {
                    getOrder(shoppingCartOrderId);
                    Log.d(TAG, "Received " + topicMessage.getPayload());
                });

        mStompClient.connect();
    }

    public void sendEchoViaStomp() {
        mStompClient.send("/app/order", "")
                .compose(applySchedulers())
                .subscribe(() -> {
                    Log.d(TAG, "STOMP echo send successfully");
                }, throwable -> {
                    Log.e(TAG, "Error send STOMP echo", throwable);
                    Log.d(TAG, throwable.getMessage());
                });
    }

    protected CompletableTransformer applySchedulers() {
        return upstream -> upstream
                .unsubscribeOn(Schedulers.newThread())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public void disconnectStomp() {
        mStompClient.disconnect();
    }

}
