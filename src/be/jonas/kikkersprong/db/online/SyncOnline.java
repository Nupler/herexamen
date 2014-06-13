package be.jonas.kikkersprong.db.online;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.os.StrictMode;
import be.jonas.kikkersprong.db.offline.Aanwezigheid;

public class SyncOnline {

	public void uploadAanwezigheden(List<Aanwezigheid> aanwezigheiden) {

		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.detectAll().penaltyLog().build();
		StrictMode.setThreadPolicy(policy);
		for (Aanwezigheid a : aanwezigheiden) {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(
					"https://r0427410.webontwerp.khleuven.be/kikker.php");

			ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();

			postParameters.add(new BasicNameValuePair("param",
					"addAanwezigheid"));
			postParameters.add(new BasicNameValuePair("id", a.getId()));
			postParameters.add(new BasicNameValuePair("datum", a.getDatum()));
			postParameters.add(new BasicNameValuePair("uren", a.getUren()));

			try {
				httppost.setEntity(new UrlEncodedFormEntity(postParameters));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			HttpResponse response = null;
			try {
				response = httpclient.execute(httppost);
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
