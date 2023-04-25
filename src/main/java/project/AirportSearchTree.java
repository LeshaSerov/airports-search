package project;

import org.apache.commons.collections4.trie.PatriciaTrie;

import java.util.SortedMap;

public class AirportSearchTree {
    private PatriciaTrie<Long> trie = new PatriciaTrie<>();

    public void addAirport(String key, Long value) {
        trie.put(key, value);
    }

    public SortedMap<String, Long> searchAirports(String prefix) {
        prefix = prefix.toLowerCase();
        return trie.prefixMap(prefix);
    }
}
