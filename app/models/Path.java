package models;

import com.avaje.ebean.Model;
import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by florenciavelarde on 11/5/16.
 */
@Entity
public class Path extends Model {
    @Id
    @Column(name = "pathId")
    public Long id;
    public String name;
    @ManyToOne
    @JoinColumn(name = "domainPaths", referencedColumnName = "domainId")
    public Domain domain;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "path")  @JsonBackReference
    public List<Article> articles;

    public static Finder<Long, Domain> find = new Finder<Long, Domain>(Domain.class);

    public Path (String name, Domain domain) {
        this.name = name;
        this.domain = domain;
        this.articles = new ArrayList<>();
    }

    public void addArticle(Article article) {
        this.articles.add(article);
    }
}