package project.parser;

import lombok.extern.slf4j.Slf4j;
import project.domain.Airport;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс AirportStringParser отвечает за парсинг строки, представляющей данные аэропорта, в объект типа Airport.
 */
@Slf4j
public class AirportStringParser {
    /**
     * Метод для парсинга строки, представляющей данные аэропорта, в объект Airport.
     *
     * @param stringAirport строка с данными аэропорта, разделенными запятыми
     * @return Airport с заполненными данными или null, если произошла ошибка парсинга
     */
    public static Airport parse(String stringAirport) {
        Airport airport = new Airport();
        try {
            stringAirport = stringAirport.replace("\\N", "");
            String[] strings = splitSeparatorAndRemovingQuotes(',', stringAirport);

            airport.setName(strings[1]);
            if (!strings[2].equals(""))
                airport.setColumn3(strings[2]);
            if (!strings[3].equals(""))
                airport.setColumn4(strings[3]);
            if (!strings[4].equals(""))
                airport.setColumn5(strings[4]);
            if (!strings[5].equals(""))
                airport.setColumn6(strings[5]);

            DecimalFormat decimalFormat = new DecimalFormat();
            airport.setIndex(decimalFormat.parse(strings[0]).intValue());
            if (!strings[6].equals(""))
                airport.setColumn7(decimalFormat.parse(strings[6]).doubleValue());
            if (!strings[7].equals(""))
                airport.setColumn8(decimalFormat.parse(strings[7]).doubleValue());
            if (!strings[8].equals(""))
                airport.setColumn9(decimalFormat.parse(strings[8]).intValue());
            if (!strings[9].equals(""))
                airport.setColumn10(decimalFormat.parse(strings[9]).doubleValue());

            if (!strings[10].equals(""))
                airport.setColumn11(strings[10]);
            if (!strings[11].equals(""))
                airport.setColumn12(strings[11]);
            if (!strings[12].equals(""))
                airport.setColumn13(strings[12]);
            if (!strings[13].equals(""))
                airport.setColumn14(strings[13]);

        } catch (NumberFormatException | IndexOutOfBoundsException | ParseException e) {
            log.atError().log("Ошибка парсинга строки: \"" + stringAirport + "\"");
        }
        return airport;
    }

    /**
     * Метод для разделения строки на элементы, используя заданный разделитель, и удаления кавычек внутри элементов.
     *
     * @param separator разделитель, используемый для разделения элементов
     * @param input     входная строка, которую необходимо разделить
     * @return массив строк, представляющих элементы строки после разделения и удаления кавычек
     */
    public static String[] splitSeparatorAndRemovingQuotes(char separator, String input) {
        List<String> result = new ArrayList<>();
        boolean insideQuotes = false;
        StringBuilder sb = new StringBuilder("");

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c == separator) {
                if (insideQuotes) {
                    // Если внутри кавычек, то добавляем запятую в текущий элемент
                    sb.append(c);
                } else {
                    // Если не внутри кавычек, то заканчиваем текущий элемент и добавляем в результат
                    result.add(sb.toString());
                    sb.setLength(0); // Очищаем StringBuilder
                }
            } else if (c == '"') {
                // Меняем флаг insideQuotes на противоположное значение при встрече кавычки
                insideQuotes = !insideQuotes;
            } else {
                // Добавляем символ в текущий элемент
                sb.append(c);
            }
        }

        // Добавляем последний элемент после завершения цикла
        result.add(sb.toString());

        // Преобразуем список в массив строк и возвращаем результат
        return result.toArray(new String[result.size()]);
    }

}
