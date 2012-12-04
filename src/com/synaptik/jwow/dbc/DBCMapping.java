package com.synaptik.jwow.dbc;

import java.util.ArrayList;
import java.util.List;

/**
 * Format is like this:
 * 	{FIELD_NAME<:FIELD_CLASS[FIELD_LENGTH]>}
 * 
 * Example:
 * 	{id} -- will store an Integer (4-bytes) into the "id" field
 *  {id:String} -- will store a String (from the DBC string table) into the "id" field
 *  {id:Float} -- Will store a Double (8-bytes) into the "id" field
 *  {id:Float[3]} -- Will store 3 Doubles (24-bytes total) into the "id" field
 *  
 * To specify multiple fields, just append them one after another. Example:
 * 	{id}{name:String}
 *  
 * @author dan
 *
 */
public final class DBCMapping {
	private String format;
	private List<DBCField> fields;
	
	public DBCMapping(String format) {
		this.format = format;
		this.fields = new ArrayList<DBCField>();
		readFields(format);
	}
	
	private void readFields(String format) {
		String[] tokens = format.split("}");
		for (String token : tokens) {
			String[] fieldTokens = token.split(":");
			String fieldName = fieldTokens[0].substring(1);
			DBCField field = null;
			if (fieldTokens.length > 1) {
				String fieldType = fieldTokens[1];
				try {
					String[] temp = fieldType.split("\\[");
					fieldType = temp[0];
					if (temp.length == 2) {
						int fieldCount = Integer.parseInt(temp[1].substring(0,temp.length-1));
						field = new DBCField(fieldName, Class.forName("java.lang." + fieldType), fieldCount);
					} else {
						field = new DBCField(fieldName, Class.forName("java.lang." + fieldType));
					}
				} catch (Exception ex) {
					System.err.println(ex.getMessage());
				}
			} else {
				field = new DBCField(fieldName);
			}
			
			if (field != null) {
				fields.add(field);
			}
		}
	}
	
	public List<DBCField> getFields() {
		return fields;
	}
	
	public String getFormat() {
		return format;
	}
}
