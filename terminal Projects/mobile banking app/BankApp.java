// import java.util.Scanner;
// import java.sql.*;
// import java.util.Random;

// public class BankApp {
//     public static void main(String[] args) throws Exception {
//         Class.forName("com.mysql.cj.jdbc.Driver");
//         Connection con = DriverManager.getConnection(
//                 "jdbc:mysql://localhost:3306/mobilebankdata", "root", "");
//         Statement stmt = con.createStatement();
//         ResultSet rs = stmt.executeQuery("select * from mobileapp");
//         System.out.println("Welcome To Log In Page");
//         Scanner enterChoice = new Scanner(System.in);
//         System.out.println("Type 1: To Log In");
//         System.out.println("Type 2: To Create New Accoutn");

//         if (enterChoice.nextInt() != 1) {

//             Scanner newUserDetails = new Scanner(System.in);
//             System.out.println("Enter your account name: ");
//             String newUserAccountName = newUserDetails.nextLine();
//             System.out.println("Enter your gender(male/female)? ");
//             String newUserGender = newUserDetails.nextLine();
//             System.out.println("Enter minimum 4 and max8 digit pincode: ");
//             int newUserPinCode = newUserDetails.nextInt();
//             Random random = new Random();

//             // Generate a random 8-digit number
//             int min = 10000000; // Minimum 8-digit number
//             int max = 99999999; // Maximum 8-digit number
//             int newUserAccountNumber = random.nextInt(max - min + 1) + min;

//             String queryNewUser = "Insert into mobileapp values(?,?,?,?,?)";

//             try (PreparedStatement npstmt = con.prepareStatement(queryNewUser)) {
//                 npstmt.setInt(1, newUserAccountNumber);
//                 npstmt.setInt(2, newUserPinCode);
//                 npstmt.setInt(3, 0);
//                 npstmt.setString(4, newUserAccountName);
//                 npstmt.setString(5, newUserGender);
//                 npstmt.executeUpdate();
//                 System.out.println("Account Created Successfully");
//                 System.out.println("Your Accout Number is: " + newUserAccountNumber);

//             } catch (SQLException e) {
//                 System.out.println(e);
//             }

//         } else {
//             Scanner userDetail = new Scanner(System.in);
//             System.out.println("Enter your account number: ");
//             int AccountNumber = userDetail.nextInt();
//             System.out.println("Enter your pin code");
//             int AccountPinCode = userDetail.nextInt();
//             boolean accessState = false;

//             while (rs.next()) {
//                 if (AccountNumber == rs.getInt(1) && AccountPinCode == rs.getInt(2)) {
//                     System.out.println("Welcome " + rs.getString(4));
//                     AppFeature app = new AppFeature();
//                     switch (app.Menu()) {
//                         case 1:
//                             System.out.println("You have Rs: " + rs.getInt(3) + " in you account");
//                             break;
//                         case 2:
//                             Scanner withDrawl = new Scanner(System.in);
//                             System.out.println("Enter withdraw amount: ");
//                             int withdrawAmount = withDrawl.nextInt();
//                             if (withdrawAmount < rs.getInt(3)) {
//                                 double newBalance = rs.getInt(3) - withdrawAmount;
//                                 String query = "update mobileapp set Balance = ? where AccountNumber = ?";
//                                 try (PreparedStatement pstmt = con.prepareStatement(query)) {
//                                     pstmt.setDouble(1, newBalance);
//                                     pstmt.setInt(2, AccountNumber);
//                                     pstmt.executeUpdate();
//                                 } catch (SQLException e) {
//                                     e.printStackTrace();
//                                 }

//                                 System.out.println("Successfully withdrawn " + withdrawAmount);
//                                 System.out.println("New balance: " + newBalance);
//                             } else {
//                                 System.out.println("Sorry Insufficient Balance");
//                             }

//                         case 3:
//                             Scanner depositeInput = new Scanner(System.in);
//                             System.out.println("Enter the amount: ");
//                             int depositeAmount = depositeInput.nextInt();
//                             String setNewBalance = "Update mobileapp set Balance = ? where AccountNumber = ?";
//                             try (PreparedStatement pstmt1 = con.prepareStatement(setNewBalance)) {

//                                 pstmt1.setDouble(1, depositeAmount + rs.getInt(3));
//                                 pstmt1.setInt(2, AccountNumber);
//                                 pstmt1.executeUpdate();
//                                 System.out.println("RS " + depositeAmount + " deposited");

//                             } catch (SQLException e) {
//                                 e.printStackTrace();
//                             }
//                             break;

//                         default:
//                             System.out.println("Have A Great Day, Bye");
//                             break;
//                     }

//                     accessState = true;
//                     break;
//                 }

//             }

//             if (!accessState) {
//                 System.out.println("Either Account number or pin code is incorrect");
//             }
//             con.close();

//         }

//     }
// }

// class AppFeature {

