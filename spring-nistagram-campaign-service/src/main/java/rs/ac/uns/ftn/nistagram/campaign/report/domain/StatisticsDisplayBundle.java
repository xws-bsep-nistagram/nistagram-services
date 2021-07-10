package rs.ac.uns.ftn.nistagram.campaign.report.domain;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Getter
public class StatisticsDisplayBundle {
    private String name;
    private List<Pair<String, Integer>> statistics;

    public StatisticsDisplayBundle() {}

    public StatisticsDisplayBundle(String name) {
        this.name = name;
        statistics = new ArrayList<>();
    }

    public void addStatisticsPair(String s, Integer i) {
        this.statistics.add(new Pair<>(s, i));
    }

    //Comparator.comparing(Pair::getSecond)
    public void sort() {
        statistics.sort((a, b) -> b.getSecond().compareTo(a.getSecond()));
    }
}
