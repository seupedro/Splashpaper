package io.github.sshnakamoto.splashpaper;

import android.util.Log;

public final class UrlParams {

    private static final String ACCESS_KEY = "1f433e64ae005fac661acd9cad0756380f5daddaa7eca62377d2c8f782b29a04";

    private static final String BASE_SITE = "https://api.unsplash.com";
    private static final String PARAM_CLIENT = "client_id=";
    private static final String PATH_RANDOM = "photos/random";
    private static final String PARAM_QUERY = "query=";

    private static final String QUERY_RANDOM_URL = BASE_SITE + "/" + PATH_RANDOM + "?" + PARAM_CLIENT
            + ACCESS_KEY + "&" + PARAM_QUERY;

    public static class UrlUtis {
        private static final String TAG = "UrlUtis";

        public static String queryRandom(String keywords) {

            /* Format words to match url */
            if (keywords.contains(" ")) {
                String formattedKeywords = keywords.replaceAll(" ", "-");
                Log.d(TAG, "queryRandom: " + formattedKeywords);
                return QUERY_RANDOM_URL + formattedKeywords;
            }

            return QUERY_RANDOM_URL + keywords;
        }

    }

}
