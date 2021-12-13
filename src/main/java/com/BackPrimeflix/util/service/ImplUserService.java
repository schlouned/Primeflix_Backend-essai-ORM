package com.BackPrimeflix.util.service;

import com.BackPrimeflix.constants.WebConstants;
import com.BackPrimeflix.dto.AddressDto;
import com.BackPrimeflix.dto.UserDto;
import com.BackPrimeflix.model.*;
import com.BackPrimeflix.context.AccountVerifEmailContext;
import com.BackPrimeflix.exception.InvalidTokenException;
import com.BackPrimeflix.exception.UnkownIdentifierException;
import com.BackPrimeflix.repository.CartRepository;
import com.BackPrimeflix.repository.EmailRegistrationTokenRepository;
import com.BackPrimeflix.repository.RoleRepository;
import com.BackPrimeflix.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;
import org.apache.commons.lang3.BooleanUtils;

import javax.mail.MessagingException;
import java.util.*;

@Service("userService")
public class ImplUserService implements UserService {
    //members
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private EmailService emailService;
    @Autowired
    private EmailRegistrationTokenService emailRegistrationTokenService;
    @Autowired
    EmailRegistrationTokenRepository emailRegistrationTokenRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    CartService cartService;
    @Autowired
    AddressService addressService;

    //recover a value from the application.properties file => put in baseUrl
    @Value("${site.base.url.https}")
    private String baseURL;

    //methods
    public UserEntity register(UserEntity userEntity, String registrationMode) {
        //encrypt the password
        encodePassword(userEntity);
        //set the user ole as CLIENT
        updateUserRole(userEntity);
        //set an address
        //warning not forget to create an empty address
        AddressEntity address = new AddressEntity();
        //address.setPersonEntity(userEntity);
        userEntity.setAddressEntity(address);
        //address.setPersonEntity(userEntity);
        //persist in DB
        addressService.save(address);
        userRepository.save(userEntity);

        // if registration mode is email => send an email
        if (registrationMode.equals("email")) {
            sendRegistrationConfirmationEmail(userEntity);
        }

        return userEntity;
    }

    //encrypt the password
    private void encodePassword(UserEntity userEntity) {
        String pw = userEntity.getPassword();
        String pwEncrypt = passwordEncoder.encode(pw);
        userEntity.setPassword(pwEncrypt);
    }

    //set the role has CLIENT
    private void updateUserRole(UserEntity userEntity) {
        Role role = roleRepository.findByCode("CLIENT");
        userEntity.addUserRoles(role);
    }

    //check if user exist
    public boolean checkIfUserExist(String email) {
        return userRepository.findByEmail(email) != null ? true : false;
    }

