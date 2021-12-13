package com.BackPrimeflix.controller;

import com.BackPrimeflix.constants.ResponseCode;
import com.BackPrimeflix.constants.WebConstants;
import com.BackPrimeflix.dto.DiscountDto;
import com.BackPrimeflix.dto.UserDto;
import com.BackPrimeflix.exception.DiscountCustomException;
import com.BackPrimeflix.exception.PersonCrudCustomException;
import com.BackPrimeflix.exception.UserNotAllowedException;
import com.BackPrimeflix.model.DiscountEntity;
import com.BackPrimeflix.model.UserEntity;
import com.BackPrimeflix.response.AlertObject;
import com.BackPrimeflix.response.DiscountResponse;
import com.BackPrimeflix.response.PersonResponse;
import com.BackPrimeflix.util.ConvertDate;
import com.BackPrimeflix.util.service.CategoryService;
import com.BackPrimeflix.util.service.DiscountService;
import com.BackPrimeflix.util.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@CrossOrigin(origins = WebConstants.ALLOWED_URL)
@RestController
@RequestMapping("/discount")
public class DiscountController {
    //members
    @Autowired
    private UserService userService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private DiscountService discountService;

    //save discount
    @PostMapping("/saveDiscount")
    public ResponseEntity<DiscountResponse> saveDiscount(@RequestBody String json, Authentication auth) {
        //members
        DiscountResponse resp = new DiscountResponse();
        AlertObject alertObject = new AlertObject();

        //check authentication (if not send exception)
        UserEntity loggedUser = userService.getByEmail(auth.getName());
        if (loggedUser == null) {
            throw new UsernameNotFoundException("USER_NOT_EXIST");
        }
        //check if user allowed to do this
        if (!userService.checkUserRoleAllowed(loggedUser, WebConstants.USER_MARKETINGMANAGER_ROLE))
            throw new UserNotAllowedException("USER_NOT_ALLOWED");

        //try
        try {
            //recover new informations and put it in the discountDto
            ObjectMapper mapper = new ObjectMapper();
            DiscountDto discountDto = mapper.readValue(json, DiscountDto.class);
            //check if discount exist
            if(discountDto.getId().equals("")) {
                //create a new discount entity
                DiscountEntity discountEntity = new DiscountEntity();
                discountEntity.setCategory(categoryService.getCategoryById(Long.parseLong(discountDto.getCategory().getId())));
                discountEntity.setPercentage(Integer.parseInt(discountDto.getPercentage()));
                discountEntity.setStartDate(ConvertDate.convertJsonDateToDate(discountDto.getStartDate()));
                discountEntity.setEndDate(ConvertDate.convertJsonDateToDate(discountDto.getEndDate()));
                //add one day to the end date: in that way the discount finish the next day
                Calendar c = Calendar.getInstance();
                c.setTime(discountEntity.getEndDate());
                c.add(Calendar.DAY_OF_MONTH, 1);
                discountEntity.setEndDate(c.getTime());
                //check if not overlap
                if (discountService.checkIfNotTwoPromotionAtTheSameTimeByCategory(discountEntity) == 0) {
                    //persist in the database
                    discountService.save(discountEntity);
                    //create the response
                    resp.setStatus(ResponseCode.SUCCESS_CODE);
                    resp.setMessage(ResponseCode.SAVE_DISCOUNT_SUCCESS);
                    alertObject.setAlertCode(ResponseCode.SAVE_DISCOUNT_SUCCESS);
                    alertObject.setAlertMessage(ResponseCode.SAVE_DISCOUNT_SUCCESS_MESSAGE);
                    alertObject.setAlertType(AlertObject.alertTypeEnum.SUCCESS.toString());
                } else {
                    //create the response
                    resp.setStatus(ResponseCode.BAD_REQUEST_CODE);
                    resp.setMessage(ResponseCode.OVERLAP_DISCOUNT_ERROR);
                    alertObject.setAlertCode(ResponseCode.OVERLAP_DISCOUNT_ERROR);
                    alertObject.setAlertMessage(ResponseCode.OVERLAP_DISCOUNT_ERROR_MESSAGE);
                    alertObject.setAlertType(AlertObject.alertTypeEnum.ERROR.toString());
                }
            }
            else{
                //if the discount already exist -> for CRUD
                DiscountEntity discountEntity = discountService.convertToDiscountEntity(discountDto);
                discountService.save(discountEntity);
                resp.setStatus(ResponseCode.SUCCESS_CODE);
                resp.setMessage(ResponseCode.SAVE_DISCOUNT_SUCCESS);
                alertObject.setAlertCode(ResponseCode.SAVE_DISCOUNT_SUCCESS);
                alertObject.setAlertMessage(ResponseCode.SAVE_DISCOUNT_SUCCESS_MESSAGE);
                alertObject.setAlertType(AlertObject.alertTypeEnum.SUCCESS.toString());
            }
        }
        //if error
        catch (Exception e) {
            throw new DiscountCustomException(ResponseCode.SAVE_DISCOUNT_ERROR);
        }
        //set the alert object
        resp.setAlertObject(alertObject);

        //send response
        return new ResponseEntity<DiscountResponse>(resp, HttpStatus.ACCEPTED);

    }

    @GetMapping("/discount-list")
    public ResponseEntity<DiscountResponse> getDiscounts(Authentication auth) {
        //members
        DiscountResponse resp = new DiscountResponse();
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
            //recover discount list
            List<DiscountEntity> discounts = discountService.getDiscounts();
            //convert to Dto
            List<DiscountDto> discountDtos = new ArrayList<>();
            for(DiscountEntity discount: discounts){
                DiscountDto discountDto = discountService.convertToDiscountDto(discount);
                discountDtos.add(discountDto);
            }
            resp.setDiscounts(discountDtos);
        }
        //if error
        catch (Exception e) {
            throw new PersonCrudCustomException(ResponseCode.PERSON_CRUD_ERROR);
        }
        //set the alert object
        resp.setAlertObject(alertObject);

        //send response
        return new ResponseEntity<DiscountResponse>(resp, HttpStatus.ACCEPTED);
    }

    @PostMapping("/delete-discount")
    public ResponseEntity<DiscountResponse> deleteDiscount(@RequestBody String json, Authentication auth) {
        //members
        DiscountResponse resp = new DiscountResponse();
        AlertObject alertObject = new AlertObject();

        //check authentication (if not send exception)
        UserEntity loggedUser = userService.getByEmail(auth.getName());
        if (loggedUser == null) {
            throw new UsernameNotFoundException("USER_NOT_EXIST");
        }
        //check if user allowed to do this
        if (!userService.checkUserRoleAllowed(loggedUser, WebConstants.USER_ADMIN_ROLE))
            throw new UserNotAllowedException("USER_NOT_ALLOWED");

        //try
        try {
            //recover new informations and put it in the discountDto
            ObjectMapper mapper = new ObjectMapper();
            DiscountDto discountDto = mapper.readValue(json, DiscountDto.class);
            DiscountEntity discountEntity = discountService.getById(Long.parseLong(discountDto.getId()));
            discountService.deleteDiscount(discountEntity);
        }
        //if error
        catch (Exception e) {
            throw new DiscountCustomException(ResponseCode.DELETE_DISCOUNT_ERROR);
        }
        //set the alert object
        resp.setAlertObject(alertObject);

        //send response
        return new ResponseEntity<DiscountResponse>(resp, HttpStatus.ACCEPTED);

    }
}
