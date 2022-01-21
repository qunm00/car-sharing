import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomerView {
    private CustomerDAO customerDAO;
    private CarDAO carDAO;
    private CompanyDAO companyDAO;
    private int id;
    private String database;

    public CustomerView(String database) {
        this.customerDAO = new CustomerDAO(database);
        this.database = database;
    }
    public CustomerView(String database,int id) {
        this.id = id;
        this.customerDAO = new CustomerDAO(database);
        this.carDAO = new CarDAO(database);
        this.companyDAO = new CompanyDAO(database);
        this.database = database;
    }

    public String create() {
        System.out.println("Enter the customer name:");
        System.out.print("> ");
        String input = Controller.scanner.nextLine();
        int result = customerDAO.insert(input);
        if (result > 0) {
            System.out.println("The customer was added!");
        } else {
            System.out.println("Can't add the customer");
        }
        Controller.previousView = Controller.CUSTOMER_CREATE;
        return Controller.LOGIN;
    }

    public String list() {
        Map<Integer, String> customerMap = customerDAO.select();
        Map<Integer, Map<Integer, String>> customerMapMap = new HashMap<>();
        if (customerMap.size() == 0) {
            System.out.println("The customer list is empty!");
            return Controller.LOGIN;
        }

        int count = 1;
        for (Integer key: customerMap.keySet()) {
            System.out.println(count + ". " + customerMap.get(key));
            customerMapMap.put(count, Map.of(key, customerMap.get(key)));
            count++;
        }
        System.out.println("0. Back");
        System.out.print("> ");
        String input = Controller.scanner.nextLine();
        System.out.println();
        if (input.contains("0")) {
            return Controller.LOGIN;
        }
        //can use set and get instead
        int id = (int) customerMapMap.get(Integer.parseInt(input)).keySet().toArray()[0];
        Controller.customerView = new CustomerView(this.database, id);
        Controller.previousView = Controller.CUSTOMER_LIST;
        return Controller.CUSTOMER_MENU;
    }

    public String menu() {
        System.out.println("1. Rent a car");
        System.out.println("2. Return a rented car");
        System.out.println("3. My rented car");
        System.out.println("0. Back");
        System.out.print("> ");
        String input = Controller.scanner.nextLine();
        System.out.println();
        if (input.contains("0")) {
            return Controller.LOGIN;
        }

        if (input.contains("1")) {
            if (this.customerDAO.getCarId(this.id) > 0) {
                System.out.println("You've already rented a car!");
            } else {
                return Controller.companyView.list(this.id);
            }
        }

        if (input.contains("2")) {
            if (customerDAO.setCar(this.id) > 0) {
                System.out.println("You've returned a rented car!");
            } else {
                System.out.println("You didn't rent a car!");
            }
        }

        if (input.contains("3")) {
            int carId = this.customerDAO.getCarId(this.id);
            if (carId != 0) {
                List<String> carInfo = this.carDAO.getCarInfo(carId);
                String carName = carInfo.get(0);
                String companyName = this.companyDAO.getCompanyInfo(Integer.parseInt(carInfo.get(1)));
                System.out.println("You rented car:");
                System.out.println(carName);
                System.out.println("Company:");
                System.out.println(companyName);
            } else {
                System.out.println("You didn't rent a car!");
            }
            System.out.println();
        }
        Controller.previousView = Controller.CUSTOMER_MENU;
        return Controller.CUSTOMER_MENU;
    }
}

