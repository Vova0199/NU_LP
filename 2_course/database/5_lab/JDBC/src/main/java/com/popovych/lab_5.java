package com.popovych;

import java.sql.*;
import java.util.Scanner;

public class lab_5 {
    private static final String url = "jdbc:mysql://localhost:3306/mydb?serverTimezone=UTC&useSSL=false";
    private static final String user = "root";
    private static final String password = "0000";

    private static Connection connection = null;
    private static Statement statement = null;
    private static ResultSet rs = null;
    public static int id = 5;
    public static void main(String args[]) {
        try {
//region    0. This will load the MySQL driver, each DB has its own driver //
            Class.forName("com.mysql.cj.jdbc.Driver");
            //endregion


//region    1. Get a connection to database //
            connection = DriverManager.getConnection(url, user, password);
            //endregion

//region  2. Create a statement
            // Statements allow to issue SQL queries to the database
            statement = connection.createStatement();
            //endregion

//            readData();

//            updateDataCity();

//            insertDataCity();

            DeleteDataCity();

//            CallProcedureForInsertToPersonBook();


        } catch (ClassNotFoundException e) {
            System.out.println("MySQL Driver is not loaded");

        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());

        } finally {
            //close connection ,statement and resultset
            if (rs != null) try {
                rs.close();
            } catch (SQLException e) {
            } // ignore
            if (statement != null) try {
                statement.close();
            } catch (SQLException e) {
            }
            if (connection != null) try {
                connection.close();
            } catch (SQLException e) {
            }
        }
    }

    private static void readData() throws SQLException {
//region    SELECT COUNT(*) FROM Person //
        // 3. executing SELECT query
        rs = statement.executeQuery("SELECT COUNT(*) FROM user");

        // 4. Process the result set
        while (rs.next()) {
            int count = rs.getInt(1);
            // Simply Print the results
            System.out.format("\ncount: %d\n", count);
        }
        //endregion

//region    SELECT * FROM Person //
        // 3. executing SELECT query
        rs = statement.executeQuery("SELECT * FROM user");

        // 4. Process the result set
        System.out.format("\nTable User --------------------\n");
        System.out.format("%3s %-12s %-12s %-10s %s\n", "iduser", "name", "surname", "position", "age");
        while (rs.next()) {
            int iduser = rs.getInt("iduser");
            String name  = rs.getString("name");
            String surname= rs.getString("surname");
            String position = rs.getString("position");
            String age = rs.getString("age");
            // Simply Print the results
            System.out.format("%6d %-12s %-12s %-10s %s\n", iduser, surname, name, position, age);
        }
        //endregion

//region    SELECT * FROM Book //
        // 3. executing SELECT query
        rs = statement.executeQuery("SELECT * FROM gadget");

        // 4. Process the result set
        System.out.format("\nTable gadget --------------------\n");
        System.out.format("%3s %-18s %-18s", "ID", "title", "price");
        while (rs.next()) {
            int id = rs.getInt("idgadget");
            String title = rs.getString("title");
            int price = rs.getInt("price");
            // Simply Print the results
            System.out.format("\n%3d %-18s %-18s", id, title, price);
        }
        //endregion

//region    SELECT * FROM City //
        // 3. executing SELECT query
        rs = statement.executeQuery("SELECT * FROM company");

        // 4. Process the result set
        System.out.format("\n\nTable Company --------------------\n");
        System.out.format("%6s %-12s %-12s %s\n", "idComp", "Name", "date", "location");
        while (rs.next()) {
            int id = rs.getInt("idCompany");
            String name = rs.getString("Name");
            String date = rs.getString("date");
            String location = rs.getString("location");
            // Simply Print the results
            System.out.format("%6d %-12s %-12s %s\n", id, name, date, location);
        }

    }

    private static void updateDataCity() throws SQLException {
        Scanner input = new Scanner(System.in);
        System.out.println("Input name of colum what you want to update: ");
        String oldTitle = input.next();
        System.out.println("Input new name for " + oldTitle);
        String title = input.next();

        // 3. executing SELECT query
// 1
        statement.execute("UPDATE gadget SET title='" + title + "' WHERE title='" + oldTitle + "';");

// 2  Returns count of updated rows
//        int n=statement.executeUpdate("UPDATE oldTitle SET City='"+title+"' WHERE City='"+oldTitle+"';");
//        System.out.println("Count rows that updated: "+n);

// 3  PreparedStatements can use variables and are more efficient
//        PreparedStatement preparedStatement;
//        preparedStatement=connection.prepareStatement("UPDATE oldTitle SET City=? WHERE City=?;");
//        preparedStatement.setString(1, title);
//        preparedStatement.setString(2, oldTitle);
//        int n=preparedStatement.executeUpdate();
//        System.out.println("Count rows that updated: "+n);

    }

    private static void insertDataCity() throws SQLException {

        Scanner input = new Scanner(System.in);
        System.out.println("Input a new name company: ");
        String newCompany = input.next();

        PreparedStatement preparedStatement;
        preparedStatement=connection.prepareStatement("INSERT company VALUES (?,?)");
        preparedStatement.setInt(1, id);
        preparedStatement.setString(2, newCompany);

        int n = preparedStatement.executeUpdate();
        System.out.println("Count rows that inserted: " + n);

    }

    private static void DeleteDataCity() throws SQLException {
        Scanner input = new Scanner(System.in);
        System.out.println("Input a name companyName for delete: ");
        String companyName = input.next();

        // 3. executing SELECT query
        //   PreparedStatements can use variables and are more efficient
        PreparedStatement preparedStatement;
        PreparedStatement unlock;
        rs = statement.executeQuery("SET foreign_key_checks = 0; ");
        preparedStatement = connection.prepareStatement("DELETE FROM company WHERE Name = ?");

        rs = statement.executeQuery("SET foreign_key_checks = 1; ");
        preparedStatement.setString(1, companyName);

        int n = preparedStatement.executeUpdate();
        System.out.println("Count rows that deleted: " + n);
    }

}