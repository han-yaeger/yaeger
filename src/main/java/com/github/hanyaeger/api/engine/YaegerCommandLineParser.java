package com.github.hanyaeger.api.engine;

import java.util.List;

/**
 * The commandline parser will parse the {@code String[]} provided as command line
 * arguments during start up to a {@link YaegerConfig}. If none are provided, the
 * {@link YaegerConfig} will contain the default values.
 */
class YaegerCommandLineParser {

    private static final String NO_SPLASH = "--noSplash";
    private static final String NO_SPLASH_EXPLANATION = "Skip the Splash screen during start up";
    private static final String SHOW_BB = "--showBB";
    private static final String SHOW_BB_EXPLANATION = "Show the BoundingBox of all Colliders and Collided Entities";
    private static final String HELP = "--help";
    private static final String HELP_SORT_EXPLANATION = "Show this help screen with all commandline options";

    private static final String TABLE_FORMAT = "%-14s%-50s";

    /**
     * Parse the given command line Arguments and create a {@link YaegerConfig} that can be used to
     * initialize a {@link YaegerGame}.
     *
     * @param args the {@code String[]} that contain the command line arguments.
     * @return An instance of {@link YaegerConfig} that can be used to initialize a {@link YaegerGame}.
     */
    YaegerConfig parseToConfig(final List<String> args) {

        if (args.contains(HELP)) {
            printHelpScreen();
        }

        var yaegerConfig = new YaegerConfig();
        
        yaegerConfig.setShowSplash(!args.contains(NO_SPLASH));
        yaegerConfig.setShowBoundingBox(args.contains(SHOW_BB));

        return yaegerConfig;
    }

    private void printHelpScreen() {
        System.out.println("Yaeger can be run with the following command line options:");
        System.out.format(TABLE_FORMAT, " " + HELP, HELP_SORT_EXPLANATION);
        System.out.print(System.lineSeparator());
        System.out.format(TABLE_FORMAT, " " + NO_SPLASH, NO_SPLASH_EXPLANATION);
        System.out.print(System.lineSeparator());
        System.out.format(TABLE_FORMAT, " " + SHOW_BB, SHOW_BB_EXPLANATION);
    }
}
