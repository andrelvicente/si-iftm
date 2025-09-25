package com.example.example.models.dto;

import com.example.example.models.Address;
import com.example.example.models.User;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class UserDTO {

    private String id;
    private String name;
    private int age;
    private Address address;

    public UserDTO(){
    }

    public UserDTO(User user){
        this.id = user.getId().toString();
        this.name = user.getName();
        this.age = user.getAge();
        this.address = user.getAddress();
    }

    public UserDTO(String nome, int age){
        this.name = name;
        this.age = age;
    }

}