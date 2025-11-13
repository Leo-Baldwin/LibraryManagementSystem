package infrastructure.csv;

public interface CsvFactory<T> {
    T fromRow(String[] row);
}