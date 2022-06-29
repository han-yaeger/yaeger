package com.github.hanyaeger.core;

import com.github.hanyaeger.api.YaegerGame;

import java.util.Arrays;
import java.util.List;

/**
 * The commandline parser will parse the {@code String[]} provided as command line
 * arguments during start up to a {@link YaegerConfig}. If none are provided, the
 * {@link YaegerConfig} will contain the default values.
 */
public class YaegerCommandLineParser {

    private static final String TABLE_FORMAT = "%-20s%-50s";

    /**
     * Parse the given command line Arguments and create a {@link YaegerConfig} that can be used to
     * initialize a {@link YaegerGame}.
     *
     * @param args the {@code String[]} that contain the command line arguments
     * @return an instance of {@link YaegerConfig} that can be used to initialize a {@link YaegerGame}
     */
    public YaegerConfig parseToConfig(final List<String> args) {
        final var invalidArgs = args.stream().filter(a -> !isValidArgument(a)).toList();
        printInvalidArgumentWarning(invalidArgs);

        if (args.contains(YaegerCommandLineArgument.HELP.flag) || !invalidArgs.isEmpty()) {
            printHelpScreen();
        }

        return new YaegerConfig(
                !args.contains(YaegerCommandLineArgument.NO_SPLASH.flag),
                args.contains(YaegerCommandLineArgument.SHOW_BB.flag),
                args.contains(YaegerCommandLineArgument.SHOW_DEBUG.flag),
                args.contains(YaegerCommandLineArgument.ENABLE_SCROLL.flag),
                args.contains(YaegerCommandLineArgument.LIMIT_GWU.flag)
        );
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
                .toList();

        return validArguments.contains(argument);
    }

    private enum YaegerCommandLineArgument {
        HELP("--help", "Show this help screen with all commandline options."),
        SHOW_BB("--showBB", "Show the BoundingBox of all Colliders and Collided Entities."),
        SHOW_DEBUG("--showDebug", "Show a debug window with information about the Scene."),
        NO_SPLASH("--noSplash", "Skip the Splash screen during start up."),
        ENABLE_SCROLL("--enableScroll", "Enable the scrolling gesture for ScrollableDynamicScenes."),
        LIMIT_GWU("--limitGWU", "Limit the Game World Update to max 60/sec.");
        private final String flag;
        private final String explanation;

        YaegerCommandLineArgument(final String flag, final String explanation) {
            this.flag = flag;
            this.explanation = explanation;
        }
    }
}
