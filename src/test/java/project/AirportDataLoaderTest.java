package project;

import lombok.extern.slf4j.Slf4j;
import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;
import project.AirportDataLoader;
import project.AirportSearchTree;
import project.domain.Airport;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class AirportDataLoaderTest {

    @Test
    public void testLoadListAirport() {
        List<AirportSearchTree.AirportDataForParsing> airportDataForParsingList = new ArrayList<>();
        airportDataForParsingList.add(new AirportSearchTree.AirportDataForParsing(0, 48));
        airportDataForParsingList.add(new AirportSearchTree.AirportDataForParsing(50, 48));
        airportDataForParsingList.add(new AirportSearchTree.AirportDataForParsing(100, 48));

        List<Airport> airportList = AirportDataLoader.loadListAirport("test.csv", airportDataForParsingList);

        // Проверяем результат
        assertEquals(3, airportList.size());
        assertEquals("Airport1", airportList.get(0).getName());
        assertEquals("Airport2", airportList.get(1).getName());
        assertEquals("Airport3", airportList.get(2).getName());
    }

    @Test
    public void testLoadListAirportWithEmptyDataForParsingList() {
        // Создаем пустой список данных для парсинга
        List<AirportSearchTree.AirportDataForParsing> airportDataForParsingList = new ArrayList<>();

        // Загружаем список аэропортов из файла
        List<Airport> airportList = AirportDataLoader.loadListAirport("test.csv", airportDataForParsingList);

        // Проверяем результат
        assertEquals(0, airportList.size());
    }

    @Test()
    public void testLoadListAirportWithNonExistentFile() {
        // Создаем список данных для парсинга
        List<AirportSearchTree.AirportDataForParsing> airportDataForParsingList = new ArrayList<>();
        airportDataForParsingList.add(new AirportSearchTree.AirportDataForParsing(0, 10));

        // Загружаем список аэропортов из несуществующего файла
        List<Airport> airportList = AirportDataLoader.loadListAirport("nonexistent.csv", airportDataForParsingList);
        assertEquals(0, airportList.size());
    }
}