    //send confirmation email
    public void sendRegistrationConfirmationEmail(UserEntity user) {
        //create a token
        EmailRegistrationToken emailRegistrationToken = emailRegistrationTokenService.createSecureToken();
        emailRegistrationToken.setUser(user);
        //persist the token in DB
        emailRegistrationTokenRepository.save(emailRegistrationToken);
        //create the email
        AccountVerifEmailContext emailContext = new AccountVerifEmailContext();
        emailContext.init(user);
        emailContext.setToken(emailRegistrationToken.getToken());
        emailContext.buildVerificationUrl(baseURL, emailRegistrationToken.getToken());
        try {
            //send the email
            emailService.sendMail(emailContext);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    //Verify the user when the confirmation link in the email il clicked
    public boolean verifyUser(String token) throws InvalidTokenException {
        //recover the token
        EmailRegistrationToken emailRegistrationToken = emailRegistrationTokenService.findByToken(token);
        //if token is not valid
        if (Objects.isNull(emailRegistrationToken) || !StringUtils.equals(token, emailRegistrationToken.getToken()) || emailRegistrationToken.isExpired()) {
            throw new InvalidTokenException("Token is not valid");
        }
        //if there is no user for this token -> false
        UserEntity user = userRepository.getOne(emailRegistrationToken.getUser().getId());
        if (Objects.isNull(user)) {
            return false;
        }
        //if token is ok so the user is verify we change the value in DB -> true
        user.setAccountVerified(true);
        userRepository.save(user); // let's same user details
        // remove the token from the DB
        emailRegistrationTokenService.removeToken(emailRegistrationToken);
        return true;
    }

    public UserEntity getUserById(String id) throws UnkownIdentifierException {
        UserEntity user = userRepository.findByEmail(id);
        if (user == null || BooleanUtils.isFalse(user.isAccountVerified())) {
            // we will ignore in case account is not verified or account does not exists
            throw new UnkownIdentifierException("unable to find account or account is not active");
        }
        return user;
    }

    @Override
    public UserEntity getByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    //save user informations
    @Override
    public void save(final UserEntity userEntity) {
        userRepository.save(userEntity);
    }

    //recover or create a cart
    public Integer checkCart(UserEntity userEntity) {
        //members
        Boolean cartOpen = false;
        CartEntity cartEntity = new CartEntity();
        //recover the cart list and check if there is an open cart
        List<CartEntity> carts = new ArrayList<>();
        carts = userEntity.getCarts();
        //on this cart list check if one is still open
        for (CartEntity cart : carts) {
            if (cart.getOpenStatus() == true) {
                cartEntity = cart;
                cartOpen = true;
                break;
            }
        }

        //if there is no open cart create a new one
        if (cartOpen == false) {
            cartEntity.setUser(userEntity);
            cartEntity.setOpenStatus(true);
            cartService.save(cartEntity);
        }

        //check how many items there is in the cart
        Integer itemsCartNumber = cartEntity.getCartItemEntities().size();

        //return
        return itemsCartNumber;
    }

    public Boolean checkUserRoleAllowed(UserEntity userEntity, String roleLevel) {
        //set up
        Integer level = 0;
        if (roleLevel.equals(WebConstants.USER_CLIENT_ROLE))
            level = 1;
        else if (roleLevel.equals(WebConstants.USER_STOREKEEPER_ROLE))
            level = 2;
        else if (roleLevel.equals(WebConstants.USER_MARKETINGMANAGER_ROLE))
            level = 3;
        else if (roleLevel.equals(WebConstants.USER_ADMIN_ROLE))
            level = 4;

        //recover the user role of the user:
        Set<Role> roles = userEntity.getUserRoles();
        Role userRole = new Role();
        //there is an implementation error here about the relation between person and role I'm aware of that
        for (Role role : roles) {
            userRole = role;
        }

        //user level
        Integer userLevel = 0;
        if (userRole.getCode().equals(WebConstants.USER_CLIENT_ROLE))
            userLevel = 1;
        else if (userRole.getCode().equals(WebConstants.USER_STOREKEEPER_ROLE))
            userLevel = 2;
        else if (userRole.getCode().equals(WebConstants.USER_MARKETINGMANAGER_ROLE))
            userLevel = 3;
        else if (userRole.getCode().equals(WebConstants.USER_ADMIN_ROLE))
            userLevel = 4;

        if (userLevel >= level)
            return true;
        else
            return false;
    }

    public UserDto convertToUserDto(UserEntity userEntity) {
        UserDto userDto = new UserDto();
        userDto.setId(userEntity.getId().toString());
        userDto.setLastName(userEntity.getLastName());
        userDto.setFirstName(userEntity.getFirstName());
        userDto.setEmail(userEntity.getEmail());
        AddressDto addressDto = new AddressDto();
        addressDto.setStreet(userEntity.getAddressEntity().getStreet());
        addressDto.setHouseNumber(userEntity.getAddressEntity().getHouseNumber());
        addressDto.setZipCode(userEntity.getAddressEntity().getZipCode());
        addressDto.setCity(userEntity.getAddressEntity().getCity());
        addressDto.setCountryCode(userEntity.getAddressEntity().getCountryCode());
        userDto.setAddress(addressDto);
        Set<Role> roles = userEntity.getUserRoles();
        Role role = roles.iterator().next();
        userDto.setRole(role.getCode());
        return userDto;
    }

    public UserEntity convertToUserEntity(UserDto userDto) {
        //recover the user
        UserEntity userEntity = new UserEntity();
        AddressEntity addressEntity = new AddressEntity();
        if (!userDto.getId().equals("")) {
            userEntity = userRepository.getById(Long.parseLong(userDto.getId()));
            addressEntity = userEntity.getAddressEntity();
        }
        //recover the address
        addressEntity.setStreet(userDto.getAddress().getStreet());
        addressEntity.setHouseNumber(userDto.getAddress().getHouseNumber());
        addressEntity.setZipCode(userDto.getAddress().getZipCode());
        addressEntity.setCity(userDto.getAddress().getCity());

        //save the role
        Role role = roleRepository.findByCode(userDto.getRole());
        Set<Role> userRoles = new HashSet<>();
        userRoles.add(role);

        //save the user
        userEntity.setEmail(userDto.getEmail());
        userEntity.setLastName(userDto.getLastName());
        userEntity.setFirstName(userDto.getFirstName());
        userEntity.setAddressEntity(addressEntity);
        userEntity.setUserRoles(userRoles);
        userEntity.setAccountVerified(userDto.isAccountVerified());

        return userEntity;
    }

    public UserEntity saveUser(UserDto userDto) {
        //if existing user
        if (!userDto.getId().equals(""))
            return userRepository.save(this.convertToUserEntity(userDto));
            //if new user
        else {
            //recover the user
            UserEntity userEntity = this.convertToUserEntity(userDto);
            //recover the address
            AddressEntity addressEntity = userEntity.getAddressEntity();

            //addressEntity.setPersonEntity(userEntity);
            userEntity.setAddressEntity(addressEntity);
            //addressEntity.setPersonEntity(userEntity);
            addressService.save(addressEntity);
            userRepository.save(userEntity);

            return userEntity;
        }

    }

    public List<UserEntity> getUsersList() {
        return userRepository.findAllByOrderByLastName();
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }


}
