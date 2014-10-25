package com.example.cal;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.support.v7.app.ActionBarActivity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView;

public class TextMessage extends ActionBarActivity {
	Button submit;
	EditText data;
	private TextView txt1, txt2;
	SharedPreferences prf;
	database datab;
	Spinner m_textmsg;
	String m_settextmessage;
	// databasemsg database;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_text_message);
		submit = (Button) findViewById(R.id.submit);
		data = (EditText) findViewById(R.id.Data);
		m_textmessagesItemSelection();
		

		// prf=getSharedPreferences("event_details", MODE_PRIVATE);

		/*
		 * txt1=(TextView) findViewById(R.id.textView2); txt2=(TextView)
		 * findViewById(R.id.textView3); String start=prf.getString("key_begin",
		 * ""); String end=prf.getString("key_end", ""); txt1.setText(start);
		 * txt2.setText(end);
		 */
		submit.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				// TODO Auto-generated method stub

				prf = getSharedPreferences("event_details", MODE_PRIVATE);

				txt1 = (TextView) findViewById(R.id.textView2);
				txt2 = (TextView) findViewById(R.id.textView3);
				String start = prf.getString("key_begin", "");
				String end = prf.getString("key_end", "");
				txt1.setText(start);
				txt2.setText(end);
				String getdata = data.getText().toString();
				if(m_settextmessage=="")
				
				
				datab = new database(getBaseContext());
				datab.open();
				long id=datab.insertdata(start, end, getdata);
				Toast.makeText(getBaseContext(), "data inserted",
						Toast.LENGTH_LONG).show();
				datab.close();

			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.text_messaage, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	
	public void m_textmessagesItemSelection() {
		m_textmsg = (Spinner) findViewById(R.id.m_textmessage);
		m_textmsg.setSelection(0);
		m_textmsg.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
					@Override
					public void onItemSelected(AdapterView<?> parent,
							View view, int pos, long id) {
						m_settextmessage = parent.getItemAtPosition(pos)
								.toString();
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
					}
				});
	}
			
}
