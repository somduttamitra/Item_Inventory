package com.ecommerce.demo.Controllers;

import com.ecommerce.demo.Model.Item;
import com.ecommerce.demo.Model.Reservation;
import com.ecommerce.demo.Model.Supply;
import com.ecommerce.demo.Repository.ItemRepository;
import com.ecommerce.demo.Service.ItemServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/")
public class ItemController {
    private final ItemServiceImpl itemService;
    private final ItemRepository itemRepository;

    public ItemController(ItemServiceImpl itemService,ItemRepository itemRepository) {
        this.itemService = itemService;
        this.itemRepository =itemRepository;
    }


    @GetMapping("/s")
    public ResponseEntity<String> method(){
        return new ResponseEntity<String>("hello world", HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/addItem")
    public ResponseEntity<String> add(@RequestBody Item i) {
        Item res =itemService.addNewItem(i);
        return new ResponseEntity<>("item created with id: "+res.getId(),HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public List<Item> getAllItems(){
        return itemService.getAllItems();
    }

    @PostMapping("/supply")
    public ResponseEntity<String> supply(@RequestBody Supply s) {
        System.out.println(s);
        Supply result =itemService.supplyItem(s.getItem().getId(), s.getQuantity_suppied());
        if(result == null){
            return new ResponseEntity<String>("Supply couldn't be done", HttpStatus.BAD_REQUEST);
        }
        else{
            return new ResponseEntity<String>("Item id with "+s.getItem().getId()+" is supplied", HttpStatus.OK);
        }

    }

    @PostMapping("/reserve")
    public ResponseEntity<String> reserve(@RequestBody Reservation reservation) {
        Reservation r =itemService.reserveItem(reservation.getItem().getId(), reservation.getQuantity());
        if( r== null){
           return  new ResponseEntity<>("Item is not available for reservation",HttpStatus.BAD_REQUEST);
       }
       return new ResponseEntity<String>("Item "+r.getItem().getName()+" is reserved with reservation Id: "+r.getReservation_id(), HttpStatus.OK);
    }

    @PostMapping("/cancel-reservation")
    public ResponseEntity<String> cancelReservation(@RequestBody Reservation reservation) {
        if(itemService.cancelReservation(reservation.getReservation_id())){
            return new ResponseEntity<String>("Required reservation is cancelled",HttpStatus.OK);
        }
        else{
            return new ResponseEntity<String>("Required reservation can't be cancelled",HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/items/{itemId}/availability")
    public ResponseEntity<String> getAvailability(@PathVariable Long itemId) {
        Integer i = itemService.getAvailableQuantity(itemId);
        if( i != null){
            return new ResponseEntity<String>("The available amount is: "+i,HttpStatus.OK);
        }
        else{
            return new ResponseEntity<String>("The item is not available",HttpStatus.BAD_REQUEST);
        }
    }

}
