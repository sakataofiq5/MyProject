package com.sakatoafiq.myandelaproj;


import android.app.Activity;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.app.ProgressDialog;
import android.widget.Toast;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CardActivity extends Activity {

    private static final String TAG = CardActivity.class.getSimpleName();
    private ListView mListView;
    private ProgressDialog dialog;
    private ListViewAdapter mListAdapter;
    private ArrayList <ListItem> mListData;

	private String BTC_API_URL = "https://min-api.cryptocompare.com/data/pricemultifull?fsyms=BTC&tsyms=NGN,USD,EUR,JPY,HKD,INR,RUB,TRY,ZAR,NOK,KRW,AUD,PLN,THB,DKK,CAD,GBP,CNY,MXN,SEK";
	private String ETH_API_URL = "https://min-api.cryptocompare.com/data/pricemultifull?fsyms=ETH&tsyms=NGN,USD,EUR,JPY,HKD,INR,RUB,TRY,ZAR,NOK,KRW,AUD,PLN,THB,DKK,CAD,GBP,CNY,MXN,SEK";
    String api = "BTC";

	//a method to empty the gridview for reload
	public void emptylistview() {
        mListData = new ArrayList<>();
        mListAdapter = new ListViewAdapter(this, R.layout.list_item_layout, mListData);
        mListView.setAdapter(mListAdapter);
	}


	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mListView = (ListView) findViewById(R.id.listView);

        //Initialize with empty data
		emptylistview();

        //Start download
        new AsyncHttpTask().execute(BTC_API_URL);
	}

	@Override  
    public boolean onCreateOptionsMenu(Menu menu) {  
		// Inflate the menu; this adds movie sort items to the action bar if it is present  
        getMenuInflater().inflate(R.menu.menu_main, menu);//Menu Resource, Menu  
        return true;  
    }  

	//menu items so user can select which api url will be executed
    @Override  
    public boolean onOptionsItemSelected(MenuItem item) {  
        switch (item.getItemId()) { 

            case R.id.btc:  
				Toast.makeText(getApplicationContext(),"Bitcoin",Toast.LENGTH_LONG).show();  
				emptylistview();
				api = "BTC";
				new AsyncHttpTask().execute(BTC_API_URL);
				return true;    

			case R.id.eth:  
				Toast.makeText(getApplicationContext(),"Etherium",Toast.LENGTH_LONG).show(); 
				emptylistview();
				api = "ETH";
                new AsyncHttpTask().execute(ETH_API_URL);
				return true;   

			default:  
                return super.onOptionsItemSelected(item);  
        }  

	}  
	//Downloading data asynchronously
    public class AsyncHttpTask extends AsyncTask<String, Void, Integer> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dialog = new ProgressDialog(CardActivity.this);

			//While AsyncClass is running downloads
			dialog.setMessage("please wait");
			dialog.setTitle("Loading");
			dialog.show();
			dialog.setCancelable(false);
		}


        @Override
        protected Integer doInBackground(String... params) {
            Integer result = 0;
            try {
                // Create Apache HttpClient
                HttpClient httpclient = new DefaultHttpClient();
                HttpResponse httpResponse = httpclient.execute(new HttpGet(params[0]));
                int statusCode = httpResponse.getStatusLine().getStatusCode();

                // 200 represents HTTP OK
                if (statusCode == 200) {
                    String response = streamToString(httpResponse.getEntity().getContent());
                    parseResult(response);
                    result = 1; // Successful
                } else {
                    result = 0; //"Failed
                }
            } catch (Exception e) {
                Log.d(TAG, e.getLocalizedMessage());
            }
            return result;
        }


        @Override
        protected void onPostExecute(Integer result) {
            // Download complete. Let us update UI
            if (result == 1) {
                mListAdapter.setListData(mListData);
            } else {
                Toast.makeText(CardActivity.this, "Failed to fetch data, seems your are offline", Toast.LENGTH_SHORT).show();
            }
            dialog.cancel();

        }
    }

    String streamToString(InputStream stream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
        String line;
        String result = "";
        while ((line = bufferedReader.readLine()) != null) {
            result += line;
        }

        // Close stream
        if (null != stream) {
            stream.close();
        }
        return result;
    }

    /**
     * Parsing the feed results for our list view and transfer to convertactivity
     * 
     */
    private void parseResult(String result) {
        try {
            JSONObject response = new JSONObject(result);
            JSONObject results = response.getJSONObject("RAW");
			ListItem item;

			JSONObject post = results.getJSONObject(api);
			String data [] = {"NGN","USD","EUR","JPY","HKD","INR","RUB","TRY","ZAR","NOK","KRW","AUD","PLN","THB","DKK","CAD","GBP","CNY","MXN","SEK"};

			for (int i = 0; i<data.length; i++){
				JSONObject poster = post.getJSONObject(data[i]);
                double currency = poster.getDouble("PRICE");
				String fromS = poster.getString("FROMSYMBOL");
				String toS = poster.getString("TOSYMBOL");


                item = new ListItem();
                item.setPrice(currency);
                item.setFromSymbol(fromS);
				item.setToSymbol(toS);

				mListData.add(item);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

		mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

					//Get list view item at position
					ListItem item = (ListItem) parent.getItemAtPosition(position);

					Intent intent = new Intent(CardActivity.this, ConvertActivity.class);
					Bundle data = new Bundle();

					data.putDouble("price", item.getPrice());

					//pass item data to converter activity
					intent.putExtra("from_symbol", item.getFromSymbol());
                    intent.putExtra("to_symbol", item.getToSymbol());
					intent.putExtras(data);

					startActivity(intent);
				}
			});

    }
}
