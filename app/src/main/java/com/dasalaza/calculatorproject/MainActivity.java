package com.dasalaza.calculatorproject;

import static java.sql.Types.NULL;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText editText01PrecioInmueble;
    private EditText editText02Estalvis;
    private EditText editText03Plac;
    private EditText editText04Euribor;
    private EditText editText05Diferencial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText01PrecioInmueble = findViewById(R.id.editText01_precio_inmueble);
        editText02Estalvis = findViewById(R.id.editText02_estalvis);
        editText03Plac = findViewById(R.id.editText03_plac);
        editText04Euribor = findViewById(R.id.editText04_euribor);
        editText05Diferencial = findViewById(R.id.editText05_diferencial);

        Button button01Calcular = findViewById(R.id.button01_calcular);
        TextView editTextEuribor04Switch = findViewById(R.id.str04_euribor);
        Switch aSwitch = findViewById(R.id.switch01);

        //LISTENERS FOR EACH EDITTEXT
        editText01PrecioInmueble.addTextChangedListener(textWatcher);
        editText02Estalvis.addTextChangedListener(textWatcher);
        editText03Plac.addTextChangedListener(textWatcher);
        editText04Euribor.addTextChangedListener(textWatcher);
        editText05Diferencial.addTextChangedListener(textWatcher);
        button01Calcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validarDatosUsuarioInput();
            }
        });

        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    editText04Euribor.setVisibility(View.GONE);
                    editTextEuribor04Switch.setVisibility(View.GONE);
                    editText04Euribor.setText("0");
                } else {
                    editText04Euribor.setVisibility(View.VISIBLE);
                    editTextEuribor04Switch.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void validarDatosUsuarioInput() {
        if (!checkAllErrorsFields()) {
            return;
        }
        try {
            double resultadoHipotecaMes = calculateHipotecaMonth();
            double resultadoHipotecaTotal = calculateHipotecaTotal();
            updateTextViewsOutput(resultadoHipotecaMes, resultadoHipotecaTotal);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    private void updateTextViewsOutput(double resultadoHipotecaMes, double resultadoHipotecaTotal) {
        DecimalFormat df = new DecimalFormat("0.00");
        TextView textView01MesResult = findViewById(R.id.textView01_mes);
        TextView textView02TotalResult = findViewById(R.id.textView02_total);
        textView01MesResult.setText("Mes: " + df.format(resultadoHipotecaMes) + " €");
        textView02TotalResult.setText("Total: " + df.format(resultadoHipotecaTotal) + " €");
    }

    private final TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            validarDatosUsuarioInput();
        }
    };

    private boolean checkEmptyFieldsInput() {
        List<EditText> listEditTexts = new ArrayList<>();
        listEditTexts.add(editText01PrecioInmueble);
        listEditTexts.add(editText02Estalvis);
        listEditTexts.add(editText03Plac);
        listEditTexts.add(editText04Euribor);
        listEditTexts.add(editText05Diferencial);

        for (EditText fieldText : listEditTexts) {
            if (fieldText.length() == NULL) {
                fieldText.setError("This field is empty!");
                return false;
            }
        }
        return true;
    }

    private boolean checkEstalvisLength() {
        long editText01= Long.parseLong(editText01PrecioInmueble.getText().toString().trim());
        int editText02 = Integer.parseInt(editText02Estalvis.getText().toString().trim());
        if (editText02 > editText01) {
            Toast.makeText(this, "Invalid length: Estalvis!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean checkMaxEuribor() {
        double euribor04Double = Double.parseDouble(editText04Euribor.getText().toString().trim());
        double limitEuribor = 6.0;
        if (euribor04Double > limitEuribor)
        {
            Toast.makeText(this, "max value of euribor is: " + limitEuribor + "!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    private boolean checkAllErrorsFields() {
        try {
            if (!checkEmptyFieldsInput())
                return false;
            if (!checkEstalvisLength())
                return false;
            if (!checkMaxEuribor())
                return false;
        } catch (NumberFormatException e)
        {
            e.printStackTrace();
            Toast.makeText(this, "Invalid max length number!" , Toast.LENGTH_SHORT).show();
        }
        return true;
    }


    private double calculateHipotecaMonth() {

        long precioInmueble01Int = Long.parseLong(editText01PrecioInmueble.getText().toString().trim());
        int estalvis02Int = Integer.parseInt(editText02Estalvis.getText().toString().trim());
        int plac03Int = Integer.parseInt(editText03Plac.getText().toString().trim());
        double euribor04Double = Double.parseDouble(editText04Euribor.getText().toString().trim());
        double diferencial05Double = Double.parseDouble(editText05Diferencial.getText().toString().trim());

        double capital = precioInmueble01Int - estalvis02Int;
        double interes = (euribor04Double + diferencial05Double) / 12.0;
        int plazoTotal = plac03Int * 12;

        return ((capital * interes) / (100.0 * (1 - Math.pow(1 + (interes / 100.0), -plazoTotal))));
    }

    private double calculateHipotecaTotal() {
        double resultMes = calculateHipotecaMonth();
        return ((resultMes * 12 * Double.parseDouble(editText03Plac.getText().toString().trim())));
    }
}