import domain.*;
import policy.*;
import ui.ConsoleMenu;

import java.util.List;

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

        // Adds demo data
        Member philip = new Member("Philip Johnson", "Philip@example.com ");
        Member kyle = new Member("Kyle Smith", "Kyle@example.com");
        library.addMember(philip);
        library.addMember(kyle);

        Book book1 = new Book("1984", "George Orwell", 1949, List.of("Fiction", "Classics"));
        Dvd dvd1 = new Dvd("Toy Story", 1995, 81, "3+",
                List.of("Animation", "Disney"));
        Magazine magazine1 = new Magazine("National Geographic", "Various", 2025,
                List.of("Science", "Nature"));

        library.addItem(book1);
        library.addItem(dvd1);
        library.addItem(magazine1);

        // Starts the console UI
        new ConsoleMenu(library).run();
    }
}
