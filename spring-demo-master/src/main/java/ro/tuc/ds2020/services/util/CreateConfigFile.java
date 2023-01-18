package ro.tuc.ds2020.services.util;

import java.io.File;  // Import the File class
import java.io.FileWriter;
import java.io.IOException;  // Import the IOException class to handle errors

public class CreateConfigFile {
    public static void createFile() {
        try {
            File myObj = new File("config-file.txt");
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static void writeToConfigFile(int deviceId) {
        try {
            FileWriter myWriter = new FileWriter("config-file.txt");
            myWriter.write(Integer.toString(deviceId));
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}