package com.ecommerce.demo.Service;

import com.ecommerce.demo.Model.Item;
import com.ecommerce.demo.Model.Reservation;
import com.ecommerce.demo.Model.Supply;
import com.ecommerce.demo.Repository.ItemRepository;
import com.ecommerce.demo.Repository.ReservationRepository;
import com.ecommerce.demo.Repository.SupplyRepository;
import jakarta.persistence.Cacheable;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ItemServiceImpl implements ItemService {

    private ItemRepository itemRepository;
    private ReservationRepository reservationRepository;
    private SupplyRepository supplyRepository;

    public ItemServiceImpl(ItemRepository itemRepository,
                           ReservationRepository reservationRepository,
                           SupplyRepository supplyRepository){
        this.itemRepository= itemRepository;
        this.reservationRepository=reservationRepository;
        this.supplyRepository=supplyRepository;
    }
    @Override
    @Transactional
    public Supply supplyItem(Long itemId, int quantity){

        Optional<Item> i= itemRepository.findById(itemId);
        if(!i.isPresent()){
            //throw new ItemNotFoundException("Product with Id"+ itemId+"name"+itemName+"not found");
            return null;
        }
        else{
            Item item = i.get();
            item.setTotalQuantity(item.getTotalQuantity()+quantity);
            itemRepository.save(item);

            Supply supply = new Supply();
            supply.setItem(item);
            supply.setSupply_date(LocalDateTime.now());
            supply.setQuantity_suppied(quantity);
            supplyRepository.save(supply);

            return supply;
        }

    }
    @Override
    @Transactional

    public Reservation reserveItem (Long itemId, int quantity){
        Optional<Item> i= itemRepository.findById(itemId);
        if(i.isEmpty()){
            String itemName = itemRepository.findNameById(itemId);
            //throw new ItemNotFoundException("Product with Id"+ itemId+"name"+itemName+"not found");
            return null;
        }
        else {
            Item item = i.get();
            if (item.getTotalQuantity() - item.getReservedQuantity() >= quantity) {
                item.setReservedQuantity(item.getReservedQuantity() + quantity);
                itemRepository.save(item);
                Reservation reservation = new Reservation();
                reservation.setItem(item);
                reservation.setCreated_at(LocalDateTime.now());
                reservation.setQuantity(quantity);
                reservationRepository.save(reservation);
                return reservation;
            } else {
                return null;
            }
        }
    }


    @Transactional
    @Override
    public boolean cancelReservation(Long reservationId) {
        Optional<Reservation> r = reservationRepository.findById(reservationId);
        if(r.isEmpty()){
            return false;
        }
        else{
            Reservation reservation = r.get();
            if(reservation.getStatus().equals("Cancelled")){
                return false;
            }
            else{
                reservation.setStatus("Cancelled");
                Item item = reservation.getItem();
                item.setReservedQuantity(item.getReservedQuantity()-reservation.getQuantity());
                return true;
            }
        }

    }

    @Override
    @Transactional
    public Integer getAvailableQuantity(Long itemId){
        Optional<Item> i = itemRepository.findById(itemId);
        if(i.isEmpty()){
           // throw new ItemNotFoundException("Product with id"+ itemId+" not found");
            return null;
        }
        else{
            Item item = i.get();
            int availableQty = item.getTotalQuantity()-item.getReservedQuantity();
            return  availableQty <=0 ? null : availableQty;
        }
    }
}
