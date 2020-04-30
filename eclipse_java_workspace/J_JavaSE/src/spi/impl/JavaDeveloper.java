package spi.impl;

import spi.Developer;

public class JavaDeveloper implements Developer {
    @Override
    public String getPrograme() {
        return "Java";
    }
}