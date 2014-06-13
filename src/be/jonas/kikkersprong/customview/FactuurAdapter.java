package be.jonas.kikkersprong.customview;

import java.util.List;

import android.content.Context;
import android.graphics.Typeface;
import android.opengl.Visibility;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnLongClickListener;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import be.jonas.kikkersprong.R;
import be.jonas.kikkersprong.db.Factuur;

public class FactuurAdapter extends BaseExpandableListAdapter  {

	private Context _context;
	private List<Factuur> facturen;

	public FactuurAdapter(Context context, List<Factuur> facturen) {
		this._context = context;

		this.facturen = facturen;
	}

	@Override
	public Object getChild(int groupPosition, int childPosititon) {
		return this.facturen.get(groupPosition).getDagen().get(childPosititon);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public View getChildView(int groupPosition, final int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {

		if (convertView == null) {
			LayoutInflater infalInflater = (LayoutInflater) this._context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = infalInflater.inflate(R.layout.dag, null);
		}

		TextView datum = (TextView) convertView.findViewById(R.id.dagDatum);

		TextView uren = (TextView) convertView.findViewById(R.id.dagUren);

		datum.setText(facturen.get(groupPosition).getDagen().get(childPosition)
				.getDatum());
		uren.setText(facturen.get(groupPosition).getDagen().get(childPosition)
				.getUren()
				+ "");
		
		

		return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return this.facturen.get(groupPosition).getDagen().size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return this.facturen.get(groupPosition);
	}

	@Override
	public int getGroupCount() {

		return this.facturen.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {

		if (convertView == null) {
			LayoutInflater infalInflater = (LayoutInflater) this._context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = infalInflater.inflate(R.layout.factuur, null);
		}

		TextView maand = (TextView) convertView.findViewById(R.id.factuurMaand);
		maand.setTypeface(null, Typeface.BOLD);

		TextView prijs = (TextView) convertView.findViewById(R.id.factuurPrijs);

		TextView betaald = (TextView) convertView
				.findViewById(R.id.factuurBetaald);
		
	
	
		
		
		if (facturen.get(groupPosition).isBetaald()) {
			betaald.setText("betaald");
			
			
		} else {
			betaald.setText("niet betaald");
			
		}

		prijs.setText(facturen.get(groupPosition).getTotaalBedrag()+"");

		maand.setText(facturen.get(groupPosition).getMaand());
		
		return convertView;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}
	
	public void addFactuur(Factuur f){
		this.facturen.add(f);
		notifyDataSetChanged();
	}
	
	
}