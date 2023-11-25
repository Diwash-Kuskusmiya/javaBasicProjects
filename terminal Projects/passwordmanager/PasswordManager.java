import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class PasswordManager {
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/passwordmanager", "root",
                    "");
                    Statement stmt = con.createStatement()) {

                Scanner usageChoice = new Scanner(System.in);
                System.out.println("Enter g to get data or any key to use data: ");

                if (usageChoice.next().toLowerCase().equals("g")) {
                    Scanner userDetailInput = new Scanner(System.in);
                    System.out.println("Enter the name of the table you want to use: ");
                    String tableName = userDetailInput.nextLine();
                    System.out.println("Enter the platform name: ");
                    String platformName = userDetailInput.nextLine();
                    System.out.println("Enter the user id: ");
                    String userId = userDetailInput.nextLine();
                    getData(con, stmt, tableName, platformName, userId);

                } else {
                    Scanner userChoice = new Scanner(System.in);
                    System.out.println("Type 'y' to create new manager table and 'n' use existing table?: ");
                    String userChose = userChoice.next().toLowerCase();

                    if (userChose.equals("y")) {
                        Scanner tableName = new Scanner(System.in);
                        System.out.println("Enter the name of the table: ");
                        createNewManagerTable(con, tableName.nextLine());
                    } else if (userChose.equals("n")) {
                        showAvailableTables(con, stmt);
                    } else {
                        System.out.println("Invalid Input");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void createNewManagerTable(Connection con, String tableName) throws SQLException {
        String createNewTableQuery = "create table if not exists " + tableName
                + " (Platform varchar(50), UserId varchar(50), Password varchar(50))";
        try (PreparedStatement queryFired = con.prepareStatement(createNewTableQuery)) {
            queryFired.executeUpdate();
            System.out.println("Table Created Successfully");

        } catch (SQLException e) {
            System.out.println("Error creating new tables " + e.getMessage());
        }

    }

    private static void showAvailableTables(Connection con, Statement stmt) throws SQLException {
        ResultSet rs = stmt.executeQuery("show tables");
        System.out.println("All the available tables in the database: ");

        while (rs.next()) {
            System.out.println(rs.getString(1));
        }

        Scanner userDetails = new Scanner(System.in);
        System.out.println("Enter the name of the table you want to use: ");
        String tableName = userDetails.nextLine();
        System.out.println("Enter the platform name: ");
        String platformName = userDetails.nextLine();
        System.out.println("Enter user id: ");
        String userId = userDetails.nextLine();
        System.out.println("Enter the account password: ");
        String password = userDetails.nextLine();
        enterDataToTheTable(con, tableName, platformName, userId, password);

    }

    private static void enterDataToTheTable(Connection con, String tableName, String platformName, String userId,
            String password) {
        String enterDataQuery = "insert into " + tableName + " values(?,?,?)";
        try (PreparedStatement queryFired3 = con.prepareStatement(enterDataQuery)) {
            queryFired3.setString(1, platformName);
            queryFired3.setString(2, userId);
            queryFired3.setString(3, password);
            queryFired3.executeUpdate();
            System.out.println("Data posted successfully");

        } catch (Exception e) {
            System.out.println("Error posting data to the database " + e.getMessage());
        }
    }

    private static void getData(Connection con, Statement stmt, String tableName, String platformName, String userId)
            throws SQLException {
        String getDataQuery = "Select * from " + tableName + " where Platform = '" + platformName
                + "' and UserId = '"
                + userId + "'";
        try (ResultSet rs = stmt.executeQuery(getDataQuery);) {

            if (!rs.next()) {
                System.out.println("Invalid details provided plz re-enter the details");

            } else {
                while (rs.next()) {
                    System.out.println("Platform name: " + rs.getString(1) + "\nUser Id: " + rs.getString(2)
                            + "\nPassword: " + rs.getString(3));
                }
            }

        } catch (Exception e) {
            System.out.println("Error while getting data " + e.getMessage());
            // TODO: handle exception
        }

    }
}
