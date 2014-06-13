package be.jonas.kikkersprong.customview;

import java.util.List;

import be.jonas.kikkersprong.ChildOverviewActivity;
import be.jonas.kikkersprong.R;
import be.jonas.kikkersprong.R.color;
import be.jonas.kikkersprong.db.Factuur;
import be.jonas.kikkersprong.db.Kind;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class KindAdapter extends BaseAdapter implements
OnClickListener{

	private Context context;

	private List<Kind> kinderen;

	public KindAdapter(Context context, 
			List<Kind> kinderen) {
		this.context = context;

		this.kinderen = kinderen;
	}

	@Override
	public int getCount() {
		return kinderen.size();
	}

	@Override
	public Object getItem(int arg0) {
		return kinderen.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return kinderen.get(arg0).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View kindView = inflater.inflate(R.layout.kind, parent, false);

		TextView naam = (TextView) kindView.findViewById(R.id.kindNaam);
		naam.setText(kinderen.get(position).getNaam());

		TextView voorNaam = (TextView) kindView.findViewById(R.id.kindVoorNaam);
		voorNaam.setText(kinderen.get(position).getVoornaam());
		
		kindView.setId(kinderen.get(position).getId());
		
		kindView.setOnClickListener(this);
		
		return kindView;
	}

	@Override
	public void onClick(View view) {
		Intent intent = new Intent(context, ChildOverviewActivity.class);
		int id = view.getId();
		intent.putExtra("id", id);
		context.startActivity(intent);
		
	}

	

}
