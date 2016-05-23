package models;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by florenciavelarde on 7/5/16.
 */
public class Scrapper {

    String defaultContent;
    String type;
    String selector;
    String extract;
    String innerSchema;

    public JSONObject get(String url, JSONObject schema) {

        JSONObject response = new JSONObject();
        try {
            Document document = Jsoup.connect(url).get();
            JSONObject properties = schema.getJSONObject("properties");
            Iterator<String> keys = properties.keys();

            JSONObject jsonObject;
            String jsonProperty;
            Iterator<String> jsonKeys;

            while (keys.hasNext()) {

                this.defaultContent = null;
                this.type = null;
                this.selector = null;
                this.extract = null;
                this.innerSchema = null;

                jsonProperty = keys.next();
                jsonObject = properties.getJSONObject(jsonProperty);
                jsonKeys = jsonObject.keys();

                while (jsonKeys.hasNext()) setProperties(jsonKeys.next(), jsonObject);
                response = getElementsValue(document.select(selector), jsonProperty, response);
            }
        } catch (Exception e) { }
        System.out.println(response);
        return response;
    }

    private void setProperties(String propertiesKey, JSONObject jsonObject) throws JSONException {
        if (propertiesKey.equals("default")) defaultContent = jsonObject.getString(propertiesKey);
        if (propertiesKey.equals("type")) type = jsonObject.getString(propertiesKey);
        if (propertiesKey.equals("selector")) selector = jsonObject.getString(propertiesKey);
        if (propertiesKey.equals("extract")) extract = jsonObject.getString(propertiesKey);
        if (propertiesKey.equals("schema")) innerSchema = jsonObject.getString(propertiesKey);
    }

    private JSONObject getElementsValue(Elements elements, String jsonProperty, JSONObject response) throws JSONException, ParseException {
        if (type.equals("string")) {
            String val = "";
            if (elements.size() == 0) {
                if (defaultContent != null) {
                    val = defaultContent;
                }
            } else val = elements.text();
            response.append(jsonProperty, val);
        } else if (type.equals("date")) {
            SimpleDateFormat sdf;
            String date = elements.attr("content");
            sdf = new SimpleDateFormat(innerSchema);
            date = sdf.parse(date).toString();
            response.append(jsonProperty, date);

        } else if (type.equals("array")) {
            List<String> list = new ArrayList<String>();
            for (Element element : elements) {
                list.add(element.text());
            }
            response.append(jsonProperty, list.toArray());
        }
        return response;
    }
}
