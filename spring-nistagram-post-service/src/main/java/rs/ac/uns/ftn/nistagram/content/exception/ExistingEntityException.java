package rs.ac.uns.ftn.nistagram.content.exception;

public class ExistingEntityException extends NistagramException {

    public ExistingEntityException(String className) {
        super(className + " with this PK already exists in the database.");
    }

    public ExistingEntityException(String className, String entityName) {
        super(className + " labeled " + entityName + " already exists in the database");
    }
}
