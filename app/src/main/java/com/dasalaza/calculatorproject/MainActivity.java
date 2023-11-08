package com.dasalaza.calculatorproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import kotlin.jvm.Throws;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText editText01PrecioInmueble = findViewById(R.id.editText01_precio_inmueble);
        EditText editText02Estalvis = findViewById(R.id.editText02_estalvis);
        EditText editText03Plac = findViewById(R.id.editText03_plac);
        EditText editText04Euribor = findViewById(R.id.editText04_euribor);
        EditText editText05Diferencial = findViewById(R.id.editText05_diferencial);
        Button button01Calcular = findViewById(R.id.button01_calcular);

        button01Calcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String precioInmueble01 = editText01PrecioInmueble.getText().toString().trim();
                String estalvis02 = editText02Estalvis.getText().toString().trim();
                String plac03 = editText03Plac.getText().toString().trim();
                String euribor04 = editText04Euribor.getText().toString().trim();
                String diferencial05 = editText05Diferencial.getText().toString().trim();

                TextView textView01MesResult = findViewById(R.id.textView01_mes);
                TextView textView02AnyoResult = findViewById(R.id.textView02_total);

                Integer resultadoHipoteca;

                if (validarDatosUsuario(editText01PrecioInmueble, editText02Estalvis, editText03Plac, editText04Euribor, editText05Diferencial)) {
                    // TODO: Informar del error al usuario. Quitar de la funcion validarDatosUsuario.
                    Toast.makeText(MainActivity.this, "Rellenar los campos correctamente", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    Integer precioInmueble01Int = Integer.parseInt(precioInmueble01);
                    Integer estalvis02Int = Integer.parseInt(estalvis02);
                    Integer plac03Int = Integer.parseInt(plac03);
                    Double euribor04Double = Double.parseDouble(euribor04);
                    Double diferencial05Double = Double.parseDouble(diferencial05);

                    // llamar a funcion para crear los calculos mensual y anual.
                    textView01MesResult.setText("Mes: ");
                    textView02AnyoResult.setText("Total: ");

                } catch (NumberFormatException e) {
                    e.printStackTrace();

                }
            }
        });
    }

    private boolean validarDatosUsuario(TextView editText01PrecioInmueble, TextView editText02Estalvis, TextView editText03Plac, TextView editText04Euribor, TextView editText05Diferencial) {

        String precioInmueble01 = editText01PrecioInmueble.getText().toString().trim();
        String estalvis02 = editText02Estalvis.getText().toString().trim();
        String plac03 = editText03Plac.getText().toString().trim();
        String euribor04 = editText04Euribor.getText().toString().trim();
        String diferencial05 = editText05Diferencial.getText().toString().trim();

        List<String> listaInputUser = new ArrayList<>();
        listaInputUser.add(precioInmueble01);
        listaInputUser.add(estalvis02);
        listaInputUser.add(plac03);
        listaInputUser.add(euribor04);
        listaInputUser.add(diferencial05);

        //TODO: limitar rango de MAX VALUE
        for (String input : listaInputUser) {
            if (input.isEmpty()) {
                return false;
            }
        }
        return (true);
    }

    private boolean comprobarMaxValueInput(Integer integer){
        if (integer >= Integer.MAX_VALUE)

            return false;
        return true;
    }
/*
    private boolean validarDatosUsuario(Integer text01, Integer text02, Integer text03, Double text04, Double text05){
        List<Integer> listaDatos = new ArrayList<>();
        listaDatos.add(text01);
        listaDatos.add(text02);
        listaDatos.add(text03);
        listaDatos.add( text04);

    }
*/
}