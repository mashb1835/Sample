package mypackage;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public final class ConvertServlet extends HttpServlet {
    public ConvertServlet() {
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        // ① リクエストボディをJSON解析
        BufferedReader reader = request.getReader();
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        String requestBody = sb.toString();
        System.out.println("DEBUG: Request body: " + requestBody);

        // ② JSONから "fruit" の値を取得
        String fruit = extractFruit(requestBody);
        System.out.println("DEBUG: Extracted fruit: [" + fruit + "]");

        // ③ 入力された単語をDBに追加
        String registeredWord = insertWordToDatabase(fruit);

        // ④ 登録した単語のみをJSON形式で応答
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter writer = response.getWriter();
        String jsonResponse = "{\"word\":\"" + registeredWord + "\"}";
        writer.println(jsonResponse);
    }

    // JSONから "fruit" の値を抽出
    private String extractFruit(String json) {
        // "fruit":"xxx" を検索
        int startIndex = json.indexOf("\"fruit\":\"");
        if (startIndex == -1) {
            return "";
        }
        startIndex += "\"fruit\":\"".length();
        int endIndex = json.indexOf("\"", startIndex);
        if (endIndex == -1) {
            return "";
        }
        return json.substring(startIndex, endIndex);
    }

    // 入力された単語をDBのTable_Wordに追加
    private String insertWordToDatabase(String word) {
        String url = "jdbc:sqlserver://localhost\\SAMPLE;" +
                     "databaseName=TestDB;" +
                     "user=sa;" +
                     "password=FCAtsugi35;" +
                     "encrypt=false;" +
                     "trustServerCertificate=true;";

        try (
            Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
        ) {
            // Table_Wordに単語を追加
            stmt.executeUpdate("INSERT INTO Table_Word (単語) VALUES ('" + word + "')");
            // 成功した場合は登録した単語のみを返す
            return word;
        } catch (Exception e) {
            e.printStackTrace();
            // エラーが発生した場合は空文字列を返す
            return "";
        }
    }
}
