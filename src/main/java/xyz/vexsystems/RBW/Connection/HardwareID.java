package xyz.vexsystems.RBW.Connection;

import java.io.FileWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.util.Scanner;

public class HardwareID {


    // THIS AUTH HAS BEEN REMOVED, THIS WAS THIS THING WAS PRIVATE...
// SHIT AUTH ANYWAYS LOL, I THINK THIS IS BROKEN LMFAOO
    public static String getHardwareID() {
        try {
            String serialNumber = getHardDriveSerialNumber();

            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] digest = md.digest(serialNumber.getBytes(StandardCharsets.UTF_8));

            // Convert hash to hex
            StringBuilder hardwareIDBuilder = new StringBuilder();
            for (byte b : digest) {
                hardwareIDBuilder.append(String.format("%02X", b));
            }

            String hardwareID = hardwareIDBuilder.toString();

            // Save to file
            saveToFile(hardwareID);

            return hardwareID;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String getHardDriveSerialNumber() throws Exception {
        Process process = Runtime.getRuntime().exec("wmic diskdrive get serialnumber");

        try (Scanner scanner = new Scanner(process.getInputStream())) {
            while (scanner.hasNext()) {
                String serialNumber = scanner.next().trim();
                if (!serialNumber.isEmpty()) {
                    return serialNumber;
                }
            }
        }

        throw new RuntimeException("Unable to retrieve hard drive serial number");
    }

    private static void saveToFile(String hardwareID) {
        try {
            String configPath = "config/RBW/";
            Files.createDirectories(Paths.get(configPath));

            String filePath = configPath + "HWID.txt";

            try (FileWriter writer = new FileWriter(filePath)) {
                writer.write(hardwareID);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

