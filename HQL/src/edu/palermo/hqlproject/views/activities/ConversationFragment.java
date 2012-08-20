package edu.palermo.hqlproject.views.activities;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import edu.palermo.hqlproject.R;
import edu.palermo.hqlproject.views.adapter.DiscussArrayAdapter;
import edu.palermo.hqlproject.views.cells.OneComment;

public class ConversationFragment extends Fragment {
	private static final int REQUEST_CODE = 1234;

	private edu.palermo.hqlproject.views.adapter.DiscussArrayAdapter adapter;
	private ListView lv;
	private Button hablar;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.activity_discuss, container,
				false);
		lv = (ListView) view.findViewById(R.id.listView1);
		hablar = (Button)view.findViewById(R.id.hablar);
		hablar.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				startVoiceRecognitionActivity();
			}
		});
		adapter = new DiscussArrayAdapter(inflater.getContext(),
				R.layout.listitem_discuss);
		lv.setAdapter(adapter);
		return view;
	}

	/**
	 * Handle the results from the voice recognition activity.
	 */
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
			// Populate the wordsList with the String values the recognition
			// engine thought it heard
			List<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
			final String[] matches = new String[result.size()];
			result.toArray(matches);
			AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());
			builder.setTitle("Make your selection");
			builder.setItems(matches, new DialogInterface.OnClickListener() {
			    public void onClick(DialogInterface dialog, int item) {
					adapter.add(new OneComment(false, matches[item]));
			    }
			});
			AlertDialog alert = builder.create();
			alert.show();
		}
		super.onActivityResult(requestCode, resultCode, data);
	}


//	private void addItems() {
//		adapter.add(new OneComment(true, "Hello bubbles!"));
//
//		for (int i = 0; i < 4; i++) {
//			boolean left = getRandomInteger(0, 1) == 0 ? true : false;
//			int word = getRandomInteger(1, 10);
//			int start = getRandomInteger(1, 40);
//			String words = ipsum.getWords(word, start);
//
//			adapter.add(new OneComment(left, words));
//		}
//	}

//	private static int getRandomInteger(int aStart, int aEnd) {
//		if (aStart > aEnd) {
//			throw new IllegalArgumentException("Start cannot exceed End.");
//		}
//		long range = (long) aEnd - (long) aStart + 1;
//		long fraction = (long) (range * random.nextDouble());
//		int randomNumber = (int) (fraction + aStart);
//		return randomNumber;
//	}

	/**
	 * Fire an intent to start the voice recognition activity.
	 */
	private void startVoiceRecognitionActivity() {
		Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
		intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
				RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
		intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "HQL");
		startActivityForResult(intent, REQUEST_CODE);
	}

}