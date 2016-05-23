package controllers;

import models.Domain;
import models.Path;
import models.Scrapper;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by florenciavelarde on 7/5/16.
 */
public class PopulateDatabase {
    public static void populateLaNacion() {
        //public static void main(String[] args) {
        ArticleController articleController = new ArticleController();
        final String prefix = "http://www.";
        final String domain = "lanacion.com.ar";
        final String name = "La Nacion";
        Scrapper scrapper = new Scrapper();
        JSONObject jsonSchema = null;
        String schema = "{\n" +
                "  \"$schema\": \"http://json-schema.org/draft-04/schema#\",\n" +
                "  \"title\": \"La Nacion Article\",\n" +
                "  \"type\": \"object\",\n" +
                "  \"properties\": {\n" +
                "    \"title\": {\n" +
                "      \"type\": \"string\",\n" +
                "      \"selector\": \"h1[itemprop=headline]\",\n" +
                "      \"extract\": \"text\"\n" +
                "    },\n" +
                "    \"tuft\": {\n" +
                "      \"type\": \"string\",\n" +
                "      \"selector\": \"p.bajada\",\n" +
                "      \"extract\": \"text\"\n" +
                "    },\n" +
                "    \"author\": {\n" +
                "      \"type\": \"string\",\n" +
                "      \"selector\": \"a[itemprop=author]\",\n" +
                "      \"extract\": \"text\",\n" +
                "      \"default\": \"anonymous\"\n" +
                "    },\n" +
                "    \"published\": {\n" +
                "      \"type\": \"date\",\n" +
                "      \"selector\": \"meta[itemprop=datePublished]\",\n" +
                "      \"extract\": \"[content]\",\n" +
                "      \"schema\" : \"yyyy-MM-dd HH:mm:ss\"\n" +
                "    },\n" +
                "    \"content\": {\n" +
                "      \"type\": \"array\",\n" +
                "      \"selector\": \"#cuerpo > p\",\n" +
                "      \"extract\": \"text\",\n" +
                "      \"items\": {\n" +
                "        \"type\": \"string\"\n" +
                "      }\n" +
                "    }\n" +
                "  }\n" +
                "}";

        try {
            jsonSchema = new JSONObject(schema);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String slugsha = "";
        String content = "";
        String author = "";
        String title = "";
        String published = "";
        String tuft = "";

        Domain domainObject = new Domain(domain, name);
        DomainController.addDomain(domainObject);

        Path path = new Path("articles", domainObject);
        PathController.addPath(path);

        for (int i = 100; i <= 200; i++) {
            slugsha = Integer.toString(1885) + Integer.toString(i);
            JSONObject result = scrapper.get(prefix + domain + "/" + slugsha, jsonSchema);
            //System.out.println(Integer.toString(i) + " : " + Integer.toString(result.length()));
            if (result.length() != 0) {

                content = "";

                if (result.has("content")) content = result.get("content").toString();
                if (content.length() > 255) content = content.substring(0, 254);

                if (result.has("author")) author = result.get("author").toString();
                if (result.has("title")) title = result.get("title").toString();
                if (result.has("published")) published = result.get("published").toString();
                if (result.has("tuft")) tuft = result.get("tuft").toString();
                if (tuft.length() > 255) tuft = tuft.substring(0, 254);

                //if (result.get("tuft") != null) tuft = result.get("tuft").toString();
                articleController.addFile(path, slugsha, content, author, published, title, tuft, name);
            }
        }
    }

    //public static void main(String[] args) {


    public static JSONObject populatePrvademecum(String path, String slugsha) { //public static void main(String[] args) {

        ArticleController articleController = new ArticleController();
        final String prefix = "http://www.";
        final String domain = "prvademecum.com";
        Scrapper scrapper = new Scrapper();
        JSONObject jsonSchema = null;
        String schema = "{\n " +
                "\"$schema\": \"http://json-schema.org/draft-04/schema#\",\n " +
                "\"title\": \"Vademecum Product\",\n" +
                "\"type\": \"object\",\n" +
                "\"path\": \"/products\",\n" +
                "\"describes\": [\"title\", \"laboratory\"],\n" +
                "\"properties\": {\n" +
                "\"title\": {\n" +
                "\"type\": \"string\",\n" +
                "\"selector\": \"#resultados > h1\",\n" +
                "\"extract\": \"text\"\n" +
                "},\n" +
                "\"laboratory\": {\n" +
                "\"type\": \"string\",\n" +
                "\"selector\": \"#resultados > h2\",\n" +
                "\"extract\": \"text\"\n" +
                "},\n" +
                "\"abstract\": {\n" +
                "\"type\": \"string\",\n" +
                "\"selector\": \"#resultados > :nth-child(3)\",\n" +
                "\"extract\": \"text\"\n" +
                "},\n" +
                "\"content\": {\n" +
                "\"type\": \"array\",\n" +
                "\"selector\": \"#resultados > :nth-child(n+4)\",\n" +
                "\"extract\": \"text\",\n" +
                "\"items\": {\n" +
                "\"type\": \"string\"\n" +
                "}\n" +
                "}\n" +
                "}\n}";


        try {
            jsonSchema = new JSONObject(schema);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONObject result = scrapper.get(prefix + domain + "/" + path, jsonSchema);
        System.out.println(result);
        return result;

        /*
        String content = "";
        String author = "";
        String title = "";
        String published = "";
        String tuft = "";

        for (int i = 100; i <= 200; i++) {
            slugsha = Integer.toString(1885) + Integer.toString(i);
            JSONObject result = scrapper.get(prefix + domain + "/" + path, jsonSchema);
            System.out.println(Integer.toString(i) + " : " + Integer.toString(result.length()));
            if (result.length() != 0) {

                content = "";


  *//*                  content = result.get("content").toString();
                    if (content.length() > 255) content = content.substring(0, 254);*//*

                author = result.get("author").toString();
                //title = result.get("title").toString();
                //published = result.get("published").toString();

                tuft = result.get("tuft").toString();
                if (tuft.length() > 255) tuft = tuft.substring(0, 254);

                //if (result.get("tuft") != null) tuft = result.get("tuft").toString();
                articleController.addFile(domain, "", slugsha, content, author, published, title, tuft);
            }
        }*/
    }
}
