package rs.ac.uns.ftn.nistagram.campaign.exception;

public class EntityNotFoundException extends CampaignException {

    public EntityNotFoundException(String message) {
        super(message);
    }
    public EntityNotFoundException() {
        super("Entity not found!");
    }

}
