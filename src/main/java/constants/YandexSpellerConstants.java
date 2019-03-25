package constants;

public class YandexSpellerConstants {

    public static final String PARAM_TEXTS = "text";
    public static final String PARAM_OPTIONS = "options";
    public static final String PARAM_LANGUAGES = "languages";

    public enum SoapActions {
        CHECK_TEXT("checkText"),
        CHECK_TEXTS("checkTexts");
        private String method;

        public String getMethod() {
            return method;
        }


        SoapActions(String action) {
            this.method = action;

        }
    }

    public enum Languages {
        EN("en"),
        RU("ru"),
        UK("uk");
        private final String lang;

        Languages(String lang) {
            this.lang = lang;
        }

        public String getLang() {
            return lang;
        }
    }

    public enum ErrorCodes {
        ERROR_UNKNOWN_WORD(1),
        ERROR_REPEAT_WORD(2),
        ERROR_CAPITALIZATION(3),
        ERROR_TOO_MANY_ERRORS(4);

        public int errorCode;

        ErrorCodes(int errorCode) {
            this.errorCode = errorCode;
        }
    }
}
