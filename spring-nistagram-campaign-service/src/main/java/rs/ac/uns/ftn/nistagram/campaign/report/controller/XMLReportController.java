package rs.ac.uns.ftn.nistagram.campaign.report.controller;

import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.nistagram.campaign.report.service.XMLReportService;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("api/campaigns/report")
@AllArgsConstructor
public class XMLReportController {

    private final XMLReportService xmlReportService;

//    @GetMapping
//    public StatisticsDisplayBundle testGet(@RequestBody ExistDbPayload payload) {
//        return xmlReportService.testGet(payload.getDatabasePath());
//    }
//
//    @PutMapping
//    public void testPost(@RequestBody ExistDbPayload payload) {
//        xmlReportService.testPost(payload.getDatabasePath(), payload.getXmlData());
//    }

//    @PutMapping
//    public void generateReport(@RequestHeader("agent") String agent) {
//        xmlReportService.generateReport(agent);
//    }
//
//    @GetMapping
//    public List<StatisticsDisplayBundle> getReport(@RequestHeader("agent") String agent) {
//        return xmlReportService.getReport(agent);
//    }

    @GetMapping
    public ResponseEntity<Resource> pdf(@RequestHeader("agent") String agent) {
        xmlReportService.generateReportAndSavePdf(agent);
        Path path = Paths.get(xmlReportService.PDF_PATH + agent);
        Resource resource = null;
        try {
            resource = new UrlResource(path.toUri());
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(MediaType.APPLICATION_PDF_VALUE))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + ".pdf\"")
                .body(resource);
    }

//    @GetMapping("log")
//    public List<StatisticsDisplayBundle> logCampaignReport(@RequestHeader("${nistagram.headers.agent}") String agent) {
//        return xmlReportService.generateCampaignReport(agent);
//    }
//
//    @GetMapping("data")
//    public CampaignReportBundle getData(@RequestHeader("${nistagram.headers.agent}") String agent) {
//        return xmlReportService.collectDomainData(agent);
//    }

    // TODO Generate PDF for a certain API token owner
}
