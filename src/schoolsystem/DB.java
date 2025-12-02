/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package schoolsystem;

import java.util.*;
import java.io.*;

/**
 *
 * @author Mika
 */
public class DB {

    Map<String, ArrayList<String>> info = new HashMap<>();
    public int idCounter = 1;

    public void saveToFile(String fileName) {
        try (PrintWriter w = new PrintWriter(new FileWriter(fileName))) {
            w.println("COUNTER," + idCounter);
            for (String key : info.keySet()) {
                ArrayList<String> details = info.get(key);
                w.println(key + "," + String.join(",", details));
            }
        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }

    public void loadFromFile(String fileName) {
        File file = new File(fileName);
        if (!file.exists()) {
            try {
                file.createNewFile();
                System.out.println("File Created");
            } catch (IOException e) {
                System.err.println("Error Creating file");
                return;
            }
        }
        info.clear();
        try (BufferedReader r = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = r.readLine()) != null) {
                if (line.startsWith("COUNTER,")) {
                    String[] parts = line.split(",");
                    if (parts.length > 1) {
                        try {
                            idCounter = Integer.parseInt(parts[1]);
                        } catch (NumberFormatException e) {
                            System.out.println("Error loading counter, reseting to 1.");
                            idCounter = 1;
                        }
                    }
                } else {
                    if (line.trim().isEmpty()) {
                        continue;
                    }
                    String[] parts = line.split(",");
                    if (parts.length < 1) {
                        continue;
                    }
                    String key = parts[0];
                    ArrayList<String> details = new ArrayList<>(Arrays.asList(parts).subList(1, parts.length));
                    info.put(key, details);
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading data: " + e.getMessage());
            idCounter = 1;
        }
    }
}
