package com.github.hanyaeger.api.engine;

import org.apache.commons.cli.*;

import java.util.Optional;

public class ArgumentParser {

    private static final String NO_SPASH = "noSplash";
    private static final String NO_SPASH_DESCRIPTION = "Do not show the splash screen";

    YaegerConfig parse(final String[] args) {
        var config = new YaegerConfig();

        var options = new Options();
        options.addOption(NO_SPASH, false, NO_SPASH_DESCRIPTION);

        var formatter = new HelpFormatter();

        var parser = new DefaultParser();

        Optional<CommandLine> cmd = Optional.empty();

        try {
            cmd = Optional.of(parser.parse(options, args));
        } catch (ParseException e) {
            formatter.printHelp("help", options);
        }

        if (cmd.isEmpty()) {
            return config;
        }

        if (cmd.get().hasOption(NO_SPASH)) {
            config.setShowSplash(false);
        }

        return config;
    }
}
