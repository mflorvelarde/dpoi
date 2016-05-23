package controllers;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.RawSql;
import com.avaje.ebean.RawSqlBuilder;
import com.avaje.ebean.SqlUpdate;
import models.Article;
import models.Path;
import play.mvc.*;

import java.util.List;

import static play.libs.Json.toJson;

/**
 * Created by florenciavelarde on 11/5/16.
 */
public class PathController extends Controller {

    public static void addPath(Path path) {
        path.save();
    }

    public static Result getSlugshasInPath(String domain, String path) {
        List<Path> paths = Ebean.find(Path.class).where().eq("NAME", path).findList();

        if (!paths.isEmpty() && paths!= null) {


            Path pathObject = paths.get(0);
            Long pathId = pathObject.id;
            List<Article> articles = Ebean.find(Article.class).where().eq("ARTICLEINPATH", pathId).findList();
/*
            String sql = " select author, tuft, slughsha"
                    + " from Article"
                    + " where articleinpath = " + Long.toString(pathId);

            RawSql rawSql = RawSqlBuilder.parse(sql)
                    // map the sql result columns to bean properties
                    .columnMapping("author", "author")
                    .columnMapping("tuft", "tuft")
                    .columnMapping("slughsha", "slughsha")
                            // we don't need to map this one due to the sql column alias
                            // .columnMapping("sum(d.order_qty*d.unit_price)", "totalAmount")
                    .create();

            List<Article> result = Ebean.find(Article.class).setRawSql(rawSql).findList();*/

            return ok(toJson(articles));
        } else return notFound("Path not found");
    }
}
