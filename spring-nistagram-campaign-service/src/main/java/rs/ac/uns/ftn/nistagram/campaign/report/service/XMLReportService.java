package rs.ac.uns.ftn.nistagram.campaign.report.service;

import com.fasterxml.jackson.databind.JsonSerializer;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.nistagram.campaign.domain.Advertisement;
import rs.ac.uns.ftn.nistagram.campaign.domain.Campaign;
import rs.ac.uns.ftn.nistagram.campaign.domain.LongTermCampaign;
import rs.ac.uns.ftn.nistagram.campaign.domain.OneTimeCampaign;
import rs.ac.uns.ftn.nistagram.campaign.domain.enums.CampaignType;
import rs.ac.uns.ftn.nistagram.campaign.report.domain.CampaignReportBundle;
import rs.ac.uns.ftn.nistagram.campaign.report.domain.Content;
import rs.ac.uns.ftn.nistagram.campaign.report.domain.StatisticsDisplayBundle;
import rs.ac.uns.ftn.nistagram.campaign.report.http.client.ContentClient;
import rs.ac.uns.ftn.nistagram.campaign.report.http.client.ExistDbClient;
import rs.ac.uns.ftn.nistagram.campaign.repository.CampaignRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class XMLReportService {

    private final ExistDbClient existDbClient;
    private final CampaignRepository<Campaign> campaignRepository;
    private final ContentClient contentClient;

    public StatisticsDisplayBundle testGet(String url) {
        return existDbClient.get(url);
    }

    public void testPost(String databasePath, StatisticsDisplayBundle statistics) {
        existDbClient.put(databasePath, statistics);
    }

    public List<StatisticsDisplayBundle> generateCampaignReport(String agent) {
        CampaignReportBundle campaignReportBundle = collectDomainData(agent);
        return List.of(
                extractUserInteractionsAsStatistics(campaignReportBundle),
                extractCommentsAsStatistics(campaignReportBundle),
                extractAdClicksAsStatistics(campaignReportBundle),
                extractCampaignAdClicksAsStatistics(campaignReportBundle)
        );
    }

    public CampaignReportBundle collectDomainData(String agent) {
        // Fetch all campaigns for this agent
        List<Campaign> allCampaigns = campaignRepository.findByUsername(agent);
        allCampaigns.forEach(camp -> log.info("xml-report-service: Campaign: {}", camp.getName()));

        // Bundle campaigns with their respective Contents
        CampaignReportBundle bundle = CampaignReportBundle.init();
        allCampaigns.forEach(campaign -> {
            Content content;
            if (campaign.getType() == CampaignType.POST)
                content = contentClient.getPostById(campaign.getContentId());
            // If Campaign is type Story, there are NO user interactions available for it
            else content = null;

            bundle.addPair(campaign, content);
        });

        log.info("xml-report-service: Collected a total of {} campaigns for agent {}", bundle.getPairs().size(), agent);
        return bundle;
    }

    // TODO Campaigns with most user interactions
    // TODO Campaigns with most comments
    // TODO Campaigns with most ad clicks in total
    // TODO Ads with most clicks

    private StatisticsDisplayBundle extractUserInteractionsAsStatistics(CampaignReportBundle campaignReportBundle) {
        StatisticsDisplayBundle statistics = new StatisticsDisplayBundle("User interactions");

        campaignReportBundle.getPairs().forEach(pair -> {
            if (pair.getFirst().getType() == CampaignType.POST)
                statistics.addStatisticsPair(pair.getFirst().getName(), pair.getSecond().getUserInteractions().size());
        });

        return statistics;
    }

    private StatisticsDisplayBundle extractCommentsAsStatistics(CampaignReportBundle campaignReportBundle) {
        StatisticsDisplayBundle statistics = new StatisticsDisplayBundle("Comments");

        campaignReportBundle.getPairs().forEach(pair -> {
            if (pair.getFirst().getType() == CampaignType.POST)
                statistics.addStatisticsPair(pair.getFirst().getName(), pair.getSecond().getComments().size());
        });

        return statistics;
    }

    private StatisticsDisplayBundle extractAdClicksAsStatistics(CampaignReportBundle campaignReportBundle) {
        StatisticsDisplayBundle statistics = new StatisticsDisplayBundle("Ad clicks");

        campaignReportBundle.getPairs().forEach(pair -> {
            pair.getFirst().getAdvertisements().forEach(ad -> {
                statistics.addStatisticsPair(ad.getMediaUrl(), ad.getAdvertisementClicks().size());
            });
        });

        return statistics;
    }

    private StatisticsDisplayBundle extractCampaignAdClicksAsStatistics(CampaignReportBundle campaignReportBundle) {
        StatisticsDisplayBundle statistics = new StatisticsDisplayBundle("Ad clicks");

        campaignReportBundle.getPairs().forEach(pair -> {
            int clicks = 0;
            for (Advertisement ad : pair.getFirst().getAdvertisements())
                clicks += ad.getAdvertisementClicks().size();
            statistics.addStatisticsPair(pair.getFirst().getName(), clicks);
        });

        return statistics;
    }
}
