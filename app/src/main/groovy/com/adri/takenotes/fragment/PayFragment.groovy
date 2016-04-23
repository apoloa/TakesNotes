package com.adri.takenotes.fragment

import android.app.AlertDialog
import android.app.Dialog
import android.app.DialogFragment
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import com.adri.takenotes.R
import com.adri.takenotes.model.ClientTable
import com.adri.takenotes.model.Dish
import groovy.transform.CompileStatic
import org.w3c.dom.Text

import java.lang.ref.WeakReference;
@CompileStatic
class PayFragment extends DialogFragment {

    private static String ARG_CLIENT_TABLE = 'com.adri.takenotes.fragment.PayFragment.ARG_CLIENT_TABLE'

    ClientTable table

    WeakReference<OnPayListener> listener

    static PayFragment newInstance(ClientTable table) {
        PayFragment f = new PayFragment()

        // Supply num input as an argument.
        Bundle args = new Bundle()
        args.putSerializable(ARG_CLIENT_TABLE, table)
        f.setArguments(args)

        return f
    }

    @Override
    void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState)
        table = getArguments().getSerializable(ARG_CLIENT_TABLE) as ClientTable
    }

    @Override
    Dialog onCreateDialog(Bundle savedInstanceState) {
        def dialog = new AlertDialog.Builder(getActivity())

        def dialogView = getActivity().getLayoutInflater()
                .inflate(R.layout.fragment_pay, null) as TableLayout

        dialog.setView(dialogView)
        dialog.setTitle("Pay: " + table.name)

        dialog.setPositiveButton(R.string.pay, null)
        dialog.setNegativeButton(android.R.string.cancel, null)

        int i = 2
        float total = 0f
        for(Dish d: table.dishes){
            TableRow row = getActivity().getLayoutInflater().inflate(R.layout.row_dish_pay, null) as TableRow
            def titleDish = row.findViewById(R.id.title_dish_pay) as TextView
            def priceDish = row.findViewById(R.id.price_dish_pay) as TextView
            def quantityDish = row.findViewById(R.id.quantity_dish_pay) as TextView
            def priceTotalDish = row.findViewById(R.id.total_price_dish) as TextView
            titleDish.text = d.name
            priceDish.text = String.format("%.2f",  d.price);
            quantityDish.text = d.quantity as String
            def priceRow = d.price * d.quantity
            priceTotalDish.text = String.format("%.2f",  priceRow);
            dialogView.addView(row,i)
            i+=1;
            total+=priceRow
        }

        def totalPriceTable = dialogView.findViewById(R.id.pay_total_price) as TextView;
        totalPriceTable.text = String.format("%.2f",  total);
        return dialog.create()
    }

    @Override
    void onStart() {
        super.onStart()
        AlertDialog dialog = getDialog() as AlertDialog
        if (dialog != null) {
            Button positiveButton = dialog.getButton(Dialog.BUTTON_POSITIVE)
            positiveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null && listener.get() != null) {
                        listener.get().onPayOkPayListener()
                        dismiss()
                    }
                }
            })

            Button negativeButton = dialog.getButton(Dialog.BUTTON_NEGATIVE)
            negativeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null && listener.get() != null) {
                        listener.get().onPayCancelListener()
                        dismiss()
                    }
                }
            })
        }
    }

    void setListener(OnPayListener listener){
        this.listener = new WeakReference<>(listener)
    }

    interface OnPayListener {
        void onPayOkPayListener()
        void onPayCancelListener()
    }
}