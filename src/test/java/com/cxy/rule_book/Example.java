package com.cxy.rule_book;

public class Example {
    public static void main(String[] args) {
        //define the interface
        RulesEngineFactory < Simple > rulesFactory =
            new RulesEngineFactory < Simple > ("TemplateRules.xls",
                Simple.class);
        Simple rules = (Simple) rulesFactory.newInstance();
        rules.hello1(12);
    }
}