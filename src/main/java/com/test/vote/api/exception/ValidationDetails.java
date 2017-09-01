package com.test.vote.api.exception;

import lombok.*;

@Builder
@Getter
class ValidationDetails {

    private String field;

    private String message;

    private Object value;
}
