package edu.palermo.hqlproject.views.activities;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import edu.palermo.hqlproject.R;

public class WebActivity extends Activity {
	private WebView webView;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.web_view);
		String url = getIntent().getDataString();
		webView = (WebView) findViewById(R.id.webview);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.loadUrl(url);

	}

}
