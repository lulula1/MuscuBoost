package uqac.dim.muscuboost.ui.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import uqac.dim.muscuboost.R;

public class EditTextDialog extends AbstractDialog<EditTextDialog> {

    private EditText editText;
    private OnSubmitListener onSubmitListener;

    public EditTextDialog(Context context) {
        super(context);

        LayoutInflater inflater = LayoutInflater.from(context);
        View parent = inflater.inflate(R.layout.edit_text_dialog, null);

        editText = parent.findViewById(R.id.edittext);

        builder.setView(parent)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(onSubmitListener != null)
                            onSubmitListener.onSubmit(editText.getText().toString());
                    }
                })
                .setNegativeButton(R.string.cancel, null);
    }

    public EditTextDialog setOnSubmitListener(OnSubmitListener onSubmitListener) {
        this.onSubmitListener = onSubmitListener;
        return this;
    }

    public EditTextDialog setText(String text) {
        editText.setText(text);
        return this;
    }

    public EditTextDialog setText(int resid) {
        editText.setText(resid);
        return this;
    }

    public interface OnSubmitListener {

        void onSubmit(String value);

    }

}
