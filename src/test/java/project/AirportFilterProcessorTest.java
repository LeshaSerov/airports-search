package project;

import org.junit.jupiter.api.Test;
import project.domain.Airport;
import project.domain.parser.BracketElement;
import project.domain.parser.ConditionElement;
import project.domain.parser.OperatorElement;
import project.domain.parser.SearchElement;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class AirportFilterProcessorTest {

    private final AirportFilterProcessor airportFilterProcessor = new AirportFilterProcessor();

    @Test
    void testProcess1() {
        List<SearchElement> searchElementList = Collections.emptyList();
        List<Airport> airportList = Arrays.asList(
                new Airport(1, "Airport1"),
                new Airport(2, "Airport2"),
                new Airport(3, "Airport3")
        );

        List<Airport> result = airportFilterProcessor.process(searchElementList, airportList);

        assertThat(result).containsExactlyElementsOf(airportList);
    }

    @Test
    void testProcess2() {

        List<SearchElement> searchElementList = Arrays.asList(
                new ConditionElement(3,ConditionElement.ConditionType.EQUAL_TO, "City1")
        );

        Airport airport1 = new Airport(1, "Airport 1");
        airport1.setColumn3("City1");
        Airport airport2 = new Airport(2, "Airport 2");
        airport2.setColumn3("City1");
        Airport airport3 = new Airport(3, "Airport 3");
        airport3.setColumn3("City3");
        List<Airport> airportList = Arrays.asList(
                airport1,
                airport2,
                airport3
        );


        List<Airport> result = airportFilterProcessor.process(searchElementList, airportList);


        assertThat(result).containsExactly(airport1,airport2);
    }

    @Test
    void testProcess3() {

        List<SearchElement> searchElementList = Arrays.asList(
//                1,2,3
        new ConditionElement(3, ConditionElement.ConditionType.GREATER_THAN, "0"),
        new OperatorElement(OperatorElement.OperatorType.AND),
        new ConditionElement(7, ConditionElement.ConditionType.GREATER_THAN, "25"),
        new OperatorElement(OperatorElement.OperatorType.OR),
//                3,7
        new BracketElement(BracketElement.BracketType.OPEN),
        new ConditionElement(4, ConditionElement.ConditionType.EQUAL_TO, "Moskva"),
        new OperatorElement(OperatorElement.OperatorType.AND),
        new ConditionElement(5, ConditionElement.ConditionType.EQUAL_TO, "R"),
        new BracketElement(BracketElement.BracketType.CLOSE),
//                4,5,6
        new OperatorElement(OperatorElement.OperatorType.OR),
        new ConditionElement(7, ConditionElement.ConditionType.EQUAL_TO, "10")
        );

        Airport airport1 = new Airport(1, "Airport1");
        airport1.setColumn3("City1");
        airport1.setColumn7(40.0);
        airport1.setColumn4("Moskva");
        airport1.setColumn5("T");

        Airport airport2 = new Airport(2, "Airport2");
        airport2.setColumn3("City1");
        airport2.setColumn7(40.0);
        airport2.setColumn4("Piter");
        airport2.setColumn5("T");

        Airport airport3 = new Airport(3, "Airport3");
        airport3.setColumn3("City1");
        airport3.setColumn7(40.0);
        airport3.setColumn4("Moskva");
        airport3.setColumn5("R");

        Airport airport4 = new Airport(4, "Airport4");
        airport4.setColumn3("City1");
        airport4.setColumn7(10.0);
        airport4.setColumn4("Piter");
        airport4.setColumn5("R");

        Airport airport5 = new Airport(5, "Airport5");
        airport5.setColumn3("City1");
        airport5.setColumn7(10.0);
        airport5.setColumn4("Moskva");
        airport5.setColumn5("T");

        Airport airport6 = new Airport(6, "Airport6");
        airport6.setColumn3("City1");
        airport6.setColumn7(10.0);
        airport6.setColumn4("Piter");
        airport6.setColumn5("T");

        Airport airport7 = new Airport(7, "Airport7");
        airport7.setColumn3("City1");
        airport7.setColumn7(20.0);
        airport7.setColumn4("Moskva");
        airport7.setColumn5("R");

        Airport airport8 = new Airport(8, "Airport8");
        airport8.setColumn3("City1");
        airport8.setColumn7(20.0);
        airport8.setColumn4("Piter");
        airport8.setColumn5("R");

        Airport airport9 = new Airport(9, "Airport9");
        airport9.setColumn3("City1");
        airport9.setColumn7(20.0);
        airport9.setColumn4("Moskva");
        airport9.setColumn5("T");

        List<Airport> airportList = Arrays.asList(
                airport1,
                airport2,
                airport3,
                airport4,
                airport5,
                airport6,
                airport7,
                airport8,
                airport9
        );


        List<Airport> result = airportFilterProcessor.process(searchElementList, airportList);


        assertThat(result).containsExactly(airport1,airport2,airport3,airport4,airport5,airport6,airport7);
    }

    @Test
    void testProcess4() {

        List<SearchElement> searchElementList = Arrays.asList(
                //
                new BracketElement(BracketElement.BracketType.OPEN),
                new ConditionElement(14, ConditionElement.ConditionType.LESS_THAN, "F"),
                new OperatorElement(OperatorElement.OperatorType.AND),
                new ConditionElement(14, ConditionElement.ConditionType.LESS_THAN, "F"),
                new BracketElement(BracketElement.BracketType.CLOSE),

                new OperatorElement(OperatorElement.OperatorType.OR),
//                1.2
//                1,2,3,4
                new ConditionElement(13, ConditionElement.ConditionType.GREATER_THAN, "a"),
                new OperatorElement(OperatorElement.OperatorType.AND),
                //1,2,5,6
                new ConditionElement(12, ConditionElement.ConditionType.LESS_THAN, "w"),

                new OperatorElement(OperatorElement.OperatorType.OR),
//
//                //5,6,7,8
                new BracketElement(BracketElement.BracketType.OPEN),
//                //5,7,8
                new ConditionElement(10, ConditionElement.ConditionType.EQUAL_TO, "12.12345"),
                new OperatorElement(OperatorElement.OperatorType.OR),
                //6,8
                new ConditionElement(9, ConditionElement.ConditionType.NOT_EQUAL_TO, "4"),
                new BracketElement(BracketElement.BracketType.CLOSE)
        );

        Airport airport1 = new Airport(0, "Airport1");
        airport1.setColumn9(4);
        airport1.setColumn10(12.123453);
        airport1.setColumn12("aw");
        airport1.setColumn13("aa");
        airport1.setColumn14("FF");

        Airport airport2 = new Airport(1, "Airport2");
        airport2.setColumn9(4);
        airport2.setColumn10(12.123455);
        airport2.setColumn12("aw");
        airport2.setColumn13("aa");
        airport2.setColumn14("FF");

        Airport airport3 = new Airport(2, "Airport3");
        airport3.setColumn9(4);
        airport3.setColumn10(12.123454);
        airport3.setColumn12("w");
        airport3.setColumn13("aa");
        airport3.setColumn14("FF");

        Airport airport4 = new Airport(3, "Airport4");
        airport4.setColumn9(4);
        airport4.setColumn10(12.123456);
        airport4.setColumn12("w");
        airport4.setColumn13("aa");
        airport4.setColumn14("FF");

        Airport airport5 = new Airport(4, "Airport5");
        airport5.setColumn9(4);
        airport5.setColumn10(12.12345);
        airport5.setColumn12("aw");
        airport5.setColumn13("a");
        airport5.setColumn14("FF");

        Airport airport6 = new Airport(5, "Airport6");
        airport6.setColumn9(3);
        airport6.setColumn10(12.123454);
        airport6.setColumn12("aw");
        airport6.setColumn13("a");
        airport6.setColumn14("FF");

        Airport airport7 = new Airport(6, "Airport7");
        airport7.setColumn9(4);
        airport7.setColumn10(12.12345);
        airport7.setColumn12("w");
        airport7.setColumn13("a");
        airport7.setColumn14("FF");

        Airport airport8 = new Airport(7, "Airport8");
        airport8.setColumn9(3);
        airport8.setColumn10(12.12345);
        airport8.setColumn12("w");
        airport8.setColumn13("a");
        airport8.setColumn14("FF");


        List<Airport> airportList = Arrays.asList(
                airport1,
                airport2,
                airport3,
                airport4,
                airport5,
                airport6,
                airport7,
                airport8
        );


        List<Airport> result = airportFilterProcessor.process(searchElementList, airportList);


        assertThat(result).containsExactly(airport1,airport2,airport5,airport6,airport7,airport8);
    }

}
