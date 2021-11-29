package com.normalizer.normalizer.features.normalizer.dto;

public class NormalizedJobTitleDto {

    private final String normalizedJobTitle;
    private final double qualityScore;

    public NormalizedJobTitleDto(String normalizedJobTitle, double qualityScore) {
        this.normalizedJobTitle = normalizedJobTitle;
        this.qualityScore = qualityScore;
    }

    public String getNormalizedJobTitle() {
        return normalizedJobTitle;
    }

    public double getQualityScore() {
        return qualityScore;
    }
}
