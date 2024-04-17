package com.Bank.project.Exception;

import lombok.Data;
import org.springframework.web.bind.annotation.ResponseStatus;
@ResponseStatus
@Data
public class InvalidException extends Exception
{
    private String reasoncode;

    public InvalidException(String message, String reasoncode) {
        super(message);
        this.reasoncode = reasoncode;
    }

}

