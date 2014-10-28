package com.example.cal;

import java.text.BreakIterator;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView;

public class TextMessage extends ActionBarActivity {
	ImageButton submit;
	EditText data;
	private TextView txt1, txt2, m_errormsg;
	SharedPreferences prf;
	database datab;
	Spinner m_textmsg;
	String m_settextmessage;
	int spinner_pos;
	String m_messagetobesent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_text_message);
		submit = (ImageButton) findViewById(R.id.m_submit);
		data = (EditText) findViewById(R.id.m_data);
		m_textmessagesItemSelection();
		data.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				if (count == 0) {
					submit.setVisibility(View.GONE);
				}
				if (count > 0) {
					submit.setVisibility(View.VISIBLE);
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}
		});

		submit.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				submit.setImageResource(R.drawable.blue_button);
				prf = getSharedPreferences("event_details", MODE_PRIVATE);

				/*txt1 = (TextView) findViewById(R.id.textView2);
				txt2 = (TextView) findViewById(R.id.textView3);*/
				String start = prf.getString("key_begin", "");
				String end = prf.getString("key_end", "");
				/*txt1.setText(start);
				txt2.setText(end);*/
				String getdata = data.getText().toString();

				if (spinner_pos != 0 && getdata.length()!=0) {
					m_errormsg = (TextView) findViewById(R.id.m_errormessage);
					m_errormsg.setText("Please input a single message");

				} else {

					if (spinner_pos != 0 && getdata.length() == 0) {

						m_messagetobesent = m_settextmessage;
					}

					else if (spinner_pos == 0 && getdata.length()!=0) {
						m_messagetobesent = getdata;
					}

					datab = new database(getBaseContext());
					datab.open();
					long id = datab.insertdata(start, end, m_messagetobesent);
					Toast.makeText(getBaseContext(), "data inserted",
							Toast.LENGTH_LONG).show();
					datab.close();
				}

			}
		});

		// prf=getSharedPreferences("event_details", MODE_PRIVATE);

		/*
		 * txt1=(TextView) findViewById(R.id.textView2); txt2=(TextView)
		 * findViewById(R.id.textView3); String start=prf.getString("key_begin",
		 * ""); String end=prf.getString("key_end", ""); txt1.setText(start);
		 * txt2.setText(end);
		 */

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

		m_textmsg
				.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
					@Override
					public void onItemSelected(AdapterView<?> parent,
							View view, int pos, long id) {
						submit = (ImageButton) findViewById(R.id.m_submit);
						if (pos == 0) {
							submit.setVisibility(View.GONE);
						} else if (pos != 0) {

							submit.setVisibility(View.VISIBLE);
							spinner_pos = pos;
							m_settextmessage = parent.getItemAtPosition(pos)
									.toString();
						}

					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
					}
				});
	}

}
