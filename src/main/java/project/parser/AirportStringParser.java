package project.parser;

import project.domain.Airport;

public class AirportStringParser {

    public static Airport parse(String stringAirport) {

        stringAirport = stringAirport.replace("\"", "");
        String[] strings = stringAirport.split(",");

        int index = Integer.parseInt(strings[0]);
        String title = strings[1];

        Airport.Builder builder = new Airport.Builder(index, title);
        if (!strings[2].startsWith("\\N")) {
            builder.colomn3(strings[2]);
        }
        if (!strings[3].startsWith("\\N")) {
            builder.colomn4(strings[3]);
        }
        if (!strings[4].startsWith("\\N")) {
            builder.colomn5(strings[4]);
        }
        if (!strings[5].startsWith("\\N")) {
            builder.colomn6(strings[5]);
        }
        if (!strings[6].startsWith("\\N")) {
            builder.colomn7(Double.parseDouble(strings[6]));
        }
        if (!strings[7].startsWith("\\N")) {
            builder.colomn8(Double.parseDouble(strings[7]));
        }
        if (!strings[8].startsWith("\\N")) {
            builder.colomn9(Integer.parseInt(strings[8]));
        }
        if (!strings[9].startsWith("\\N")) {
            builder.colomn10(Double.parseDouble(strings[9]));
        }
        if (!strings[10].startsWith("\\N")) {
            builder.colomn11(strings[10]);
        }
        if (!strings[11].startsWith("\\N")) {
            builder.colomn12(strings[11]);
        }
        if (!strings[12].startsWith("\\N")) {
            builder.colomn13(strings[12]);
        }
        if (!strings[13].startsWith("\\N")) {
            builder.colomn14(strings[13]);
        }
        return builder.build();
    }

}
