package project.domain.parser;

/**
 * Интерфейс для общего функционала элементов поисковой строки
 */
public interface SearchElement {
    /**
     * Метод getType() возвращает тип элемента поиска.
     *
     * @return объект, представляющий тип элемента поиска
     */
    Object getType();
}
