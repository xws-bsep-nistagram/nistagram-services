package rs.ac.uns.ftn.nistagram.campaign.report.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.nistagram.campaign.domain.Campaign;
import rs.ac.uns.ftn.nistagram.campaign.domain.LongTermCampaign;
import rs.ac.uns.ftn.nistagram.campaign.domain.OneTimeCampaign;
import rs.ac.uns.ftn.nistagram.campaign.domain.enums.CampaignType;
import rs.ac.uns.ftn.nistagram.campaign.report.domain.CampaignReportBundle;
import rs.ac.uns.ftn.nistagram.campaign.report.domain.Content;
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
    private final CampaignRepository<OneTimeCampaign> oneTimeCampaignCampaignRepository;
    private final CampaignRepository<LongTermCampaign> longTermCampaignCampaignRepository;
    private final ContentClient contentClient;

    public String testGet(String url) {
        return existDbClient.get(url);
    }

    public String testPost(String databasePath, String xmlData) {
        return existDbClient.put(databasePath, xmlData);
    }

    public CampaignReportBundle generateCampaignReport(String agent) {
//        allCampaigns.forEach(campaign -> {
//            log.info("campaign-report-log: {} of type {}:", campaign.getName(), campaign.getType());
//            log.info("Ad list:");
//            campaign.getAdvertisements().forEach(ad -> {
//                log.info("{} - Number of clicks: {}", ad.getCaption(), ad.getAdvertisementClicks().size());
//            });
//        });

        return collectDomainData(agent);
    }

    private CampaignReportBundle collectDomainData(String agent) {
        // Fetch all campaigns for this agent
        ArrayList<Campaign> allCampaigns = new ArrayList<>();
        allCampaigns.addAll(oneTimeCampaignCampaignRepository.findByUsername(agent));
        allCampaigns.addAll(longTermCampaignCampaignRepository.findByUsername(agent));

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

        return bundle;
    }
}
