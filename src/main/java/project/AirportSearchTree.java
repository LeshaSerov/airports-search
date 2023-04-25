package project;

import org.apache.commons.collections4.trie.PatriciaTrie;

import java.util.SortedMap;

public class AirportSearchTree {
    private PatriciaTrie<Long> trie = new PatriciaTrie<>();

    // Добавление аэропорта
    public void addAirport(String key, Long value) {
        trie.put(key, value);
    }

    // Поиск аэропортов по префиксу имени
    public SortedMap<String, Long> searchAirports(String prefix) {
        // Преобразование префикса в нижний регистр, если требуется
        prefix = prefix.toLowerCase();
        // Возврат списка значений, соответствующих префиксу в PatriciaTrie
        return trie.prefixMap(prefix);
    }
}
