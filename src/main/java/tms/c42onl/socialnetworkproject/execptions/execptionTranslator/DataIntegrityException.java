package tms.c42onl.socialnetworkproject.execptions.execptionTranslator;

public class DataIntegrityException extends StorageException{
    public DataIntegrityException(String message, Throwable cause) {
        super(message, cause);
    }
}
