import java.util.List;

public class CarView {
    private CarDAO carDAO;
    private CustomerDAO customerDAO;
    private int id;

    public CarView(String database, int id) {
        this.carDAO = new CarDAO(database);
        this.customerDAO = new CustomerDAO(database);
        this.id = id;
    }

    public String menu() {
        System.out.println("1. Car list");
        System.out.println("2. Create a car");
        System.out.println("0. Back");
        System.out.print("> ");
        String input = Controller.scanner.nextLine();
        System.out.println();

        if (input.contains("0")) {
            return Controller.COMPANY_MENU;
        }

        if (input.contains("1")) {
            Controller.previousView = Controller.CAR_MENU;
            return Controller.CAR_LIST;
        }

        if (input.contains("2")) {
            System.out.println(("Enter the car name:"));
            System.out.print("> ");
            String carName = Controller.scanner.nextLine();
            int result = carDAO.insert(new String[]{carName, String.valueOf(this.id)});
            if (result > 0) {
                System.out.println("The car was added!");
            } else {
                System.out.println("Can't create the car!");
            }
        }
        System.out.println();
        Controller.previousView = Controller.CAR_MENU;
        return Controller.CAR_MENU;
    }

    public String list() {
        List<String> resultList = carDAO.select(this.id);
        int count = 1;
        if (!resultList.isEmpty()) {
            System.out.println("Car list:");
            for (String car: resultList) {
                System.out.println(count + ". " + car);
                count++;
            }
        } else {
            System.out.println("The car list is empty! ");
        }
        System.out.println();
        Controller.previousView = Controller.CAR_LIST;
        return Controller.CAR_MENU;
    }

    public String list(int customerId) {
        List<String> resultList = carDAO.selectAvailableCars(this.id);
        int count = 1;
        if (!resultList.isEmpty()) {
            System.out.println("Car list:");
            for (String car: resultList) {
                System.out.println(count + ". " + car);
                count++;
            }
            System.out.println("0. Back");
            System.out.print("> ");
            String input = Controller.scanner.nextLine();
            System.out.println();

            if (input.contains("0")) {
                return Controller.COMPANY_LIST;
            }

            String carName = resultList.get(Integer.parseInt(input) - 1);
            int carId = this.carDAO.getCarId(carName);
            System.out.println(carName + " " + carId);
            if (this.customerDAO.setCar(carId, customerId) > 0) {
                System.out.println("You rented '" + carName + "'");
                System.out.println();
            } else {
                System.out.println("You've already rented a car!");
            }
            return Controller.CUSTOMER_MENU;
        } else {
            System.out.println("The car list is empty! ");
        }
        System.out.println();
        Controller.previousView = Controller.CAR_LIST;
        return Controller.COMPANY_LIST;
    }
}
