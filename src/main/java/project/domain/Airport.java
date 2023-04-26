package project.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class Airport {
    private int index;
    private String name;
    private String column3 = null;
    private String column4 = null;
    private String column5 = null;
    private String column6 = null;
    private Double column7 = null;
    private Double column8 = null;
    private Integer column9 = null;
    private Double column10 = null;
    private String column11 = null;
    private String column12 = null;
    private String column13 = null;
    private String column14 = null;

    public Airport(int index, String name) {
        this.index = index;
        this.name = name;
    }

    /**
     * Неоптимизированный метод toString(), который возвращает строковое представление аэропорта
     * с значениями всех полей, включая дополнительные колонки.
     *
     * @return строковое представление аэропорта
     */
    @Override
    public String toString() {
        int countFields = this.getClass().getDeclaredFields().length;
        StringBuilder string = new StringBuilder("\"" + name + "\"[" + index);
        for (int i = 2; i <= countFields; i++) {
            Object object = getValueForColumn(i);
            if (object == null) {
                string.append(", ").append("null");
            } else if (object.getClass() == String.class) {
                string.append(", \"").append(object).append("\"");
            } else {
                string.append(", ").append(object);
            }
        }
        return string.append("]").toString();
    }

    /**
     * Метод getValueForColumn() возвращает значение для указанной колонки.
     *
     * @param columnName номер колонки
     * @return значение для указанной колонки
     * @throws IllegalArgumentException если указано неверное имя колонки
     */
    public Object getValueForColumn(int columnName) {
        switch (columnName) {
            case 1:
                return index;
            case 2:
                return name;
            case 3:
                return column3;
            case 4:
                return column4;
            case 5:
                return column5;
            case 6:
                return column6;
            case 7:
                return column7;
            case 8:
                return column8;
            case 9:
                return column9;
            case 10:
                return column10;
            case 11:
                return column11;
            case 12:
                return column12;
            case 13:
                return column13;
            case 14:
                return column14;
            default:
                throw new IllegalArgumentException("Неверное имя колонки: " + columnName);
        }
    }
}
