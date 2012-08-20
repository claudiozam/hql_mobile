package edu.palermo.hqlproject.views.activities;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import edu.palermo.hqlproject.R;

public class PreferencesActivity extends PreferenceActivity implements OnSharedPreferenceChangeListener{
	
	private EditTextPreference server;
	
    @Override
    @SuppressWarnings("deprecation")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
        server = (EditTextPreference)getPreferenceScreen().findPreference(getString(R.string.preference_url_key));
    }

    @SuppressWarnings("deprecation")
	@Override
    protected void onResume() {
        super.onResume();

        SharedPreferences sharedPreferences = getPreferenceScreen().getSharedPreferences();
        // Setup the initial values
        server.setSummary(sharedPreferences.getString(getString(R.string.preference_url_key), getString(R.string.preference_url_default)));

        // Set up a listener whenever a key changes            
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @SuppressWarnings("deprecation")
	@Override
    protected void onPause() {
        super.onPause();

        // Unregister the listener whenever a key changes            
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);    
    }

	@SuppressWarnings("deprecation")
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(getString(R.string.preference_url_key))) {
            Preference connectionPref = findPreference(key);
            connectionPref.setSummary(sharedPreferences.getString(key, getString(R.string.preference_url_default)));
        }
		
	}

    
}
