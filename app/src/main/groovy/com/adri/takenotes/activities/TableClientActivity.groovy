package com.adri.takenotes.activities

import android.app.Activity
import android.app.FragmentManager
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.adri.takenotes.R
import com.adri.takenotes.fragment.DishFragment
import com.adri.takenotes.fragment.ParamsDishFragment
import com.adri.takenotes.fragment.PayFragment
import com.adri.takenotes.model.ClientTable
import com.adri.takenotes.model.Dish
import com.adri.takenotes.model.Restaurant
import com.arasthel.swissknife.SwissKnife
import com.arasthel.swissknife.annotations.OnClick
import groovy.transform.CompileStatic

@CompileStatic
class TableClientActivity extends AppCompatActivity implements ParamsDishFragment.OnParamsDishListener, PayFragment.OnPayListener {

    public static
    final String EXTRA_TABLE = "com.adri.takenotes.activities.TableClientActivity.EXTRA_TABLE"

    private static final int ACTIVITY_ADD_DISH = 0
    private ClientTable table

    DishFragment dishFragment

    @OnClick(R.id.btn_add_dish)
    void addNewTableClient() {
        startNewDish()
    }

    @OnClick(R.id.btn_pay)
    void payTableClient(){
        startPayClient()
    }

    void startPayClient() {
        def payTableClientFragment = PayFragment.newInstance(table)
        payTableClientFragment.setListener(this as PayFragment.OnPayListener)
        payTableClientFragment.show(getFragmentManager(),null)
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_table_clients)

        int tableId = getIntent().getIntExtra(EXTRA_TABLE,0)
        table = Restaurant.getInstance().getTable(tableId)
        Log.d("TAG",table.name)
        SwissKnife.inject(this)

        dishFragment = DishFragment.newInstance(table.getDishes())

        FragmentManager fm = getFragmentManager()
        if (fm.findFragmentById(R.id.fragment_list_dish) == null) {
            fm.beginTransaction()
                    .add(R.id.fragment_list_dish, dishFragment)
                    .commit()
        }

    }

    @Override
    void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState)
    }

    void startNewDish() {
        Intent dishAddIntent = new Intent(this, AddNewDishActivity.class)
        startActivityForResult(dishAddIntent, ACTIVITY_ADD_DISH)
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ACTIVITY_ADD_DISH) {
            if (resultCode == RESULT_OK) {
                Dish dish = data.getSerializableExtra(AddNewDishActivity.EXTRA_DISH) as Dish
                table.addDish(dish, false)
                dishFragment.prepareDish()
            }
        }
    }

    @Override
    void onParamsDishOKListener(Dish dish) {
        if (dish.quantity == 0) {
            if (table.removeDish(dish.id)) {
                dishFragment.prepareDish()
            }
        } else {
            table.addDish(dish, true)
            dishFragment.prepareDish()
        }
    }


    @Override
    void onParamsDishCancelListener() {

    }

    @Override
    void onPayOkPayListener() {
        Intent resultIntent = new Intent()
        resultIntent.putExtra(EXTRA_TABLE, table.numberTable)
        setResult(Activity.RESULT_OK, resultIntent);
        finish()
    }

    @Override
    void onPayCancelListener() {

    }
}