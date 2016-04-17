package com.adri.takenotes.activities

import android.app.FragmentManager
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.adri.takenotes.R
import com.adri.takenotes.fragment.DishFragment
import com.adri.takenotes.model.ClientTable
import com.adri.takenotes.model.Restaurant
import com.arasthel.swissknife.SwissKnife

class TableClientActivity extends AppCompatActivity {

    public static final String EXTRA_TABLE = "com.adri.takenotes.activities.TableClientActivity.EXTRA_TABLE"

    private ClientTable table

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_table_clients)

        table = getIntent().getSerializableExtra(EXTRA_TABLE)

        //SwissKnife.inject(this)

        FragmentManager fm = getFragmentManager();
        if(fm.findFragmentById(R.id.fragment_list_dish) == null){
            fm.beginTransaction()
                    .add(R.id.fragment_list_dish, DishFragment.newInstance(table.dishes))
                    .commit();
        }

    }

    @Override
    void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState)
    }
}