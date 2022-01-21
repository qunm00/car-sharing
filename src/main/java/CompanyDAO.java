import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class CompanyDAO {
    private String database;

    public CompanyDAO(String database) {
        this.database = "jdbc:h2:file:" + database;
    }

    public void drop() {
        try (Connection connection = DriverManager.getConnection(this.database)) {
            Statement statement = connection.createStatement();
            statement.execute(
                    "DROP TABLE COMPANY"
            );
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void create() {
        try (Connection connection = DriverManager.getConnection(this.database)) {
            Statement statement = connection.createStatement();
            statement.execute(
                    "CREATE TABLE COMPANY (" +
                            "ID INTEGER AUTO_INCREMENT PRIMARY KEY," +
                            "NAME VARCHAR UNIQUE NOT NULL" +
                            ");"
            );
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public int insert(String companyName) {
        int affectedRows = 0;
        try (Connection connection = DriverManager.getConnection(this.database)) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO COMPANY (name) VALUES (?)"
            );
            preparedStatement.setString(1, companyName);
            affectedRows = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return affectedRows;
    }

    public Map<Integer, String> selectAll() {
        Map<Integer, String> resultMap = new HashMap<>();
        try (Connection connection = DriverManager.getConnection(this.database)) {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(
                    "SELECT * FROM COMPANY"
            );
            while (result.next()) {
                resultMap.put(result.getInt("id"), result.getString("name"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return resultMap;
    }

    public String getCompanyInfo(int companyId) {
        String companyName = null;
        try (Connection connection = DriverManager.getConnection(this.database)) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT NAME FROM COMPANY WHERE ID = ?"
            );
            preparedStatement.setInt(1, companyId);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            companyName = resultSet.getString("NAME");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return companyName;
    }
}