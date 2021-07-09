package rs.ac.uns.ftn.nistagram.campaign.exception;

public class CampaignException extends RuntimeException {

    public CampaignException(String message) {
        super(message);
    }

    public CampaignException() {
        super("Nistagram campaign service exception has occured!");
    }

}
