package com.benz.service.consumer.model;


import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Builder
public class User {

    //{ name: “Hello”, dob: “20-08-2020”, salary: “122111241.150”, age: 20 }

    @NotNull @NotEmpty
    private String name;
    @NotNull @NotEmpty
    private String dob;
    @NotNull @NotEmpty
    private String salary;
    @NotNull @NotEmpty
    private Integer age;

}
