package cc.chen.mytestapi.test1;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import cc.chen.mytestapi.BaseActivity;
import cc.chen.mytestapi.R;
import cc.chen.mytestapi.fragments.SimpleTextFragment;
import cc.chen.mytestapi.logger.Log;

public class NetworkConnectTest extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.network_connect_main);

		// Initialize text fragment that displays intro text.
		SimpleTextFragment introFragment = (SimpleTextFragment) getSupportFragmentManager().findFragmentById(
				R.id.intro_fragment);
		introFragment.setText(R.string.welcome_message);
		introFragment.getTextView().setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16.0f);

		// Initialize the logging framework.
		initializeLogging();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.network_connect_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		// When the user clicks FETCH, fetch the first 500 characters of
		// raw HTML from www.google.com.
		case R.id.fetch_action:
			new DownloadTask().execute("http://www.baidu.com");
			return true;
			// Clear the log view fragment.
		case R.id.clear_action:
			logFragment.getLogView().setText("");
			return true;
		}
		return false;
	}

	/**
	 * Implementation of AsyncTask, to fetch the data in the background away
	 * from the UI thread.
	 */
	private class DownloadTask extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... urls) {
			try {
				return loadFromNetwork(urls[0]);
			} catch (IOException e) {
				return getString(R.string.connection_error);
			}
		}

		/**
		 * Uses the logging framework to display the output of the fetch
		 * operation in the log fragment.
		 */
		@Override
		protected void onPostExecute(String result) {
			Log.i(TAG, result);
		}
	}

	/** Initiates the fetch operation. */
	private String loadFromNetwork(String urlString) throws IOException {
		InputStream stream = null;
		String str = "";

		try {
			stream = downloadUrl(urlString);
			str = readIt(stream, 500);
		} finally {
			if (stream != null) {
				stream.close();
			}
		}
		return str;
	}

	/**
	 * Given a string representation of a URL, sets up a connection and gets an
	 * input stream.
	 * 
	 * @param urlString
	 *            A string representation of a URL.
	 * @return An InputStream retrieved from a successful HttpURLConnection.
	 * @throws java.io.IOException
	 */
	private InputStream downloadUrl(String urlString) throws IOException {

		URL url = new URL(urlString);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setReadTimeout(10000 /* milliseconds */);
		conn.setConnectTimeout(15000 /* milliseconds */);
		conn.setRequestMethod("GET");
		conn.setDoInput(true);
		// Start the query
		conn.connect();
		InputStream stream = conn.getInputStream();
		return stream;

	}

	/**
	 * Reads an InputStream and converts it to a String.
	 * 
	 * @param stream
	 *            InputStream containing HTML from targeted site.
	 * @param len
	 *            Length of string that this method returns.
	 * @return String concatenated according to len parameter.
	 * @throws java.io.IOException
	 * @throws java.io.UnsupportedEncodingException
	 */
	private String readIt(InputStream stream, int len) throws IOException, UnsupportedEncodingException {
		Reader reader = null;
		reader = new InputStreamReader(stream, "UTF-8");
		char[] buffer = new char[len];
		reader.read(buffer);
		return new String(buffer);
	}
}
