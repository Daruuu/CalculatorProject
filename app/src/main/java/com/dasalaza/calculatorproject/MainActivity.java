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

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
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

        button01Calcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                validarDatosUsuarioInput();
//                Toast.makeText(MainActivity.this, "Rellenar los campos correctamente", Toast.LENGTH_SHORT).show();
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
//            textView01MesResult.setText("Mes: " + resultadoHipotecaMes + "€");
//            textView02AnyoResult.setText("Total: " + resultadoHipotecaTotal + "€");
            //TODO: limitar rango de MAX VALUE
        } catch (NumberFormatException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error calculating!", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateTextViewsOutput(double resultadoHipotecaMes, double resultadoHipotecaTotal) {
        DecimalFormat df = new DecimalFormat("0.00");
//        NumberFormat nf = new ;

        TextView textView01MesResult = findViewById(R.id.textView01_mes);
        TextView textView02TotalResult = findViewById(R.id.textView02_total);
        textView01MesResult.setText("Mes: " + df.format(resultadoHipotecaMes) + " €");
//        df.setRoundingMode(RoundingMode.UP);
//        textView02TotalResult.setText(getString(R.string.totalTextView) + df.format(resultadoHipotecaTotal )+ "€");
        textView02TotalResult.setText("Total: " + df.format(resultadoHipotecaTotal) + "€");
    }

    private boolean comprobarMaxValueInput(Integer integer) {
        if (integer >= Integer.MAX_VALUE) {
            Toast.makeText(this, "Invalid length number!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void textviewInputValues()
    {
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                String precioInmueble = editText01PrecioInmueble.getText().toString().trim();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
    }



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
        return true;
    }

    private double calcularHipotecaMonth() {

        int precioInmueble01Int = Integer.parseInt(editText01PrecioInmueble.getText().toString().trim());
        int estalvis02Int = Integer.parseInt(editText02Estalvis.getText().toString().trim());
        int plac03Int = Integer.parseInt(editText03Plac.getText().toString().trim());
        double euribor04Double = Double.parseDouble(editText04Euribor.getText().toString().trim());
        double diferencial05Double = Double.parseDouble(editText05Diferencial.getText().toString().trim());

        double capital = precioInmueble01Int - estalvis02Int;
        double interes = (euribor04Double + diferencial05Double) / 12;
        int plazoTotal = plac03Int * 12;

        return ((capital * interes) / (100 * (1 - Math.pow(1 + (interes / 100), -plazoTotal))));
    }

    private double calcularHipotecaTotal() {
//        return (Math.round(totalHipoteca * 100d) / 100d);
//        return (calcularHipotecaMonth() * 12 * Double.parseDouble(editText03Plac.getText().toString().trim()));
        return ((Math.round (calcularHipotecaMonth() * 12 * Double.parseDouble(editText03Plac.getText().toString().trim())) * 100d)/ 100d);
    }
}
/*
        if (editText01PrecioInmueble.length() == 0)
        {
            editText01PrecioInmueble.setError("This field is empty!");
            return false;
        }
        if (editText02Estalvis.length() == 0)
        {
            editText02Estalvis.setError("This field is empty!");
            return false;
        }
        if (editText03Plac.length() == 0)
        {
            editText03Plac.setError("This field is empty!");
            return false;
        }
        if (editText04Euribor.length() == 0)
        {
            editText04Euribor.setError("This field is empty!");
            return false;
        }
        if (editText05Diferencial.length() == 0)
        {
            editText05Diferencial.setError("This field is empty!");
            return false;
        }
        return true;
*/