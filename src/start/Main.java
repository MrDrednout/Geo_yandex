package start;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.sql.ResultSet;
import java.util.regex.*;
/**
 * Created by coole on 11.07.2016.
 */
public class Main {

    public static void main(String[] args) throws Exception {


        OraConnect geo = new OraConnect();
        geo.OpenConnect();
        ResultSet sel = geo.SQLQuery("select * from VIK_GEO_ADDRESS where id_address = 1");

        while (sel.next()) {
            System.out.println(sel.getString("ADDRESS"));
            String adr = sel.getString("ADDRESS");
            Matcher m = geo_parsing(geo_api(adr));

            m.find();
            System.out.println(m.group());
            String y = m.group();

            m.find();
            System.out.println(m.group());
            String x = m.group();

            geo.mOraUpdate("update VIK_GEO_ADDRESS set X_FLOAT = " + x + ", Y_FLOAT = " + y + ", actual_dt = sysdate where id_address = " + sel.getInt("id_address"));
        }



        geo.mOraClose();
    }

    static Matcher geo_parsing(StringBuilder text_api) {
        //System.out.println(text_api);
        String xy = null, x=null, y=null, k=null;
        String pattern = "<pos>[a-z0-9. -]{1,20}</pos>";

        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(text_api);

        m.find();
        xy = m.group();
        System.out.println(xy);

        String pattern2 = "\\d*[.]\\d*" ;
        Pattern p2 = Pattern.compile(pattern2);
        Matcher m2 = p2.matcher(xy);

        return m2;
    }

    static StringBuilder geo_api(String adr) throws Exception {
        URLConnection connection = new URL("https://geocode-maps.yandex.ru/1.x/?geocode="+adr).openConnection();
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
