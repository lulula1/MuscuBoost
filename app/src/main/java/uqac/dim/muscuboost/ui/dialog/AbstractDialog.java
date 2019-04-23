package uqac.dim.muscuboost.ui.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import uqac.dim.muscuboost.R;

public class AbstractDialog<T extends AbstractDialog<T>> implements DialogInterface.OnShowListener {

    protected Context context;
    protected AlertDialog.Builder builder;

    public AbstractDialog(Context context) {
        this.context = context;
        builder = new AlertDialog.Builder(context);
    }

    @Override
    public void onShow(DialogInterface arg0) {
        AlertDialog dialog = (AlertDialog) arg0;
        int colorPrimary = context.getResources().getColor(R.color.colorPrimary);
        dialog.getButton(DialogInterface.BUTTON_POSITIVE)
                .setTextColor(colorPrimary);
        dialog.getButton(DialogInterface.BUTTON_NEGATIVE)
                .setTextColor(colorPrimary);
        dialog.getButton(DialogInterface.BUTTON_NEUTRAL)
                .setTextColor(colorPrimary);
    }

    public T setTitle(String title) {
        builder.setTitle(title);
        return (T) this;
    }

    public T setTitle(int resid) {
        return setTitle(context.getString(resid));
    }

    public void show() {
        AlertDialog dialog = builder.create();
        dialog.setOnShowListener(this);
        dialog.show();
    }

}
