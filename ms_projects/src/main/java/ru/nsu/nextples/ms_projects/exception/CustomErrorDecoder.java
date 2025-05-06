package ru.nsu.nextples.ms_projects.exception;

import feign.Response;
import feign.codec.ErrorDecoder;
import jakarta.persistence.EntityNotFoundException;

public class CustomErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String s, Response response) {
        if (response.status() == 404) {
            return new EntityNotFoundException("Requested entity not found");
        }
        return new Default().decode(s, response);
    }
}
