package com.example.budgetducklings.repository;

import com.example.budgetducklings.db.MysqlDatabase;
import com.example.budgetducklings.model.Payment;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;

@Repository
public class InvoiceRepository {
    private MysqlDatabase database;
    private Connection connection;

    public InvoiceRepository(){
        database = MysqlDatabase.getInstance();
        connection = database.getConnection();
        initialiseTable();
    }

    //Checks if the table payment already exists, if not, create one
    private void initialiseTable(){
        boolean foundDatabse = false;
        int i = 1;
        try {
            DatabaseMetaData databaseMetaData = connection.getMetaData();
            ResultSet resultSet = databaseMetaData.getTables(null, null, "payment", null);
            System.out.println(resultSet.getString("tableNamePattern"));
            while (resultSet.next())
            {
                if (resultSet.getMetaData().getTableName(i).equals("payment"))
                {
                    foundDatabse = true;
                }
                i++;
            }
        }
        catch (Exception e)
        {

        }

        if(!foundDatabse)
        {
            createTable();
        }
    }

    //Creates a table
    private void createTable(){
        String sql = "CREATE TABLE payment (id INT AUTO_INCREMENT PRIMARY KEY, username VARCHAR(255),title VARCHAR(255), date VARCHAR(255)," +
                " description VARCHAR(255), category VARCHAR(255), price VARCHAR(255))";
        try
        {
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
        }
        catch (Exception e)
        {

        }
    }

    //Creates a payment in the database
    public void createPayment(Payment payment){
        String sql = "INSERT INTO payment(username, title, date, description, category, price) VALUES(?,?,?,?,?,?)";
        try
        {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, payment.getUsername());
            preparedStatement.setString(2, payment.getTitle());
            preparedStatement.setString(3, payment.getDate());
            preparedStatement.setString(4, payment.getDescription());
            preparedStatement.setString(5, payment.getCategory());
            preparedStatement.setString(6, payment.getPrice());
            preparedStatement.execute();
        }
        catch(Exception e)
        {

        }
    }

    //Gets all Payment from a username and returns an ArrayList<Payment>
    public ArrayList<Payment> getPayments(String username){
        ArrayList<Payment> foundPayments = new ArrayList<>();
        String sql = "SELECT * FROM payment WHERE username=?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next())
            {
                Payment payment = new Payment();
                payment.setUsername(resultSet.getString("username"));
                payment.setTitle(resultSet.getString("title"));
                payment.setDate(resultSet.getString("date"));
                payment.setDescription(resultSet.getString("description"));
                payment.setCategory(resultSet.getString("category"));
                payment.setPrice(resultSet.getString("price"));
                payment.setId(resultSet.getInt("id"));
                foundPayments.add(payment);
            }
        }
        catch(Exception e)
        {

        }
        return foundPayments;
    }

    //Updates a payment based on id
    public void updatePayment(Payment payment){
        String sql = "UPDATE payment SET username=?, title=?, date=?, description=?, category=?, price=? " +
                "WHERE id=?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, payment.getUsername());
            preparedStatement.setString(2, payment.getTitle());
            preparedStatement.setString(3, payment.getDate());
            preparedStatement.setString(4, payment.getDescription());
            preparedStatement.setString(5, payment.getCategory());
            preparedStatement.setString(6, payment.getPrice());
            preparedStatement.setInt(7, payment.getId());
            preparedStatement.execute();

        }
        catch (Exception e)
        {

        }

    }

    //Deletes a payment based on username and id
    public void deletePayment(String username, int id)
    {
        String sql = "DELETE FROM payment WHERE username=? AND id=?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            preparedStatement.setInt(2, id);
            preparedStatement.execute();
        }
        catch (Exception e)
        {

        }

    }

    //Gets one payment based on username and id and returns a Payment
    public Payment getOnePayment(String username, int id){
        Payment payment = new Payment();
        String sql = "SELECT * FROM payment WHERE username=? AND id=?";
        try
        {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            preparedStatement.setInt(2, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            //If there is nothing found
            if (!resultSet.next())
            {
                return null;
            }
            payment.setUsername(resultSet.getString("username"));
            payment.setTitle(resultSet.getString("title"));
            payment.setDate(resultSet.getString("date"));
            payment.setDescription(resultSet.getString("description"));
            payment.setCategory(resultSet.getString("category"));
            payment.setPrice(resultSet.getString("price"));
            payment.setId(resultSet.getInt("id"));
        }

        catch (Exception e)
        {

        }

        return payment;

    }
}
