package com.med.batch.model;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Customer {
    private String id;
    private String firstName;
    private String lastName;
    private String salary;

}
