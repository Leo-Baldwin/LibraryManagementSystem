import common.CsvFactory;
import domain.*;
import factory.BookFactory;
import factory.DvdFactory;
import factory.MagazineFactory;
import factory.MemberFactory;
import policy.*;
import ui.ConsoleMenu;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class App {

    /**
     * Application entry point - Sets up the Library and runs the console UI.
     *
     * @param args allows for passing arguments to the program on the command line at initialisation
     */
    public static void main(String[] args) {

        // Injects policy interfaces with their configurations and create Library object
        LoanPolicy loanPolicy = new StandardLoanPolicy(14);        // 14 day loan period
        FinePolicy finePolicy = new StandardFinePolicy(50);     // 50 pence per day fine
        Library library = new Library(loanPolicy, finePolicy);

        loadDemoData(library);

        // Starts the console UI
        new ConsoleMenu(library).run();
    }

    private static void loadDemoData(Library library) {
        loadCsv("data/members.csv",   new MemberFactory(),   library::addMember);
        loadCsv("data/books.csv",     new BookFactory(),     library::addItem);
        loadCsv("data/dvds.csv",      new DvdFactory(),      library::addItem);
        loadCsv("data/magazines.csv", new MagazineFactory(), library::addItem);
    }

    private static <T> void loadCsv(String classpath,
                                    CsvFactory<T> factory,
                                    Consumer<T> consumer) {
        for (String[] row : readCsv(classpath)) {
            if (row.length == 0) continue;
            consumer.accept(factory.fromRow(row));
        }
    }

    // Reads from src/main/resources
    private static List<String[]> readCsv(String classpath) {
        List<String[]> rows = new ArrayList<>();
        try {
            InputStream in = App.class.getClassLoader().getResourceAsStream(classpath);
            if (in == null) {
                System.err.println("CSV file not found on classpath: " + classpath);
                return rows;
            }
            try (BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8))) {
                String line; boolean header = true;
                while ((line = br.readLine()) != null) {
                    line = line.trim();
                    if (line.isBlank()) continue;
                    if (header) { header = false; continue; }
                    rows.add(line.split(",", -1));
                }
            }
        } catch (Exception e) {
            System.err.println("Error reading CSV " + classpath + ": " + e.getMessage());
        }
        return rows;
    }
}
