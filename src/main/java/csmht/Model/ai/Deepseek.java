package csmht.Model.ai;


import com.alibaba.fastjson2.JSON;
import com.google.gson.Gson;
import csmht.View.UserBaseServlet;
import csmht.dao.classobject.Post;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;


import javax.servlet.annotation.WebServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/AI")
public class Deepseek extends UserBaseServlet {

    private static final String API_KEY = "sk-b243cc49a48345c784effc66cc4d4ac6";
    private static final String API_URL = "https://api.deepseek.com/chat/completions";
    private static final String MODEL_NAME = "deepseek-chat";

    public void AI(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        BufferedReader one = req.getReader();
        String oneLine = one.readLine();
        Post Json = JSON.parseObject(oneLine, Post.class);
        String ansText = null;
        String inputText = Json.getContent();

        HttpClient httpClient = HttpClientBuilder.create().build();
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", MODEL_NAME);
        Map<String, String> message = new HashMap<>();
        message.put("role", "user");
        message.put("content", "请用中文总结并解释以下文本：" + inputText);
        requestBody.put("messages", new Object[]{message});

        Gson gson = new Gson();
        String jsonBody = gson.toJson(requestBody);


        HttpPost httpPost = new HttpPost(API_URL);
        httpPost.setHeader("Content-Type", "application/json; charset=UTF-8");
        httpPost.setHeader("Authorization", "Bearer " + API_KEY);
        httpPost.setEntity(new StringEntity(jsonBody, StandardCharsets.UTF_8));

        try{
            HttpResponse httpResponse = httpClient.execute(httpPost);
            BufferedReader reader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent(), StandardCharsets.UTF_8));
            StringBuilder responseBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                responseBuilder.append(line);
            }
            reader.close();

            Map responseMap = gson.fromJson(responseBuilder.toString(), Map.class);


            if (responseMap.containsKey("choices")) {
                List<Map<String, Object>> choices = (List<Map<String, Object>>) responseMap.get("choices");
                if (!choices.isEmpty()) {
                    Map<String, Object> choice = choices.get(0);
                    if (choice.containsKey("message")) {
                        Map<String, String> messageObj = (Map<String, String>) choice.get("message");
                        ansText = messageObj.get("content");
                    }
                }
            }


        }catch (IOException e) {
            e.printStackTrace();
        }

        if(ansText!=null){
            Map<String,String> map = new HashMap<>();
            map.put("content",ansText);
            String json = gson.toJson(map);
            resp.getWriter().write(json);
        }else{
            resp.sendError(500);
        }


    }


}
