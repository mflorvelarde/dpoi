package controllers;

import com.avaje.ebean.Ebean;
import models.*;
import org.json.JSONObject;
import play.db.ebean.Transactional;
import play.mvc.*;
import views.html.metrics;

import java.util.ArrayList;
import java.util.List;

import static play.libs.Json.toJson;

/**
 * Created by florenciavelarde on 7/5/16.
 */
public class ArticleController extends Controller {

    @Transactional
    public static Result getAllDomains() {
        List<Article> articles = Article.find.all();

        return ok(toJson(articles));
    }

    public static void addFile(Path path, String slugsha, String content, String author,
                               String published, String title, String tuft, String name) {
        Article article = new Article(path, slugsha, content, author, published, tuft, title, name);
        article.save();
    }

    @Transactional
    public static Result getPathsInDomain(String domain) {
        List<Article> articles = Ebean.find(Article.class).where().eq("DOMAIN", domain).findList();
        return ok(toJson(articles));
    }

    @Transactional
    public static Result populateLaNacion() {
        PopulateDatabase.populateLaNacion();
        return ok("Tabla cargada");
    }

    @Transactional
    public static Result populatePrvademecum() {
        //PopulateDatabase.populatePrvademecum();
        return ok("Tabla cargada");
    }

    @Transactional
    public static Result getArcticleBySlugsha(String slugsha) {
        List<Article> articles = Ebean.find(Article.class).where().eq("SLUGHSHA", slugsha).findList();
        return ok(toJson(articles));
    }

    public static Result getPrvademecum(String path, String slugsha) {
        JSONObject jsonObject = PopulateDatabase.populatePrvademecum(path, slugsha);
        if (jsonObject.length() > 1) return ok(jsonObject.toString());
        else return notFound();

    }

    @Transactional
    public static Result getArticlesCountByAuthor() {

        List<Article> allArticles = Ebean.find(Article.class).findList();

        List<ArticlesPerAuthor> result = new ArrayList<>();

        boolean found;
        for (Article article : allArticles) {
            found = false;
            for (int i = 0; i < result.size(); i++) {
                if (result.get(i).author.equals(article.author)) {
                    result.get(i).addToCount();
                    found = true;
                    break;
                }
            }
            if (!found) result.add(new ArticlesPerAuthor(article.author, 1));
        }

        return ok(toJson(result));
    }


    @Transactional
    public static Result getArticlesCountByHour() {

        List<Article> allArticles = Ebean.find(Article.class).findList();

        List<ArticlesPerHour> result = new ArrayList<>();

        boolean found;
        int hour;
        for (Article article : allArticles) {
            found = false;
            hour = castDate(article.published);

            for (int i = 0; i < result.size(); i++) {
                if (result.get(i).hour == hour) {
                    result.get(i).addToCount();
                    found = true;
                    break;
                }
            }
            if (!found) result.add(new ArticlesPerHour(hour, 1));
        }

        return ok(toJson(result));
    }

    @Transactional
    public static Result getMetrics() {
        return ok(metrics.render());
    }

    private static int castDate(String date) {
        String subString = date.substring(13, 15);
        return Integer.parseInt(subString);
    }
}

