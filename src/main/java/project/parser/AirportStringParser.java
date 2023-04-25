package project.parser;

import project.domain.Airport;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.*;

public class AirportStringParser {

    public static Airport parse(String stringAirport) {

        stringAirport = stringAirport.replace("\"", "");
        stringAirport = stringAirport.replace("\\N", "");
        String[] strings = splitWithQuotesAndRemoveQuotes(stringAirport, ",");

        int index = Integer.parseInt(strings[0]);
        String title = strings[1];

        Airport airport = new Airport(index, title);
        if (!strings[2].equals(""))
            airport.setColumn3(strings[2]);
        if (!strings[3].equals(""))
            airport.setColumn4(strings[3]);
        if (!strings[4].equals(""))
            airport.setColumn5(strings[4]);
        if (!strings[5].equals(""))
            airport.setColumn6(strings[5]);

        try {
            DecimalFormat decimalFormat = new DecimalFormat();
            if (!strings[6].equals(""))
                airport.setColumn7(decimalFormat.parse(strings[6]).doubleValue());
            if (!strings[7].equals(""))
                airport.setColumn8(decimalFormat.parse(strings[7]).doubleValue());
            if (!strings[8].equals(""))
                airport.setColumn9(decimalFormat.parse(strings[8]).intValue());
            if (!strings[9].equals(""))
                airport.setColumn10(decimalFormat.parse(strings[9]).doubleValue());
        }
        catch (Exception e)
        {

        }

        if (!strings[10].equals(""))
            airport.setColumn11(strings[10]);
        if (!strings[11].equals(""))
            airport.setColumn12(strings[11]);
        if (!strings[12].equals(""))
            airport.setColumn13(strings[12]);
        if (!strings[13].equals(""))
            airport.setColumn14(strings[13]);

        return airport;
    }

    public static String[] splitWithQuotesAndRemoveQuotes(String input, String separator) {
        List<String> result = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        boolean withinQuotes = false;

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);

            if (input.startsWith(separator, i) && !withinQuotes) {
                if (sb.length() > 0) {
                    result.add(sb.toString().trim());
                    sb.setLength(0);
                }
                i += separator.length() - 1;
            } else if (c == '\"') {
                withinQuotes = !withinQuotes;
            } else {
                sb.append(c);
            }
        }

        result.add(sb.toString().trim());

        String[] arrayResult = result.toArray(new String[result.size()]);
        for (int i = 0; i < arrayResult.length; i++) {
            String s = arrayResult[i];
            if (s.length() > 0 && s.charAt(0) == '\"' && s.charAt(s.length() - 1) == '\"') {
                arrayResult[i] = s.substring(1, s.length() - 1);
            }
        }

        return arrayResult;
    }


//    public static String[] splitWithQuotesAndRemoveQuotes(String input, char separator) {
//        List<String> result = new ArrayList<>();
//        StringBuilder sb = new StringBuilder();
//        boolean withinQuotes = false;
//
//        for (int i = 0; i < input.length(); i++) {
//            char c = input.charAt(i);
//
//            if (c == separator && !withinQuotes) {
//                result.add(sb.toString().trim());
//                sb.setLength(0);
//            } else if (c == '\"') {
//                withinQuotes = !withinQuotes;
//            } else {
//                sb.append(c);
//            }
//        }
//
//        result.add(sb.toString().trim());
//
//        String[] arrayResult = result.toArray(new String[result.size()]);
//        for (int i = 0; i < arrayResult.length; i++) {
//            arrayResult[i] = arrayResult[i].replaceAll("^\"|\"$", ""); // Удаление кавычек
//        }
//
//        return arrayResult;
//    }
}
