package start;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class OraConnect {

    Connection c = null;//Соединение с БД

    public void OpenConnect() {

        String user = "U_COLLECTOR";//Логин пользователя
        String password = "d63wZt7TJ_KsdEmi";//Пароль пользователя
        String url = "jdbc:oracle:thin:@172.18.17.25:1521/DWHODI";//URL адрес
        String driver = "oracle.jdbc.driver.OracleDriver"; //Имя драйвера

        try {
            Class.forName(driver);//Регистрируем драйвер
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try{
            c = DriverManager.getConnection(url, user, password);//Установка соединения с БД
            System.out.println("Connect open");
        } catch(Exception e){
            e.printStackTrace();
        }

    }

    public ResultSet LoadAddress() {
        ResultSet rs = null;
        try{
            Statement st = c.createStatement();//Готовим запрос
            rs = st.executeQuery("select * from VIK_GEO_ADDRESS where ID_ADDRESS = 1");

            while (rs.next()) {
                System.out.println(rs.getString("ADDRESS"));
            }
        } catch(Exception e){
            e.printStackTrace() ;
        }

        return rs;
    }



    public void mOraClose() {
        try {
            if(c != null)
                c.close();
            System.out.println("Connect close");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
