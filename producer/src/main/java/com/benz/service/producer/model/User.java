package com.benz.service.producer.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlRootElement;

@Data
@Builder
@AllArgsConstructor
@XmlRootElement
@NoArgsConstructor
public class User {

    //{ name: “Hello”, dob: “20-08-2020”, salary: “122111241.150”, age: 20 }

    private String name;
    private String dob;
    private String salary;
    private Integer age;

}
