package be.jonas.kikkersprong;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;
import be.jonas.kikkersprong.customview.KindAdapter;
import be.jonas.kikkersprong.db.Factuur;
import be.jonas.kikkersprong.db.Kind;
import be.jonas.kikkersprong.db.online.OnlineDBTask;

public class FinancialActivity extends Activity  {

	private ListView list;
	private KindAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_financial);
		try {
			initList();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.financial, menu);
		return true;
	}

	private void initList() throws JSONException {
		OnlineDBTask dbOnline = new OnlineDBTask();

		AsyncTask<String, Integer, List<String>> task = dbOnline.execute(
				"https://r0427410.webontwerp.khleuven.be/kikker.php",
				"kinderen","0");
		
		String result =null;
		
		try {
			result = task.get().get(0);
			Log.i("financieel json",result);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		JSONArray jArray = new JSONArray(result);
		List<Kind> kinderen = new ArrayList<Kind>();
		for (int i = 0; i < jArray.length(); i++) {
			JSONObject json_data = jArray.getJSONObject(i);

			int id = Integer.parseInt(json_data.getString("id"));
	
			String voornaam = json_data.getString("naam");
			String naam = json_data.getString("achternaam");
			
			kinderen.add(new Kind(id,voornaam,naam));
			
		
			
		}
		
		list = (ListView) findViewById(R.id.kindList);	
		adapter = new KindAdapter(this, kinderen);
		list.setAdapter(adapter);
		
		
	}

	

	public void createFacturen(View v) {
		Toast.makeText(this, "maak facturen, not implemented",
				Toast.LENGTH_LONG).show();
	}

}
