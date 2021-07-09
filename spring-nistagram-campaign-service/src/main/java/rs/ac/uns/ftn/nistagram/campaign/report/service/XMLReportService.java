package rs.ac.uns.ftn.nistagram.campaign.report.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.nistagram.campaign.report.http.client.ExistDbClient;

@Service
@AllArgsConstructor
public class XMLReportService {

    private final ExistDbClient existDbClient;

    public String testGet(String url) {
        return existDbClient.get(url);
    }

    public String testPost(String databasePath, String xmlData) {
        return existDbClient.put(databasePath, xmlData);
    }
}
