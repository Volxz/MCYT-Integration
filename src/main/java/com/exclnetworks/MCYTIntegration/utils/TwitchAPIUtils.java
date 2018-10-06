package com.exclnetworks.MCYTIntegration.utils;

import static com.exclnetworks.MCYTIntegration.utils.Utils.dateTimeDifference;
import static com.exclnetworks.MCYTIntegration.utils.Utils.getJson;
import static com.exclnetworks.MCYTIntegration.utils.Utils.parser;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import org.json.simple.JSONObject;

import com.exclnetworks.MCYTIntegration.Data;

public class TwitchAPIUtils {
	
	private static final String getLatestStreamRequest = "https://api.twitch.tv/kraken/streams/%user_name%?client_id=%client_id_key%";
	
	//Returns twitcher's channel link if they are streaming.
	public static String isNewStream(String user_name){
		try {
			JSONObject items = (JSONObject) parser.parse(getJson(getLatestStreamRequest.replaceAll("%user_name%", user_name).replaceAll("%client_id_key%", Data.twitch_api_key)));

			JSONObject stream = (JSONObject) items.get("stream");
			
			String startedAt = (String) stream.get("created_at");

			final ZonedDateTime dateTime = ZonedDateTime.parse(startedAt, DateTimeFormatter.ISO_DATE_TIME);
			final ZonedDateTime now = ZonedDateTime.now(ZoneId.of("Z"));

			if (dateTimeDifference(dateTime, now, ChronoUnit.SECONDS) <= 300){
				return "https://www.twitch.tv/" + user_name;
			}
		} catch (Exception e) { }
		
		return null;
	}
	
}
