package models;

import com.avaje.ebean.Model;

import javax.persistence.*;

/**
 * Created by florenciavelarde on 11/5/16.
 */
@Entity
public class Prvademecum extends Model {
    @Id
    public Long id;
    public String domain;
    public String path;
    public String slugsha;
    public String describes;
    public String title;
    public String laboratory;

    public static Finder<Long, Prvademecum > find = new Finder<Long, Prvademecum>(Prvademecum.class);

    public Prvademecum(String domain, String path, String slugsha, String title, String describes, String laboratory) {
        this.domain = domain;
        this.path = path;
        this.slugsha = slugsha;
        this.title = title;
        this.describes = describes;
        this.laboratory = laboratory;
    }
}
