package rs.ac.uns.ftn.nistagram.campaign.report.domain;

import lombok.Getter;
import rs.ac.uns.ftn.nistagram.campaign.domain.Campaign;

import java.util.ArrayList;
import java.util.List;

@Getter
public class CampaignReportBundle {
    private List<Pair<Campaign, Content>> pairs;

    public CampaignReportBundle() {}

    public static CampaignReportBundle init() {
        CampaignReportBundle bundle = new CampaignReportBundle();
        bundle.pairs = new ArrayList<>();
        return bundle;
    }

    public void addPair(Campaign campaign, Content content) {
        this.pairs.add(new Pair<>(campaign, content));
    }
}
