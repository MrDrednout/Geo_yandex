/**
 * Created by coole on 18.01.2017.
 */
public class SQL {

        private static final String url = "jdbc:mysql://92.53.96.107:3306/mrdrednout_msale";
        private static final String user = "mrdrednout_msale";
        private static final String pass = "V7gVopcK";

        private static Connection con;
        private static Statement stmt;
        ResultSet rs;

        public int inspection_password(String login, String password) {
            String query = "select id_user, f, i, o, flg_block from USERS where login = '" + login + "' and password = '" + password + "'";
            int id_user = 0;
            int block_flg = 0;
            int fin = 0; // 0 - неверный логин или пароль, 1 - пользователь заблокирован, 2 - вход подтвержден

            try {
                con = DriverManager.getConnection(url, user, pass);
                stmt = con.createStatement();
                rs = stmt.executeQuery(query);

                while (rs.next()) {
                    id_user = rs.getInt("id_user");
                    block_flg = rs.getInt("flg_block");
                }

                if (id_user == 0) fin = 0;
                else if (block_flg == 1) fin = 1;
                else fin = 2;

            } catch (SQLException sqlEx) {
                sqlEx.printStackTrace();
            } finally {
                try {
                    con.close();
                } catch (SQLException se) { /*can't do anything */ }
                try {
                    stmt.close();
                } catch (SQLException se) { /*can't do anything */ }
                try {
                    rs.close();
                } catch (SQLException se) { /*can't do anything */ }
            }
            return fin;
        }


}
