package com.mindblowninnovation.ank;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.wifi.WifiConfiguration.Status;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;


public class MainActivity extends Activity implements OnClickListener{
	final static String TAG = "MainActivity";
	ProgressDialog progress;
	Context context;
	Button search;
	ListView lv;
	EditText query;
	TracksAdapter adapter;
	ArrayList<Track> tracks;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        this.context = this;
        progress= new ProgressDialog(context);
        
        search = (Button)findViewById(R.id.search);
        search.setOnClickListener(this);
        query = (EditText)findViewById(R.id.query);
        
        tracks = new ArrayList<Track>();
        lv = (ListView) findViewById(R.id.list);
    }

    @Override
	public void onClick(View arg0) {
    	//Generate a get request to the following URL
    	String url = "track.json";
    	List<NameValuePair> pairs = new ArrayList<NameValuePair>();
    	//set a get param called query, set to the value of the edit text
		pairs.add(new BasicNameValuePair("q", query.getText().toString())); 
		progress.setTitle("Loading Songs");
		progress.show();
    	GetTracks getter = new GetTracks(url, ANUtils.ACTION_GET, pairs, "tracks", asyncHandler, context);
    	getter.execute();
	}  
    
    public class GetTracks extends ANTask{
    	Status status;
		public GetTracks(String URL, String action, List<NameValuePair> params,
				String type, Handler mainUIHandler, Context context) {
			super(URL, action, params, type, mainUIHandler, context);
		}

		@Override
		protected Object decode(String jsondata) throws JSONException {
			Log.d("JSON", jsondata);
			JSONObject ob = new JSONObject(jsondata);
			JSONArray jtracks = ob.getJSONArray("tracks");
			for(int i = 0; i < jtracks.length(); i++){
				JSONObject jtrack = (JSONObject) jtracks.get(i);
				tracks.add(new Track(jtrack));
			}
			return tracks;
		}
		
    }
    ANHandler asyncHandler = new ANHandler()
    {
    	@Override
    	public void resultOK(Message msg) 
    	{
    		Bundle b = msg.getData();

    		if(progress.isShowing()){
    			progress.dismiss();
    		}
    		tracks = (ArrayList<Track>)msg.obj;
    		adapter = new TracksAdapter(MainActivity.this, R.layout.track_cell, tracks);
    		lv.setAdapter(adapter);
    		adapter.notifyDataSetChanged();
    	}

    	@Override
    	public void resultFailed(Message msg) {
    		Log.d("LocationActivity", "Handling Error Message");
    		if(progress.isShowing()){
    			progress.dismiss();
    		}
    	}
    };
    private class TracksAdapter extends ArrayAdapter<Track> 
    {
    	private ArrayList<Track> tracks;
    	public TracksAdapter(Context context, int textViewResourceId, ArrayList<Track> bs) 
    	{
    		super(context, textViewResourceId, bs);
    		this.tracks = bs;
    	}
    	@Override
    	public View getView(int position, View convertView, ViewGroup parent) {
    		View v = convertView;
    		if (v == null) {
    			LayoutInflater vi = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    			v = vi.inflate(R.layout.track_cell, null);
    		}
    		Track aTrack = tracks.get(position);

    		//initialize all the text views
    		TextView text = (TextView) v.findViewById(R.id.title);
    		text.setText(aTrack.name);
    		TextView artists = (TextView) v.findViewById(R.id.artists);
    		artists.setText("By "+ aTrack.getArtists());
    		return v;
    	}
    }
    
}
