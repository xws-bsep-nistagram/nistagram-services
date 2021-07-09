package rs.ac.uns.ftn.nistagram.campaign.report.domain;

import lombok.Getter;

import java.util.List;

public class Content {

    // Note, we don't need the objects themselves, we care about statistics, ie length of these lists
    private List<Object> comments;
    private List<Object> userInteractions;

    public List<Object> getComments() {
        if (comments == null)
            comments = List.of();
        return comments;
    }

    public List<Object> getUserInteractions() {
        if (userInteractions == null)
            userInteractions = List.of();
        return userInteractions;
    }
}
