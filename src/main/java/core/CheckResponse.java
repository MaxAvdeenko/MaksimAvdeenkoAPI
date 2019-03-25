package core;

import beans.YandexSpellerAnswer;

import java.util.List;

import static constants.YandexSpellerConstants.ErrorCodes;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertFalse;

public class CheckResponse {
    public static void checkAnswer(String[] texts, List[] expectedSuggestions, List<List<YandexSpellerAnswer>> answers,
                                   ErrorCodes error) {
        assertThat(answers.size(), equalTo(texts.length));
        for (int i = 0; i < texts.length; i++) {
            if (!answers.get(i).isEmpty()) {
                assertThat("Proposed suggestion is not expected:", answers.get(i).get(0).s,
                        equalTo(expectedSuggestions[i]));
                assertThat("Error is incorect", answers.get(i).get(0).code, equalTo(error.errorCode));
            } else {
                assertFalse(answers.get(i).isEmpty(), "Received response is empty:");
            }
        }
    }
}
