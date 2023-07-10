import com.cinema.manager.DAO.Conexao;
import com.cinema.manager.Users.User;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        Conexao c = new Conexao();
        User user = new User(c.conectar());
        user.accessAccount();
    }
}