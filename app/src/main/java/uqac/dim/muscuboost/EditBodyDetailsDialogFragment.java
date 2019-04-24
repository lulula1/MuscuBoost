package uqac.dim.muscuboost;

import android.app.Dialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import uqac.dim.muscuboost.ProfilActivity;
import uqac.dim.muscuboost.R;
import uqac.dim.muscuboost.db.BodyDAO;

public class EditBodyDetailsDialogFragment extends BottomSheetDialogFragment {

    private View contentView;
    private ProfilActivity parent;

    @Override
    public void setupDialog(Dialog dialog, int style) {
        contentView = View.inflate(getContext(), R.layout.editdatabody_fragment, null);
        dialog.setContentView(contentView);

        Button ajouter = contentView.findViewById(R.id.button_add_body_data);
        ajouter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText ajout_masse = contentView.findViewById(R.id.ajout_poid);
                EditText ajout_taille = contentView.findViewById(R.id.ajout_taille);

                String masse = ajout_masse.getText().toString();
                String taille = ajout_taille.getText().toString();

                if(masse.equals("") || taille.equals("")){
                    Toast.makeText(getContext(), "Saisie des données incomplètes", Toast.LENGTH_SHORT).show();
                }
                else{
                    BodyDAO bodyDAO = new BodyDAO(getContext());
                    bodyDAO.open();
                    bodyDAO.insert(Double.parseDouble(masse), Double.parseDouble(taille));
                    bodyDAO.close();
                    parent.update();
                    dismiss();
                }
            }
        });
    }

    public void setParent(ProfilActivity activity){
        this.parent = activity;
    }

}
