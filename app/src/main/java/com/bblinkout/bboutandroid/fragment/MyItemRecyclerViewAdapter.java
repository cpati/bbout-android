package com.bblinkout.bboutandroid.fragment;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bblinkout.bboutandroid.R;
import com.bblinkout.bboutandroid.activity.CartActivity;
import com.bblinkout.bboutandroid.entity.CartItem;
import com.bblinkout.bboutandroid.fragment.ItemFragment.OnListFragmentInteractionListener;
import com.bblinkout.bboutandroid.fragment.dummy.DummyContent.DummyItem;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder> {

    private final String TAG="CP MymovieRecyclerViewA";
    private final List<CartItem> mCartItems;
    private final ItemFragment.OnListFragmentInteractionListener mListener;

    public MyItemRecyclerViewAdapter(List<CartItem> products, ItemFragment.OnListFragmentInteractionListener listener) {
        mCartItems=products;
        mListener = listener;
    }


    @Override
    public MyItemRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item, parent, false);
        return new MyItemRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyItemRecyclerViewAdapter.ViewHolder holder, final int position) {
        Log.d(TAG,"BVH");
        holder.mCartItem = mCartItems.get(position);
        holder.mProductNameView.setText(mCartItems.get(position).getName());
        holder.mPriceView.setText(mCartItems.get(position).getPrice()+" "+mCartItems.get(position).getUOM());
        String[] items = new String[]{"1", "2", "3","4","5"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(holder.mView.getContext(), android.R.layout.simple_spinner_dropdown_item, items);
        holder.mQuantityView.setAdapter(adapter);
        holder.mDeleteView.setText("Delete");
        holder.mImageView.setImageBitmap(mCartItems.get(position).getProductImage());
        //holder.mImageView.setImageBitmap(mProducts.get(position).bitmapPoster);



        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    Log.d(TAG,"setOnClickListener:"+position);
                    mListener.deleteItem(position);

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mCartItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mProductNameView;
        public final TextView mPriceView;
        public final Spinner mQuantityView;
        //public final Button mDeleteView;
        public final Button mDeleteView;
        public final ImageView mImageView;
        public CartItem mCartItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mProductNameView = (TextView) view.findViewById(R.id.product_name);
            mPriceView = (TextView) view.findViewById(R.id.price);
            mQuantityView =view.findViewById(R.id.quantity);
            mDeleteView =view.findViewById(R.id.delete);
            mImageView =view.findViewById(R.id.product_image);

        }

    }

}
