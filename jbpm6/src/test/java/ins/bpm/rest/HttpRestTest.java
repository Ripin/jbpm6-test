package ins.bpm.rest;

import ins.bpm.common.RestConstants;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.hornetq.utils.json.JSONException;
import org.hornetq.utils.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.DatatypeConverter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * des
 *
 * @author Ripin Yan
 * @version $Id: HttpRestTest.java  2016/9/21 15:57$
 */
public class HttpRestTest {

    private static final Logger logger = LoggerFactory.getLogger(HttpRestTest.class);

    private static String baseUrl = "http://127.0.0.1:8888/jbpm-console/rest";
    private static String deploymentId = "org.jbpm:Evaluation:1.1";
    private static String processId = "evaluation";


    public static void main(String[] args) {
        String api = RestConstants.RuntimeREST.GET_PROCESS_DEFINITION;
        api = RestConstants.TaskREST.POST_CLAIM_TASK;
        api = RestConstants.TaskREST.POST_START_TASK;
        api = RestConstants.TaskREST.POST_COMPLETE_TASK;
        api = RestConstants.RuntimeREST.POST_START_PROCESS_INSTANCE;
        String url = baseUrl + api;
        String username = "krisv";
        String password = "krisv";
        Map<String, String> paramsMap = new HashMap<String, String>();
        paramsMap.put("reason", "Yearly performance evaluation!");
        paramsMap.put("employee", "krisv");
        paramsMap.put("performance", "httptest");
        url = url.replaceAll("\\{deploymentId\\}", deploymentId).replaceAll("\\{procDefId\\}", processId).replaceAll("\\{taskId\\}", "41");

        System.out.println(post(url, username, password, paramsMap));

    }

    public static String get(String url, String username, String password, Map<String, String> paramsMap) {
        String result = "error";

        HttpClient client = HttpClientBuilder.create().build();
        //http://127.0.0.1:8888/jbpm-console/rest/runtime/org.jbpm:Evaluation:1.1/process/instance/44
        HttpGet get = new HttpGet(url);
        //使用base64进行加密
        String token = DatatypeConverter.printBase64Binary((username + ":" + password).getBytes());
        //把认证信息发到header中
        get.setHeader("Authorization", "Basic " + token);
        BufferedReader br = null;
        try {
            HttpResponse response = client.execute(get);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != -1) {
                br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                StringBuilder builder = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    builder.append(line);
                }
                result = builder.toString();
            } else {
                logger.error("GET to [" + url + "] failed with status code " + statusCode);
            }
        } catch (Exception e) {
            logger.error("GET to [" + url + "] failed: " + e.getMessage(), e);
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e1) {
                    logger.error("GET to [" + url + "] close io failed: ", e1);
                }
                br = null;
            }
        }

        return result;
    }

    public static String post(String url, String username, String password, Map<String, String> paramsMap) {
        String result = "error";
        HttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(url);
        //使用base64进行加密
        String token = DatatypeConverter.printBase64Binary((username + ":" + password).getBytes());
        //把认证信息发到header中
        post.setHeader("Authorization", "Basic " + token);
        // configure the form parameters
        List<NameValuePair> formParams = new ArrayList<NameValuePair>(2);
        JSONObject jo = new JSONObject();
        Iterator<Map.Entry<String, String>> iterator = paramsMap.entrySet().iterator();
        do {
            if (!iterator.hasNext()) {
                break;
            }
            Map.Entry<String, String> next = iterator.next();
            String key = next.getKey();
            String value = next.getValue();
            formParams.add(new BasicNameValuePair(key, value));
            try {
                jo.append(key, value);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } while (true);

        BufferedReader br = null;
        try {
            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(formParams, "utf-8");

            StringEntity entity = new StringEntity(jo.toString(), "UTF-8");
            entity.setContentType("application/json");
            post.setEntity(entity);
            HttpResponse response = client.execute(post);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != -1) {
                // TODO return result
                System.out.println(response.toString());
                br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                StringBuilder builder = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    builder.append(line);
                }
                result = builder.toString();
            } else {
                logger.error("POST to [" + url + "] failed with status code " + statusCode);
            }
        } catch (Exception e) {
            logger.error("POST to [" + url + "] failed: " + e.getMessage(), e);
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e1) {
                    logger.error("POST to [" + url + "] close io failed: ", e1);
                }
            }
        }
        return result;
    }
}
