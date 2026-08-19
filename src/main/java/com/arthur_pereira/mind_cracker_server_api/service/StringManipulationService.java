package com.arthur_pereira.mind_cracker_server_api.service;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.similarity.LevenshteinDistance;
import org.springframework.stereotype.Service;

@Service
public class StringManipulationService {

    public boolean matchUnformattedStrings(String str1, String str2, int characterTolerance) {
        str1 = unformatString(str1);
        str2 = unformatString(str2);
        LevenshteinDistance levenshteinDistance = new LevenshteinDistance(characterTolerance);
        return (levenshteinDistance.apply(str1, str2) <= characterTolerance);
    }

    public String unformatString(String string) {
        string = StringUtils.stripAccents(string);
        string = string.replaceAll(" ","");
        return string;
    }

}
