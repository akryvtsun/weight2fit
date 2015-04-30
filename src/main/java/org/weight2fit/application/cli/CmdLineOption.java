package org.weight2fit.application.cli;

import org.kohsuke.args4j.Option;
import org.kohsuke.args4j.spi.DoubleOptionHandler;
import org.kohsuke.args4j.spi.OptionHandler;

import java.lang.annotation.Annotation;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * @author Andiry Kryvtsun
 */
public class CmdLineOption implements Option {

    private String name;
    private String[] aliases;
    private String usage;
    private String metaVar;
    private boolean required;
    private Class<? extends OptionHandler> handler;

    private CmdLineOption() {}

    @Override
    public String name() {
        return name;
    }

    @Override
    public String[] aliases() {
        return aliases;
    }

    @Override
    public String usage() {
        return usage;
    }

    @Override
    public String metaVar() {
        return metaVar;
    }

    @Override
    public boolean required() {
        return required;
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
        return null;
    }

    @Override
    public String[] forbids() {
        return null;
    }

    @Override
    public Class<? extends Annotation> annotationType() {
        return null;
    }

    public static class Builder {
        private String name;
        private String longName;
        private String description;
        private String metaVar = "value";
        private boolean required;
        private Class<? extends OptionHandler> handler = DoubleOptionHandler.class;

        private Builder() {}

        public static Builder create() {
            return new Builder();
        }

        public Builder name(final String name) {
            this.name = name;
            return this;
        }

        public Builder longName(String longName) {
            this.longName = longName;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder metaVar(String metaVar) {
            this.metaVar = metaVar;
            return this;
        }

        public Builder required() {
            this.required = true;
            return this;
        }

        public Builder handler(Class<? extends OptionHandler> handler) {
            this.handler = handler;
            return this;
        }

        public Option build() {

            checkArgument(this.name != null, "name must be not null");
            checkArgument(this.handler != null, "handler must be not null");

            final CmdLineOption option = new CmdLineOption();

            option.name = "-" + this.name;
            option.aliases = new String[] { "--" + this.longName };
            option.usage = this.description;
            option.metaVar = this.metaVar;
            option.required = this.required;
            option.handler = this.handler;

            return option;
        }
    }
}
