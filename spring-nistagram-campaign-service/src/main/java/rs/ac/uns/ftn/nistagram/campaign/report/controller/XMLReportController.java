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
    public String testGet() {
        return "Hello world";
    }

    @GetMapping
    public String testGet(@RequestBody ExistDbPayload payload) {
        System.out.println("xml-report: Testing get...");
        return xmlReportService.testGet(payload.getDatabasePath());
    }

    @PutMapping
    public String testPost(@RequestBody ExistDbPayload payload) {
        System.out.println("xml-report: Testing post...");
        return "TBD.";
    }

    // TODO How to package XML? String to XML or directly send?
    // TODO Generate PDF for a certain API token owner
}
