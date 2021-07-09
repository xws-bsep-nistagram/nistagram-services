package rs.ac.uns.ftn.nistagram.campaign.report.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Pair<T, K> {
    private T first;
    private K second;

    public Pair() {}

    public Pair(T first, K second) {
        this.first = first;
        this.second = second;
    }
}
