package rs.ac.uns.ftn.nistagram.campaign.report.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.nistagram.campaign.domain.Campaign;
import rs.ac.uns.ftn.nistagram.campaign.http.payload.ExistDbPayload;
import rs.ac.uns.ftn.nistagram.campaign.report.domain.CampaignReportBundle;
import rs.ac.uns.ftn.nistagram.campaign.report.domain.Content;
import rs.ac.uns.ftn.nistagram.campaign.report.domain.StatisticsDisplayBundle;
import rs.ac.uns.ftn.nistagram.campaign.report.service.XMLReportService;

import java.util.List;

@RestController
@RequestMapping("api/campaigns/report")
@AllArgsConstructor
public class XMLReportController {

    private final XMLReportService xmlReportService;

    @GetMapping
    public StatisticsDisplayBundle testGet(@RequestBody ExistDbPayload payload) {
        return xmlReportService.testGet(payload.getDatabasePath());
    }

    @PutMapping
    public void testPost(@RequestBody ExistDbPayload payload) {
        xmlReportService.testPost(payload.getDatabasePath(), payload.getXmlData());
    }

    @GetMapping("log")
    public List<StatisticsDisplayBundle> logCampaignReport(@RequestHeader("${nistagram.headers.agent}") String agent) {
        return xmlReportService.generateCampaignReport(agent);
    }

    @GetMapping("data")
    public CampaignReportBundle getData(@RequestHeader("${nistagram.headers.agent}") String agent) {
        return xmlReportService.collectDomainData(agent);
    }

    // TODO Generate PDF for a certain API token owner
}
