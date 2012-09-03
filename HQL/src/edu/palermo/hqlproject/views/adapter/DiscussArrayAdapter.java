package edu.palermo.hqlproject.views.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import edu.palermo.hqlproject.R;
import edu.palermo.hqlproject.views.cells.OneComment;

public class DiscussArrayAdapter extends ArrayAdapter<OneComment> {

	private TextView countryName;
	private List<OneComment> comments = new ArrayList<OneComment>();
	private LinearLayout wrapper;

	@Override
	public void add(OneComment object) {
		if (comments != null) {
			comments.add(object);
		}
		super.add(object);
	}

	@Override
	public void clear(){
		if (comments != null) {
			comments.clear();
		}
		super.clear();
	}
	
	public DiscussArrayAdapter(Context context, int textViewResourceId) {
		super(context, textViewResourceId);
	}

	public int getCount() {
		return this.comments.size();
	}

	public OneComment getItem(int index) {
		return this.comments.get(index);
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		if (row == null) {
			LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			row = inflater.inflate(R.layout.listitem_discuss, parent, false);
		}

		wrapper = (LinearLayout) row.findViewById(R.id.wrapper);

		OneComment coment = getItem(position);

		countryName = (TextView) row.findViewById(R.id.comment);

		countryName.setText(coment.getComment());

		countryName.setBackgroundResource(coment.isLeft() ? R.drawable.bubble_yellow : R.drawable.bubble_green);
		wrapper.setGravity(coment.isLeft() ? Gravity.LEFT : Gravity.RIGHT);

		return row;
	}

	public Bitmap decodeToBitmap(byte[] decodedByte) {
		return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
	}

}