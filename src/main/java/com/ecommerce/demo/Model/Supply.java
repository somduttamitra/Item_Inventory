package com.ecommerce.demo.Model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
public class Supply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int supplyId;
    private int quantity_suppied;
    private LocalDateTime supply_date = LocalDateTime.now();
    @ManyToOne
    private Item item;

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public LocalDateTime getSupply_date() {
        return supply_date;
    }

    public void setSupply_date(LocalDateTime supply_date) {
        this.supply_date = supply_date;
    }

    public int getSupplyId() {
        return supplyId;
    }

    public void setSupplyId(int supplyId) {
        this.supplyId = supplyId;
    }

    public int getQuantity_suppied() {
        return quantity_suppied;
    }

    public void setQuantity_suppied(int quantity_suppied) {
        this.quantity_suppied = quantity_suppied;
    }


}
