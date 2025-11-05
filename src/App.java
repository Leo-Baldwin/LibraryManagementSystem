import domain.*;
import policy.*;

import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class App {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        LoanPolicy loanPolicy = new StandardLoanPolicy(14);        // 14 day loan period
        FinePolicy finePolicy = new StandardFinePolicy(50);     // 50 pence per day fine
        Library library = new Library(loanPolicy, finePolicy);

        loadDemoData(library);

        runMenu(library);
    }

    public static void runMenu(Library library) {
        boolean running = true;
        while (running) {
            System.out.println("\n---Library System---\n\n");
            System.out.println("Welcome to the Library Management System, please select an option from" +
                    "the menu below.\n\n");
            System.out.println("1. List all media");
            System.out.println("2. Checkout item");
            System.out.println("3. Return item");
            System.out.println("4. Place reservation");
            System.out.println("5. Add new book");
            System.out.println("6. Exit\n");
            System.out.println("Enter your choice: ");

            String choice = scanner.nextLine().trim();

            try {
                switch (choice) {
                    case "1":
                        listItems(library);
                    case "2":
                        loanItem(library);
                    case "3":
                        returnItem(library);
                    case "4":
                        placeReservation(library);
                    case "5":
                        addBook(library);
                    case "6":
                        running = false;
                    default:
                        System.out.println("Invalid choice");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
        System.out.println("Exiting Library Management System...");
    }

    public static void listItems(Library library) {
        System.out.println("\nMedia Items Catalogue:");
        for (MediaItem item : library.listItems()) {
            System.out.println(item);
        }
    }

    public static void loanItem(Library library) {
        System.out.print("Enter Member ID: ");
        UUID memberId = UUID.fromString(scanner.nextLine().trim());
        System.out.print("Enter Media ID: ");
        UUID mediaId = UUID.fromString(scanner.nextLine().trim());

        Loan loan = library.loanItem(memberId, mediaId);
        System.out.println("Checked out successfully. Due: " + loan.getDueDate());
    }

    public static void returnItem(Library library) {
        System.out.println("Enter Media ID: ");
        UUID mediaId = UUID.fromString(scanner.nextLine().trim());

        Loan loan = library.returnItem(mediaId);
        System.out.println("Returned successfully. Fine: £" + loan.getFineAccrued() / 100);
    }

    public static void placeReservation(Library library) {
        System.out.println("Enter Member ID: ");
        UUID memberId = UUID.fromString(scanner.nextLine().trim());
        System.out.print("Enter Media ID: ");
        UUID mediaId = UUID.fromString(scanner.nextLine().trim());

        Reservation reservation = library.placeReservation(memberId, mediaId);
        System.out.println("Reservation placed. Reservation ID: " + reservation.getReservationId());
    }

    public static void addBook(Library library) {
        System.out.println("Enter Book Title: ");
        String title = scanner.nextLine().trim();
        System.out.println("Enter Book Author: ");
        String author = scanner.nextLine().trim();
        System.out.println("Enter Book Year of Publication: ");
        int yearOfPublication = scanner.nextInt();
        System.out.println("Enter Book Categories (seperated by a comma): ");
        String categoriesInput = scanner.nextLine().trim();
        List<String> categories = List.of(categoriesInput.split("\\s*,\\s*"));

        Book  book = new Book(title, author, yearOfPublication, categories);
        library.addItem(book);
        System.out.println("Book added successfully. Book ID: " + book.getMediaId());
    }

    public static void loadDemoData(Library library) {
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

        System.out.println("Demo data loaded successfully.");
    }
}
