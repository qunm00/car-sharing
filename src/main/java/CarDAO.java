import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CarDAO {
    private String database;

    public CarDAO(String database) {
        this.database = "jdbc:h2:file:" + database;
    }

    public void drop() {
        try (Connection connection = DriverManager.getConnection(this.database)) {
            Statement statement = connection.createStatement();
            statement.execute(
                    "DROP TABLE CAR"
            );
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void create() {
        try (Connection connection = DriverManager.getConnection(this.database)) {
            Statement statement = connection.createStatement();
            statement.execute(
                    "CREATE TABLE CAR (" +
                            "ID INTEGER AUTO_INCREMENT PRIMARY KEY," +
                            "NAME VARCHAR UNIQUE NOT NULL," +
                            "COMPANY_ID INTEGER NOT NULL," +
                            "CONSTRAINT fk_company FOREIGN KEY (COMPANY_ID)" +
                            "REFERENCES COMPANY(ID)" +
                            ");"
            );
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public int insert(String[] values) {
        int affectedRows = 0;
        try (Connection connection = DriverManager.getConnection(this.database)) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO CAR (NAME, COMPANY_ID) VALUES (?, ?)"
            );
            preparedStatement.setString(1, values[0]);
            preparedStatement.setInt(2, Integer.parseInt(values[1]));
            affectedRows = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return affectedRows;
    }

    public List<String> select(int id) {
        List<String> resultList = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(this.database)) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM CAR WHERE COMPANY_ID = ?"
            );
            preparedStatement.setInt(1, id);
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                resultList.add(result.getString("name"));
                ;            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return resultList;
    }

    public List<String> selectAvailableCars(int id) {
        List<String> resultList = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(this.database)) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT NAME FROM CAR WHERE COMPANY_ID = ? AND ID NOT IN (" +
                            "SELECT RENTED_CAR_ID FROM CUSTOMER WHERE RENTED_CAR_ID IS NOT NULL" +
                            ")"
            );
            preparedStatement.setInt(1, id);
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                resultList.add(result.getString("name"));
                ;            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return resultList;
    }


    public List<String> getCarInfo(int carId) {
        List<String> carInfo = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(this.database)) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT NAME, COMPANY_ID FROM CAR WHERE ID = ?"
            );
            preparedStatement.setInt(1, carId);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            carInfo.add(resultSet.getString("NAME"));
            carInfo.add(String.valueOf(resultSet.getInt("COMPANY_ID")));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return carInfo;
    }

    public int getCarId(String carName) {
        int carId = 0;
        try (Connection connection = DriverManager.getConnection(this.database)) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT ID FROM CAR WHERE NAME = ?"
            );
            preparedStatement.setString(1, carName);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            carId = resultSet.getInt("ID");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return carId;
    }
}
