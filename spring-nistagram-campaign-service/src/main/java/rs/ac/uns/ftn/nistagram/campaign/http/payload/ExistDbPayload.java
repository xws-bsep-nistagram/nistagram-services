package rs.ac.uns.ftn.nistagram.campaign.http.payload;

import lombok.Getter;
import rs.ac.uns.ftn.nistagram.campaign.report.domain.StatisticsDisplayBundle;

@Getter
public class ExistDbPayload {

    private String databasePath;
    private StatisticsDisplayBundle xmlData;
}
