import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class SendHttpPost {

    public Integer CheckWO(String uid) throws IOException {
        String hostPost = "http://rainbow.evos.in.ua/proxy/" + uid + "/api/weborders/cost";
        URL objPost = new URL(hostPost);
        HttpURLConnection connect = (HttpURLConnection) objPost.openConnection();
        connect.setRequestMethod("POST");
        connect.setRequestProperty("Content-Type", "application/json");
        connect.setRequestProperty("Authorization", "Basic Z3Vlc3Q6YjBlMGVjN2ZhMGE4OTU3N2M5MzQxYzE2Y2ZmODcwNzg5Mj"
                + "IxYjMxMGEwMmNjNDY1ZjQ2NDc4OTQwN2Y4M2YzNzdhODdhOTdkNjM1Y2FjMjY"
                + "2NjE0N2E4ZmI1ZmQyN2Q1NmRlYTNkNGNlYmExZmM3ZDAyZjQyMmRkYTY3OTRlM2M=");
        connect.setRequestProperty("X-WO-API-APP-ID", "rainbow-taxi");
        connect.setDoOutput(true);
        DataOutputStream dstream = new DataOutputStream(connect.getOutputStream());
        dstream.writeBytes(
                "{\n" +
                        "\t\"reservation\":false\n" +
                        "    ,\"route\":\n" +
                        "       [\n" +
                        "        {\"name\":\"TEST\",\"lat\":1.1, \"lng\":1.1}\n" +
                        "\n" +
                        "        ]\n" +
                        "    ,\"taxiColumnId\":0\n" +
                        "\n" +
                        "}"
        );
        dstream.flush();
        dstream.close();

        int responseCode = connect.getResponseCode();
        return responseCode;
    }
}
