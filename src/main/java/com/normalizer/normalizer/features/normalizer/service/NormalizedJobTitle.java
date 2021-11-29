package com.normalizer.normalizer.features.normalizer.service;

public enum NormalizedJobTitle {

    ARCHITECT("Architect", "architect"),
    SOFTWARE_ENGINEER("Software engineer", "engineer"),
    QUANTITY_SURVEYOR("Quantity surveyor", "surveyor"),
    ACCOUNTANT("Accountant", "accountant");

    private final String normalizedTitle;
    private final String keyWord;

    NormalizedJobTitle(String normalizedTitle, String keyWord) {
        this.normalizedTitle = normalizedTitle;
        this.keyWord = keyWord;
    }

    public String getNormalizedTitle() {
        return normalizedTitle;
    }

    public String getKeyWord() {
        return keyWord;
    }
}
