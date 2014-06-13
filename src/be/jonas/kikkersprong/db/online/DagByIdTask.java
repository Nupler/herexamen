package be.jonas.kikkersprong.db.online;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.os.AsyncTask;
import android.util.Log;
import be.jonas.kikkersprong.db.Factuur;
import be.jonas.kikkersprong.db.Kind;

public class DagByIdTask extends
		AsyncTask<String, Integer, List<String>> {

	public static final String LOG_TAG = "ONLINEDB";
	private static ArrayList<Factuur> facturen;
	private static ArrayList<Kind> kinderen;






	public DagByIdTask() {
		super();

	}

	@Override
	protected List<String> doInBackground(String... params) {
		List<String> out = new ArrayList<String>();
		Log.i(LOG_TAG, params[1]);
		Log.i(LOG_TAG, params[2]);

		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(params[0]);
		ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();

		postParameters.add(new BasicNameValuePair("param", params[1]));
		postParameters.add(new BasicNameValuePair("id", params[2]));

		try {
			httppost.setEntity(new UrlEncodedFormEntity(postParameters));
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			InputStream is = entity.getContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "iso-8859-1"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;

			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");

			}
			is.close();
			String result = sb.toString();
			out.add(result);

		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}

		return out;
	}

}
