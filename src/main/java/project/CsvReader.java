package project;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class CsvReader {

    static public List<String> readDefinedStrings(String nameFile, List<Long> airportIndexList){
        List<String> resultList = new ArrayList<>();
        try (RandomAccessFile raf = new RandomAccessFile(nameFile, "r");
             BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(raf.getFD())))) {
            for (Long entry : airportIndexList) {
                raf.seek(entry);
                String line = br.readLine();
                resultList.add(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultList;
    }
}