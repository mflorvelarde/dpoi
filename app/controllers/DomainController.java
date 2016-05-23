package controllers;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.SqlUpdate;
import models.Article;
import models.Domain;
import models.Path;
import org.json.JSONObject;
import play.db.ebean.Transactional;
import play.mvc.*;

import java.util.List;

import static play.libs.Json.toJson;

/**
 * Created by florenciavelarde on 11/5/16.
 */
public class DomainController extends Controller{

    @Transactional
    public static Result getAllDomains() {
        List<Domain> domains = Domain.find.all();
        return ok(toJson(domains));
    }

    @Transactional
    public static void addDomain (Domain domain) {
        domain.save();
    }

    @Transactional
    public static Result getDomainByName(String domainName) {
        Domain domain = Ebean.find(Domain.class).where().eq("DOMAIN", domainName).findUnique();
        return ok(toJson(domain));
    }

    @Transactional
    public static Result getPathsInDomain(String domainName) {
        List<Domain> domains = Ebean.find(Domain.class).where().eq("DOMAIN", domainName).findList();

        if (!domains.isEmpty() && domains!=null ) {
            Domain domain = domains.get(0);
            Long domainId = domain.id;
            List<Path> paths = Ebean.find(Path.class).where().eq("domainPaths", domainId).findList();
            return ok(toJson(paths));
        } else return notFound("Domain not found");
    }
}
