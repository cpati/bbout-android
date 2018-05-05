package com.bblinkout.bboutandroid.fragment;

import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.bblinkout.bboutandroid.R;
import com.bblinkout.bboutandroid.entity.SalesOrder;
import com.bblinkout.bboutandroid.fragment.OrderFragment.OnListFragmentInteractionListener;
import com.bblinkout.bboutandroid.fragment.dummy.DummyContent.DummyItem;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class OrderRecyclerViewAdapter extends RecyclerView.Adapter<OrderRecyclerViewAdapter.ViewHolder> {

    private final List<SalesOrder> mValues;
    private final OnListFragmentInteractionListener mListener;
    RequestQueue requestQueue;

    public OrderRecyclerViewAdapter(List<SalesOrder> orders, OnListFragmentInteractionListener listener) {
        mValues = orders;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.cardview_order, parent, false);

            return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mOrderIdLblView.setText("Order #");
        holder.mOrderIdView.setText(mValues.get(position).getOrderId().toString());
        holder.mOrderDate.setText(mValues.get(position).getOrderDate().toString());
        holder.mTotalProductsView.setText(mValues.get(position).getTotalProducts());
        holder.mOrderTotalView.setText("$ "+mValues.get(position).getOrderTotal());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public  View mView;
        public  TextView mOrderIdLblView;
        public  TextView mOrderIdView;
        public  TextView mOrderDate;
        public  TextView mTotalProductsView;
        public  TextView mOrderTotalView;
        public SalesOrder mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mOrderIdLblView = (TextView) view.findViewById(R.id.order_number_label);
            mOrderIdView = (TextView) view.findViewById(R.id.order_number);
            mOrderDate = (TextView) view.findViewById(R.id.order_date);
            mTotalProductsView = (TextView) view.findViewById(R.id.total_products);
            mOrderTotalView = (TextView) view.findViewById(R.id.order_total);



        }

        @Override
        public String toString() {
            return super.toString() + " '" + mOrderTotalView.getText() + "'";
        }
    }

}
