package com.nuramov.hw08_JSON_Object_Writer;

import java.util.Objects;

public class ClassToSerializing {
    private final int value1;
    private final String value2;
    private final int value3;

    public ClassToSerializing(int value1, String value2, int value3) {
        this.value1 = value1;
        this.value2 = value2;
        this.value3 = value3;
    }


    @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            ClassToSerializing that = (ClassToSerializing) o;

            if (value1 != that.value1) return false;
            if (value3 != that.value3) return false;
            return Objects.equals(value2, that.value2);
        }

        @Override
        public String toString() {
            return "ClassToSerializing{" +
                    "value1=" + value1 +
                    ", value2='" + value2 + '\'' +
                    ", value3=" + value3 +
                    '}';
        }
}
