package com.exclnetworks.MCYTIntegration.utils;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.exclnetworks.MCYTIntegration.Data;

public class YoutubeAPIUtils {
	
	private static final String getLatestVideoRequest = "https://www.googleapis.com/youtube/v3/search?part=snippet&channelId=%channel_id%&maxResults=1&order=date&type=video&key=%api_key%";
	
	//Returns video id if there is a new one
	public static String isNewVideo(String channel_id){
		try {
			JSONObject items = (JSONObject) Utils.parser.parse(Utils.getJson(getLatestVideoRequest.replaceAll("%channel_id%", channel_id).replaceAll("%api_key%", Data.yt_api_key)));

			JSONArray array = (JSONArray) items.get("items");
			JSONObject video = (JSONObject) array.get(0);
			JSONObject snippet = (JSONObject) video.get("snippet");

			String publishedAt = (String) snippet.get("publishedAt");

			final ZonedDateTime dateTime = ZonedDateTime.parse(publishedAt, DateTimeFormatter.ISO_DATE_TIME);
			final ZonedDateTime now = ZonedDateTime.now(ZoneId.of("Z"));

			if (Utils.dateTimeDifference(dateTime, now, ChronoUnit.SECONDS) <= 60){
				JSONObject id = (JSONObject) video.get("id");
				return (String) id.get("videoId");
			}
		} catch (Exception e) { }
		
		return null;
	}
	
}
