package com.adri.takenotes.fragment

import android.app.AlertDialog
import android.app.Dialog
import android.app.DialogFragment
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.NumberPicker
import com.adri.takenotes.R
import com.adri.takenotes.model.Dish
import com.arasthel.swissknife.SwissKnife
import com.arasthel.swissknife.annotations.InjectView

import java.lang.ref.WeakReference

class ParamsDishFragment extends DialogFragment {

    private static String ARG_DISH = 'com.adri.takenotes.fragment.ParamsDishFragment.ARG_DISH'

    WeakReference<OnParamsDishListener> listener

    @InjectView(R.id.number_picker_quantity)
    NumberPicker pickerQuantity

    @InjectView(R.id.text_annotations)
    EditText textAnnotation

    Dish dish

    static ParamsDishFragment newInstance(Dish dish) {
        ParamsDishFragment f = new ParamsDishFragment()

        // Supply num input as an argument.
        Bundle args = new Bundle()
        args.putSerializable(ARG_DISH, dish)
        f.setArguments(args)

        return f
    }

    void setListener(OnParamsDishListener listener){
        this.listener = new WeakReference<>(listener)
    }

    @Override
    void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState)
        dish = getArguments().getSerializable(ARG_DISH)
    }

    @Override
    Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity())


        View dialogView = getActivity().getLayoutInflater()
                .inflate(R.layout.fragment_params_dish, null)

        dialog.setView(dialogView)
        dialog.setTitle(dish.name)
        dialog.setPositiveButton(android.R.string.ok, null)
        dialog.setNegativeButton(android.R.string.cancel, null)

        SwissKnife.inject(this, dialogView)

        pickerQuantity.setMaxValue(99)

        if(dish.quantity == 0){
            pickerQuantity.setMinValue(1)
            pickerQuantity.value = 0
        }else{
            pickerQuantity.setMinValue(0)
            pickerQuantity.value = dish.quantity
        }
        textAnnotation.text = dish.additions
        return dialog.create()
    }

    @Override
    void onStart() {
        super.onStart()
        AlertDialog dialog = getDialog()
        if (dialog != null) {
            Button positiveButton = dialog.getButton(Dialog.BUTTON_POSITIVE)
            positiveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dish.quantity = pickerQuantity.value
                    dish.additions = textAnnotation.text

                    if (listener != null && listener.get() != null) {
                        listener.get().onParamsDishOKListener(dish)
                        dismiss()
                    }
                }
            })

            Button negativeButton = dialog.getButton(Dialog.BUTTON_NEGATIVE)
            negativeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null && listener.get() != null) {
                        listener.get().onParamsDishCancelListener()
                        dismiss()
                    }
                }
            })
        }
    }

    interface OnParamsDishListener {
        void onParamsDishOKListener(Dish dish)
        void onParamsDishCancelListener()
    }
}