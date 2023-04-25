package project.domain;

import lombok.Getter;
import lombok.ToString;

import java.lang.reflect.Method;

@Getter
@ToString
public class Airport {
    private final Integer index;
    private final String name;
    private final String column3;
    private final String column4;
    private final String column5;
    private final String column6;
    private final Double column7;
    private final Double column8;
    private final Integer column9;
    private final Double column10;
    private final String column11;
    private final String column12;
    private final String column13;
    private final String column14;

    public Airport(Builder builder) {
        this.index = builder.index;
        this.name = builder.name;
        this.column3 = builder.column3;
        this.column4 = builder.column4;
        this.column5 = builder.column5;
        this.column6 = builder.column6;
        this.column7 = builder.column7;
        this.column8 = builder.column8;
        this.column9 = builder.column9;
        this.column10 = builder.column10;
        this.column11 = builder.column11;
        this.column12 = builder.column12;
        this.column13 = builder.column13;
        this.column14 = builder.column14;
    }

    public static class Builder {
        Integer index;
        String name;
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

        public Builder(int index, String name) {
            this.index = index;
            this.name = name;
        }

        public Airport build() {
            return new Airport(this);
        }

        public Builder colomn3(String val) {
            column3 = val;
            return this;
        }

        public Builder colomn4(String val) {
            column4 = val;
            return this;
        }

        public Builder colomn5(String val) {
            column5 = val;
            return this;
        }

        public Builder colomn6(String val) {
            column6 = val;
            return this;
        }

        public Builder colomn7(Double val) {
            column7 = val;
            return this;
        }

        public Builder colomn8(Double val) {
            column8 = val;
            return this;
        }

        public Builder colomn9(Integer val) {
            column9 = val;
            return this;
        }

        public Builder colomn10(Double val) {
            column10 = val;
            return this;
        }

        public Builder colomn11(String val) {
            column11 = val;
            return this;
        }

        public Builder colomn12(String val) {
            column12 = val;
            return this;
        }

        public Builder colomn13(String val) {
            column13 = val;
            return this;
        }

        public Builder colomn14(String val) {
            column14 = val;
            return this;
        }

    }

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

//
//    // Метод для вызова геттера на основе имени колонки
//    public Object getValueForColumn(int columnName) {
//        String getterName = "getColumn" + columnName;
//        try {
//            Method getter = getClass().getMethod(getterName);
//            return getter.invoke(this);
//        } catch (Exception e) {
//            throw new IllegalArgumentException("Неверное имя колонки: " + columnName, e);
//        }
//    }
//
//    public Object getTypeForColumn(int columnName) {
//        String getterName = "getColumn" + columnName;
//        try {
//            Method getter = getClass().getMethod(getterName);
//            return getter.invoke(this).getClass();
//        } catch (Exception e) {
//            throw new IllegalArgumentException("Неверное имя колонки: " + columnName, e);
//        }
//    }
}
