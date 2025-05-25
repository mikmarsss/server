package com.example.learntodoback.dto.schema.factory;

import com.example.learntodoback.dto.schema.validator.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class CircuitValidationFactory {

    private final List<CircuitValidator> validators = new ArrayList<>();

    public CircuitValidationFactory(
            CircuitClosureValidator closureValidator,
            LedValidator ledValidator,
            DoActionValidator doActionValidator
    ) {
        validators.add(closureValidator);
        validators.add(ledValidator);
        validators.add(doActionValidator);
    }

    public List<CircuitValidator> getValidators() {
        return Collections.unmodifiableList(validators);
    }
}