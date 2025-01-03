package com.med.batch.model;

import com.med.batch.utils.NumbersUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Customer {
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
}