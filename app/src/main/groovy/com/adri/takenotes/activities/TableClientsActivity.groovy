package com.adri.takenotes.activities

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ListAdapter
import android.widget.ListView
import com.adri.takenotes.R
import com.adri.takenotes.model.ClientTable
import com.adri.takenotes.model.Restaurant
import com.arasthel.swissknife.SwissKnife
import com.arasthel.swissknife.annotations.InjectView
import com.arasthel.swissknife.annotations.OnClick

class TableClientsActivity extends AppCompatActivity {

    final String TAG = TableClientsActivity.class.getCanonicalName()
    private Restaurant restaurant

    @InjectView(R.id.list_table_clients)
    ListView tableClients

    @OnClick(R.id.button_add_client)
    public void addNewTableClient(){
        println("Hello");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_table_clients)
        SwissKnife.inject(this)

        restaurant = new Restaurant()

        ListAdapter adapter = new ArrayAdapter<ClientTable>(
                this,
                android.R.layout.simple_list_item_1,
                restaurant.tables)

        tableClients.setAdapter(adapter)
    }
}
