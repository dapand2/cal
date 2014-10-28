package com.example.cal;

import java.text.BreakIterator;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.app.IntentService;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.CalendarContract.Events;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;
import android.view.View.OnClickListener;

;

class MyCalendar {
	public String name;
	public String id;

	public MyCalendar(String _name, String _id) {
		name = _name;
		id = _id;
	}

	@Override
	public String toString() {
		return name;
	}
}

public class MainActivity extends Activity {

	private Spinner m_spinner_calender;

	private Button m_button_getEvents;
	private Button text1;
	private Button text2;
	private Button text3;
	
	private Button m_button_show;
	private TextView m_text_event;
	String begin1, begin2, begin3;
	String end1, end2, end3;
	database datab;
	private TextView displ2;
	SharedPreferences prf;
	private ImageView img;
	private ImageButton m_cal_button;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		getWindow().getDecorView().setBackgroundColor(Color.BLUE);
		getCalendars();
		callCalendar();
		populateGetEventsBtn();
		saveeventdata1();
		saveeventdata2();
		saveeventdata3();
		// callCalendar();
		displ2 = (TextView) findViewById(R.id.messages);
		displ2.setVisibility(View.GONE);
		// img.setVisibility(View.GONE);
		// text4.setVisibility(View.GONE);

	}

	private void callCalendar() {
		// TODO Auto-generated method stub

		m_cal_button=(ImageButton) findViewById(R.id.m_cal_button);
		m_cal_button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(Intent.ACTION_VIEW, android.net.Uri
						.parse("content://com.android.calendar/time/")));
			}
		});
	}

	private void populateCalendarSpinner() {
		if (m_calendars == null) {
			displ2.setText("no calendar found");
		} else if (m_calendars != null) {
			m_spinner_calender = (Spinner) this
					.findViewById(R.id.spinner_calendar);
			ArrayAdapter l_arrayAdapter = new ArrayAdapter(
					this.getApplicationContext(), R.layout.spinner_layout,
					m_calendars);
			l_arrayAdapter.setDropDownViewResource(R.layout.spinner_layout);
			m_spinner_calender.setAdapter(l_arrayAdapter);
			m_spinner_calender.setSelection(0);
			m_spinner_calender
					.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
						@Override
						public void onItemSelected(AdapterView<?> p_parent,
								View p_view, int p_pos, long p_id) {
							m_selectedCalendarId = m_calendars[(int) p_id].id;
							text1.setVisibility(View.GONE);
							text2.setVisibility(View.GONE);
							text3.setVisibility(View.GONE);
							displ2.setVisibility(View.GONE);

						}

						@Override
						public void onNothingSelected(AdapterView<?> arg0) {
						}
					});
		}
	}

	private void populateGetEventsBtn() {
		m_button_getEvents = (Button) findViewById(R.id.button_get_events);
		m_button_getEvents.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				getLastThreeEvents();

			}
		});
	}

	private void saveeventdata1() {
		text1 = (Button) findViewById(R.id.button1);
		text1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				prf = getSharedPreferences("event_details", MODE_PRIVATE);
				SharedPreferences.Editor edit = prf.edit();
				edit.putString("key_begin", begin1);
				edit.putString("key_end", end1);
				edit.commit();
				Toast.makeText(getBaseContext(), "inserted", Toast.LENGTH_LONG)
						.show();
				/*
				 * datab = new database(getBaseContext()); datab.open(); long id
				 * = datab.insertdata(begin1, end1);
				 * Toast.makeText(getBaseContext(), "inserted",
				 * Toast.LENGTH_LONG) .show(); Cursor C = datab.returndata(); if
				 * (C.moveToFirst()) { do {
				 * displ1.setText(C.getString(0).toString());
				 * displ2.setText(C.getString(1).toString()); } while
				 * (C.moveToNext()); }
				 */
				// datab.close();
				Intent intent = new Intent(v.getContext(), TextMessage.class);
				startActivityForResult(intent, 0);
			}
		});
	}

	private void saveeventdata2() {
		text2 = (Button) findViewById(R.id.button2);
		text2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				prf = getSharedPreferences("event_details", MODE_PRIVATE);
				SharedPreferences.Editor edit = prf.edit();
				edit.putString("key_begin", begin2);
				edit.putString("key_end", end2);
				edit.commit();
				Toast.makeText(getBaseContext(), "inserted", Toast.LENGTH_LONG)
						.show();
				/*
				 * datab = new database(getBaseContext()); datab.open(); long id
				 * = datab.insertdata(begin1, end1);
				 * Toast.makeText(getBaseContext(), "inserted",
				 * Toast.LENGTH_LONG) .show(); Cursor C = datab.returndata(); if
				 * (C.moveToFirst()) { do {
				 * displ1.setText(C.getString(0).toString());
				 * displ2.setText(C.getString(1).toString()); } while
				 * (C.moveToNext()); }
				 */
				// datab.close();
				Intent intent = new Intent(v.getContext(), TextMessage.class);
				startActivityForResult(intent, 0);
				// TODO Auto-generated method stub
				/*
				 * datab = new database(getBaseContext()); datab.open(); long id
				 * = datab.insertdata(begin2, end2);
				 * Toast.makeText(getBaseContext(), "inserted",
				 * Toast.LENGTH_LONG) .show(); datab.close();
				 */
			}
		});
	}

	private void saveeventdata3() {
		text3 = (Button) findViewById(R.id.button3);
		text3.setOnClickListener(new View.OnClickListener() {

			// Calendar beginTime = Calendar.getInstance().set(2012, 0, 19, 7,
			// 30);

			@Override
			public void onClick(View v) {
				prf = getSharedPreferences("event_details", MODE_PRIVATE);
				SharedPreferences.Editor edit = prf.edit();
				edit.putString("key_begin", begin3);
				edit.putString("key_end", end3);
				edit.commit();
				Toast.makeText(getBaseContext(), "inserted", Toast.LENGTH_LONG)
						.show();
				Intent intent = new Intent(v.getContext(), TextMessage.class);
				startActivityForResult(intent, 0);
			}
		});
	}

	private MyCalendar m_calendars[];
	private String m_selectedCalendarId = "0";

	private void getCalendars() {
		String[] l_projection = new String[] { "_id", "calendar_displayName" };
		Uri l_calendars;
		if (Build.VERSION.SDK_INT >= 8) {
			l_calendars = Uri.parse("content://com.android.calendar/calendars");
		} else {
			l_calendars = Uri.parse("content://calendar/calendars");
		}

		Cursor l_managedCursor = this.managedQuery(l_calendars, l_projection,
				null, null, null);
		if (l_managedCursor.getCount() == 0) {
			
			m_cal_button=(ImageButton) findViewById(R.id.m_cal_button);
			m_cal_button.setVisibility(View.VISIBLE);
			img = (ImageView) findViewById(R.id.imageView1);
			img.setImageResource(R.drawable.nocalendar);
			img.setVisibility(View.VISIBLE);

		} else if (l_managedCursor.getCount() != 0) {
			m_spinner_calender = (Spinner) this
					.findViewById(R.id.spinner_calendar);
			m_button_getEvents = (Button) findViewById(R.id.button_get_events);
			m_spinner_calender.setVisibility(View.VISIBLE);
			m_button_getEvents.setVisibility(View.VISIBLE);
			// all calendars
			/*
			 * if (l_managedCursor.getCount() == 0)
			 * displ2.setVisibility(View.VISIBLE);
			 * displ2.setText("no calendar available"); else if
			 * (l_managedCursor.getCount() != 0)
			 * 
			 * {
			 */
			// Cursor l_managedCursor = this.managedQuery(l_calendars,
			// l_projection, "selected=1", null, null); //active calendars
			if (l_managedCursor.moveToFirst()) {
				m_calendars = new MyCalendar[l_managedCursor.getCount()];
				String l_calName;
				String l_calId;
				int l_cnt = 0;
				int l_nameCol = l_managedCursor.getColumnIndex(l_projection[1]);
				int l_idCol = l_managedCursor.getColumnIndex(l_projection[0]);
				do {
					l_calName = l_managedCursor.getString(l_nameCol);
					l_calId = l_managedCursor.getString(l_idCol);
					m_calendars[l_cnt] = new MyCalendar(l_calName, l_calId);
					++l_cnt;
				} while (l_managedCursor.moveToNext());
			}
			populateCalendarSpinner();
		}

	}

	private void getLastThreeEvents() {
		Uri l_eventUri;
		if (Build.VERSION.SDK_INT >= 8) {

			l_eventUri = Uri.parse("content://com.android.calendar/events");

		} else {

			l_eventUri = Uri.parse("content://calendar/events");

		}

		Uri.Builder builder = Uri.parse(
				"content://com.android.calendar/instances/when").buildUpon();
		long now = new Date().getTime();
		// create the time span based on the inputs
		ContentUris.appendId(builder, now - (DateUtils.DAY_IN_MILLIS * 1)
				- (DateUtils.HOUR_IN_MILLIS * 1));
		ContentUris.appendId(builder, now + (DateUtils.DAY_IN_MILLIS * 1)
				+ (DateUtils.HOUR_IN_MILLIS * 1));

		String[] l_projection = new String[] { "title", "dtstart", "dtend" };
		Cursor l_managedCursor = this.managedQuery(builder.build(),
				l_projection, "calendar_id=" + m_selectedCalendarId, null,
				"dtstart ASC, dtend DESC");
		// Cursor l_managedCursor = this.managedQuery(l_eventUri, l_projection,
		// null, null, null);
		if (l_managedCursor.getCount() == 0) {
			displ2.setVisibility(View.VISIBLE);
			displ2.setText("No Entry found");

		}
		if (l_managedCursor.moveToFirst()) {
			int l_cnt = 0;
			String l_title;
			String l_begin;
			String l_end;
			StringBuilder l_displayText = new StringBuilder();
			int l_colTitle = l_managedCursor.getColumnIndex(l_projection[0]);
			int l_colBegin = l_managedCursor.getColumnIndex(l_projection[1]);
			int l_colEnd = l_managedCursor.getColumnIndex(l_projection[2]);
			/*
			 * if(l_managedCursor.getCount()==0); {
			 * 
			 * 
			 * }
			 */
			do {
				l_title = l_managedCursor.getString(l_colTitle);
				l_begin = getDateTimeStr(l_managedCursor.getString(l_colBegin));
				l_end = getDateTimeStr(l_managedCursor.getString(l_colEnd));
				String res = l_title + "\n" + l_begin + "\n" + l_end + "";
				// l_displayText.append(l_title + "\n" + l_begin + "\n" + l_end
				// + "");
				if (l_cnt == 0) {

					text1.setVisibility(View.VISIBLE);
					text1.setText(res.toString());
					begin1 = l_begin;
					end1 = l_end;
				} else if (l_cnt == 1) {
					text2.setVisibility(View.VISIBLE);
					text2.setText(res.toString());
					begin2 = l_begin;
					end2 = l_end;
				} else if (l_cnt == 2) {
					text3.setVisibility(View.VISIBLE);
					text3.setText(res.toString());
					begin3 = l_begin;
					end3 = l_end;
				}
				++l_cnt;
			} while (l_managedCursor.moveToNext() && l_cnt < 3);
			// m_button_show = (Button) findViewById(R.id.button1);
			// m_button_show.setText(l_displayText.toString());
			// m_text_event.setText(l_displayText.toString());
		}
	}

	private static final String DATE_TIME_FORMAT = "yyyy MMM dd, HH:mm:ss";

	public static String getDateTimeStr(int p_delay_min) {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_FORMAT);
		if (p_delay_min == 0) {
			return sdf.format(cal.getTime());
		} else {
			Date l_time = cal.getTime();
			l_time.setMinutes(l_time.getMinutes() + p_delay_min);
			return sdf.format(l_time);
		}
	}

	public static String getDateTimeStr(String p_time_in_millis) {
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_FORMAT);
		Date l_time = new Date(Long.parseLong(p_time_in_millis));
		return sdf.format(l_time);
	}

}
