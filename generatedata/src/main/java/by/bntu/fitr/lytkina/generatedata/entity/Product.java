package by.bntu.fitr.lytkina.generatedata.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "product")
public class Product
{
    @Id
    @Column(name = "id")
    public int id;
    @Column(name = "name")
    public String name;
    @Column(name = "price")
    public double price;
}
