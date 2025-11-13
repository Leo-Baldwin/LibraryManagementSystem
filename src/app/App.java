package app;

import domain.policy.FinePolicy;
import domain.policy.LoanPolicy;
import domain.policy.StandardFinePolicy;
import domain.policy.StandardLoanPolicy;
import domain.service.Library;
import presentation.console.ConsoleMenu;

public class App {

    /**
     * Application entry point - Sets up the Library and runs the console UI.
     *
     * @param args allows for passing arguments to the program on the command line
     */
    public static void main(String[] args) {

        // Injects policy interfaces with their configurations and create Library object
        LoanPolicy loanPolicy = new StandardLoanPolicy(14);        // 14 day loan period
        FinePolicy finePolicy = new StandardFinePolicy(50);     // 50 pence per day fine
        Library library = new Library(loanPolicy, finePolicy);

        DemoDataLoader.loadDemoData(library);

        // Starts the console UI
        new ConsoleMenu(library).run();
    }
}
