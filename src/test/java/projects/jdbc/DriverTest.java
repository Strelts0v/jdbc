package projects.jdbc;

import org.junit.Test;

import java.sql.ResultSet;
import static org.junit.Assert.*;

/**
 * Created by Глеб on 03.08.2016.
 */
public class DriverTest {

    Driver driver = new Driver("jdbc:mysql://127.0.0.1:3306/shop", "root", "4477");

    @Test
    public void executeQuery() throws Exception {
        try{
            ResultSet rS = driver.executeQuery("select * from customers");
            while(rS.next()){
                System.out.println(rS.getString("id_customer") + " | " + rS.getString("name") + " | " + rS.getString("email"));
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void executeInsertion() throws Exception {
        try{
            driver.executeInsertion("insert into customers" +
                    "(name, email) values " +
                    "('Vi', 'vi@gmail.com')");
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void executeDeleting() throws Exception{
        try{
            driver.executeDeleting("delete from customers " +
                    "where customers.name = 'Vi'");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void executePreparedStatement() throws Exception {
        driver.executePreparedStatement(7);
    }

    @Test
    public void insertCustomer() throws Exception {
        driver.insertCustomer("Vika", "vika@gmail.com");
    }

    @Test
    public void greet() throws Exception {
        driver.greet("baby Vi");
    }

    @Test
    public void getEmail() throws Exception {
        driver.getEmail(7);
    }

    @Test
    public void getCustomers() throws Exception {
        ResultSet rS = driver.getCustomers(4);
        while(rS.next()){
            System.out.println(rS.getString("id_customer") + " | " + rS.getString("name") + " | "
                    + rS.getString("email"));
        }
    }

}