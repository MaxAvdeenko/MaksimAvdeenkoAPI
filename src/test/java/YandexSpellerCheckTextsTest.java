import beans.YandexSpellerAnswer;
import core.YandexSpellerApi;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static constants.YandexSpellerConstants.ErrorCodes.*;
import static constants.YandexSpellerConstants.Languages;
import static constants.YandexSpellerConstants.Languages.EN;
import static constants.YandexSpellerConstants.Languages.RU;
import static core.CheckResponse.checkAnswer;


public class YandexSpellerCheckTextsTest {

    @DataProvider
    public Object[][] unknownEnWordError() {
        return new Object[][]{
                {new String[]{"degits", "cartons"},
                        EN,
                        new List[]{Arrays.asList("digits", "degitz", "digital"),
                                Arrays.asList("cartoons", "cartoon", "carton")}}
        };
    }

    @Test(dataProvider = "unknownEnWordError")
    public void checkIncorrectEnWords(String[] texts, Languages lang, List[] expectedSuggestions) {

        List<List<YandexSpellerAnswer>> answer =
                YandexSpellerApi.getYandexSpellerAnswers(YandexSpellerApi.with()
                        .language(lang)
                        .options(0)
                        .texts(texts)
                        .getCheckTexts());

        checkAnswer(texts, expectedSuggestions, answer, ERROR_UNKNOWN_WORD);

    }

    @DataProvider
    public Object[][] unknownRuWordError() {
        return new Object[][]{
                {new String[]{"кортофель", "варона"},
                        RU,
                        new List[]{Arrays.asList("картофель", "картофеля", "картофел"),
                                Arrays.asList("ворона", "варана", "верона")}}
        };
    }

    @Test(dataProvider = "unknownRuWordError")
    public void checkIncorrectRuWords(String[] texts, Languages lang, List[] expectedSuggestions) {

        List<List<YandexSpellerAnswer>> answer =
                YandexSpellerApi.getYandexSpellerAnswers(YandexSpellerApi.with()
                        .language(lang)
                        .options(0)
                        .texts(texts)
                        .getCheckTexts());

        checkAnswer(texts, expectedSuggestions, answer, ERROR_UNKNOWN_WORD);

    }

    @DataProvider
    public Object[][] incorrectSpacing() {
        return new Object[][]{
                {new String[]{"not anumber", "giventest"},
                        EN,
                        new List[]{Arrays.asList("number", "a number"),
                                Arrays.asList("given test", "given tests", "given these")}}
        };
    }

    @Test(dataProvider = "incorrectSpacing")
    public void checkIncorrectSpacing(String[] texts, Languages lang, List[] expectedSuggestions) {
        List<List<YandexSpellerAnswer>> answer =
                YandexSpellerApi.getYandexSpellerAnswers(YandexSpellerApi.with()
                        .language(lang)
                        .options(0)
                        .texts(texts)
                        .getCheckTexts());

        checkAnswer(texts, expectedSuggestions, answer, ERROR_UNKNOWN_WORD);

    }

    @DataProvider
    public Object[][] repeatWords() {
        return new Object[][]{
                {new String[]{"eeat cook cook", "car car movis"},
                        EN,
                        new List[]{Collections.singletonList("cook"),
                                Collections.singletonList("car")}
                }
        };
    }

    //Service Bug - duplicate word not found(then unknown word error)
    @Test(dataProvider = "repeatWords")
    public void checkRepeatWordOption(String[] texts, Languages lang, List[] expectedSuggestions) {
        List<List<YandexSpellerAnswer>> answer =
                YandexSpellerApi.getYandexSpellerAnswers(YandexSpellerApi.with()
                        .language(lang)
                        .options(8)
                        .texts(texts)
                        .getCheckTexts());

        checkAnswer(texts, expectedSuggestions, answer, ERROR_REPEAT_WORD);
    }

    @DataProvider
    public Object[][] digitAndCapitalization() {
        return new Object[][]{
                {new String[]{"eng1ish", "lond0n"},
                        EN,
                        new List[]{Collections.singletonList("English"),
                                Collections.singletonList("London")}
                }
        };

    }

