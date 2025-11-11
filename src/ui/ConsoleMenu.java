package ui;

import domain.*;
import common.ValidationException;

import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class ConsoleMenu {

    private final Library library;
    private final Scanner scanner = new Scanner(System.in);

    public ConsoleMenu(Library library) {
        this.library = library;
    }

    public void run() {
        boolean running = true;
        while (running) {
            System.out.println("---Library System---\n");
            System.out.println("Welcome to the Library Management System.\nPlease select an option from" +
                    " the menu below.\n");
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
                        break;
                    case "2":
                        loanItem(library);
                        break;
                    case "3":
                        returnItem(library);
                        break;
                    case "4":
                        placeReservation(library);
                        break;
                    case "5":
                        addBook(library);
                        break;
                    case "6":
                        running = false;
                        break;
                    default:
                        System.out.println("Invalid choice");
                }
            } catch (ValidationException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
        System.out.println("\nExiting Library Management System...");
    }


    public void listItems(Library library) {
        System.out.println("\nMedia Items Catalogue:\n");

        for (MediaItem item : library.listItems()) {
            System.out.println(item);
            System.out.println("\n");
        }
    }

    public void loanItem(Library library) {
        UUID memberId = readUUID("Enter Member ID: ");
        UUID mediaId = readUUID("Enter Media ID: ");

        Loan loan = library.loanItem(memberId, mediaId);
        System.out.println("Checked out successfully. Due: " + loan.getDueDate());
        System.out.println();
    }

    public void returnItem(Library library) {
        UUID mediaId = readUUID("Enter Media ID: ");
        Loan loan = library.returnItem(mediaId);
        System.out.println("Returned successfully. Fine: " + String.format("£%.2f", loan.getFineAccrued() / 100.0));
        System.out.println();
    }

    public void placeReservation(Library library) {
        UUID memberId = readUUID("Enter Member ID: ");
        UUID mediaId = readUUID("Enter Media ID: ");

        Reservation reservation = library.placeReservation(memberId, mediaId);
        System.out.println("Reservation placed. Reservation ID: " + reservation.getReservationId());
        System.out.println();
    }

    public void addBook(Library library) {
        System.out.println("Enter Book Title: ");
        String title = scanner.nextLine().trim();

        System.out.println("Enter Book Author: ");
        String author = scanner.nextLine().trim();

        System.out.println("Enter Book Year of Publication: ");
        int yearOfPublication = scanner.nextInt();

        System.out.println("Enter Book Categories (seperated by a comma): ");
        String categoriesInput = scanner.nextLine().trim();
        List<String> categories = categoriesInput.isEmpty()
                ? List.of()
                : List.of(categoriesInput.split("\\s*,\\s*"));

        Book book = new Book(title, author, yearOfPublication, categories);
        library.addItem(book);
        System.out.println("Book added successfully. Book ID: " + book.getMediaId());
    }

    // ---------------------------------------- Internals ---------------------------------------

    private UUID readUUID(String prompt) {
        while (true) {
            System.out.println(prompt);
            String input = scanner.nextLine().trim();
            try {
                return UUID.fromString(input);
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
                System.out.println();
            }
        }
    }
}
