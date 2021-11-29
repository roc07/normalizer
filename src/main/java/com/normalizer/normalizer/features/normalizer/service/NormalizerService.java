package com.normalizer.normalizer.features.normalizer.service;

import com.normalizer.normalizer.features.normalizer.dto.NormalizedJobTitleDto;
import com.normalizer.normalizer.features.normalizer.exception.InvalidJobTitleException;
import org.springframework.stereotype.Service;

@Service
public class NormalizerService {
    
    public NormalizedJobTitleDto normalizeJobTitle(String jobTitleToNormalize) {
        jobTitleToNormalize = sanitizeTitle(jobTitleToNormalize);

        JobTitleInformation jobTitleInformation = new JobTitleInformation();

        extractTitleMeaning(jobTitleToNormalize, NormalizedJobTitle.ARCHITECT, jobTitleInformation);
        extractTitleMeaning(jobTitleToNormalize, NormalizedJobTitle.SOFTWARE_ENGINEER, jobTitleInformation);
        extractTitleMeaning(jobTitleToNormalize, NormalizedJobTitle.QUANTITY_SURVEYOR, jobTitleInformation);
        extractTitleMeaning(jobTitleToNormalize, NormalizedJobTitle.ACCOUNTANT, jobTitleInformation);
        
        return new NormalizedJobTitleDto(jobTitleInformation.jobTitleResult, jobTitleInformation.score);
    }

    private String sanitizeTitle(String originalTitle) {
        if (originalTitle == null) {
            throw new InvalidJobTitleException("null value given");
        }

        String sanitizedTitle = originalTitle.toLowerCase().replaceAll("[^a-z]","");
        if (sanitizedTitle.isEmpty()) {
            throw new InvalidJobTitleException(String.format("Invalid job title: %s", originalTitle));
        }

        return sanitizedTitle;
    }

    private void extractTitleMeaning(String jobTitle, NormalizedJobTitle job, JobTitleInformation info) {
        if (jobTitle.contains(job.getKeyWord())) {
            if (info.score == 0) {
                info.score = 1;
                info.jobTitleResult = job.getNormalizedTitle();
            } else {
                info.score = info.score / 2;
            }
        }
    }

    private static class JobTitleInformation {
        String jobTitleResult = "unexpected job title";
        double score = 0;
    }
}
