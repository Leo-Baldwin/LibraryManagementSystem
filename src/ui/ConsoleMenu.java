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
        System.out.print("Enter Member ID: ");
        UUID memberId = UUID.fromString(scanner.nextLine().trim());

        System.out.print("Enter Media ID: ");
        UUID mediaId = UUID.fromString(scanner.nextLine().trim());

        Loan loan = library.loanItem(memberId, mediaId);
        System.out.println("Checked out successfully. Due: " + loan.getDueDate());
    }

    public void returnItem(Library library) {
        System.out.println("Enter Media ID: ");
        UUID mediaId = UUID.fromString(scanner.nextLine().trim());

        Loan loan = library.returnItem(mediaId);
        System.out.println("Returned successfully. Fine: £" + loan.getFineAccrued() / 100);
    }

    public void placeReservation(Library library) {
        System.out.println("Enter Member ID: ");
        UUID memberId = UUID.fromString(scanner.nextLine().trim());

        System.out.print("Enter Media ID: ");
        UUID mediaId = UUID.fromString(scanner.nextLine().trim());

        Reservation reservation = library.placeReservation(memberId, mediaId);
        System.out.println("Reservation placed. Reservation ID: " + reservation.getReservationId());
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
        List<String> categories = List.of(categoriesInput.split("\\s*,\\s*"));

        Book book = new Book(title, author, yearOfPublication, categories);
        library.addItem(book);
        System.out.println("Book added successfully. Book ID: " + book.getMediaId());
    }
}
