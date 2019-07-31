package com.test.autothon.auto.core;

import com.test.autothon.common.Constants;
import com.test.autothon.common.ReadPropertiesFile;
import com.test.autothon.ui.core.UIOperations;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class TestDemo2019 extends UIOperations {

    public static Map<String, Map<String, String>> movieDeatils = new HashMap<>();
    private static Map<String, String> movieInfo = new HashMap<>();

    public void getAllValuesFromPropFile() {
        Properties prop = ReadPropertiesFile.loadPropertiesfile(Constants.configResourcePath + "/movieName1.properties");
        Set<String> keys = prop.stringPropertyNames();
        for (String key : keys) {
            String movieName = prop.getProperty(key);
            movieDeatils.put(movieName, null);
        }
    }

    public void searchMoviesInGoogle() {
        launchURL(ReadPropertiesFile.getPropertyValue("google.url"));
        String searchElementGoogle = ReadPropertiesFile.getPropertyValue("google.search.textbox");
        String searchButtonGoogle = ReadPropertiesFile.getPropertyValue("google.search.button");
        String wikiLinkElementGoogle = ReadPropertiesFile.getPropertyValue("google.wiki.link");
        for (Map.Entry<String, Map<String, String>> entry : movieDeatils.entrySet()) {
            String movieName = entry.getKey() + "+ wikipedia";
            enterText(searchElementGoogle, movieName);
            click(searchButtonGoogle);
            String wikiLinkUrl = getTextFromElementAttribute(wikiLinkElementGoogle, "href");
            movieInfo.put("wikilink", wikiLinkUrl);
            movieDeatils.put(movieName, movieInfo);
        }
    }

    public void getDirectorNameAndIMDBLinkFromWiki() {

    }
}
