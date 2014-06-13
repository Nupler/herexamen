package be.jonas.kikkersprong;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import be.jonas.kikkersprong.customview.FactuurAdapter;
import be.jonas.kikkersprong.db.Dag;
import be.jonas.kikkersprong.db.Factuur;
import be.jonas.kikkersprong.db.online.OnlineDBTask;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.Toast;

public class ChildOverviewActivity extends Activity  {

	private ExpandableListView factuurView;
	private FactuurAdapter adapter;
	private int idKind;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_child_overview);
		idKind = getIntent().getIntExtra("id", 0);
		try {
			createList();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.child_overview, menu);
		return true;
	}

	private void createList() throws JSONException {

		OnlineDBTask factuur = new OnlineDBTask();
		OnlineDBTask dag = new OnlineDBTask();

		AsyncTask<String, Integer, List<String>> factuurTask = factuur
				.execute("https://r0427410.webontwerp.khleuven.be/kikker.php",
						"overviewOfChild", idKind + "");

		AsyncTask<String, Integer, List<String>> dagTask = dag.execute(
				"https://r0427410.webontwerp.khleuven.be/kikker.php",
				"dayChild", idKind + "");

		String result = null;

		try {
			result = factuurTask.get().get(0);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Log.i("financieel",result);
		JSONArray jArray = new JSONArray(result);
		List<Factuur> facturen = new ArrayList<Factuur>();
		for (int i = 0; i < jArray.length(); i++) {
			JSONObject json_data = jArray.getJSONObject(i);

			int id = Integer.parseInt(json_data.getString("idkind"));
			int factuurId = Integer.parseInt(json_data.getString("id"));
			String maand = json_data.getString("maand");
			boolean betaald = Boolean.parseBoolean(json_data
					.getString("betaald"));
			facturen.add(new Factuur(id, betaald, maand,factuurId));

		}
		
		try {
			result = dagTask.get().get(0);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		jArray = new JSONArray(result);
		List<Dag> dagen = new ArrayList<Dag>();
		for (int i = 0; i < jArray.length(); i++) {
			JSONObject json_data = jArray.getJSONObject(i);

			int id = Integer.parseInt(json_data.getString("idfactuur"));
			String datum = json_data.getString("datum");
			double uren = Double.parseDouble(json_data
					.getString("uren"));
			dagen.add(new Dag(datum, uren, id));

		}
		
		for (Dag d : dagen){
			for (Factuur f : facturen)
			if (d.getFactuurId() == f.getFactuurId()){
				f.getDagen().add(d);
			}
		}
		
		

		factuurView = (ExpandableListView) findViewById(R.id.factuurList);
		adapter = new FactuurAdapter(this, facturen);
		factuurView.setAdapter(adapter);

	}

	

}
