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
public class AudioEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private SimpleStringProperty name;
    private SimpleStringProperty path;
    
    public AudioEntity(){
        name = new SimpleStringProperty();
        path = new SimpleStringProperty();
    }
    
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
        if (!(object instanceof AudioEntity)) {
            return false;
        }
        AudioEntity other = (AudioEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "models.AudioEntity[ id=" + id + " ]";
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getPath() {
        return path.get();
    }

    public void setPath(String path) {
        this.path.set(path);
    }
    
    private void writeObject(java.io.ObjectOutputStream out)
            throws IOException {
        out.writeObject(getName());
        out.writeObject(getPath());
    }

    private void readObject(java.io.ObjectInputStream in)
            throws IOException, ClassNotFoundException {
//        id = new SimpleStringProperty();
        name = new SimpleStringProperty();
        path = new SimpleStringProperty();

        String s = (String) in.readObject();
        setName(s);
        s = (String) in.readObject();
        setPath(s);
    }
}
