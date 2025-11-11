package ui;

import domain.*;
import common.ValidationException;

import java.util.List;
import java.util.Scanner;
import java.util.UUID;
import java.util.function.Function;

public class ConsoleMenu {

    private final Library library;
    private final Scanner scanner = new Scanner(System.in);

    public ConsoleMenu(Library library) {
        this.library = library;
    }

    public void run() {
        boolean running = true;
        while (running) {
            System.out.println();
            System.out.println("---Library System---\n");
            System.out.println("""
                    Welcome to the Library Management System.
                    Please select an option from\
                     the menu below.
                    """);
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
            } catch (CancelledOperationException e) {
                System.out.println("Cancelled. Returning to menu...\n");
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
        try {
            String mq  = readLine("Search for member by name");
            Member member = selectFromList(
                    library.searchMembers(mq),
                    "Results",
                    this::fmtMember
            );

            String iq  = readLine("Search for item by title/author");
            MediaItem item = selectFromList(
                    library.searchMedia(iq),
                    "Results",
                    this::fmtMedia
            );

            boolean cfm = confirm("You are about to create a loan:", List.of(
                    "Member: " + fmtMember(member),
                    "Item: " + fmtMedia(item)
            ));

            if (!cfm) {
                System.out.println("Cancelled. Returning to menu...\n");
                return;
            }

            Loan loan = library.loanItem(member.getId(), item.getMediaId());
            System.out.println("Loan created successfully.");
            System.out.println("Receipt:");
            System.out.println("  Member: " + member.getName());
            System.out.println("  Item:   " + item.getTitle());
            System.out.println("  Due:    " + loan.getDueDate());
            System.out.println();

        } catch (CancelledOperationException ignored) {
            System.out.println("Cancelled. Returning to menu...\n");
            System.out.println();
        } catch (ValidationException e) {
            System.out.println("Error: " + e.getMessage());
            System.out.println();
        }
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
        String title = readLine("Enter Book Title: ");
        String author = readLine("Enter Book Author: ");

        System.out.println("Enter Book Year of Publication: ");
        int yearOfPublication = scanner.nextInt();

        String categoriesInput = readLine("Enter Book Categories (seperated by a comma): ");
        List<String> categories = categoriesInput.isEmpty()
                ? List.of()
                : List.of(categoriesInput.split("\\s*,\\s*"));

        Book book = new Book(title, author, yearOfPublication, categories);
        library.addItem(book);
        System.out.println("Book added successfully. Book ID: " + book.getMediaId());
        System.out.println();
    }

    // ---------------------------------------- Internals ---------------------------------------

    private boolean isCancelled(String s) {
        return s.isBlank() || switch  (s.toLowerCase()) {
            case "cancel", "quit", "q", "exit" -> true;
            default -> false;
        };
    }

    private UUID readUUID(String prompt) {
        while (true) {
            System.out.print(prompt + " (Press Enter to cancel): ");
            String input = scanner.nextLine().trim();
            if (isCancelled(input)) throw new CancelledOperationException();

            try {
                return UUID.fromString(input);
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
                System.out.println();
            }
        }
    }

    private String readLine(String prompt) {
        System.out.print(prompt + " (Press Enter to cancel): ");
        String input = scanner.nextLine().trim();

        if (isCancelled(input)) {
            throw new CancelledOperationException();
        }
        return input;
    }

    private <T> T selectFromList(
            List<T> options,
            String heading,
            Function<T, String> displayFormatter
    ) {
        if (options == null || options.isEmpty()) {
            System.out.println("No results.\n");
            throw new CancelledOperationException();
        }

        System.out.println("----------------------------------------");
        System.out.println(heading);
        for (int i = 0; i < options.size(); i++) {
            System.out.printf("%d) %s%n", i + 1, displayFormatter.apply(options.get(i)));
        }
        System.out.println("0) Cancel");
        System.out.println("----------------------------------------");

        while (true) {
            String input = readLine("Select a number");
            if(input.equals("0")) throw new CancelledOperationException();
            try {
                int index = Integer.parseInt(input);
                if (index >= 1 && index <= options.size()) return options.get(index - 1);
            } catch (NumberFormatException ignored) {
                System.out.println("Error: Please enter a valid number from the list.\n");
            }
        }
    }

    private boolean confirm(String title, List<String> lines) {
        System.out.println("----------------------------------------");
        System.out.println(title);
        for (String line : lines) System.out.println("  " + line);
        System.out.println("----------------------------------------");
        String answer = readLine("Confirm? (Y/n)").toLowerCase();
        return answer.equals("y") || answer.equals("yes") || answer.isBlank();
    }

    private static String shortId(UUID id) {
        String s = id.toString();
        return s.substring(0, 8);
    }

    private String fmtMedia(MediaItem m) {
        String title = (m.getTitle() == null || m.getTitle().isBlank()) ? "(untitled)" : m.getTitle();
        String author = (m instanceof Book b && b.getAuthor() != null && !b.getAuthor().isBlank())
                ? " — " + b.getAuthor()
                : "";
        String status = (m.getStatus() == null) ? "" : " [" + m.getStatus() + "]";
        return title + author + " (id:" + shortId(m.getMediaId()) + ")" + status;
    }

    private String fmtMember(Member member) {
        String name = member.getName() == null ? "(unnamed)" : member.getName();
        String email = member.getEmail() == null || member.getEmail().isBlank() ? "" : " <" + member.getEmail() + ">";
        return name + email + " (id:" + shortId(member.getId()) + ")";
    }
}