//     int Menu() {
//         System.out.println("Type1: To check balance");
//         System.out.println("Type2: To withdraw cash");
//         System.out.println("Type3: To deposite cash");
//         System.out.println("Type4: To exit");
//         Scanner userChoice = new Scanner(System.in);

//         return userChoice.nextInt();

//     }

// }

import java.util.Scanner;
import java.sql.*;
import java.util.Random;

public class BankApp {
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mobilebankdata", "root", "");
                    Statement stmt = con.createStatement()) {

                Scanner enterChoice = new Scanner(System.in);
                System.out.println("Type 1: To Log In\nType 2: To Create New Account");

                if (enterChoice.nextInt() != 1) {
                    createNewAccount(con);
                } else {
                    logIn(stmt);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void createNewAccount(Connection con) throws SQLException {
        Scanner newUserDetails = new Scanner(System.in);
        System.out.println("Enter your account name: ");
        String newUserAccountName = newUserDetails.nextLine();
        System.out.println("Enter your gender(male/female)? ");
        String newUserGender = newUserDetails.nextLine();
        System.out.println("Enter minimum 4 and max 8 digit pin code: ");
        int newUserPinCode = newUserDetails.nextInt();

        Random random = new Random();
        int min = 10000000;
        int max = 99999999;
        int newUserAccountNumber = random.nextInt(max - min + 1) + min;

        String queryNewUser = "INSERT INTO mobileapp VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement npstmt = con.prepareStatement(queryNewUser)) {
            npstmt.setInt(1, newUserAccountNumber);
            npstmt.setInt(2, newUserPinCode);
            npstmt.setInt(3, 0);
            npstmt.setString(4, newUserAccountName);
            npstmt.setString(5, newUserGender);
            npstmt.executeUpdate();

            System.out.println("Account Created Successfully");
            System.out.println("Your Account Number is: " + newUserAccountNumber);

        } catch (SQLException e) {
            System.out.println("Error creating a new account: " + e.getMessage());
        }
    }

    private static void logIn(Statement stmt) throws SQLException {
        Scanner userDetail = new Scanner(System.in);
        System.out.println("Enter your account number: ");
        int AccountNumber = userDetail.nextInt();
        System.out.println("Enter your pin code");
        int AccountPinCode = userDetail.nextInt();

        ResultSet rs = stmt.executeQuery("SELECT * FROM mobileapp");

        boolean accessState = false;

        while (rs.next()) {
            if (AccountNumber == rs.getInt(1) && AccountPinCode == rs.getInt(2)) {
                System.out.println("Welcome " + rs.getString(4));
                AppFeature app = new AppFeature();
                switch (app.Menu()) {
                    case 1:
                        System.out.println("You have Rs: " + rs.getInt(3) + " in your account");
                        break;
                    case 2:
                        performWithdrawal(rs, stmt);
                        break;
                    case 3:
                        performDeposit(rs, stmt);
                        break;
                    default:
                        System.out.println("Have A Great Day, Bye");
                        break;
                }
                accessState = true;
                break;
            }
        }

        if (!accessState) {

            System.out.println("Either Account number or pin code is incorrect");

        }

    }

    private static void performWithdrawal(ResultSet rs, Statement stmt) throws SQLException {
        Scanner withDrawl = new Scanner(System.in);
        System.out.println("Enter withdrawal amount: ");
        int withdrawAmount = withDrawl.nextInt();
        if (withdrawAmount < rs.getInt(3)) {
            double newBalance = rs.getInt(3) - withdrawAmount;
            String query = "UPDATE mobileapp SET Balance = ? WHERE AccountNumber = ?";
            try (PreparedStatement pstmt = stmt.getConnection().prepareStatement(query)) {
                pstmt.setDouble(1, newBalance);
                pstmt.setInt(2, rs.getInt(1));
                pstmt.executeUpdate();
                System.out.println("Successfully withdrawn " + withdrawAmount);
                System.out.println("New balance: " + newBalance);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Sorry, Insufficient Balance");
        }
    }

    private static void performDeposit(ResultSet rs, Statement stmt) throws SQLException {
        Scanner depositInput = new Scanner(System.in);
        System.out.println("Enter the amount: ");
        int depositAmount = depositInput.nextInt();
        String setNewBalance = "UPDATE mobileapp SET Balance = ? WHERE AccountNumber = ?";
        try (PreparedStatement pstmt1 = stmt.getConnection().prepareStatement(setNewBalance)) {
            pstmt1.setDouble(1, depositAmount + rs.getInt(3));
            pstmt1.setInt(2, rs.getInt(1));
            pstmt1.executeUpdate();
            System.out.println("RS " + depositAmount + " deposited");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

class AppFeature {
    int Menu() {
        System.out.println("Type 1: To check balance");
        System.out.println("Type 2: To withdraw cash");
        System.out.println("Type 3: To deposit cash");
        System.out.println("Type 4: To exit");
        Scanner userChoice = new Scanner(System.in);
        return userChoice.nextInt();
    }
}
