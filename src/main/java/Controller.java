import java.util.Scanner;

public class Controller {
    static final String COMPANY_MENU = "COMPANY_MENU";
    static final String COMPANY_LIST = "COMPANY_LIST";
    static final String CAR_MENU = "CAR_MENU";
    static final String CAR_LIST = "CAR_LIST";
    static final String CUSTOMER_MENU = "CUSTOMER_MENU";
    static final String CUSTOMER_LIST = "CUSTOMER_LIST";
    static final String CUSTOMER_CREATE = "CUSTOMER_CREATE";
    static final String LOGIN = "LOGIN";
    static final String EXIT = "EXIT";
    static String currentView = LOGIN;
    static String previousView = LOGIN;

    static CarView carView;
    static CustomerView customerView;
    static CompanyView companyView;

    static Scanner scanner = new Scanner(System.in);

    public Controller(String database) {
        this.scanner = new Scanner(System.in);
        this.companyView = new CompanyView(database);
        this.customerView = new CustomerView(database);
    }

    public void run() {
        while (true) {
            if (EXIT.equals(currentView)) {
//                System.exit(1);
                break;
            }

            if (LOGIN.equals(currentView)) {
                login();
            }

            if (COMPANY_MENU.equals(currentView)) {
                this.currentView = companyView.menu();
            }

            if (COMPANY_LIST.equals(currentView)) {
                this.currentView = companyView.list();
            }

            if (CAR_MENU.equals(currentView)) {
                this.currentView = carView.menu();
            }

            if (CAR_LIST.equals(currentView)) {
                this.currentView = carView.list();
            }

            if (CUSTOMER_MENU.equals(currentView)) {
                this.currentView = customerView.menu();
            }

            if (CUSTOMER_LIST.equals(currentView)) {
                this.currentView = customerView.list();
            }

            if (CUSTOMER_CREATE.equals(currentView)) {
                this.currentView = customerView.create();
            }
        }
    }

    public void login() {
        System.out.println("1. Log in as a manager");
        System.out.println("2. Log in as a customer");
        System.out.println("3. Create a customer");
        System.out.println("0. Exit");
        System.out.print("> ");
        String input = this.scanner.nextLine();
        System.out.println();

        previousView = LOGIN;

        if (input.contains("0")) {
            currentView = EXIT;
        }

        if (input.contains("1")) {
            currentView = COMPANY_MENU;
        }

        if (input.contains("2")) {
            currentView = CUSTOMER_LIST;
        }

        if (input.contains("3")) {
            currentView = CUSTOMER_CREATE;
        }
    }
}

