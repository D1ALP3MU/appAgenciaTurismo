package com.example.appagenciaturismo;

import static java.lang.Integer.parseInt;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    String[] tdestinos = {"Cartagena", "Santa Marta", "San Andrés", "Medellín"};
    String presSelect;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Instanciar y referenciar los IDs del archivo xml
        EditText nombre = findViewById(R.id.etnombre);
        Spinner destino = findViewById(R.id.spdestino);
        EditText fechaSalida = findViewById(R.id.etfechaSalida);
        EditText numeroPersonas = findViewById(R.id.etnumeroPersonas);
        RadioButton rb2 = findViewById(R.id.rb2);
        RadioButton rb4 = findViewById(R.id.rb4);
        RadioButton rb6 = findViewById(R.id.rb6);
        Switch tourCiudad = findViewById(R.id.swtourCiudad);
        Switch discoteca = findViewById(R.id.swdiscoteca);
        TextView totalPagar = findViewById(R.id.etvalorPagar);
        Button calcular = findViewById(R.id.btncalcular);
        Button limpiar = findViewById(R.id.btnclear);

        //Crear el adaptador que contendrá el arreglo tdestinos
        ArrayAdapter adpTdestino = new ArrayAdapter(this, android.R.layout.simple_list_item_checked, tdestinos);

        //Asignar el adaptador al Spinner
        destino.setAdapter(adpTdestino);

        //Chequear el Destino
        destino.setOnItemSelectedListener(this);

        //Evento click boton limpiar
        limpiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nombre.setText("");
                fechaSalida.setText("");
                numeroPersonas.setText("");
                totalPagar.setText("");
                rb2.setChecked(true);
                rb4.setChecked(false);
                rb6.setChecked(false);
                tourCiudad.setChecked(false);
                discoteca.setChecked(false);
                nombre.requestFocus(); // Se envía el foco al nombre
            }
        });

        //Evento click del botón calcular
        calcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!nombre.getText().toString().isEmpty()){
                    if (!fechaSalida.getText().toString().isEmpty()){
                        if (!numeroPersonas.getText().toString().isEmpty()){
                            if (parseInt(numeroPersonas.getText().toString()) >= 1 && parseInt(numeroPersonas.getText().toString()) <= 10){
                                //Chequear le destino seleccionado
                                double valorDestino = 0;
                                switch (presSelect){
                                    case "Cartagena":
                                        valorDestino = 200000;
                                        break;
                                    case "Santa Marta":
                                        valorDestino = 180000;
                                        break;
                                    case "San Andrés":
                                        valorDestino = 170000;
                                        break;
                                    case "Medellín":
                                        valorDestino = 190000;
                                        break;
                                }
                                double ndias = 0;
                                if (rb2.isChecked()){
                                    ndias = 2;
                                }
                                if (rb4.isChecked()){
                                    ndias = 4;
                                }
                                if (rb6.isChecked()){
                                    ndias = 6;
                                }
                                int npersonas = parseInt(numeroPersonas.getText().toString());
                                double subtotal = valorDestino * ndias * npersonas;

                                //Se aplica descuento del 10% si son mas de 5 personas
                                double descuento = 0;
                                if (npersonas > 5){
                                    descuento = subtotal * 10 / 100;
                                }
                                double xvalortotal = subtotal - descuento;
                                //Asignar xtotalDeuda al objeto referenciado totalPagar
                                DecimalFormat valueFormat = new DecimalFormat("###,###,###,###.#");
                                double valortour = 0;
                                if (tourCiudad.isChecked()){
                                    valortour = npersonas * 60000;
                                }
                                double valorDiscoteca = 0;
                                if (discoteca.isChecked()){
                                    valorDiscoteca = npersonas * 80000;
                                }
                                totalPagar.setText(valueFormat.format(xvalortotal + valortour + valorDiscoteca));
                            } else {
                                Toast.makeText(getApplicationContext(), "Debe ingresar un número de personas máximo de 10 y mínimo 1", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "Número de personas obligatorio", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Fecha de salida obligatotia", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Nombre obligatorio", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        presSelect = tdestinos[position]; //Tomar el valor de la opción seleccionado destinos
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}