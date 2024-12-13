package org.example.lab4b.FileSaver;

import org.example.lab4b.Model.Teacher;
import org.example.lab4b.Model.TeacherClass;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class TeacherToCSV {

    public static void saveToCSV(String filepath, List<Teacher> teachers) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filepath))) {
            writer.write("id,name,surname,salary,condition,birth_year,group_id");
            writer.newLine();

            for (Teacher teacher : teachers) {
                String row = teacher.getId() + "," + teacher.getName() + "," + teacher.getSurname() + "," + teacher.getSalary() + "," + teacher.getCondition() + "," + teacher.getYearOfBirth() + "," + teacher.getGroup().getId();
                writer.write(row);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
