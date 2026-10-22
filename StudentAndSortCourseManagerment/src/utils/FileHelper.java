package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public abstract class FileHelper<T> {
    private ArrayList<T> dataList;

    public FileHelper(ArrayList<T> dataList) {
        this.dataList = dataList;
    }

    public boolean loadFromFile(String url) {
        File f = new File(url);
        if (!f.exists()) {
            return false;
        }
        try {
            FileReader flr = new FileReader(f);
            BufferedReader reader = new BufferedReader(flr);
            String line = reader.readLine();
            while (line != null) {
                if (!line.trim().isEmpty()) {
                    T t = handleLine(line);
                    if (t != null) {
                        dataList.add(t);
                    }
                }
                line = reader.readLine();
            }
            reader.close();
            return true;
        } catch (Exception e) {
            System.out.println("LoadFromFile Error: " + e.getMessage());
            return false;
        }
    }

    public abstract T handleLine(String line);

    public boolean saveToFile(String url) {
        File f = new File(url);
        try {
            FileOutputStream fos = new FileOutputStream(f);
            OutputStreamWriter writer = new OutputStreamWriter(fos);
            for (T t : dataList) {
                writer.write(t.toString());
                writer.write("\n");
            }
            writer.close();
            return true;
        } catch (Exception e) {
            System.out.println("SaveToFile Error: " + e.getMessage());
            return false;
        }
    }
}
