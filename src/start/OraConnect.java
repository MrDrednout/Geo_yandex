package start;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class OraConnect {

    Connection c = null;//Соединение с БД

    public void OpenConnect() {

        ConnectString connectString = new ConnectString();

        String user = connectString.user();//Логин пользователя
        String password = connectString.password();//Пароль пользователя
        String url = connectString.url();//URL адрес
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

    public ResultSet SQLQuery(String query) {
        ResultSet rs = null;
        try{
            Statement st = c.createStatement();//Готовим запрос
            rs = st.executeQuery(query);
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

    public void mOraUpdate (String query) throws SQLException {
        Statement st = c.createStatement();//Готовим запрос
        st.executeUpdate(query);
    }

}
