package com.infonuascape.osrshelper.network;

import android.net.Uri;
import android.text.TextUtils;

import com.infonuascape.osrshelper.enums.SkillType;
import com.infonuascape.osrshelper.models.HTTPResult;
import com.infonuascape.osrshelper.models.Skill;
import com.infonuascape.osrshelper.models.StatusCode;
import com.infonuascape.osrshelper.models.players.Delta;
import com.infonuascape.osrshelper.models.players.PlayerSkills;
import com.infonuascape.osrshelper.models.players.SkillDiff;
import com.infonuascape.osrshelper.utils.exceptions.APIError;
import com.infonuascape.osrshelper.utils.exceptions.PlayerNotFoundException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.TimeZone;

public class DataPointsApi {
    private final static String API_URL = NetworkStack.ENDPOINT + "/wom/datapointsdelta/%s";
    private final static String KEY_DATAPOINTS = "datapoints";
    private final static String KEY_BEFORE = "before";
    private final static String KEY_AFTER = "after";
    private final static String KEY_DELTAS = "deltas";
    private final static String KEY_EXPERIENCE = "experience";
    private final static String KEY_RANK = "rank";

    public static ArrayList<Delta> fetch(final String username) throws PlayerNotFoundException, APIError {
        String url = String.format(API_URL, Uri.encode(username));
        HTTPResult httpResult = NetworkStack.getInstance().performGetRequest(url);

        if (httpResult.statusCode == StatusCode.NOT_FOUND) {
            throw new PlayerNotFoundException(username);
        } else if(httpResult.statusCode != StatusCode.FOUND) {
            throw new APIError("Unexpected response from the server.");
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));

        ArrayList<Delta> deltas = new ArrayList<>();
        if(httpResult.statusCode == StatusCode.FOUND) {
            try {
                JSONArray jsonArray = new JSONArray(httpResult.output);
                for(int i=0; i<jsonArray.length(); i++) {
                    JSONObject deltaJson = jsonArray.getJSONObject(i);
                    Delta delta = new Delta();
                    Iterator<String> keys = deltaJson.keys();
                    while(keys.hasNext()) {
                        String key = keys.next();
                        if (TextUtils.equals(key, KEY_BEFORE)) {
                            delta.timestamp = sdf.parse(deltaJson.getString(key)).getTime();
                        } else if (TextUtils.equals(key, KEY_AFTER)) {
                            delta.timestampRecent = sdf.parse(deltaJson.getString(key)).getTime();
                        } else {
                            final PlayerSkills ps = new PlayerSkills();
                            for (Skill s : ps.skillList) {
                                if (s.getSkillType().equals(key)) {
                                    JSONObject skillNameJson = deltaJson.getJSONObject(key);
                                    SkillDiff skillDiff = new SkillDiff();
                                    skillDiff.skillType = s.getSkillType();
                                    skillDiff.experience = skillNameJson.getLong(KEY_EXPERIENCE);
                                    skillDiff.rank = skillNameJson.getLong(KEY_RANK);
                                    delta.skillDiffs.add(skillDiff);
                                    break;
                                }
                            }
                        }
                    }

                    if (!delta.skillDiffs.isEmpty()) {
                        deltas.add(delta);
                    }
                }
            } catch (JSONException | ParseException | NullPointerException e) {
                e.printStackTrace();
            }
        }

        //Sort desc
        Collections.sort(deltas, (delta, t1) -> {
            if(delta.timestampRecent > t1.timestampRecent) {
                return -1;
            } else if(delta.timestampRecent == t1.timestampRecent) {
                return 0;
            }

            return 1;
        });

        return deltas;
    }
}