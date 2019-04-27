package uqac.dim.muscuboost.ui.train;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import uqac.dim.muscuboost.R;
import uqac.dim.muscuboost.core.training.Exercise;
import uqac.dim.muscuboost.core.training.Statistics;

public class StatRecapView extends LinearLayout {

    private EditText weightEdit;
    private TextView repCountView;

    private Exercise exercise;
    private Statistics statistics;

    public StatRecapView(Context context, Exercise exercise, Statistics statistics) {
        super(context);
        this.exercise = exercise;
        this.statistics = statistics;

        render();
    }

    private void render() {
        inflate(getContext(), R.layout.train_stats_recap, this);

        TextView exerciseNameView = findViewById(R.id.exercise_name);
        Button minusRepButton = findViewById(R.id.minus_rep);
        Button plusRepButton = findViewById(R.id.plus_rep);

        weightEdit = findViewById(R.id.weight);
        repCountView = findViewById(R.id.rep_count);

        exerciseNameView.setText(exercise.getName());

        updateWeight(statistics.getWeight());
        updateRepCount(statistics.getRepCount());

        weightEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    statistics.setWeight(Double.parseDouble(s.toString()));
                    weightEdit.setHint(String.valueOf(statistics.getWeight()));
                }catch(NumberFormatException e) {
                    // The value is not a double, don't do anything
                }
            }
        });
        minusRepButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                statistics.setRepCount(statistics.getRepCount() - 1);
                updateRepCount(statistics.getRepCount());
            }
        });
        plusRepButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                statistics.setRepCount(statistics.getRepCount() + 1);
                updateRepCount(statistics.getRepCount());
            }
        });
    }

    private void updateWeight(double weight) {
        String weightStr = String.valueOf(weight);
        weightEdit.setText(weight >= 0 ? weightStr : "");
        weightEdit.setHint(weight >= 0 ? weightStr : "n/a");
    }

    private void updateRepCount(int repCount) {
        String repCountStr = String.valueOf(repCount);
        repCountView.setText(repCountStr);
    }

}
