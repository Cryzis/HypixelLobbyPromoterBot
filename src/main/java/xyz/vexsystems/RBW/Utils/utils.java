package xyz.vexsystems.RBW.Utils;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class utils {
    public static void sendChatMessage(String message) {
        String formattedMessage = String.format("\u00A78[\u00A7fRBW\u00A78]\u00A7f %s", replaceColorSymbols(message));
        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(formattedMessage));
    }

    private static String replaceColorSymbols(String message) {
        return message.replaceAll("&", "\u00A7");
    }

    // WTF IS THIS DOING HERE.

    public static int redColorsDraw(int yOffset, int yTotal, long speed) {
        float verticalFactor = (float) yOffset / yTotal;

        float time = (float) (System.currentTimeMillis() % speed) / speed;

        Color startColor = new Color(182, 9, 9);
        Color endColor = new Color(173, 76, 76);

        int red = (int) (startColor.getRed() + (endColor.getRed() - startColor.getRed()) * (0.5f + 0.5f * (float) Math.sin(2 * Math.PI * time)));
        int green = (int) (startColor.getGreen() + (endColor.getGreen() - startColor.getGreen()) * (0.5f + 0.5f * (float) Math.sin(2 * Math.PI * time)));
        int blue = (int) (startColor.getBlue() + (endColor.getBlue() - startColor.getBlue()) * (0.5f + 0.5f * (float) Math.sin(2 * Math.PI * time)));

        int interpolatedColor = new Color(red, green, blue).getRGB();

        return interpolatedColor;
    }




    //USELESS CODE, WAS FOR MY BACKEND. CAN BE USED FOR HYPIXEL API IMPLEMENTATION.

    public static String sendHttpRequest(String apiUrl) throws IOException {
        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        StringBuilder response = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String line;
            while ((line = in.readLine()) != null) {
                response.append(line);
            }
        } finally {
            connection.disconnect();
        }

        return response.toString();
    }

}
