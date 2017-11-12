package com.sakatoafiq.myandelaproj;

import android.app.Activity;
import android.view.View.OnClickListener;
import android.view.View;
import android.os.Bundle;
import android.content.Intent;
import android.widget.*;
import android.widget.Button;
import android.widget.Button.OnClickListener;

import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.Window;


//This is our launch activity to display some cool stuff.
public class SplashActivity extends Activity
{

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);        
        this.setContentView(R.layout.splash_layout);

		//Button to start Our Main activity when you're done reading
		Button start = (Button) findViewById(R.id.start_button);
		start.setOnClickListener (new OnClickListener() {

				@Override 
				public void onClick(View arg0)
				{
					Intent openit = new Intent (SplashActivity.this, CardActivity.class);
					startActivity(openit);
				}

			});


	}



}


