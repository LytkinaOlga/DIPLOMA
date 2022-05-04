package by.bntu.fitr.lytkina.generatedata;

import by.bntu.fitr.lytkina.generatedata.entity.Customer;
import by.bntu.fitr.lytkina.generatedata.entity.Order;
import by.bntu.fitr.lytkina.generatedata.entity.OrderProduct;
import by.bntu.fitr.lytkina.generatedata.entity.Product;
import com.vaadin.exampledata.DataType;
import com.vaadin.exampledata.ExampleDataGenerator;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Random;

@SpringBootApplication
public class GenerateDataApplication implements CommandLineRunner
{

    @Autowired
    public JdbcTemplate jdbcTemplate;

    public static void main(String[] args)
    {
        SpringApplication.run(GenerateDataApplication.class, args);
    }

    @Override
    public void run(String... args)
    {
//		String sql = "SELECT * FROM product";

        System.out.println("Start");
//        List<Product> generatedProducts = generateProducts(900);
//        List<Customer> generatedCustomer = generateCustomers(900);
//        List<Order> generatedOrders = generateOrders(1000);
        List<OrderProduct> generatedOderProduct = generateOrderProduct(1000);


//        insertGeneratedProductsList(generatedProducts);
//        insertGeneratedCustomersList(generatedCustomer);
//        insertGeneratedOrdersList(generatedOrders);
        insertGeneratedOrderProductList(generatedOderProduct);
        System.out.println("Finish");
    }

    public List<Product> generateProducts(int count)
    {
        var generator = new ExampleDataGenerator<>(Product.class, LocalDateTime.now());
        generator.setData(Product::setName, DataType.FOOD_PRODUCT_NAME);
        generator.setData(Product::setPrice, DataType.PRICE);
        return generator.create(count, new Random().nextInt());
    }

    public List<Customer> generateCustomers(int count)
    {
        var generator = new ExampleDataGenerator<>(Customer.class, LocalDateTime.now());
        generator.setData(Customer::setFirstname, DataType.FIRST_NAME);
        generator.setData(Customer::setLastname, DataType.LAST_NAME);
        return generator.create(count, new Random().nextInt());
    }

    public List<Order> generateOrders(int count)
    {
        var generator = new ExampleDataGenerator<>(Order.class, LocalDateTime.now());
        generator.setData(Order::setOrderDate, DataType.DATE_LAST_1_YEAR);
        generator.setData(Order::setCustomerId, DataType.NUMBER_UP_TO_1000);
        return generator.create(count, new Random().nextInt());
    }

    public List<OrderProduct> generateOrderProduct(int count)
    {
        var generator = new ExampleDataGenerator<>(OrderProduct.class, LocalDateTime.now());
        generator.setData(OrderProduct::setOrderId, DataType.NUMBER_UP_TO_1000);
        generator.setData(OrderProduct::setProductId, DataType.NUMBER_UP_TO_1000);
        generator.setData(OrderProduct::setQuantity, DataType.NUMBER_UP_TO_10);
        return generator.create(count, new Random().nextInt());
    }

    public void insertGeneratedProductsList(List<Product> products)
    {
        String sqlForUpdate = "insert into product(name, price) values (?, ?)";
        jdbcTemplate.batchUpdate(sqlForUpdate,
                                 new BatchPreparedStatementSetter()
                                 {

                                     public void setValues(PreparedStatement ps, int i)
                                         throws SQLException
                                     {
                                         ps.setString(1, products.get(i).getName());
                                         ps.setDouble(2, products.get(i).getPrice());
                                     }

                                     public int getBatchSize()
                                     {
                                         return products.size();
                                     }
                                 });
    }

    public void insertGeneratedCustomersList(List<Customer> customers)
    {
        String sqlForUpdate = "insert into customer(firstname, lastname) values (?, ?)";
        jdbcTemplate.batchUpdate(sqlForUpdate,
                                 new BatchPreparedStatementSetter()
                                 {

                                     public void setValues(PreparedStatement ps, int i)
                                         throws SQLException
                                     {
                                         ps.setString(1, customers.get(i).getFirstname());
                                         ps.setString(2, customers.get(i).getLastname());
                                     }

                                     public int getBatchSize()
                                     {
                                         return customers.size();
                                     }
                                 });
    }

    public void insertGeneratedOrdersList(List<Order> orders)
    {
        String sqlForUpdate = "insert into orders(order_date, customer_id) values (?, ?)";
        jdbcTemplate.batchUpdate(sqlForUpdate,
                                 new BatchPreparedStatementSetter()
                                 {

                                     public void setValues(PreparedStatement ps, int i)
                                         throws SQLException
                                     {
                                         ps.setDate(1, Date.valueOf(orders.get(i).getOrderDate()));
                                         ps.setInt(2, orders.get(i).getCustomerId());
                                     }

                                     public int getBatchSize()
                                     {
                                         return orders.size();
                                     }
                                 });
    }

    public void insertGeneratedOrderProductList(List<OrderProduct> orderProduct)
    {
        String sqlForUpdate = "insert into order_product(order_id, product_id, quantity) values (?, ?, ?)";
        jdbcTemplate.batchUpdate(sqlForUpdate,
                                 new BatchPreparedStatementSetter()
                                 {

                                     public void setValues(PreparedStatement ps, int i)
                                         throws SQLException
                                     {
                                         ps.setInt(1, orderProduct.get(i).getOrderId());
                                         ps.setInt(2, orderProduct.get(i).getProductId());
                                         ps.setInt(3, orderProduct.get(i).getQuantity());
                                     }

                                     public int getBatchSize()
                                     {
                                         return orderProduct.size();
                                     }
                                 });
    }
}
