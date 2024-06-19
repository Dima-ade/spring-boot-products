package ro.adela.products.exception;

import org.springframework.core.NestedRuntimeException;

public class ResourceNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ResourceNotFoundException(String msg) {
        super(msg);
    }
}
