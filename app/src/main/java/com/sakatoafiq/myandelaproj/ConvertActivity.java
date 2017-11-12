package com.sakatoafiq.myandelaproj;

import android.app.Activity;
import android.os.Bundle;

import android.util.Log;
import android.widget.Spinner;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;
import android.widget.Button.OnClickListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView.*;
import android.widget.*;

public class ConvertActivity extends Activity 

{

	private TextView currency_rate;
	private EditText get_input;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.convert_activity_detail_layout);

		final Spinner spinner = (Spinner)findViewById(R.id.spinner);
		get_input = (EditText) findViewById(R.id.input_item);
		currency_rate = (TextView) findViewById(R.id.currency_rate);

		final String get_fromSymbol = getIntent().getStringExtra("from_symbol");
		final String get_toSymbol = getIntent().getStringExtra("to_symbol");
		Bundle data = getIntent().getExtras();
		final double currency_price = data.getDouble("price");

		String[] options = new String[] { "Convert "+get_fromSymbol+" to "+get_toSymbol, "Convert "+get_toSymbol+" to "+get_fromSymbol};

		ArrayAdapter<String>adapter = new ArrayAdapter<String>(ConvertActivity.this,  android.R.layout.simple_spinner_item,options);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);

		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
				@Override
				public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

					final String item = (String) parent.getItemAtPosition(position);

					Toast.makeText(getBaseContext(), item, Toast.LENGTH_SHORT).show();
				}

				@Override
				public void onNothingSelected(AdapterView<?> parent) {
					// TODO Auto-generated method stub
				}
			});

		Button calculate = (Button) findViewById(R.id.calc_button);
		calculate.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					int selected_option = spinner.getSelectedItemPosition();
					final double input = Double.parseDouble(get_input.getText().toString());
					convert(selected_option,currency_price,input);
				}
			});

	}

	public void convert (int position, double currency_price, double input){
		if (position == 0){
			double answer = Math.round(input * currency_price);
			String ans_converted = Double.toString(answer);
			currency_rate.setText(ans_converted);

		} else if (position == 1) {
			double answer = Math.round(input / currency_price);
			String ans_converted = Double.toString(answer);
			currency_rate.setText(ans_converted);
		}
	}
}


