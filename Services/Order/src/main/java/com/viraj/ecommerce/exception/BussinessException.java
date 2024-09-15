package com.viraj.ecommerce.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class BussinessException extends RuntimeException {
    private final String message;
}
