package Core.Massage;

import java.io.Serializable;

public class Massage implements Serializable {
    private String massage;
    private transient MassageType massageType;

    public enum MassageType {
        VALID_PASSWORD("valid_password");

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

    public Massage(MassageType massageType) {
        massage = massageType.getMassage();
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
}
