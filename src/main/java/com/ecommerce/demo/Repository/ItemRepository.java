package com.ecommerce.demo.Repository;

import com.ecommerce.demo.Model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
   // public Item findByName(String name);
    public String findNameById(Long id);
   // public Item findIdByReservationId(Long reservationId);
}
