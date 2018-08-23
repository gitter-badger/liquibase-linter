package com.wcg.liquibase.config.rules.generic;

import com.wcg.liquibase.config.RuleConfig;
import com.wcg.liquibase.config.rules.Rule;
import liquibase.change.Change;

public class NotBlankRule extends Rule<String> {

    public NotBlankRule(RuleConfig ruleConfig) {
        super(ruleConfig);
    }

    @Override
    public boolean invalid(String value, Change change) {
        return value == null || value.isEmpty();
    }

}