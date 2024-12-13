package org.example.lab4b.FileSaver;

import org.example.lab4b.Model.Rate;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class RateToCSV {

    public static void saveToCSV(String filepath, List<Rate> rates) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filepath))) {
            writer.write("id,comment,date,rate,group_id");
            writer.newLine();

            for (Rate rate : rates) {
                String comment = rate.getComment() != null ? rate.getComment() : "";
                String row = rate.getId() + "," + comment + "," + rate.getDate() + "," + rate.getRate() + "," + rate.getGroup().getId();
                writer.write(row);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
