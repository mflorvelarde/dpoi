package models;



import com.avaje.ebean.Model;

import javax.persistence.*;

/**
 * Created by florenciavelarde on 7/5/16.
 */
@Entity
public class Article extends Model {

    @Id
    public Long id;
    @ManyToOne
    @JoinColumn(name = "articleInPath", referencedColumnName = "pathId")
    public Path path;
    public String name;
    public String slughsha;
    public String content;
    public String author;
    public String published;
    public String tuft;
    public String title;

    public static Finder<Long, Article> find = new Finder<Long, Article>(Article.class);

    public Article(Path path, String slughsha, String content, String author, String published,
                   String tuft, String title, String name) {
        this.path = path;
        this.slughsha = slughsha;
        this.content = content;
        this.author = author;
        this.published = published;
        this.tuft = tuft;
        this.title = title;
        this.name = name;
    }

}