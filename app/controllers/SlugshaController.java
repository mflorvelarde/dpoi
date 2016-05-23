package controllers;

import com.avaje.ebean.Ebean;
import models.Article;
import models.Path;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.List;

import static play.libs.Json.toJson;

/**
 * Created by florenciavelarde on 18/5/16.
 */
public class SlugshaController extends Controller {

    public static void addPath(Path path) {
        path.save();
    }

    public static Result getSlugshasInPath(String domain, String path, String slugsha) {
        List<Article> articles = Ebean.find(Article.class).where().eq("SLUGHSHA", slugsha).findList();

        if (!articles.isEmpty() && articles!= null) {
            return ok(toJson(articles));
        } else return notFound("Slughsha not found");
    }
}
