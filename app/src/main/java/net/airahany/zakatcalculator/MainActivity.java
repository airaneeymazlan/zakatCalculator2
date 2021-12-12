package net.airahany.zakatcalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    EditText addGram;
    EditText addValue;
    Button buttonCalculate;
    TextView tvPayable;
    TextView tvOutput;
    Spinner spinner;
    ArrayAdapter<CharSequence> adapter;

    float gram;
    float value;

    SharedPreferences sharedPref;
    SharedPreferences sharedPref2;

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
    }

    public void onNothingSelected(AdapterView<?> parent) {

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinner = (Spinner) findViewById(R.id.spinner);
        adapter = ArrayAdapter.createFromResource(this, R.array.status, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        addGram = (EditText) findViewById(R.id.addGram);
        addValue = (EditText) findViewById(R.id.addValue);
        buttonCalculate = (Button) findViewById(R.id.buttonCalculate);
        tvOutput = (TextView) findViewById(R.id.tvOutput);
        tvPayable = (TextView) findViewById(R.id.tvPayable);

        buttonCalculate.setOnClickListener(this);
        spinner.setOnItemSelectedListener(this);

        sharedPref = this.getSharedPreferences("weight", Context.MODE_PRIVATE);
        gram = sharedPref.getFloat("weight", 0.0F);

        sharedPref2 = this.getSharedPreferences("value", Context.MODE_PRIVATE);
        value = sharedPref2.getFloat("value", 0.0F);

        addGram.setText(""+gram);
        addValue.setText(""+value);


    }

    @Override
    public void onClick(View v) {

        try {
            switch (v.getId()) {

                case R.id.buttonCalculate:
                    calculate();
                    break;

            }
        } catch (java.lang.NumberFormatException nfe) {
            Toast.makeText(this, "Please enter a valid number", Toast.LENGTH_SHORT).show();

        } catch (Exception exp) {
            Toast.makeText(this,"Unknown Exception" + exp.getMessage(),Toast.LENGTH_SHORT).show();

            Log.d("Exception",exp.getMessage());

        }
    }

    public void calculate(){
        DecimalFormat df = new DecimalFormat("##.00");
        float weight = Float.parseFloat(addGram.getText().toString());
        float value = Float.parseFloat(addValue.getText().toString());
        String stat = spinner.getSelectedItem().toString();
        double totValue;
        double uruf;
        double payable;
        double totZakat;
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putFloat("weight", weight);
        editor.apply();
        SharedPreferences.Editor editor2 = sharedPref2.edit();
        editor2.putFloat("value", value);
        editor2.apply();


        if (stat.equals("Keep")){
            totValue= weight * value;
            uruf= weight - 85;

            if(uruf>=0.0) {
                payable = uruf * value;
                totZakat = payable * 0.025;
            }

            else{
                payable = 0.0;
                totZakat = payable * 0.025;

            }

            tvPayable.setText("Zakat payable: RM"+ df.format(payable));
            tvOutput.setText("Total zakat: RM"+ df.format(totZakat));
        }

        else{
            totValue= weight * value;
            uruf= weight - 200;

            if(uruf>=0.0) {
                payable = uruf * value;
                totZakat = payable * 0.025;
            }

            else{
                payable = 0.0;
                totZakat = payable * 0.025;

            }

            tvPayable.setText("Zakat payable: RM"+ df.format(payable));
            tvOutput.setText("Total Zakat: RM"+ df.format(totZakat));

        }

    }



    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.about:
                //Toast.makeText(this,"This is about",Toast.LENGTH_LONG).show();

                Intent intent = new Intent(this, AboutActivity.class);
                startActivity(intent);

                break;

            case R.id.settings:
                Toast.makeText(this,"This is settings",Toast.LENGTH_LONG).show();
                break;

            case R.id.profile:
                Toast.makeText(this,"This is profile",Toast.LENGTH_LONG).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}