package core;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import testProperties.ReadProperties;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static constants.YandexSpellerConstants.*;
import static constants.YandexSpellerConstants.SoapActions.CHECK_TEXTS;

public class ApiBuilder {

    YandexSpellerApi spellerApi;

    ApiBuilder(YandexSpellerApi gcApi) {
        spellerApi = gcApi;
    }

    public ApiBuilder texts(String... texts) {
        List<String> textsList = Arrays.asList(texts);
        spellerApi.params.put(PARAM_TEXTS, textsList);
        return this;
    }

    public ApiBuilder options(int... options) {
        spellerApi.params.put(PARAM_OPTIONS, Integer.toString(IntStream.of(options).sum()));
        return this;
    }

    public ApiBuilder language(Languages... languages) {
        List<String> languagesList = new ArrayList<>();
        for (Languages language : languages) {
            languagesList.add(language.getLang());
        }
        String newLanguageList = String.join(", ", languagesList);
        spellerApi.params.put(PARAM_LANGUAGES, newLanguageList);
        return this;
    }

    public Response getCheckTexts() {
        return RestAssured.with()
                .queryParams(spellerApi.params)
                .log().all()
                .get(ReadProperties.getTestProperty("baseUrl") + CHECK_TEXTS.getMethod()).prettyPeek();
    }

    public Response getCheckText() {
        return RestAssured.with()
                .queryParams(spellerApi.params)
                .log().all()
                .get(ReadProperties.getTestProperty("baseUrl") + CHECK_TEXTS.getMethod()).prettyPeek();
    }
}