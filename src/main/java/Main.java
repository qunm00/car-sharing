import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        File file = new File(System.getProperty("user.dir") + "/src/main/db");
        file.mkdir();

//        String database = file + "/" + args[1];
        String database = file + "/carsharing" ;

        CompanyDAO companyDAO = new CompanyDAO(database);
        CarDAO carDAO = new CarDAO(database);
        CustomerDAO customerDAO = new CustomerDAO(database);
//        customerDAO.drop();
//        carDAO.drop();
//        companyDAO.drop();
        companyDAO.create();
        carDAO.create();
        customerDAO.create();

        Controller controller = new Controller(database);
        controller.run();
    }
}