import java.util.HashMap;
import java.util.Map;

public class CompanyView {
    protected CompanyDAO companyDAO;
    private String database;

    public CompanyView(String database) {
        this.companyDAO = new CompanyDAO(database);
        this.database = database;
    }

    public String menu() {
        System.out.println("1. Company list");
        System.out.println("2. Create a company");
        System.out.println("0. Back");
        System.out.print("> ");
        String input = Controller.scanner.nextLine();
        System.out.println();

        if (input.contains("0")) {
            return Controller.LOGIN;
        }

        if (input.contains("1")) {
            return Controller.COMPANY_LIST;
        }

        if (input.contains("2")) {
            System.out.println("Enter the company name:");
            System.out.print("> ");
            String companyName = Controller.scanner.nextLine();
            int result = companyDAO.insert(companyName);
            if (result > 0) {
                System.out.println("The company was created!");
            } else {
                System.out.println("Can't create the company!");
            }
        }
        System.out.println();
        return Controller.COMPANY_MENU;
    }

    public String list() {
        Map<Integer, String> resultMap = companyDAO.selectAll();
        Map<Integer, Map<Integer, String>> resultMapMap = new HashMap<>();
        int count = 1;
        if (!resultMap.isEmpty()) {
            System.out.println("Choose the company:");
            for (Integer key: resultMap.keySet()) {
                System.out.println(count + ". " + resultMap.get(key));
                resultMapMap.put(count, Map.of(key, resultMap.get(key)));
                count++;
            }
            System.out.println("0. Back");
            System.out.print("> ");
            String input = Controller.scanner.nextLine();
            System.out.println();

            if (input.contains("0")) {
                return Controller.COMPANY_MENU;
            }
            // can use set and get instead
            int id = (int) resultMapMap.get(Integer.parseInt(input)).keySet().toArray()[0];
            Controller.carView = new CarView(this.database, id);
            return Controller.CAR_MENU;
        }
        System.out.println("The company list is empty!");
        System.out.println();
        return Controller.COMPANY_MENU;
    }

    public String list(int customerId) {
        Map<Integer, String> resultMap = companyDAO.selectAll();
        Map<Integer, Map<Integer, String>> resultMapMap = new HashMap<>();
        int count = 1;
        if (!resultMap.isEmpty()) {
            System.out.println("Choose the company:");
            for (Integer key : resultMap.keySet()) {
                System.out.println(count + ". " + resultMap.get(key));
                resultMapMap.put(count, Map.of(key, resultMap.get(key)));
                count++;
            }
            System.out.println("0. Back");
            System.out.print("> ");
            String input = Controller.scanner.nextLine();
            System.out.println();

            if (input.contains("0")) {
                return Controller.CUSTOMER_MENU;
            }
            int id = (int) resultMapMap.get(Integer.parseInt(input)).keySet().toArray()[0];
            Controller.carView = new CarView(this.database, id);
            return Controller.carView.list(customerId);
        }
        System.out.println("The company list is empty!");
        return Controller.CUSTOMER_MENU;
    }
}
