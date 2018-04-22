package com.bblinkout.bboutandroid.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bblinkout.bboutandroid.R;
import com.bblinkout.bboutandroid.entity.CartItem;
import com.bblinkout.bboutandroid.fragment.dummy.DummyContent;
import com.bblinkout.bboutandroid.fragment.dummy.DummyContent.DummyItem;
import com.bblinkout.bboutandroid.util.RestClientQueue;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class ItemFragment extends Fragment {

    // TODO: Customize parameter argument names
    private final String TAG="CPLog CartItemFragment";
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private ItemFragment.OnListFragmentInteractionListener mListener;
    private List<CartItem> mCartItems=null;
    private Context mContext;
    private RecyclerView recyclerView;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ItemFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static ItemFragment newInstance(String title, String year) {
        ItemFragment fragment = new ItemFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG,"onCreateView");
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);

        try {
            callProductAPI();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        if (view instanceof RecyclerView) {
            Log.d(TAG,"onCreateView RecyclerView");
            Context context = view.getContext();
            recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
        }
        setAdapter();
        return view;
    }


    @Override
    public void onAttach(Context context) {
        Log.d(TAG,"onAttach");
        mContext=context;
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (ItemFragment.OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(CartItem cartItem);
    }

    public void callProductAPI() throws UnsupportedEncodingException {
        Log.d(TAG,"callProductAPI");
        final RestClientQueue instance = RestClientQueue.getInstance(this.getContext());
        //String url = "http://www.omdbapi.com/?i=tt0068646&apikey=a50b4852";
        final String baseUrl = "http:localhost:6565";
        /*String url = baseUrl+"&s="+ URLEncoder.encode(title,"UTF-8");
        if(year !=null)
            url+="&y="+URLEncoder.encode(year,"UTF-8");;*/
        //instance.addToRequestQueue(cartItemRequest(baseUrl,"url"));
        mCartItems=new ArrayList<CartItem>();
        for (int i = 0; i < 5; i++) {
            mCartItems.add(new CartItem("Product"+i,"Product Desc"+i,100.00*i,"EA"));
        }

    }

    public JsonObjectRequest cartItemRequest(String baseUrl, String url){
        final RestClientQueue instance = RestClientQueue.getInstance(this.getContext());
        final String baseUrl1=baseUrl;
        return new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>(){
                    @Override
                    public void onResponse(JSONObject response) {
                        Gson gson=new Gson();
                        CartItem cartItem=gson.fromJson(response.toString(),CartItem.class);

                    }
                },new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG,"Eroor is: "+ error.toString());

                    }
                });
    }


    public void setAdapter(){
        Log.d(TAG,"setAdapter:"+recyclerView.toString());
        recyclerView.setAdapter(new MyItemRecyclerViewAdapter(mCartItems, mListener));
    }

   /* public class ImageDownloadTask extends AsyncTask<CartItem,String,String> {
        @Override
        protected String doInBackground(CartItem... films) {
            *//*Film film=films[0];
            if (!film.Poster.equals("N/A")) {
                Bitmap bmp = null;
                try {
                    Log.d("CPLog URL:", film.Poster);
                    URL url = new URL(film.Poster);
                    bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                    film.bitmapPoster = bmp;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }*//*
            return "success";
        }

        @Override
        protected void onPostExecute(String s) {
            setAdapter();
        }
    }*/
}
