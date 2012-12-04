package com.synaptik.jwow.dbc;

public class DBCField {
	public String fieldName;
	public Class fieldType;
	public int fieldSize;
	
	public DBCField(String fieldName) {
		this(fieldName, Integer.class, 1);
	}
	
	public DBCField(String fieldName, Class fieldType) {
		this(fieldName, fieldType, 1);
	}
	public DBCField(String fieldName, Class fieldType, int size) {
		this.fieldName = fieldName;
		this.fieldType = fieldType;
		this.fieldSize = size;
	}
}
