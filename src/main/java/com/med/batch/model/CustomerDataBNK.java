package com.med.batch.model;


import com.med.batch.utils.NumbersUtils;
import lombok.*;

import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDataBNK {

    private String bankName;
    private String id;
    private String firstName;
    private String lastName;
    private String salary;

    public boolean isValid() {
        return Objects.nonNull(id)
                && Objects.nonNull(firstName)
                && Objects.nonNull(lastName)
                && Objects.nonNull(salary)
                && NumbersUtils.isNumeric(salary);
    }

    public Customer mapperToCustomer() {
        return new Customer(id, firstName, lastName, salary);
    }
}
