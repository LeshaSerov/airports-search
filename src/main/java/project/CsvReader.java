package project;

import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
public class CsvReader {

    static public List<String> readDefinedStrings(String nameFile, SortedMap<String, Long> longSortedMap) {

        //TODO переделать добавление в массив
        List<String> resultList = new ArrayList<>();
        try (RandomAccessFile raf = new RandomAccessFile(nameFile.toString(), "r")) {
            for (Map.Entry<String, Long> entry : longSortedMap.entrySet()) {
                raf.seek(entry.getValue());
                resultList.add(raf.readLine());
            }

            //TODO Переделать catch
        } catch (Exception e) {
            e.printStackTrace();
        }


        return resultList;
    }
}
