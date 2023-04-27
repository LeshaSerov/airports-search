package project;


import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CsvReaderTest {

    @Test
    public void testNotFile() {
        List<AirportSearchTree.AirportDataForParsing> airportDataForParsings = new ArrayList<>();
        airportDataForParsings.add(new AirportSearchTree.AirportDataForParsing(0, 10));
        airportDataForParsings.add(new AirportSearchTree.AirportDataForParsing(11, 10));
        List<String> actualResult = CsvReader.readDefinedStrings("testNotFile.csv", airportDataForParsings);
        List<String> expectedResult = new ArrayList<>();
        assertThat(expectedResult).isEqualTo(actualResult);
    }

    @Test
    public void testNotDefinedString() {
        List<AirportSearchTree.AirportDataForParsing> airportDataForParsings = new ArrayList<>();
        airportDataForParsings.add(new AirportSearchTree.AirportDataForParsing(0, 10));
        airportDataForParsings.add(new AirportSearchTree.AirportDataForParsing(11, 10));

        try {
            Path path = Paths.get("testNotDefinedString.csv");
            Files.createFile(path);
            List<String> actualResult = CsvReader.readDefinedStrings(path.toString(), airportDataForParsings);
            List<String> expectedResult = new ArrayList<>();
            expectedResult.add("test12345\n");
            expectedResult.add("test67890\n");

            assertThat(expectedResult).isNotEqualTo(actualResult);
            Files.delete(path);
        } catch (IOException e) {
       }
    }

    @Test
    public void testReadDefinedStrings() {
        List<AirportSearchTree.AirportDataForParsing> airportDataForParsingList = new ArrayList<>();
        airportDataForParsingList.add(new AirportSearchTree.AirportDataForParsing(0, 10));
        airportDataForParsingList.add(new AirportSearchTree.AirportDataForParsing(11, 10));

        try {
            Path path = Paths.get("testReadDefinedStrings.csv");
            Files.createFile(path);
            Files.write(path, "test12345\ntest67890".getBytes());
            List<String> actualResult = CsvReader.readDefinedStrings(path.toString(), airportDataForParsingList);
            List<String> expectedResult = new ArrayList<>();
            expectedResult.add("test12345\n");
            expectedResult.add("test67890\n");

            assertThat(expectedResult).isNotEqualTo(actualResult);
            Files.delete(path);
        } catch (IOException e) {
        }
    }

}