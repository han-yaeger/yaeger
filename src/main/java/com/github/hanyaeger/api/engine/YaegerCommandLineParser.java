package com.github.hanyaeger.api.engine;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The commandline parser will parse the {@code String[]} provided as command line
 * arguments during start up to a {@link YaegerConfig}. If none are provided, the
 * {@link YaegerConfig} will contain the default values.
 */
class YaegerCommandLineParser {

    private static final String TABLE_FORMAT = "%-14s%-50s";

    /**
     * Parse the given command line Arguments and create a {@link YaegerConfig} that can be used to
     * initialize a {@link YaegerGame}.
     *
     * @param args the {@code String[]} that contain the command line arguments
     * @return an instance of {@link YaegerConfig} that can be used to initialize a {@link YaegerGame}
     */
    YaegerConfig parseToConfig(final List<String> args) {
        final var invalidArgs = args.stream().filter(a -> !isValidArgument(a)).collect(Collectors.toList());
        printInvalidArgumentWarning(invalidArgs);

        if (args.contains(YaegerCommandLineArgument.HELP.flag) || !invalidArgs.isEmpty()) {
            printHelpScreen();
        }

        final var yaegerConfig = new YaegerConfig();

        yaegerConfig.setShowSplash(!args.contains(YaegerCommandLineArgument.NO_SPLASH.flag));
        yaegerConfig.setShowBoundingBox(args.contains(YaegerCommandLineArgument.SHOW_BB.flag));
        yaegerConfig.setShowDebug(args.contains(YaegerCommandLineArgument.SHOW_DEBUG.flag));

        return yaegerConfig;
    }

    private void printHelpScreen() {
        System.out.println("Yaeger can be run with the following command line options:");
        for (final var argument : YaegerCommandLineArgument.values()) {
            System.out.format(TABLE_FORMAT, " " + argument.flag, argument.explanation);
            System.out.print(System.lineSeparator());
        }
    }

    private void printInvalidArgumentWarning(final List<String> invalidArgs) {
        invalidArgs.forEach(
                arg -> System.out.println("WARNING: Invalid command line argument: " + arg)
        );
    }

    private boolean isValidArgument(final String argument) {
        final var validArguments = Arrays
                .stream(YaegerCommandLineArgument.values())
                .map(a -> a.flag)
                .collect(Collectors.toList());

        return validArguments.contains(argument);
    }

    private enum YaegerCommandLineArgument {
        HELP("--help", "Show this help screen with all commandline options"),
        SHOW_BB("--showBB", "Show the BoundingBox of all Colliders and Collided Entities"),
        SHOW_DEBUG("--showDebug", "Show a debug window with information about the Scene"),
        NO_SPLASH("--noSplash", "Skip the Splash screen during start up");

        private final String flag;
        private final String explanation;

        YaegerCommandLineArgument(final String flag, final String explanation) {
            this.flag = flag;
            this.explanation = explanation;
        }
    }
}
