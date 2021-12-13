package com.BackPrimeflix.controller;

import com.BackPrimeflix.constants.ResponseCode;
import com.BackPrimeflix.constants.WebConstants;
import com.BackPrimeflix.dto.DiscountDto;
import com.BackPrimeflix.dto.InventoryDto;
import com.BackPrimeflix.dto.UserDto;
import com.BackPrimeflix.exception.AccountCustomException;
import com.BackPrimeflix.exception.PersonCrudCustomException;
import com.BackPrimeflix.exception.UserNotAllowedException;
import com.BackPrimeflix.model.*;
import com.BackPrimeflix.response.AlertObject;
import com.BackPrimeflix.response.DiscountResponse;
import com.BackPrimeflix.response.InventoryResponse;
import com.BackPrimeflix.response.UserResponse;
import com.BackPrimeflix.util.service.InventoryService;
import com.BackPrimeflix.util.service.MovieService;
import com.BackPrimeflix.util.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@CrossOrigin(origins = WebConstants.ALLOWED_URL)
@RestController
@RequestMapping("/inventory")
public class InventoryController {
    //members
    @Autowired
    private InventoryService inventoryService;
    @Autowired
    private UserService userService;
    @Autowired
    private MovieService movieService;

    //methods
    @PostMapping("/saveInventoryItem")
    public ResponseEntity<InventoryResponse> saveInventory(@RequestBody String json, Authentication auth) {
        //members
        InventoryResponse resp = new InventoryResponse();
        AlertObject alertObject = new AlertObject();

        //check authentication (if not send exception)
        UserEntity loggedUser = userService.getByEmail(auth.getName());
        if (loggedUser == null) {
            throw new UsernameNotFoundException("USER_NOT_EXIST");
        }
        //check if user allowed to do this
        if (!userService.checkUserRoleAllowed(loggedUser, WebConstants.USER_STOREKEEPER_ROLE))
            throw new UserNotAllowedException("USER_NOT_ALLOWED");
        try {
            //recover information
            ObjectMapper mapper = new ObjectMapper();
            InventoryDto inventoryDto = mapper.readValue(json, InventoryDto.class);
            InventoryEntity inventoryEntity = new InventoryEntity();
            inventoryEntity.setInventoryDate(new Date());
            inventoryEntity.setStockAfterInventory(Integer.parseInt(inventoryDto.getStockAfterInventory()));
            //recover the movie
            MovieEntity movieEntity = movieService.getMovieById(inventoryDto.getMovieId());
            inventoryEntity.setMovie(movieEntity);
            //recover the quantity before inventory
            inventoryEntity.setStockBeforeInventory(movieEntity.getStockQuantity());
            //calculate and save the difference
            inventoryEntity.setDifference(inventoryEntity.getStockAfterInventory() - inventoryEntity.getStockBeforeInventory());
            //save new movie quantity
            movieEntity.setStockQuantity(inventoryEntity.getStockAfterInventory());
            movieService.save(movieEntity);

            //persist in the database
            inventoryService.save(inventoryEntity);
            resp.setStatus(ResponseCode.SUCCESS_CODE);
            resp.setMessage(ResponseCode.INVENTORY_SAVED_SUCCESS);
            alertObject.setAlertCode(ResponseCode.INVENTORY_SAVED_SUCCESS);
            alertObject.setAlertMessage(ResponseCode.INVENTORY_SAVED_SUCCESS_MESSAGE);
            alertObject.setAlertType(AlertObject.alertTypeEnum.SUCCESS.toString());

        }
        //if error
        catch (Exception e) {
            throw new AccountCustomException(ResponseCode.INVENTORY_SAVED_ERROR);
        }
        //set the alert object
        resp.setAlertObject(alertObject);

        //send response
        return new ResponseEntity<InventoryResponse>(resp, HttpStatus.ACCEPTED);
    }

    @GetMapping("/inventory-list")
    public ResponseEntity<InventoryResponse> getDiscounts(Authentication auth) {
        //members
        InventoryResponse resp = new InventoryResponse();
        AlertObject alertObject = new AlertObject();

        //check authentication (if not send exception)
        UserEntity loggedUser = userService.getByEmail(auth.getName());
        if (loggedUser == null) {
            throw new UsernameNotFoundException("USER_NOT_EXIST");
        }
        //check if user allowed to do this
        if (!userService.checkUserRoleAllowed(loggedUser, WebConstants.USER_ADMIN_ROLE))
            throw new UserNotAllowedException("USER_NOT_ALLOWED");
        try {
            //recover inventory list
            List<InventoryEntity> inventories = inventoryService.getAll();
            //convert to Dto
            List<InventoryDto> inventoryDtos = new ArrayList<>();
            for(InventoryEntity inventory: inventories){
                InventoryDto inventoryDto = inventoryService.convertToInventoryDto(inventory);
                inventoryDtos.add(inventoryDto);
            }
            resp.setInventories(inventoryDtos);
        }
        //if error
        catch (Exception e) {
            throw new PersonCrudCustomException(ResponseCode.PERSON_CRUD_ERROR);
        }
        //set the alert object
        resp.setAlertObject(alertObject);

        //send response
        return new ResponseEntity<InventoryResponse>(resp, HttpStatus.ACCEPTED);
    }
}
