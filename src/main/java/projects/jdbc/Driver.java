package projects.jdbc;

import java.sql.*;

/**
 * @author Gleb Streltsov
 * @since 31.07.2016
 */
public class Driver {

    private Connection connection;

    /**
     * Get connection to the database according to @params url, username, password
     * throws exception if params ara invalid.
     * @param url - location of database.
     * @param username - user of database.
     * @param password - password for database.
     */
    public Driver(String url, String username, String password){
        try {
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * Execute select statement, which describes in @param query.
     * @param query select statement for querying database.
     * @return ResultSet of returning elements from database
     * or null there is nothing to return
     */
    public ResultSet executeQuery(String query){
        ResultSet rS = null;
        try {
            Statement st = connection.createStatement();
            rS = st.executeQuery(query);
        } catch (SQLException e){
            e.printStackTrace();
        }
        System.out.println("Query is complete.");
        return rS;
    }

    /**
     * Method insert data into database.
     * @param insertion - query, which has signature insertion x from y where z;
     * x, y, z - are sets for querying database.
     */
    public void executeInsertion(String insertion){
        try{
            Statement st = connection.createStatement();
            st.executeUpdate(insertion);
        } catch (SQLException e){
            e.printStackTrace();
        }
        System.out.println("Insertion is complete.");
    }

    /**
     * Method deletes rows from connected database according deleting SQL
     * statement @param deleting
     * @param deleting - SQL statement.
     */
    public int executeDeleting(String deleting){
        int rowsAffected = 0;
        try{
            Statement st = connection.createStatement();
            rowsAffected = st.executeUpdate(deleting);
        } catch (SQLException e){
            e.printStackTrace();
        }
        System.out.println("Rows affected: " + rowsAffected);
        System.out.println("Deleting is complete.");
        return rowsAffected;
    }

    public void executePreparedStatement(int id_customer){
        try{
            PreparedStatement pSt = connection.prepareStatement("select * from customers where id_customer >= ?");
            pSt.setInt(1, id_customer);
            ResultSet rS = pSt.executeQuery();
            while(rS.next()){
                System.out.println(rS.getString("id_customer") + " | " +
                        rS.getString("name") + " | " + rS.getString("email"));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    // IN parameter
    public void insertCustomer(String name, String email){
        try {
            PreparedStatement pSt = connection.prepareCall("call insert_customer(?, ?)");
            pSt.setString(1, name);
            pSt.setString(2, email);
            pSt.execute();
        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    // INOUT parameters
    public void greet(String person){
        try{
            // 1. creating callable statement
            CallableStatement cSt = connection.prepareCall("call greet_person(?)");

            // 2. set parameters for callable statement
            cSt.registerOutParameter(1, Types.VARCHAR);
            cSt.setString(1, person);

            // 3. execution and printing
            cSt.execute();
            System.out.println(cSt.getString(1));
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    // OUT parameter
    public void getEmail(int id_customer){
        try{
            CallableStatement cSt = connection.prepareCall("call get_email(?,?)");
            cSt.setInt(1, id_customer);
            cSt.registerOutParameter(2, Types.VARCHAR);
            cSt.execute();
            System.out.println(cSt.getString(2));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // returning ResultSet
    public ResultSet getCustomers(int i){
        try{
            CallableStatement cSt = connection.prepareCall("call get_customers(?)");
            cSt.setInt(1,i);
            cSt.execute();
            return cSt.getResultSet();
        }catch(SQLException e){
            System.err.println(e);
        }
        return null;
    }

    public static void main(String[] args) {
        try {
            // 1. Connection to database
            Connection myConn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/shop", "root", "4477");

            // 2. Create a statement object
            Statement st = myConn.createStatement();

            // 3. Execute SQL query
            ResultSet rS = st.executeQuery("select * from customers");

            // 4. Process ResultSet
            while(rS.next()){
                System.out.println(rS.getInt("id_customer") + ", " + rS.getString("name") + ", " + rS.getString("email"));
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
