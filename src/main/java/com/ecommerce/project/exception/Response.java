package com.ecommerce.project.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class Response {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object data;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object row;

    private HttpStatus status;

    public Response(Object data, Object message, Object row) {
        this.data = data;
        this.message = message;
        this.row = row;
        this.status = status;
    }

    public Response(Object data, Object message, HttpStatus status) {
        this.data = data;
        this.message = message;
        this.status = status;
    }

    public Response(Object message, HttpStatus status) {
        this.message = message;
        this.status= status;
    }
}
