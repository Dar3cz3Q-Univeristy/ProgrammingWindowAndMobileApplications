package org.example.lab4b.FileSaver;

import org.example.lab4b.Model.TeacherClass;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class GroupToCSV {

    public static void saveToCSV(String filepath, List<TeacherClass> groups) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filepath))) {
            writer.write("id,name,capacity");
            writer.newLine();

            for (TeacherClass group : groups) {
                String row = group.getId() + "," + group.getName() + "," + group.getCapacity();
                writer.write(row);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
