package web;

import com.fastcgi.FCGIInterface;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class Main {
    private static final String RESPONSE_TEMPLATE = "Content-Type: application/json\nContent-Length: %d\nStatus: %d\n\n%s";

    private static void sendJson(int statusCode, String jsonDump) {
        System.out.printf(RESPONSE_TEMPLATE, jsonDump.getBytes(StandardCharsets.UTF_8).length, statusCode, jsonDump);
    }

    public static void main(String[] args) {
        FCGIInterface fcgi = new FCGIInterface();
        System.out.println("server start...");
        while (fcgi.FCGIaccept() >= 0) {
            System.out.println("get request");
            long startTime = System.nanoTime();
            try {
                if (FCGIInterface.request != null && FCGIInterface.request.inStream != null) {
                    String body = readRequestBody();
                    JSONObject jsonRequest = new JSONObject(body);

                    float x = jsonRequest.getFloat("x");
                    float y = jsonRequest.getFloat("y");
                    float r = jsonRequest.getFloat("r");

                    if (x < -2 || x > 2 || y < -3 || y > 5 || r < 0 || r > 5) {
                        sendJson(400, new JSONObject().put("error", "Invalid input data").toString());
                    } else {
                        Response response = new Response(x, y, r, startTime);
                        String jsonResponse = new JSONObject()
                                .put("result", response.check)
                                .put("x", response.x)
                                .put("y", response.y)
                                .put("r", response.r)
                                .put("currentTime", response.timeNow)
                                .put("executionTime", response.executionTime)
                                .toString();
                        sendJson(200, jsonResponse);
                    }
                } else {
                    sendJson(400, new JSONObject().put("error", "Invalid FCGI request.").toString());
                }
            } catch (Exception e) {
                sendJson(500, new JSONObject().put("error", e.getMessage()).toString());
            }
        }
    }

    private static String readRequestBody() throws IOException {
        FCGIInterface.request.inStream.fill();
        int contentLength = FCGIInterface.request.inStream.available();
        var buffer = ByteBuffer.allocate(contentLength);
        var readBytes = FCGIInterface.request.inStream.read(buffer.array(), 0, contentLength);
        var requestBodyRaw = new byte[readBytes];
        buffer.get(requestBodyRaw);
        buffer.clear();
        return new String(requestBodyRaw, StandardCharsets.UTF_8);
    }
}