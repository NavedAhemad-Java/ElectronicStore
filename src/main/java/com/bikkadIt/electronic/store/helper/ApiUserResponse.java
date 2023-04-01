package com.bikkadIt.electronic.store.helper;

import lombok.*;
import org.springframework.http.HttpStatus;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiUserResponse {


    private String message;

    private boolean success;

    private HttpStatus status;
}
