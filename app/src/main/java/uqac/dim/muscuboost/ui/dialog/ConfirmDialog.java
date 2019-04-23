package uqac.dim.muscuboost.ui.dialog;

import android.content.Context;
import android.content.DialogInterface;

import uqac.dim.muscuboost.R;

public class ConfirmDialog extends AbstractDialog<ConfirmDialog> {

    public ConfirmDialog(Context context) {
        super(context);
        setNegativeListener(null);
        setPositiveListener(null);
    }

    public ConfirmDialog setNegativeListener(DialogInterface.OnClickListener onClickListener) {
        builder.setNegativeButton(R.string.cancel, onClickListener);
        return this;
    }

    public ConfirmDialog setPositiveListener(DialogInterface.OnClickListener onClickListener) {
        builder.setPositiveButton(R.string.yes, onClickListener);
        return this;
    }

}
