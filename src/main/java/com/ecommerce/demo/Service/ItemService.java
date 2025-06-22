package com.ecommerce.demo.Service;

import com.ecommerce.demo.Model.Reservation;
import com.ecommerce.demo.Model.Supply;
import org.springframework.stereotype.Service;

@Service
public interface ItemService {
    public Supply supplyItem(Long itemId, int quantity);
    public Reservation reserveItem (Long itemId, int quantity);
    public boolean cancelReservation(Long reservationId);
    public Integer getAvailableQuantity(Long itemId);

}
