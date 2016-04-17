package com.adri.takenotes.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ListView
import com.adri.takenotes.R
import com.adri.takenotes.fragment.NewTableClientFragment
import com.adri.takenotes.model.ClientTable
import com.adri.takenotes.model.Restaurant
import com.arasthel.swissknife.SwissKnife
import com.arasthel.swissknife.annotations.InjectView
import com.arasthel.swissknife.annotations.OnClick
import com.arasthel.swissknife.annotations.OnItemClick

class RestaurantActivity extends AppCompatActivity implements NewTableClientFragment.OnTableClientAddedListener {

    final String TAG = RestaurantActivity.class.getCanonicalName()
    private Restaurant restaurant
    ArrayAdapter<ClientTable> adapter

    @InjectView(R.id.list_table_clients)
    ListView tableClients

    @OnClick(R.id.button_add_client)
    void addNewTableClient(){
        startNewTableDialog()
    }

    @OnItemClick(R.id.list_table_clients)
    void tableClientsClick(int position){
        ClientTable table = restaurant.getTables().get(position)
        startDetailTableClient(table)
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant)
        SwissKnife.inject(this)

        restaurant = new Restaurant()

        adapter = new ArrayAdapter<ClientTable>(
                this,
                android.R.layout.simple_list_item_1,
                restaurant.tables)

        tableClients.setAdapter(adapter)
    }
    void startNewTableDialog(){
        NewTableClientFragment dialog = new NewTableClientFragment()
        dialog.setListener(this)
        dialog.show(getFragmentManager(),null)
    }

    void startDetailTableClient(ClientTable table){
        Intent tableClientIntent = new Intent(this, TableClientActivity.class)
        tableClientIntent.putExtra(TableClientActivity.EXTRA_TABLE, (Serializable) table)
        startActivity(tableClientIntent)
    }

    @Override
    void onTableAddedListener(String name) {
        restaurant.addNewTable(name)
        adapter.notifyDataSetChanged()
        Log.d(TAG, "Added new Table")
    }

    @Override
    void onTableCancelListener() {

    }
}
