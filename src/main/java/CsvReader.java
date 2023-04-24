import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class CsvReader {
    static public List<String> readFile(Path path) throws IOException {
        long start = System.nanoTime();

        List<String> result =  Files.readAllLines(path);
//        List<String> result =  Files.newBufferedReader(path).lines().collect(Collectors.toList());

        long finish = System.nanoTime();
        long elapsed = finish - start;
        System.out.println("Прошло времени, мс: " + elapsed);

        return result;
    }

    static public List<String> readDefinedStrings(String path, List<Integer> listNumbersStrings) throws IOException {
        List<String> resultList = new ArrayList<>();

        RandomAccessFile raf = new RandomAccessFile(path.toString(), "r");
        for (Integer i : listNumbersStrings) {
            raf.seek(i);
            resultList.add(raf.readLine());
        }
        return resultList;
    }
}
