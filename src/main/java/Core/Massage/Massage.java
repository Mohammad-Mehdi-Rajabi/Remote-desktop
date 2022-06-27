package Core.Massage;

import java.io.Serializable;

public class Massage implements Serializable {
    public static final long serialVersionUID = 66131654865l;
    private String massage;
    private String path;
    private String fileName;
    private transient MassageType massageType;

    public enum MassageType {
        VALID_PASSWORD("valid_password"),
        FILE_UPLOADED_SUCCESSFULLY("file_uploaded_successfully"),
        SENDING_ROOT_OF_FILES("sending_root_of_files"),
        FILE_DOWNLOAD_REQUEST("file_download_request"),
        FILE_DOWNLOAD_REQUEST_REJECTED_CAUSE_DIRECTORY_SELECTED("file_download_request_rejected_cause_directory_selected"),
        FILE_DOWNLOAD_REQUEST_ACCEPTED("file_download_request_accepted"),
        FILE_UPLOAD_REQUEST("file_upload_request"),
        FILE_UPLOAD_REQUEST_REJECTED_CAUSE_FILE_SELECTED("file_download_upload_rejected_cause_file_selected"),
        FILE_UPLOAD_REQUEST_ACCEPTED("file_upload_request_accepted"),
        GET_ROOT_OF_FILES("get_root_of_files");

        private String massage;


        MassageType(String massage) {
            this.massage = massage;

        }

        public String getMassage() {
            return massage;
        }
    }

    public Massage(String massage) {
        this.massage = massage;
    }

    public Massage(MassageType massageType, String path) {
        this.path = path;
        this.massageType = massageType;
        massage = massageType.getMassage();
    }

    public Massage(MassageType massageType, String path, String fileName) {
        this.path = path;
        this.fileName = fileName;
        this.massageType = massageType;
        massage = massageType.getMassage();
    }

    public Massage(MassageType massageType) {
        massage = massageType.getMassage();
        this.massageType = massageType;
    }

    public String getMassage() {
        return massage;
    }

    public void setMassage(String massage) {
        this.massage = massage;
    }

    public MassageType getMassageType() {
        return massageType;
    }

    public void setMassageType(MassageType massageType) {
        this.massageType = massageType;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
