import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class TestConnection {

    public static void main(String[] args) {

        String url =
            "jdbc:sqlserver://localhost\\SAMPLE;" +
            "databaseName=master;" +
            "user=sa;" +
            "password=FCAtsugi35;" +
            "encrypt=false;" +
            "trustServerCertificate=true;";

        try (
            Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
        ) {

            System.out.println("接続成功");
            
            // Table_Wordに「りんご」を追加
            int result = stmt.executeUpdate("INSERT INTO Table_Word (単語) VALUES ('りんご')");
            System.out.println(result + "行が追加されました。");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}