package com.adri.takenotes.fragment

import android.app.AlertDialog
import android.app.Dialog
import android.app.DialogFragment
import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.view.View
import com.adri.takenotes.R
import com.arasthel.swissknife.SwissKnife
import com.arasthel.swissknife.annotations.InjectView
import com.arasthel.swissknife.annotations.OnClick
import groovy.transform.CompileStatic

import java.lang.ref.WeakReference

@CompileStatic
class NewTableClientFragment extends DialogFragment {

    WeakReference<OnTableClientAddedListener> listener

    @InjectView(R.id.txt_name_new_table_client)
    TextInputEditText nameTable

    @OnClick(R.id.btn_create_new_table_client)
    void create() {
        println(nameTable.text)
        if (nameTable.text.length() <= 0) {
            nameTable.setError("La tabla requiere un nombre")
        } else {
            if (listener != null && listener.get() != null) {
                listener.get().onTableAddedListener(nameTable.text.toString())
                dismiss()
            }

        }
    }

    @OnClick(R.id.btn_cancel_new_table_client)
    void cancel() {
        if (listener != null && listener.get() != null) {
            listener.get().onTableCancelListener()
            dismiss()
        }
    }

    @Override
    Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity())

        View dialogView = getActivity().getLayoutInflater()
                .inflate(R.layout.fragment_add_table_client, null)

        dialog.setView(dialogView)

        SwissKnife.inject(this, dialogView)

        return dialog.create()

    }

    void setListener(OnTableClientAddedListener listener) {
        this.listener = new WeakReference<>(listener)
    }

    interface OnTableClientAddedListener {
        void onTableAddedListener(String name)
        void onTableCancelListener()
    }
}

