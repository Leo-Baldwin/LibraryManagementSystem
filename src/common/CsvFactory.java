package common;

public interface CsvFactory<T> {
    T fromRow(String[] row);
}