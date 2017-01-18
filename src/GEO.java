import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.*;
/**
 * Created by coole on 11.07.2016.
 */
public class GEO {

    public static void main(String[] args) throws Exception {
        geo_parsing(geo_api());
    }

    static void geo_parsing(StringBuilder text_api) {
        System.out.println(text_api);
        String xy = null, x=null, y=null, k=null;
        String pattern = "<pos>[a-z0-9. -]{1,20}</pos>";

        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(text_api);

        while (m.find()) {
            xy = m.group();
            System.out.println(xy);
        }

        String pattern2 = "\\d*[.]\\d*" ;
        Pattern p2 = Pattern.compile(pattern2);
        Matcher m2 = p2.matcher(xy);
/*
        while (m2.find()) {
            k = m2.group();
            System.out.println(k);
        }
*/
        m2.find();
        y = m2.group();

        m2.find();
        x = m2.group();

        System.out.println(x);
        System.out.println(y);

    }

    static StringBuilder geo_api() throws Exception {
        URLConnection connection = new URL("https://geocode-maps.yandex.ru/1.x/?geocode=Москва+Константина Царева+18+118").openConnection();
        InputStream is = connection.getInputStream();
        InputStreamReader reader = new InputStreamReader(is);
        char[] buffer = new char[256];
        int rc;
        StringBuilder sb = new StringBuilder();
        while ((rc = reader.read(buffer)) != -1)
            sb.append(buffer, 0, rc);
        reader.close();
        return sb;
    }

}
