package by.bntu.fitr.lytkina.generatedata.entity;


import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Data
@Table(name = "orders")
public class Order
{
    @Id
    @Column(name = "id")
    public int id;
    @Column(name = "order_date")
    public LocalDate orderDate;
    @Column(name = "customer_id")
    public int customerId;
}
