package com.dasalaza.calculatorproject;

import static java.sql.Types.NULL;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
    private Button button01Calcular;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText01PrecioInmueble = findViewById(R.id.editText01_precio_inmueble);
        editText02Estalvis = findViewById(R.id.editText02_estalvis);
        editText03Plac = findViewById(R.id.editText03_plac);
        editText04Euribor = findViewById(R.id.editText04_euribor);
        editText05Diferencial = findViewById(R.id.editText05_diferencial);
        button01Calcular = findViewById(R.id.button01_calcular);

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
    }

    private void validarDatosUsuarioInput() {

        if (!checkAllFieldsErrors()) {
            return;
        }
        try {
            double resultadoHipotecaMes = calcularHipotecaMonth();
            double resultadoHipotecaTotal = calcularHipotecaTotal();
            updateTextViewsOutput(resultadoHipotecaMes, resultadoHipotecaTotal);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error calculating!", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateTextViewsOutput(double resultadoHipotecaMes, double resultadoHipotecaTotal) {
        DecimalFormat df = new DecimalFormat("0.00");
        TextView textView01MesResult = findViewById(R.id.textView01_mes);
        TextView textView02TotalResult = findViewById(R.id.textView02_total);
        textView01MesResult.setText("Mes: " + df.format(resultadoHipotecaMes) + " €");
        textView02TotalResult.setText("Total: " + df.format(resultadoHipotecaTotal) + "€");
    }

    /*
        private boolean comprobarMaxValueInput(Integer integer) {
            if (integer >= Integer.MAX_VALUE) {
                Toast.makeText(this, "Invalid length number!", Toast.LENGTH_SHORT).show();
                return false;
            }
            return true;
        }
    */
    private TextWatcher textWatcher = new TextWatcher() {
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

    private boolean checkAllFieldsErrors() {

        List<EditText> listValues = new ArrayList<>();
        listValues.add(editText01PrecioInmueble);
        listValues.add(editText02Estalvis);
        listValues.add(editText03Plac);
        listValues.add(editText04Euribor);
        listValues.add(editText05Diferencial);

        for (EditText fieldText : listValues) {
            if (fieldText.length() == NULL) {
                fieldText.setError("This field is empty!");
                return false;
            }
        }
        int editText01 = Integer.parseInt(editText01PrecioInmueble.getText().toString().trim());
        int editText02 = Integer.parseInt(editText02Estalvis.getText().toString().trim());
        if (editText02 > editText01)
        {
            Toast.makeText(this, "Invalid length: Estalvis!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private double calcularHipotecaMonth() {

        int precioInmueble01Int = Integer.parseInt(editText01PrecioInmueble.getText().toString().trim());
        int estalvis02Int = Integer.parseInt(editText02Estalvis.getText().toString().trim());
        int plac03Int = Integer.parseInt(editText03Plac.getText().toString().trim());
        double euribor04Double = Double.parseDouble(editText04Euribor.getText().toString().trim());
        double diferencial05Double = Double.parseDouble(editText05Diferencial.getText().toString().trim());

        double capital = precioInmueble01Int - estalvis02Int;
        double interes = (euribor04Double + diferencial05Double) / 12.0;
        int plazoTotal = plac03Int * 12;

        return ((capital * interes) / (100.0 * (1 - Math.pow(1 + (interes / 100.0), -plazoTotal))));
    }

    private double calcularHipotecaTotal() {
        double resultMes = calcularHipotecaMonth();
        return ((resultMes * 12 * Double.parseDouble(editText03Plac.getText().toString().trim())));
    }
}