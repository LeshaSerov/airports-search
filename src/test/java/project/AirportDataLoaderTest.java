package project;

import org.junit.jupiter.api.Test;
import project.domain.Airport;

import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.SortedMap;

import static org.assertj.core.api.Assertions.assertThat;


public class AirportDataLoaderTest {

    @Test
    public void testLoadListSearchTreeFromCsv() {
        List<byte[]> strings = Arrays.asList(
                "1,\"Airport1\",\\N,\\N,\\N,\\N,\\N,\\N,\\N,\\N,\\N,\\N,\\N,\\N".getBytes(),
                "1,\"Airport2\",\\N,\\N,\\N,\\N,\\N,\\N,\\N,\\N,\\N,\\N,\\N,\\N".getBytes(),
                "1,\"Airport3\",\\N,\\N,\\N,\\N,\\N,\\N,\\N,\\N,\\N,\\N,\\N,\\N".getBytes()
        );

        String fileName = "testLoadListSearchTreeFromCsv.csv";
        try {
            Path filePath = Paths.get(fileName);
            Files.createFile(filePath);
            try (FileOutputStream fos = new FileOutputStream(fileName)) {
                // записываем строки в файл, разделяя их символом переноса строки
                for (byte[] s : strings) {
                    fos.write(s);
                    fos.write(10);
                }
            }
            AirportSearchTree airportSearchTree = AirportDataLoader.loadAirportSearchTreeFromCsv(filePath);
            Files.delete(Paths.get(fileName));

            SortedMap<String, AirportSearchTree.AirportDataForParsing> stringAirportDataLoaderSortedMap = airportSearchTree.searchAirports("airport");
            assertThat(stringAirportDataLoaderSortedMap.size()).isEqualTo(3);
            assertThat(stringAirportDataLoaderSortedMap.get("airport1")).isEqualTo(new AirportSearchTree.AirportDataForParsing(0, 48));
            assertThat(stringAirportDataLoaderSortedMap.get("airport2")).isEqualTo(new AirportSearchTree.AirportDataForParsing(49, 48));
            assertThat(stringAirportDataLoaderSortedMap.get("airport3")).isEqualTo(new AirportSearchTree.AirportDataForParsing(98, 48));

        } catch (
                Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testLoadListAirport() {
        List<AirportSearchTree.AirportDataForParsing> airportDataForParsingList = new ArrayList<>();
        airportDataForParsingList.add(new AirportSearchTree.AirportDataForParsing(0, 48));
        airportDataForParsingList.add(new AirportSearchTree.AirportDataForParsing(49, 48));
        airportDataForParsingList.add(new AirportSearchTree.AirportDataForParsing(98, 48));
        List<byte[]> strings = Arrays.asList(
                "1,\"Airport1\",\\N,\\N,\\N,\\N,\\N,\\N,\\N,\\N,\\N,\\N,\\N,\\N".getBytes(),
                "1,\"Airport2\",\\N,\\N,\\N,\\N,\\N,\\N,\\N,\\N,\\N,\\N,\\N,\\N".getBytes(),
                "1,\"Airport3\",\\N,\\N,\\N,\\N,\\N,\\N,\\N,\\N,\\N,\\N,\\N,\\N".getBytes()
        );

        String fileName = "testLoadListAirport.csv";
        try {
            Files.createFile(Paths.get(fileName));
            try (FileOutputStream fos = new FileOutputStream(fileName)) {
                // записываем строки в файл, разделяя их символом переноса строки
                for (byte[] s : strings) {
                    fos.write(s);
                    fos.write(10);
                }
            }
            List<Airport> airportList = AirportDataLoader.loadListAirport(fileName, airportDataForParsingList);
            Files.delete(Paths.get(fileName));

            assertThat(airportList.size()).isEqualTo(3);
            assertThat(airportList.get(0).getName()).isEqualTo("Airport1");
            assertThat(airportList.get(1).getName()).isEqualTo("Airport2");
            assertThat(airportList.get(2).getName()).isEqualTo("Airport3");

        } catch (Exception e) {
        }
    }

    @Test
    public void testLoadListAirportWithEmptyDataForParsingList() {
        List<AirportSearchTree.AirportDataForParsing> airportDataForParsingList = new ArrayList<>();
        List<byte[]> strings = Arrays.asList(
                "1,\"Airport1\",\\N,\\N,\\N,\\N,\\N,\\N,\\N,\\N,\\N,\\N,\\N,\\N\n".getBytes(),
                "1,\"Airport2\",\\N,\\N,\\N,\\N,\\N,\\N,\\N,\\N,\\N,\\N,\\N,\\N\n".getBytes(),
                "1,\"Airport3\",\\N,\\N,\\N,\\N,\\N,\\N,\\N,\\N,\\N,\\N,\\N,\\N\n".getBytes()
        );

        String fileName = "testLoadListAirportWithEmptyDataForParsingList.csv";
        try {
            Files.createFile(Paths.get(fileName));
            for (byte[] e : strings) {
                Files.write(Paths.get(fileName), e);
            }
            List<Airport> airportList = AirportDataLoader.loadListAirport(fileName, airportDataForParsingList);
            assertThat(airportList.size()).isEqualTo(0);

            Files.delete(Paths.get(fileName));
        } catch (Exception e) {
        }

    }

    @Test()
    public void testLoadListAirportWithNonExistentFile() {
        // Создаем список данных для парсинга
        List<AirportSearchTree.AirportDataForParsing> airportDataForParsingList = new ArrayList<>();
        airportDataForParsingList.add(new AirportSearchTree.AirportDataForParsing(0, 10));
        List<Airport> airportList = AirportDataLoader.loadListAirport("test.csv", airportDataForParsingList);
        assertThat(airportList.size()).isEqualTo(0);
    }
}
