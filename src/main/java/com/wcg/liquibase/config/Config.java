package com.wcg.liquibase.config;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Pattern;

import static java.util.Optional.ofNullable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Config {

    private final Pattern ignoreContextPattern;
    private final String ignoreContextPatternString;
    private final Rules rules;

    @JsonCreator
    public Config(@JsonProperty("ignore-context-pattern") String ignoreContextPatternString, @JsonProperty("rules") Rules rules) {
        this.ignoreContextPatternString = ignoreContextPatternString;
        this.ignoreContextPattern = ignoreContextPatternString != null ? Pattern.compile(ignoreContextPatternString) : null;
        this.rules = rules;
    }


    public static Config fromInputStream(final InputStream inputStream) throws IOException {
        return new ObjectMapper().readValue(inputStream, Config.class);
    }

    public Pattern getIgnoreContextPattern() {
        return ignoreContextPattern;
    }

    public Rules getRules() {
        return rules;
    }

    Config mixin(Config toMix) {
        if (toMix == null) {
            return this;
        }
        return new Config(
                ofNullable(toMix.ignoreContextPatternString).orElse(ignoreContextPatternString),
                rules.mixin(toMix.rules)
        );
    }
}