package project;

import static org.junit.jupiter.api.Assertions.*;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import project.domain.Airport;
import project.domain.parser.ConditionElement;
import project.domain.parser.SearchElement;

class AirportFilterProcessorTest {

    private final AirportFilterProcessor airportFilterProcessor = new AirportFilterProcessor();

    @Test
    void testProcessWithEmptyFilterShouldReturnOriginalAirportList() {
        // given
        List<SearchElement> searchElementList = Collections.emptyList();
        List<Airport> airportList = Arrays.asList(
                new Airport(1, "Airport1"),
                new Airport(2, "Airport2"),
                new Airport(3, "Airport3")
        );

        // when
        List<Airport> result = airportFilterProcessor.process(searchElementList, airportList);

        // then
        assertThat(result).containsExactlyElementsOf(airportList);
    }

    @Test
    void testProcess_withSimpleFilter_shouldReturnFilteredAirportList() {
        // given
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

        // when
        List<Airport> result = airportFilterProcessor.process(searchElementList, airportList);

        // then
        assertThat(result).containsExactly(airport1,airport2);
    }

//    @Test
//    void testProcess_withComplexFilter_shouldReturnFilteredAirportList() {
//        // given
//        List<SearchElement> searchElementList = Arrays.asList(
//                new ColumnElement("country"),
//                new ConditionElement(Condition.EQUALS, "Country 2"),
//                new OperatorElement(OperatorType.AND),
//                new BracketElement(BracketType.OPEN),
//                new ColumnElement("city"),
//                new ConditionElement(Condition.EQUALS, "City 1"),
//                new OperatorElement(OperatorType.OR),
//                new ColumnElement("city"),
//                new ConditionElement(Condition.EQUALS, "City 3"),
//                new BracketElement(BracketType.CLOSE)
//        );
//        List<Airport> airportList = Arrays.asList(
//                new Airport("AAA", "Airport 1", "City 1", "Country 1"),
//                new Airport("BBB", "Airport 2", "City 2", "Country 2"),
//                new Airport("CCC", "Airport 3", "City 3", "Country 3")
//        );
//
//        // when
//        List<Airport> result = airportFilterProcessor.process(searchElementList, airportList);
//
//        // then
//        assertThat(result).containsExactly(
//                new Airport("AAA", "Airport 1", "City 1", "Country 1"),
//                new Airport("BBB", "Airport 2", "City 2", "Country 2"),
//                new Airport("CCC", "Airport 3", "City 3", "Country 3")
//        );
//    }

}
