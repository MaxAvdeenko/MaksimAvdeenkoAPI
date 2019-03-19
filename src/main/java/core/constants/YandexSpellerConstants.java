package core.constants;

public class YandexSpellerConstants {

    public static final String PARAM_TEXT = "text";
    public static final String PARAM_OPTIONS = "options";
    public static final String PARAM_LANG = "lang";
    public static final String QUOTES = "\"";

    public enum Options {
        IGNORE_DIGITS(2),
        IGNORE_URLS(4),
        FIND_REPEAT_WORDS(8),
        IGNORE_CAPITALIZATION(512);

        private int code;

        public String getCode() {
            return String.valueOf(code);
        }

        Options(int code) {
            this.code = code;
        }

        public static String computeOptions(Options... options) {
            int sum = 0;
            for (Options element : options) {
                sum += element.code;
            }
            return String.valueOf(sum);
        }
    }

    public enum Language {
        RU("ru"),
        UK("uk"),
        EN("en");
        public String languageCode;
        public String langCode(){return languageCode;}
        Language(String lang) {
            this.languageCode = lang;
        }
    }

    public enum ErrorCodes {
        ERROR_UNKNOWN_WORD("1"),
        ERROR_REPEAT_WORD("2"),
        ERROR_CAPITALIZATION("3"),
        ERROR_TOO_MANY_ERRORS("4");

        private String code;
        public String getCode() {return code;}

        ErrorCodes(String code) {
            this.code = code;
        }
    }
}
