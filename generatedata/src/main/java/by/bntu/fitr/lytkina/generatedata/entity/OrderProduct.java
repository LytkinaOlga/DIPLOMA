package by.bntu.fitr.lytkina.generatedata.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "order_product")
public class OrderProduct
{
    @Id
    @Column(name = "id")
    public int id;
    @Column(name = "order_id")
    public int orderId;
    @Column(name = "product_id")
    public int productId;
    @Column(name = "quantity")
    public int quantity;
}
