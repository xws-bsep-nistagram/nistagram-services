package rs.ac.uns.ftn.nistagram.campaign.report.pdf;

import rs.ac.uns.ftn.nistagram.campaign.report.domain.StatisticsDisplayBundle;

import java.util.List;

public class StatisticsToText {

    public static String call(List<StatisticsDisplayBundle> statistics) {
        StringBuilder builder = new StringBuilder();

        statistics.forEach(statistic -> {
            appendWithNewLine(builder, statistic.getName());

            statistic.sort();

            statistic.getStatistics().forEach(pair -> {
                appendWithNewLine(builder, List.of(pair.getFirst(), pair.getSecond().toString()));
            });

            newLine(builder);
        });

        return builder.toString();
    }

    private static void newLine(StringBuilder builder) {
        builder.append("\n");
    }

    private static void appendWithNewLine(StringBuilder builder, String text) {
        builder.append(text).append("\n");
    }

    private static void appendWithNewLine(StringBuilder builder, List<String> strings) {
        strings.forEach(string -> builder.append(string).append(" "));
        builder.append("\n");
    }
}
