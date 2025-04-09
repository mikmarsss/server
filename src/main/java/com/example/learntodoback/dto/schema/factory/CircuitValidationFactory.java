package com.example.learntodoback.dto.schema.factory;

import com.example.learntodoback.dto.schema.validator.CircuitClosureValidator;
import com.example.learntodoback.dto.schema.validator.CircuitValidator;
import com.example.learntodoback.dto.schema.validator.ComponentConnectionValidator;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class CircuitValidationFactory {

    private final List<CircuitValidator> validators = new ArrayList<>();

    public CircuitValidationFactory(
            CircuitClosureValidator closureValidator,
            ComponentConnectionValidator componentValidator
    ) {
        validators.add(closureValidator);
        validators.add(componentValidator);
    }

    public List<CircuitValidator> getValidators() {
        return Collections.unmodifiableList(validators);
    }
}