package com.deividsantos.assembly.stub;

import com.deividsantos.assembly.model.Associated;

public class AssociatedStub {
    public static Associated build() {
        final Associated associated = new Associated();
        associated.setCpf("02565485201");
        associated.setId(12345);
        return associated;
    }
}
