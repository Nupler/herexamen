package be.jonas.kikkersprong;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import be.jonas.kikkersprong.db.offline.Aanwezigheden;

public class UserActivity extends Activity {
	private TextView naam, voornaam, message;
	private View layout;
	private int childId;
	private Aanwezigheden db;
	private View container, checkIn, checkOut;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user);
		db = new Aanwezigheden(this);
		naam = (TextView) findViewById(R.id.naam);
		voornaam = (TextView) findViewById(R.id.voornaam);
		layout = (View) findViewById(R.id.activityUserLayout);

		message = (TextView) findViewById(R.id.message);
		container = findViewById(R.id.messageContainer);
		String vn = getIntent().getStringExtra("voornaam");
		String n = getIntent().getStringExtra("naam");
		childId = getIntent().getIntExtra("id", -1);

		checkIn = findViewById(R.id.checkInButton);
		checkOut = findViewById(R.id.checkOutButton);

		Log.i("childid", childId + "");
		voornaam.setText(vn);
		naam.setText(n);
		doBackground();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.user, menu);
		return true;
	}

	public void removeMessage(View v) {
		container.setVisibility(View.GONE);
	}

	public void checkIn(View v) {

		db.childArrives(childId);

		doBackground();

		message.setText("Welkom " + voornaam.getText());
		container.setVisibility(View.VISIBLE);

	}

	public void checkOut(View v) {
		if (db.childIsPresent(childId)) {
			message.setText("Dag " + voornaam.getText());
			container.setVisibility(View.VISIBLE);

			db.childLeaves(childId);
			doBackground();
			
		}else{
			Toast.makeText(this, "Kind is al uitgechecked", Toast.LENGTH_LONG)
			.show();
		}

	}

	public void toOverview(View v) {
		Intent i = new Intent(this, ChildOverviewActivity.class);
		i.putExtra("id", childId);
		startActivity(i);
	}

	private void doBackground() {
		if (db.childIsPresent(childId)) {
			layout.setBackground(getResources().getDrawable(R.drawable.inside));
			checkIn.setBackground(getResources().getDrawable(
					R.drawable.button_green));
			checkOut.setBackground(getResources()
					.getDrawable(R.drawable.button));
		} else {
			layout.setBackground(getResources().getDrawable(R.drawable.outside));
			checkIn.setBackground(getResources().getDrawable(R.drawable.button));
			checkOut.setBackground(getResources().getDrawable(
					R.drawable.button_green));
		}
	}

}
