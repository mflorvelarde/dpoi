package models;

/**
 * Created by florenciavelarde on 11/5/16.
 */
import com.avaje.ebean.Model;
import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Domain extends Model {
    @Id
    @Column(name = "domainId")
    public Long id;
    public String domain;
    public String name;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "domain") @JsonBackReference
    public List<Path> paths;


    public static Finder<Long, Domain> find = new Finder<Long, Domain>(Domain.class);

    public Domain (String domain, String name) {
        this.name = name;
        this.domain = domain;
        this.paths = new ArrayList<>();
    }

    public void addArticle(Path paths) {
        this.paths.add(paths);
    }
}
