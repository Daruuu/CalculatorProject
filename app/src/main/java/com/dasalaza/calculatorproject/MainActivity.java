package com.dasalaza.calculatorproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Notification;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText editText01PrecioInmueble;
    EditText editText02Estalvis;
    EditText editText03Plac;
    EditText editText04Euribor;
    EditText editText05Diferencial;
    Button button01Calcular;
    private Float resultadoHipotecaMes;
    private Float resultadoHipotecaTotal;

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

/*
                String precioInmueble01 = editText01PrecioInmueble.getText().toString().trim();
                String estalvis02 = editText02Estalvis.getText().toString().trim();
                String plac03 = editText03Plac.getText().toString().trim();
                String euribor04 = editText04Euribor.getText().toString().trim();
                String diferencial05 = editText05Diferencial.getText().toString().trim();

                Integer precioInmueble01Int = Integer.parseInt(precioInmueble01);
                Integer estalvis02Int = Integer.parseInt(estalvis02);
                Integer plac03Int = Integer.parseInt(plac03);
                Float euribor04Double = Float.parseFloat(euribor04);
                Float diferencial05Double = Float.parseFloat(diferencial05);

                TextView textView01MesResult = findViewById(R.id.textView01_mes);
                TextView textView02AnyoResult = findViewById(R.id.textView02_total);
*/
                validarDatosUsuarioInput();

                // TODO: Informar del error al usuario. Quitar de la funcion validarDatosUsuario.
                Toast.makeText(MainActivity.this, "Rellenar los campos correctamente", Toast.LENGTH_SHORT).show();
                return;
            }

        });
    }

    private void validarDatosUsuarioInput() {

        if (!checkAllFieldsErrors()) {
            return;
        }
        try {
            resultadoHipotecaMes = calcularHipotecaMonth();
            resultadoHipotecaTotal = calcularHipotecaTotal();

/*
            textView01MesResult.setText("Mes: " + resultadoHipotecaMes + "€");
            textView02AnyoResult.setText("Total: " + resultadoHipotecaTotal + "€");
*/
            //TODO: limitar rango de MAX VALUE
        } catch (NumberFormatException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error calculating!", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean comprobarMaxValueInput(Integer integer) {
        if (integer >= Integer.MAX_VALUE) {
            Toast.makeText(this, "Invalid length number!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean checkAllFieldsErrors() {

        List<EditText> listValues = new ArrayList<>();
        listValues.add(editText01PrecioInmueble);
        listValues.add(editText02Estalvis);
        listValues.add(editText03Plac);
        listValues.add(editText04Euribor);
        listValues.add(editText05Diferencial);

        for (EditText fieldText : listValues) {
            if (fieldText.length() == 0) {
                fieldText.setError("This field is empty!");
                return false;
            }
        }
        return true;
    }

    private float calcularHipotecaMonth() {
        int precioInmueble01Int = Integer.parseInt(editText01PrecioInmueble.getText().toString().trim());
        int estalvis02Int = Integer.parseInt(editText02Estalvis.getText().toString().trim());
        int plac03Int = Integer.parseInt(editText03Plac.getText().toString().trim());
        float euribor04Double = Float.parseFloat(editText04Euribor.getText().toString().trim());
        float diferencial05Double = Float.parseFloat(editText05Diferencial.getText().toString().trim());

        double capital = precioInmueble01Int - estalvis02Int;
        double interes = (euribor04Double + diferencial05Double) / 12;
        int plazoTotal = plac03Int * 12;

        return (float) ((capital * interes) / (100 * (1 - Math.pow(1 + (interes / 100), -plazoTotal))));
    }

    private float calcularHipotecaTotal() {
//        return (Math.round(totalHipoteca * 100d) / 100d);
        return (calcularHipotecaMonth() * 12 * Float.parseFloat(editText03Plac.getText().toString().trim()));
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
}
        }