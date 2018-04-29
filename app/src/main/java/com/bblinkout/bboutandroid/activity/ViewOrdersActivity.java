package com.bblinkout.bboutandroid.activity;

import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.view.View;

import com.bblinkout.bboutandroid.R;
import com.bblinkout.bboutandroid.entity.SalesOrder;
import com.bblinkout.bboutandroid.fragment.OrderFragment;
import com.bblinkout.bboutandroid.fragment.dummy.DummyContent;

public class ViewOrdersActivity extends BaseActivity implements OrderFragment.OnListFragmentInteractionListener{

    FragmentManager manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View cartView=this.getLayoutInflater().inflate(R.layout.activity_view_orders,null, true);
        drawer.addView(cartView, 0);
        setTitle("View Orders");
        OrderFragment orderFragment=new OrderFragment();

        manager = getSupportFragmentManager();
        manager.beginTransaction()
                .replace(R.id.view_orders_container, orderFragment, "vieworders")
                .commit();
    }


    @Override
    public void onListFragmentInteraction(SalesOrder item) {

    }
}
