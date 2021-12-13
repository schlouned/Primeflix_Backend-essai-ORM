package com.BackPrimeflix.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
@DiscriminatorValue("U")
public class UserEntity extends PersonEntity implements Serializable {
    //members
    @Column(unique = true)
    private String email;
    private String password;
    private boolean accountVerified;
    private int failedLoginAttempts;
    private boolean loginDisabled;
    @OneToMany(mappedBy = "user")
    private Set<EmailRegistrationToken> tokens;
    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "user_roles_association",
            joinColumns =@JoinColumn(name = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"
    ))
    private Set<Role> userRoles = new HashSet<>();
    @OneToMany( targetEntity=CartEntity.class, mappedBy="user")
    private List<CartEntity> carts = new ArrayList<>();

    //getter and setter
    public String getEmail() {return email;}

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAccountVerified(boolean accountVerified) {
        this.accountVerified = accountVerified;
    }

    public Set<EmailRegistrationToken> getTokens() {
        return tokens;
    }

    public void setTokens(Set<EmailRegistrationToken> tokens) {
        this.tokens = tokens;
    }

    public void addToken(final EmailRegistrationToken token){
        tokens.add(token);
    }

    public int getFailedLoginAttempts() {
        return failedLoginAttempts;
    }

    public void setFailedLoginAttempts(int failedLoginAttempts) {
        this.failedLoginAttempts = failedLoginAttempts;
    }

    public void setLoginDisabled(boolean loginDisabled) {
        this.loginDisabled = loginDisabled;
    }

    public Set<Role> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(Set<Role> userRoles) {
        this.userRoles = userRoles;
    }

    public List<CartEntity> getCarts() {
        return carts;
    }

    public void setCarts(List<CartEntity> carts) {
        this.carts = carts;
    }

    //methods
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }

    public void addUserRoles(Role role){
        userRoles.add(role);
        role.getUsers().add(this);
    }

    public void removeUserRoles(Role role){
        userRoles.remove(role);
        role.getUsers().remove(this);
    }

    public boolean isAccountVerified() {
        return accountVerified;
    }

    public boolean isLoginDisabled() {
        return loginDisabled;
    }

    //getOpenCart
    public CartEntity getOpenCart(){
        CartEntity cart = new CartEntity();
        for(CartEntity c: this.getCarts()){
            if(c.getOpenStatus()==true){
                cart = c;
                break;
            }
        }
        return cart;
    }
}
