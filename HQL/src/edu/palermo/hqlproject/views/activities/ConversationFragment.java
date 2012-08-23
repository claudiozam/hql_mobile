package edu.palermo.hqlproject.views.activities;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.speech.RecognizerIntent;
import android.speech.tts.*;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import edu.palermo.hqlproject.R;
import edu.palermo.hqlproject.server.AsyncRestRequest;
import edu.palermo.hqlproject.server.listeners.AsyncRestListener;
import edu.palermo.hqlproject.server.models.Analize;
import edu.palermo.hqlproject.server.models.ResponseData;
import edu.palermo.hqlproject.views.adapter.DiscussArrayAdapter;
import edu.palermo.hqlproject.views.cells.OneComment;

public class ConversationFragment extends Fragment implements
		AsyncRestListener<Analize>, OnInitListener {
	private static final int REQUEST_CODE = 1234;
	private static final int TTS_REQUEST_CODE = 1235;
	private TextToSpeech mTts;
    private edu.palermo.hqlproject.views.adapter.DiscussArrayAdapter adapter;
	private ListView lv;
	private Button hablar;
	private EditText texto;
	private boolean ttsReadyToUse = false;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.activity_discuss, container,
				false);
		lv = (ListView) view.findViewById(R.id.listView1);
		hablar = (Button) view.findViewById(R.id.hablar);
		texto = (EditText)view.findViewById(R.id.texto);
		texto.addTextChangedListener(new TextWatcher() {
			
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if (count > 0){
					hablar.setText(R.string.enviar);
				}else{
					hablar.setText(R.string.hablar);
				}
			}
			
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}
			
			public void afterTextChanged(Editable s) {
			}
		});
		hablar.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (hablar.getText().equals(getString(R.string.hablar))){
					startVoiceRecognitionActivity();
				}else{
					sendTextToServer(texto.getText().toString());
				}
			}
		});
		adapter = new DiscussArrayAdapter(inflater.getContext(),
				R.layout.listitem_discuss);
		lv.setAdapter(adapter);
		setHasOptionsMenu(true);
		
	    Intent checkIntent = new Intent();
	    checkIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
	    startActivityForResult(checkIntent, TTS_REQUEST_CODE);
	        
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
			List<String> result = data
					.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
			final String[] matches = new String[result.size()];
			result.toArray(matches);
			AlertDialog.Builder builder = new AlertDialog.Builder(
					this.getActivity());
			builder.setTitle(R.string.voice_recog);
			builder.setItems(matches, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int item) {
//					sendTextToServer(matches[item]);
					texto.setText(matches[item]);
				}
			});
			AlertDialog alert = builder.create();
			alert.show();
		} else if (requestCode == TTS_REQUEST_CODE) {
            if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS)
            {
                mTts = new TextToSpeech(this.getActivity(), this);
            }
            else
            {
                Intent installIntent = new Intent();
                installIntent.setAction(
                        TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
                startActivity(installIntent);
            }
        }
		super.onActivityResult(requestCode, resultCode, data);
	}

	@SuppressWarnings("unchecked")
	protected void sendTextToServer(String string) {
		adapter.add(new OneComment(false, string));
		SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
		String base_api_url = sharedPref.getString(getString(R.string.preference_url_key), getString(R.string.preference_url_default));
		AsyncRestRequest<Analize> api = new AsyncRestRequest<Analize>(base_api_url + "/analize.html?text={text}&userAgent={userAgent}", Analize.class, ConversationFragment.this);
		Map<String,String> param = new HashMap<String,String>();
		param.put("text", string);
		api.execute(param);
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.main_menu, menu);
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.settings:
			Intent i = new Intent(getActivity(), PreferencesActivity.class);
			startActivity(i);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}

	}

	// private void addItems() {
	// adapter.add(new OneComment(true, "Hello bubbles!"));
	//
	// for (int i = 0; i < 4; i++) {
	// boolean left = getRandomInteger(0, 1) == 0 ? true : false;
	// int word = getRandomInteger(1, 10);
	// int start = getRandomInteger(1, 40);
	// String words = ipsum.getWords(word, start);
	//
	// adapter.add(new OneComment(left, words));
	// }
	// }

	// private static int getRandomInteger(int aStart, int aEnd) {
	// if (aStart > aEnd) {
	// throw new IllegalArgumentException("Start cannot exceed End.");
	// }
	// long range = (long) aEnd - (long) aStart + 1;
	// long fraction = (long) (range * random.nextDouble());
	// int randomNumber = (int) (fraction + aStart);
	// return randomNumber;
	// }

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

	public void restCallDidFinishSucessfuly(Analize result) {
		ResponseData data = result.getResponseData();
		adapter.add(new OneComment(true, data.getSimpleText()));
		
		if(result.getResponseType().equals("text") && ttsReadyToUse) {
			mTts.speak(data.getSimpleText(),
	                TextToSpeech.QUEUE_FLUSH,  // Drop all pending entries in the playback queue.
	                null);
		}
		
		texto.setText("");
	}

	public void restCallDidFinishWithErrors() {

	}

	public void onInit(int status) {
		if (status == TextToSpeech.SUCCESS) {
			int result = mTts.setLanguage(new Locale("es"));
			if (result == TextToSpeech.LANG_MISSING_DATA
					|| result == TextToSpeech.LANG_NOT_SUPPORTED) {
				
				Log.e("ConversationFragment", "Language is not available");
			} else {
				ttsReadyToUse = true;
			}
		} else {
			// Initialization failed.
			Log.e("ConversationFragment", "Could not initialize TextToSpeech");
		}
		
	}

}