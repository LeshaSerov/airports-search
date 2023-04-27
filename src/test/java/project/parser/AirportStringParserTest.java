package project.parser;

import org.junit.jupiter.api.Test;
import project.domain.Airport;

import static org.assertj.core.api.Assertions.assertThat;

public class AirportStringParserTest {

    @Test
    void testParse() {
        String stringAirport = "1,\"John F Kennedy Intl\",\"New York\",\"United States\",\"JFK\",\"KJFK\",40.639751,-73.778925,13,-5,\"A\",\"America/New_York\",\"Airport\",\"OurAirports\"";
        Airport expectedAirport = new Airport();
        expectedAirport.setIndex(1);
        expectedAirport.setName("John F Kennedy Intl");
        expectedAirport.setColumn3("New York");
        expectedAirport.setColumn4("United States");
        expectedAirport.setColumn5("JFK");
        expectedAirport.setColumn6("KJFK");
        expectedAirport.setColumn7(40.639751);
        expectedAirport.setColumn8(-73.778925);
        expectedAirport.setColumn9(13);
        expectedAirport.setColumn10(-5.0);
        expectedAirport.setColumn11("A");
        expectedAirport.setColumn12("America/New_York");
        expectedAirport.setColumn13("Airport");
        expectedAirport.setColumn14("OurAirports");

        Airport actualAirport = AirportStringParser.parse(stringAirport);
        assertThat(expectedAirport).isEqualTo(actualAirport);
    }

    @Test
    void testParseNullString() {
        String stringAirport = "1,\"John F Kennedy Intl\",\"\",\"\",\"\",\"\",,,,,\"\",\"\",\"\",\"\"";
        Airport expectedAirport = new Airport();
        expectedAirport.setIndex(1);
        expectedAirport.setName("John F Kennedy Intl");

        Airport actualAirport = AirportStringParser.parse(stringAirport);
        assertThat(expectedAirport).isEqualTo(actualAirport);
    }

    @Test
    void testParseError() {
        String stringAirport = "\",\"\",\"\",\"\",\"\",,,,,\"\",\"\",\"\",\"\"";
        try {
            Airport actualAirport = AirportStringParser.parse(stringAirport);

        }catch (Exception e)
        {
            assertThat(e).isInstanceOf(RuntimeException.class);
            assertThat(e).hasMessageStartingWith("Ошибка длины строки");
        }
    }
}