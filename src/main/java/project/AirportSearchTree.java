package project;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.collections4.trie.PatriciaTrie;

import java.util.SortedMap;

/**
 * Класс AirportSearchTree представляет собой поисковое дерево для хранения и поиска аэропортов на основе их названий.
 * Ключами являются строки, а значениями - объекты AirportDataForParsing, содержащие информацию для парсинга аэропортов.
 */
public class AirportSearchTree {
    private PatriciaTrie<AirportDataForParsing> trie = new PatriciaTrie<>();

    public void addAirport(String key, Long value, int size) {
        trie.put(key, new AirportDataForParsing(value, size));
    }

    public SortedMap<String, AirportDataForParsing> searchAirports(String prefix) {
        return trie.prefixMap(prefix);
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @EqualsAndHashCode
    public static class AirportDataForParsing {
        private long numberOfBytesUpTo;
        private int length;
    }
}
