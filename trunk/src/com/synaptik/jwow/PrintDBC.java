package com.synaptik.jwow;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;

import com.synaptik.jwow.dbc.DBC;

public class PrintDBC {
	private static final String PATH = "C:\\devtools\\mpq\\Work\\DBFilesClient\\soundentries.dbc";
	
	public static void main(String[] args) throws Exception {
		DBC dbc = DBC.read(new File(PATH));
		
		System.out.println("DBC File...: " + PATH);
		System.out.println("Entries....: " + dbc.size());
		System.out.println("Num fields.: " + dbc.getNumFields());
		System.out.println("Record size: " + dbc.getRecordSize());
		
		printRecords(dbc);
		
//		printSortedAreaDB(dbc);
	}
	
	private static void printRecords(DBC dbc) {
		List<Map<String,Object>> data = dbc.map();
		System.out.println("Records:");
		for (Map<String, Object> record : data) {
			System.out.print("\t");
			for (String key : record.keySet()) {
				System.out.print(key + "=" + record.get(key) + ",");
			}
			System.out.println("");
		}
	}

	// Relies on data from WorldMapArea.dbc
	private static void printSortedAreaDB(DBC dbc) {
		SortedMap<Integer, String> zones = new TreeMap<Integer,String>();
		List<Map<String,Object>> data = dbc.map();
		for (Map<String, Object> record : data) {
			zones.put((Integer)record.get("areaTableID"), (String)record.get("name"));
		}
		
		Iterator<Entry<Integer,String>> i = zones.entrySet().iterator();
		while (i.hasNext()) {
			Entry<Integer,String> e = i.next();
			System.out.println("\t[" + e.getKey() + "] = BZ[\"" + e.getValue() + "\"],");
		}
	}
}
