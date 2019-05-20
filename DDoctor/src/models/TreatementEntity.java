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
public class TreatementEntity implements Serializable {

    public TreatementEntity(){
        id_patient = new SimpleStringProperty();
        name_audio = new SimpleStringProperty();
        level = new SimpleStringProperty();
        time = new SimpleStringProperty();
    }
    public void setLevel(String level) {
        this.level.set(level);
    }

    public String getId_patient() {
        return id_patient.get();
    }

    public void setId_patient(String id_patient) {
        this.id_patient.set(id_patient);
    }

    public String getName_audio() {
        return name_audio.get();
    }

    public void setName_audio(String name_audio) {
        this.name_audio.set(name_audio);
    }

    public String getLevel() {
        return level.get();
    }

    public String getTime() {
        return time.get();
    }

    public void setTime(String time) {
        this.time.set(time);
    }

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private SimpleStringProperty id_patient;
    private SimpleStringProperty name_audio;
    private SimpleStringProperty level;     //heard level
    private SimpleStringProperty time;      //treatement time
        
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
        if (!(object instanceof TreatementEntity)) {
            return false;
        }
        TreatementEntity other = (TreatementEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "models.TreatementEntity[ id=" + id + " ]";
    }
    
    private void writeObject(java.io.ObjectOutputStream out)
            throws IOException {
        out.writeObject(getId_patient());
        out.writeObject(getName_audio());
        out.writeObject(getLevel());
        out.writeObject(getTime());
    }

    private void readObject(java.io.ObjectInputStream in)
            throws IOException, ClassNotFoundException {
//        id = new SimpleStringProperty();
        id_patient = new SimpleStringProperty();
        name_audio =new SimpleStringProperty();
        level = new SimpleStringProperty();
        time = new SimpleStringProperty();

        String s = (String) in.readObject();
        setId_patient(s);
        
        s = (String) in.readObject();
        setName_audio(s);
        
        s = (String) in.readObject();
        setLevel(s);
        
        s = (String) in.readObject();
        setTime(s);
    }
    public int getLevelByNumber(){
        if (getLevel().equals("Rất kém")) return 1;
        if (getLevel().equals("Kém")) return 2;
        //if (getLevel().equals("Được")) return 3;
        if (getLevel().equals("Tốt")) return 4;
        if (getLevel().equals("Rất tốt")) return 5;
        return 3;
    }
}
