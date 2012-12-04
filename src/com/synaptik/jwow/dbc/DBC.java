package com.synaptik.jwow.dbc;

import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.synaptik.jwow.ByteBufferUtil;
import com.synaptik.jwow.JWoWException;

/**
Reference: http://madx.dk/wowdev/wiki/index.php?title=DBC

@author Dan Watling <dan@synaptik.com>
 */
public class DBC {

	byte[] signature = new byte[4];
	int nRecords;
	int nFields;
	int lRecord;
	int lStringBlock;
	byte[] data;
	byte[] strings;
	String dbcType;
	
	public static DBC read(File f) throws Exception {
		FileInputStream fis = null;
		DBC result = null;
		try {
			ByteBuffer bb = ByteBuffer.allocate((int)f.length());
			fis = new FileInputStream(f);
			fis.getChannel().read(bb);
			bb.rewind();
			
			result = read(bb);
			
			result.dbcType = f.getName().toLowerCase();
		} finally {
			if (fis != null) {
				fis.close();
			}
		}
		return result;
	}
	
	public static DBC read(ByteBuffer bb) {
		bb.order(ByteOrder.LITTLE_ENDIAN);
		DBC result = new DBC();
		
		bb.get(result.signature);
		result.nRecords = bb.getInt();
		result.nFields = bb.getInt();
		result.lRecord = bb.getInt();
		result.lStringBlock = bb.getInt();
		
		result.data = new byte[result.nRecords * result.lRecord];
		bb.get(result.data);
		
		result.strings = new byte[result.lStringBlock];
		bb.get(result.strings);
		
		return result;
	}
	
	public List<Map<String,Object>> map() {
		List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
		
		for (int i = 0; i < nRecords; i ++) {
			DBCMapping mapping = DBCMappings.MAPPINGS.get(dbcType);
			if (mapping != null) {
				byte[] recordData = getRecord(i);
				HashMap<String,Object> entry = mapData(recordData, mapping);
				result.add(entry);
			} else {
				System.err.println("Mapping for '" + dbcType + "' not found. Please add it to DBCMappings.MAPPINGS and use http://www.pxr.dk/wowdev/wiki/index.php as a reference.");
				break;
			}
		}
		return result;
	}
	
	private HashMap<String,Object> mapData(byte[] bytes, DBCMapping mapping) {
		HashMap<String,Object> result = new HashMap<String,Object>();
		ByteBuffer bb = ByteBuffer.wrap(bytes);
		bb.order(ByteOrder.LITTLE_ENDIAN);	// format is in little endian, ensure the buffer is too
		for (DBCField field : mapping.getFields()) {
			List<Object> record = new ArrayList<Object>();
			try {
				for (int i = 0; i < field.fieldSize; i ++) {
					record.add(readField(bb, field.fieldType));
				}
				
				if (record.size() > 1) {
					result.put(field.fieldName, record);
				} else {
					result.put(field.fieldName, record.get(0));
				}
			} catch (Exception ex) {
				System.err.println("Field name: " + field.fieldName + " - " + ex.toString());
			}
		}
		
		return result;
	}
	
	private Object readField(ByteBuffer bb, Class fieldType) {
		Object data = null;
		if (fieldType.equals(Integer.class)) {
			data = Integer.valueOf(bb.getInt());
		} else if (fieldType.equals(String.class)) {
			int offset = Integer.valueOf(bb.getInt());
			data = getString(offset);
		} else if (fieldType.equals(Float.class)) {
			data = Float.valueOf(bb.getFloat());
		} else if (fieldType.equals(Double.class)) {
			data = Double.valueOf(bb.getDouble());
		} else {
			System.err.println("Data type " + data.getClass() + " is not yet supported.");
		}
		return data;
	}
	
	public int size() {
		return nRecords;
	}
	
	public int getRecordSize() {
		return lRecord;
	}
	
	public int getNumFields() {
		return nFields;
	}
	
	public int getStringBlockSize() {
		return lStringBlock;
	}

	public String getString(int offset) {
		boolean done = false;
		ByteBuffer bb = ByteBuffer.allocate(1024);
		while (!done) {
			byte ch = strings[offset++];
			if (ch == 0) {
				done = true;
			}
			bb.put(ch);
		}
		return new String(bb.array()).trim();
	}
	public byte[] getRecord(int index) {
		byte[] result = new byte[lRecord];
		
		System.arraycopy(data, index*lRecord, result, 0, lRecord);
		
		return result;
	}
	
	public int[] getRecordAsIntArray(int index) {
		byte[] data = getRecord(index);
		int fieldLength = lRecord / nFields;
		int[] result = new int[nFields];
		for (int i = 0; i < result.length; i ++) {
			int value = 0;
			int t1 = ByteBufferUtil.normalizeByte(data[i * fieldLength]);
			int t2 = ByteBufferUtil.normalizeByte(data[i * fieldLength +1]) << 8;
			int t3 = ByteBufferUtil.normalizeByte(data[i * fieldLength +2]) << 16;
			int t4 = ByteBufferUtil.normalizeByte(data[i * fieldLength +3]) << 24;
			value = t1 + t2 + t3 + t4;
			result[i] = value;
		}
		
		return result;
	}
	
	public int getInt(int record, int column) throws JWoWException {
		if (column > nFields) {
			throw new JWoWException("Column exceeds number of fields (column: " + column +", nFields: " + nFields+")");
		}
		int[] data = getRecordAsIntArray(record);
		
		return data[column];
	}
	
	public String toString() {
		return "signature: " + new String(signature) + 
			"; nRecords: " + nRecords +
			"; nFields: " + nFields + 
			"; lRecord: " + lRecord +
			"; lStringBlock: " + lStringBlock;
	}
}
