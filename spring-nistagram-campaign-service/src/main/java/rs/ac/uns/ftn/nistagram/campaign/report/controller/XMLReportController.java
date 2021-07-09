package rs.ac.uns.ftn.nistagram.campaign.report.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.nistagram.campaign.domain.Campaign;
import rs.ac.uns.ftn.nistagram.campaign.http.payload.ExistDbPayload;
import rs.ac.uns.ftn.nistagram.campaign.report.domain.CampaignReportBundle;
import rs.ac.uns.ftn.nistagram.campaign.report.domain.Content;
import rs.ac.uns.ftn.nistagram.campaign.report.service.XMLReportService;

import java.util.List;

@RestController
@RequestMapping("api/campaigns/report")
@AllArgsConstructor
public class XMLReportController {

    private final XMLReportService xmlReportService;

    @GetMapping
    public String testGet(@RequestBody ExistDbPayload payload) {
        return xmlReportService.testGet(payload.getDatabasePath());
    }

    @PutMapping
    public String testPost(@RequestBody ExistDbPayload payload) {
        return xmlReportService.testPost(payload.getDatabasePath(), payload.getXmlData());
    }

    @GetMapping("log")
    public CampaignReportBundle logCampaignReport(@RequestHeader("${nistagram.headers.agent}") String agent) {
        return xmlReportService.generateCampaignReport(agent);
    }

    // TODO Generate PDF for a certain API token owner
}
