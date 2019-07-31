package com.test.autothon.auto.core;

import com.test.autothon.common.ReadPropertiesFile;
import com.test.autothon.ui.core.UIOperations;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Demo2019 extends UIOperations {

    Properties objectProperties;

    Properties movieNames;

    private String configResourcePath = Thread.currentThread().getContextClassLoader().getResource("demo").getPath();

    List<String> movies;

    private final static Logger logger = LogManager.getLogger(Demo2019.class);

    public void loadProperties() {
        //  String objProps = configResourcePath + "/objprop.properties";
        String movieName = configResourcePath + "\\movies.properties";
        // objectProperties = ReadPropertiesFile.loadPropertiesfile(objProps);
        movieNames = ReadPropertiesFile.loadPropertiesfile(movieName.replaceAll("%20", " "));
    }

    public void createMovieList(int movieCount) {
        int index = 1;
        movies = new ArrayList<>();
        while (index <= movieCount) {
            movies.add(movieNames.getProperty("movie" + index));
            index++;
        }
        logger.info(movies);
    }

    public Demo2019() {
        loadProperties();
    }


}
