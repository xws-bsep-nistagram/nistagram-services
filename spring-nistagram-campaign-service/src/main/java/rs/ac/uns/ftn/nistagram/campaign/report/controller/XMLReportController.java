package rs.ac.uns.ftn.nistagram.campaign.report.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.nistagram.campaign.http.payload.ExistDbPayload;
import rs.ac.uns.ftn.nistagram.campaign.report.service.XMLReportService;

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

    // TODO How to package XML? String to XML or directly send?
    // TODO Generate PDF for a certain API token owner
}
