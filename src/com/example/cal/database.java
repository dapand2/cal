package com.example.cal;



import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database {

	public static final String start="starttime";
	public static final String end="endtime";
	public static final String msg="message";
	public static final String table_name="time";
	public static final String database_name="mydatabase";
	public static final int database_version=1;
	public static final String database_create_statement="create table time (starttime TEXT not null,endtime TEXT not null,message TEXT not null)";
	Databasehelp dbhelper;
	Context ctx;
	SQLiteDatabase db;
	public Database(Context ctx)
	{
		this.ctx = ctx;
		dbhelper=new Databasehelp(ctx);		
	}
	
	
	
	
	public static class Databasehelp extends SQLiteOpenHelper
	{

		public Databasehelp(Context ctx)
		{
			super(ctx,database_name,null,database_version);
			
		}
		
		
		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			try{
			db.execSQL(database_create_statement);
			}
			catch(SQLException e)
			{
				e.printStackTrace();
			}
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			db.execSQL("DROP TABLE IF EXISTS time");
			onCreate(db);
		}
	
	}
	
	public Database open() {
		db = dbhelper.getWritableDatabase();
		return this;
	}
	
	public void close() {
		dbhelper.close();

	}
	public long insertdata(String starttime,String endtime,String message) {
		ContentValues content = new ContentValues();
		content.put(start,starttime);
		content.put(end, endtime);
		content.put(msg, message);
		return db.insertOrThrow(table_name, null, content);
	}


	public Cursor returndata() {
		return db.query(table_name, new String[] { start,end,msg }, null, null, null,
				null, null);
	}
}
