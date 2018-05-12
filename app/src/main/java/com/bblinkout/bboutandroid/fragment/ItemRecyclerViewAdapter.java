package com.bblinkout.bboutandroid.fragment;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bblinkout.bboutandroid.R;
import com.bblinkout.bboutandroid.entity.CartItem;
import com.bblinkout.bboutandroid.fragment.ItemFragment.OnListFragmentInteractionListener;
import com.bblinkout.bboutandroid.fragment.dummy.DummyContent.DummyItem;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class ItemRecyclerViewAdapter extends RecyclerView.Adapter<ItemRecyclerViewAdapter.ViewHolder> {

    private final String TAG="CP MymovieRecyclerViewA";
    private final List<CartItem> mCartItems;
    private final ItemFragment.OnListFragmentInteractionListener mListener;

    public ItemRecyclerViewAdapter(List<CartItem> products, ItemFragment.OnListFragmentInteractionListener listener) {
        mCartItems=products;
        mListener = listener;
    }


    @Override
    public ItemRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item, parent, false);
        return new ItemRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ItemRecyclerViewAdapter.ViewHolder holder, final int position) {
        Log.d(TAG,"BVH");
        holder.mCartItem = mCartItems.get(position);
        holder.mProductNameView.setText(mCartItems.get(position).getName());
        //holder.mProductNameView.setText(String.valueOf(position));
        //holder.mProductIdView.setText(String.valueOf(holder.mCartItem.getId()));
        holder.mPriceView.setText(mCartItems.get(position).getPrice().toString());

        String[] items = new String[]{"1", "2", "3","4","5"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(holder.mView.getContext(), android.R.layout.simple_spinner_dropdown_item, items);
        holder.mQuantityView.setAdapter(adapter);

        holder.mDeleteView.setText("Delete");
        holder.mImageView.setImageBitmap(holder.mCartItem.getProductImage());

        holder.mQuantityView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            int selectedQty=Integer.valueOf(holder.mQuantityView.getSelectedItem().toString());
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                TextView id=((View)view.getParent()).findViewById(R.id.product_id);
                TextView name=((View)view.getParent()).findViewById(R.id.product_name);
                Log.d(TAG,"name 11"+adapterView.getItemAtPosition(i).toString());
                if (name != null){
                    Log.d(TAG,"name "+name.getText().toString());
                }
                if (id !=null){
                    Log.d(TAG,"Qyantity"+id.getText().toString());
                    int updatedQty=Integer.valueOf(adapterView.getItemAtPosition(i).toString());
                    mListener.updateItem(Long.parseLong(id.getText().toString()),updatedQty);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        holder.mDeleteView.setOnClickListener(new View.OnClickListener() {
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
        public final TextView mProductIdView;
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
            mProductIdView = (TextView) view.findViewById(R.id.product_id);
            mPriceView = (TextView) view.findViewById(R.id.price);
            mQuantityView =view.findViewById(R.id.quantity);
            mDeleteView =view.findViewById(R.id.delete);
            mImageView =view.findViewById(R.id.product_image);

        }

    }

}
