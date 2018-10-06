package com.exclnetworks.MCYTIntegration.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;

import org.json.simple.parser.JSONParser;

public class Utils {
	
	static JSONParser parser = new JSONParser();
	
	public static long dateTimeDifference(Temporal d1, Temporal d2, ChronoUnit unit){
	    return unit.between(d1, d2);
	}
	
	public static String getJson(String URL){
		BufferedReader reader = null;
		try {
			URL url = new URL(URL);
			reader = new BufferedReader(new InputStreamReader(url.openStream()));
			StringBuffer buffer = new StringBuffer();
			int read;
			char[] chars = new char[1024];
			while ((read = reader.read(chars)) != -1) buffer.append(chars, 0, read);

			return buffer.toString();
		} catch(Exception e){
			e.printStackTrace();
			return null;
		}finally {
			if (reader != null)
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}
	
}
