package be.jonas.kikkersprong;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ScanActivity extends Activity implements OnClickListener {

	private Button scanBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scan);
		scanBtn = (Button) findViewById(R.id.scanButton);

		scanBtn.setOnClickListener(this);
	}

	private boolean isNetworkAvailable() {
		ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager
				.getActiveNetworkInfo();
		return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.scan, menu);
		return true;
	}

	public void onClick(View v) {
		if (v.getId() == R.id.scanButton) {
			IntentIntegrator scanIntegrator = new IntentIntegrator(this);
			scanIntegrator.initiateScan();
		}
	}

	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		IntentResult scanningResult = IntentIntegrator.parseActivityResult(
				requestCode, resultCode, intent);

		if (scanningResult != null) {
			String scanContent = scanningResult.getContents();
			String scanFormat = scanningResult.getFormatName();

			Log.i("scan", scanContent);
			try {
				if (scanContent.equals("financieel")) {
					if (isNetworkAvailable()) {
						Intent i = new Intent(this, FinancialActivity.class);
						startActivity(i);
					} else {

						Toast.makeText(this, "Geen internet verbinding",
								Toast.LENGTH_LONG);
					}
				} else {
					Intent i = new Intent(this, UserActivity.class);
					String[] content = scanContent.split("-");

					String voornaam = content[0];
					String familienaam = content[1];
					int id = Integer.parseInt(content[2]);

					i.putExtra("voornaam", voornaam);
					i.putExtra("naam", familienaam);
					i.putExtra("id", id);

					startActivity(i);
				}
			} catch (Exception e) {
				Toast.makeText(this, "Geen correcte code", Toast.LENGTH_LONG)
						.show();
			}

		} else {
			Toast toast = Toast.makeText(getApplicationContext(),
					"No scan data received!", Toast.LENGTH_LONG);
			toast.show();
		}
	}

}
