import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class CustomerDAO {
    private String database;

    public CustomerDAO(String database) {
        this.database = "jdbc:h2:file:" + database;
    }

    public void drop() {
        try (Connection connection = DriverManager.getConnection(this.database)) {
            Statement statement = connection.createStatement();
            statement.execute(
                    "DROP TABLE CUSTOMER"
            );
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void create() {
        try (Connection connection = DriverManager.getConnection(this.database)) {
            Statement statement = connection.createStatement();
            statement.execute(
                    "CREATE TABLE CUSTOMER (" +
                            "ID INTEGER AUTO_INCREMENT PRIMARY KEY," +
                            "NAME VARCHAR UNIQUE NOT NULL," +
                            "RENTED_CAR_ID INTEGER DEFAULT NULL," +
                            "CONSTRAINT fk_rentedCar FOREIGN KEY (RENTED_CAR_ID)" +
                            "REFERENCES CAR(ID)" +
                            ");"
            );
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public int insert(String customerName) {
        int result = 0;
        try (Connection connection = DriverManager.getConnection(this.database)) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO CUSTOMER (name) VALUES (?)"
            );
            preparedStatement.setString(1, customerName);
            result = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    public Map<Integer, String> select() {
        Map<Integer, String> resultMap = new HashMap<>();
        try (Connection connection = DriverManager.getConnection(this.database)) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(
                    "SELECT * FROM CUSTOMER"
            );
            while (resultSet.next()) {
                resultMap.put(resultSet.getInt("id"), resultSet.getString("name"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return resultMap;
    }

    public int setCar(int carId, int customerId) {
        int affectedRows = 0;
        try (Connection connection = DriverManager.getConnection(this.database)) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "UPDATE CUSTOMER SET RENTED_CAR_ID = ? WHERE ID = ? AND RENTED_CAR_ID IS NULL"
            );
            preparedStatement.setInt(1, carId);
            preparedStatement.setInt(2, customerId);
            affectedRows = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return affectedRows;
    }

    public int setCar(int customerId) {
        int affectedRows = 0;
        try (Connection connection = DriverManager.getConnection(this.database)) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "UPDATE CUSTOMER SET RENTED_CAR_ID = NULL WHERE ID = ? AND RENTED_CAR_ID IS NOT NULL"
            );
            preparedStatement.setInt(1, customerId);
            affectedRows = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return affectedRows;
    }


    public int getCarId(int customerId) {
        int carId = 0;
        try (Connection connection = DriverManager.getConnection(this.database)) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT RENTED_CAR_ID FROM CUSTOMER WHERE ID = ? AND RENTED_CAR_ID IS NOT NULL"
            );
            preparedStatement.setInt(1, customerId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                carId = resultSet.getInt("RENTED_CAR_ID");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return carId;
    }
}
