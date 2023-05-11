package bank;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DataSource {
  
  public static Connection connect() {
    String db_file = "jdbc:sqlite:resources/bank.db";
    Connection connection = null; 
    
    try {
      connection = DriverManager.getConnection(db_file);
      System.out.println("We're connected");
    } catch(SQLException e) {
      e.printStackTrace();
    }
    return connection;
    
  }
// Never send raw user input as part of a database query, it could be malicious input
  public static Customer getCustomer(String username) {
    String sql = "SELECT * FROM customers WHERE username = ?"; //Question mark is a placeholder
    Customer customer = null;
    try(Connection connection = connect(); 
      PreparedStatement statement = connection.prepareStatement(sql)) {

        statement.setString(1, username);
        try(ResultSet resultSet = statement.executeQuery()) {
          // Instantiate the customer object
          customer = new Customer(
            resultSet.getInt("id"), 
            resultSet.getString("name"),
            resultSet.getString("username"),
            resultSet.getString("password"),
            resultSet.getInt("account_id"));
        }

    } catch(SQLException e) {
      e.printStackTrace();
    }
    return customer;
  }

  public static Account getAccount(int accountId) {
    String sql = "SELECT * FROM accounts WHERE id = ?";
    Account account = null;

    try(Connection connection = connect(); 
      PreparedStatement statement = connection.prepareStatement(sql)) {
      statement.setInt(1, accountId);
      try(ResultSet resultSet = statement.executeQuery()) {
        account = new Account(
          resultSet.getInt("id"),
          resultSet.getString("type"),
          resultSet.getDouble("balance"));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return account;
  }

  public static void main(String[] args) {
  Customer customer = getCustomer("twest8o@friendfeed.com");
  System.out.println(customer.getName());

  Account account = getAccount(customer.getAccountId());
  System.out.println(account.getBalance());
  }
}
