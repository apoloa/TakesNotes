package com.adri.takenotes.activities

import android.app.FragmentManager
import android.content.Intent
import android.os.Bundle
import android.support.annotation.Nullable
import android.support.v7.app.AppCompatActivity
import android.widget.NumberPicker
import com.adri.takenotes.R
import com.adri.takenotes.fragment.DishFragment
import com.adri.takenotes.fragment.ParamsDishFragment
import com.adri.takenotes.fragment.PayFragment
import com.adri.takenotes.model.Dish
import com.adri.takenotes.model.Restaurant
import com.arasthel.swissknife.SwissKnife
import com.arasthel.swissknife.annotations.InjectView
import groovy.transform.CompileStatic

@CompileStatic
class AddNewDishActivity extends AppCompatActivity implements ParamsDishFragment.OnParamsDishListener {

    final static String EXTRA_DISH = "com.adri.takenotes.activities.AddNewDishActivity.EXTRA_DISH"

    @InjectView(R.id.number_picker_quantity)
    NumberPicker numberPickerQuantity

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_add_new_dish)

        FragmentManager fm = getFragmentManager()
        if (fm.findFragmentById(R.id.fragment_list_dish) == null) {
            fm.beginTransaction()
                    .add(R.id.fragment_list_dish_add, DishFragment.newInstance(Restaurant.getInstance().dishes))
                    .commit()
        }


        SwissKnife.inject(this)


    }

    @Override
    void onParamsDishOKListener(Dish dish) {
        setResult(RESULT_OK, new Intent().putExtra(EXTRA_DISH, dish))
        finish()
    }

    @Override
    void onParamsDishCancelListener() {
        setResult(RESULT_CANCELED)
        finish()
    }

}