package models;

/**
 * Created by florenciavelarde on 22/5/16.
 */
public class ArticlesPerAuthor {
    public String author;
    public int articlesCount;

    public ArticlesPerAuthor(String author, int articlesCount) {
        this.author = author;
        this.articlesCount = articlesCount;
    }

    public void addToCount() {
        articlesCount += 1;
    }
}