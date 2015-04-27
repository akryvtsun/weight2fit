package org.weight2fit.application.ci;

import org.kohsuke.args4j.Option;
import org.kohsuke.args4j.spi.OptionHandler;

import java.lang.annotation.Annotation;

/**
 * Created by englishman on 4/24/15.
 */
public class CmdLineOption implements Option {

    private String name;
    private String longName;
    private String description;
    private Class<? extends OptionHandler> handler;

    CmdLineOption(String name, String longName, String description, Class<? extends OptionHandler> handler) {
        this.name = name;
        this.longName = longName;
        this.description = description;
        this.handler = handler;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public String[] aliases() {
        return new String[] { longName };
    }

    @Override
    public String usage() {
        return description;
    }

    @Override
    public String metaVar() {
        return "value";
    }

    @Override
    public boolean required() {
        return false;
    }

    @Override
    public boolean help() {
        return false;
    }

    @Override
    public boolean hidden() {
        return false;
    }

    @Override
    public Class<? extends OptionHandler> handler() {
        return handler;
    }

    @Override
    public String[] depends() {
        return new String[0];
    }

    @Override
    public String[] forbids() {
        return new String[0];
    }

    @Override
    public Class<? extends Annotation> annotationType() {
        return null;
    }
}
