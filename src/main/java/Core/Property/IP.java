package Core.Property;

import javafx.beans.property.SimpleStringProperty;

import java.io.Serializable;

public class IP implements Serializable {
    private transient SimpleStringProperty IP;
    private transient SimpleStringProperty password;
    private String IPString;
    private String passwordString;

    public IP(String IP, String password) {
        this.IPString = IP;
        this.passwordString = password;
        this.IP = new SimpleStringProperty(IP);
        this.password = new SimpleStringProperty(password);


    }

    public String getIP() {
        IP.set(IPString);
        return IP.get();
    }

    public SimpleStringProperty IPProperty() {
        IP.set(IPString);
        return IP;
    }

    public void setIP(String IP) {
        IPString = IP;
        this.IP.set(IP);
    }

    public String getPassword() {
        password.set(passwordString);
        return password.get();
    }

    public SimpleStringProperty passwordProperty() {
        password.set(passwordString);
        return password;
    }

    public void setPassword(String password) {
        passwordString = password;
        this.password.set(password);
    }

}
