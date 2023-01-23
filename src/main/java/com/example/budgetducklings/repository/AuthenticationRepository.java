package com.example.budgetducklings.repository;

import com.example.budgetducklings.db.MysqlDatabase;
import org.springframework.stereotype.Repository;

import java.sql.*;

@Repository
public class AuthenticationRepository {
    private MysqlDatabase database;
    private Connection connection;

    public AuthenticationRepository(){
        database = MysqlDatabase.getInstance();
        connection = database.getConnection();
        initialiseTable();
    }

    //Checks if the table user exist and initialise it if it doesn't
    private void initialiseTable(){
        boolean foundDatabse = false;
        int i = 1;
        try {
            DatabaseMetaData databaseMetaData = connection.getMetaData();
            ResultSet resultSet = databaseMetaData.getTables(null, null, "user", null);
            System.out.println(resultSet.getString("tableNamePattern"));
            while (resultSet.next())
            {
                if (resultSet.getMetaData().getTableName(i).equals("user"))
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

    //Creates a table user
    private void createTable(){
        String sql = "CREATE TABLE user (username VARCHAR(255), password VARCHAR(255))";
        try
        {
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
            initialiseUsers();
        }
        catch (Exception e)
        {

        }
    }

    //Initialise first users
    private void initialiseUsers(){
        createUser("Karin", "123");
        createUser("Per", "123");
        createUser("Sofia", "123");
        createUser("Olof", "123");

    }

    //Creates users
    private void createUser(String username, String password){
        String sql = "INSERT INTO user(username, password) VALUES (?,?)";
        try
        {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.execute();
        }
        catch (Exception e)
        {

        }
    }

    //Gets the password from the username that was written
    public String login(String username){
        String sql = "SELECT password FROM user WHERE username=?";
        try
        {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getString("password");

        }
        catch(Exception e)
        {

        }
        return "";
    }
}
