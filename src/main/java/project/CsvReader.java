package project;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class CsvReader {
    /**
     * Читает строки из CSV-файла на основе предварительно определенных данных о расположении аэропортов в файле.
     *
     * @param fileName               Имя CSV-файла.
     * @param airportDataForParsingList Список объектов AirportDataForParsing, содержащих информацию о расположении аэропортов в файле.
     * @return Список строк, прочитанных из файла.
     */
    static public List<String> readDefinedStrings(String fileName, List<AirportSearchTree.AirportDataForParsing> airportDataForParsingList) {
        List<String> resultList = new ArrayList<>();
        try (RandomAccessFile raf = new RandomAccessFile(fileName, "r")) {
            for (AirportSearchTree.AirportDataForParsing entry : airportDataForParsingList) {
                raf.seek(entry.getNumberOfBytesUpTo());
                byte[] buffer = new byte[entry.getLength()]; // размер буфера
                int bytesRead = raf.read(buffer); // чтение данных в буфер
                String line = new String(buffer, 0, bytesRead); // преобразование буфера в строку
                resultList.add(line);
            }
        } catch (IOException| StringIndexOutOfBoundsException e) {
            log.atError().log("Ошибка чтения файла: ", e);
        }
        return resultList;
    }
}