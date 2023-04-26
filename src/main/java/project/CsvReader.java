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
     * @param nameFile               Имя CSV-файла.
     * @param airportDataForParsings Список объектов AirportDataForParsing, содержащих информацию о расположении аэропортов в файле.
     * @return Список строк, прочитанных из файла.
     */
    static public List<String> readDefinedStrings(String nameFile, List<AirportSearchTree.AirportDataForParsing> airportDataForParsings) {
        List<String> resultList = new ArrayList<>();
        try (RandomAccessFile raf = new RandomAccessFile(nameFile, "r")) {
            for (AirportSearchTree.AirportDataForParsing entry : airportDataForParsings) {
                raf.seek(entry.getNumberOfBytesUpTo());
                byte[] buffer = new byte[entry.getLength()]; // размер буфера
                int bytesRead = raf.read(buffer); // чтение данных в буфер
                String line = new String(buffer, 0, bytesRead); // преобразование буфера в строку
                resultList.add(line);
            }
        } catch (IOException | StringIndexOutOfBoundsException e) {
            log.atError().log("Ошибка чтения файла: " + nameFile);
        }
        return resultList;
    }
}