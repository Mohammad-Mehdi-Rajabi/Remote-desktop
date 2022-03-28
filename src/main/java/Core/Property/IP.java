package Core.Property;

import javafx.beans.property.SimpleStringProperty;

public class IP {
    private SimpleStringProperty IP;
    private SimpleStringProperty password;

    public IP(String IP, String password) {
        this.IP= new SimpleStringProperty(IP);
        this.password= new SimpleStringProperty(password);


    }

    public String getIP() {
        return IP.get();
    }

    public SimpleStringProperty IPProperty() {
        return IP;
    }

    public void setIP(String IP) {
        this.IP.set(IP);
    }

    public String getPassword() {
        return password.get();
    }

    public SimpleStringProperty passwordProperty() {
        return password;
    }

    public void setPassword(String password) {
        this.password.set(password);
    }
}
