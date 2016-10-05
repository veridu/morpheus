package com.veridu.morpheus.impl;

import com.veridu.morpheus.interfaces.facts.IStringFactValue;

public class StringFactValue implements IStringFactValue {

    private String value;

    public StringFactValue(String value) {
        this.value = value;
    }

    @Override
    public String getStringFactValue() {
        return this.value;
    }

}