package org.example;

import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files

public class ReadFile {
    public static int readFromFile() {
        String data = "";
        try {
            File myObj = new File("D:\\AN4\\DS\\Demo\\-DS2022_30444_Pasca_EduardMarian_Assignment_2\\spring-demo-master\\config-file.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                data = myReader.nextLine();

            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return Integer.parseInt(data);
    }
}