package xyz.vexsystems.RBW.Connection;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


// THIS AUTH HAS BEEN REMOVED, THIS WAS THIS THING WAS PRIVATE...
// SHIT AUTH ANYWAYS LOL, I THINK THIS IS BROKEN LMFAOO
public class Auth {

    private static String latestVersion; 

    public static boolean authenticate(String hardwareId, String version) {
        try {
            String backendUrl = "NO BACKEND" + hardwareId + "&version=" + version;
            URL url = new URL(backendUrl);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                // auth successful
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = in.readLine()) != null) {
                    response.append(line);
                }
                in.close();

                JsonParser parser = new JsonParser();
                JsonObject jsonResponse = parser.parse(response.toString()).getAsJsonObject();

                latestVersion = jsonResponse.get("latest_version").getAsString();

                return "accepted".equals(jsonResponse.get("status").getAsString());
            } else {
                // auth failed
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static String getLatestVersion() {
        return latestVersion;
    }
}
