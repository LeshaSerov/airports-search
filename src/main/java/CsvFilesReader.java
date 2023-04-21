import java.io.BufferedReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CsvFilesReader {
    static public List<String> readFile(Path path) throws IOException {
        return Files.readAllLines(path);
//        return Files.newBufferedReader(path).lines().collect(Collectors.toList());
    }

    static public List<String> readDefinedStrings(Path path, List<Integer> listNumbersStrings) throws IOException {
        List<String> resultList = new ArrayList<>();

//        First
//        List<String> stringList = Files.readAllLines(path);
//        for (int i:
//             listNumbersStrings) {
//            resultList.add(stringList.get(i));
//        }

//        Second
//        BufferedReader br = Files.newBufferedReader(path);
//        int numberLastReadString = 0;
//        for (Integer i : listNumbersStrings) {
//            int a = i - numberLastReadString - 1;
//            for (int j = 0; j < a; j++)
//                br.readLine();
//            resultList.add(br.readLine());
//            numberLastReadString = i;
//        }

        //Third
        RandomAccessFile raf = new RandomAccessFile(path.toString(), "r");
        for (Integer i : listNumbersStrings) {
            raf.seek(i);
            resultList.add(raf.readLine());
        }
        return resultList;
    }
}
