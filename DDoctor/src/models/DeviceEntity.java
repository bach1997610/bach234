/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.io.IOException;
import java.io.Serializable;
import javafx.beans.property.SimpleStringProperty;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author binhm
 */
@Entity
public class DeviceEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    private SimpleStringProperty id_patient;
    private SimpleStringProperty deviceName;
    private SimpleStringProperty usageDate;
    
    public DeviceEntity(){
        id_patient = new SimpleStringProperty();
        deviceName = new SimpleStringProperty();
        usageDate = new SimpleStringProperty();
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DeviceEntity)) {
            return false;
        }
        DeviceEntity other = (DeviceEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "models.DeviceEntity[ id=" + id + " ]";
    }

    public String getId_patient() {
        return id_patient.get();
    }

    public void setId_patient(String id_patient) {
        this.id_patient.set(id_patient);
    }

    public String getDeviceName() {
        return deviceName.get();
    }

    public void setDeviceName(String deviceName) {
        this.deviceName.set(deviceName);
    }

    public String getUsageDate() {
        return usageDate.get();
    }

    public void setUsageDate(String usageDate) {
        this.usageDate.set(usageDate);
    }
    
    private void writeObject(java.io.ObjectOutputStream out)
            throws IOException {
        out.writeObject(getId_patient());
        out.writeObject(getDeviceName());
        out.writeObject(getUsageDate());
    }

    private void readObject(java.io.ObjectInputStream in)
            throws IOException, ClassNotFoundException {
        id_patient = new SimpleStringProperty();
        deviceName = new SimpleStringProperty();
        usageDate = new SimpleStringProperty();

        String s = (String) in.readObject();
        setId_patient(s);
        s = (String) in.readObject();
        setDeviceName(s);
        s = (String) in.readObject();
        setUsageDate(s);
        
    }
    
}