    //Bug was found - capitalization error not detected by yandex speller
    @Test(dataProvider = "digitAndCapitalization")
    public void checkIgnoreCapitalLettersAndDigitsOption(String[] texts, Languages lang, List[] expectedSuggestions) {
        List<List<YandexSpellerAnswer>> answer =
                YandexSpellerApi.getYandexSpellerAnswers(YandexSpellerApi.with()
                        .language(lang)
                        .options(2, 4, 512)
                        .texts(texts)
                        .getCheckTexts());

        checkAnswer(texts, expectedSuggestions, answer, ERROR_CAPITALIZATION);
    }

    //Bug was found - incorrect word not detected by yandex speller
    @Test(dataProvider = "digitAndCapitalization")
    public void checkOptionToIgnoreDigits(String[] texts, Languages lang, List[] expectedSuggestions) {
        List<List<YandexSpellerAnswer>> answer =
                YandexSpellerApi.getYandexSpellerAnswers(YandexSpellerApi.with()
                        .language(lang)
                        .options(2)
                        .texts(texts)
                        .getCheckTexts());

        checkAnswer(texts, expectedSuggestions, answer, ERROR_UNKNOWN_WORD);
    }

    @DataProvider
    public Object[][] wordsWithDigits() {
        return new Object[][]{
                {new String[]{"car2", "move0"},
                        EN,
                        new List[]{Arrays.asList("car 2", "cars 2", "card 2", "care 2"),
                                Arrays.asList("move 0", "movie 0", "moved 0")}
                }
        };

    }

    @Test(dataProvider = "wordsWithDigits")
    public void checkWordsWithDigits(String[] texts, Languages lang, List[] expectedSuggestions) {
        List<List<YandexSpellerAnswer>> answer =
                YandexSpellerApi.getYandexSpellerAnswers(YandexSpellerApi.with()
                        .language(lang)
                        .options(0)
                        .texts(texts)
                        .getCheckTexts());

        checkAnswer(texts, expectedSuggestions, answer, ERROR_UNKNOWN_WORD);
    }

    @DataProvider
    public Object[][] incorrectRuWord() {
        return new Object[][]{
                {new String[]{"move направа", "pretty платъе"},
                        EN,
                        new List[]{Arrays.asList("направо", "на права", "неправа"),
                                Arrays.asList("платье", "платья")}
                }
        };
    }

    @Test(dataProvider = "incorrectRuWord")
    public void checkWrongRuWordInEnglishLang(String[] texts, Languages lang, List[] expectedSuggestions) {
        List<List<YandexSpellerAnswer>> answer =
                YandexSpellerApi.getYandexSpellerAnswers(YandexSpellerApi.with()
                        .language(lang)
                        .options(0)
                        .texts(texts)
                        .getCheckTexts());

        checkAnswer(texts, expectedSuggestions, answer, ERROR_UNKNOWN_WORD);
    }

    @DataProvider
    public Object[][] ruAndEnTexts() {
        return new Object[][]{
                {new String[]{"at the end of the coridor", "в канце коридора"},
                        new List[]{Arrays.asList("corridor", "corridors"),
                                Collections.singletonList("конце")}
                }
        };
    }

    @Test(dataProvider = "ruAndEnTexts")
    public void checkDefaultLanguage(String[] texts, List[] expectedSuggestions) {
        List<List<YandexSpellerAnswer>> answer =
                YandexSpellerApi.getYandexSpellerAnswers(YandexSpellerApi.with()
                        .texts(texts)
                        .getCheckTexts());

        checkAnswer(texts, expectedSuggestions, answer, ERROR_UNKNOWN_WORD);
    }

    @DataProvider
    public Object[][] manyErrors() {
        return new Object[][]{
                {new String[]{"colect marcs is my hobbi"},
                        new List[]{Collections.singletonList("collect"),
                                Arrays.asList("marcus", "marcs", "marcos", "marks"),
                                Collections.singletonList("hobby")}
                }
        };
    }

    @Test(dataProvider = "manyErrors")
    public void checkSeveralErrorsInText(String[] texts, List[] expectedSuggestions) {
        List<List<YandexSpellerAnswer>> answer =
                YandexSpellerApi.getYandexSpellerAnswers(YandexSpellerApi.with()
                        .texts(texts)
                        .getCheckTexts());

        checkAnswer(texts, expectedSuggestions, answer, ERROR_UNKNOWN_WORD);
    }
}
