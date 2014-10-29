package com.example.cal;

public class Calendarclass {
	
	public String name;
	public String id;

	public Calendarclass(String m_name, String m_id) {
		name = m_name;
		id = m_id;
	}

	@Override
	public String toString() {
		return name;
	}
}
