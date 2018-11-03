package com.pencil.engine.utils.service;

import com.pencil.engine.Metrics;
import com.pencil.engine.Settings;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * @author Matthias Kovacic
 * This class handles all analytic data and sends it to bStats.
 */
public class MetricsService {

    //TODO: Log

    public enum MetricsLogType {
        COMMAND_USAGE_RATIO("command_usage_ratio"),
        SELECTION_TYPE("selection_type");

        private String name;

        MetricsLogType(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    private Metrics metrics;

    //Command Usage Ratio
    private int commands;
    private int guiCommands;

    //Selection Type
    private int normalType;
    private int polyType;

    public MetricsService(Metrics metrics) {
        this.metrics = metrics;

        commands = 0;
        guiCommands = 0;

        normalType = 0;
        polyType = 0;

        metrics.addCustomChart(new Metrics.AdvancedPie(MetricsLogType.COMMAND_USAGE_RATIO.getName(), new Callable<Map<String, Integer>>() {
            @Override
            public Map<String, Integer> call() throws Exception {
                Map<String, Integer> valueMap = new HashMap<>();

                valueMap.put("Command Usage", getCommandUsage());
                valueMap.put("Interface Usage", getInterfaceUsage());

                return valueMap;
            }

            private int getCommandUsage() {
                return Settings.getAnalytics().<Integer>get("analytics.command-usage-ratio.commands");
            }

            private int getInterfaceUsage() {
                return Settings.getAnalytics().<Integer>get("analytics.command-usage-ratio.interface");
            }
        }));

        metrics.addCustomChart(new Metrics.AdvancedPie(MetricsLogType.SELECTION_TYPE.getName(), new Callable<Map<String, Integer>>() {
            @Override
            public Map<String, Integer> call() throws Exception {
                Map<String, Integer> valueMap = new HashMap<>();

                valueMap.put("Normal Selection", getNormalTypeUsage());
                valueMap.put("Poly Selection", getPolyTypeUsage());

                return valueMap;
            }

            private int getNormalTypeUsage() {
                return Settings.getAnalytics().<Integer>get("analytics.selection-type.normal");
            }

            private int getPolyTypeUsage() {
                return Settings.getAnalytics().<Integer>get("analytics.selection-type.poly");
            }
        }));
    }

    public int getCommands() {
        return commands;
    }

    public int getGuiCommands() {
        return guiCommands;
    }

    public void logCommand(boolean command) {
        if (command) {
            commands++;
        } else {
            guiCommands++;
        }
    }

    public int getNormalType() {
        return normalType;
    }

    public int getPolyType() {
        return polyType;
    }

    public void logSelectionType(boolean poly) {
        if (poly) {
            polyType++;
        } else {
            normalType++;
        }
    }

}
