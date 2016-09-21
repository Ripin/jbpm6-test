package ins.bpm.rest;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import javax.xml.bind.DatatypeConverter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * des
 *
 * @author Ripin Yan
 * @version $Id: HttpRestTest.java  2016/9/21 15:57$
 */
public class HttpRestTest {

    public static void main(String[] args) {
        try {
            DefaultHttpClient Client = new DefaultHttpClient();

            //HttpGet httpGet = new HttpGet("http://127.0.0.1:8888/jbpm-console/rest/deployment/processes");
            HttpGet httpGet = new HttpGet("http://127.0.0.1:8888/jbpm-console/rest/runtime/org.jbpm:Evaluation:1.1/process/instance/44");
            String encoding = DatatypeConverter.printBase64Binary("admin:admin".getBytes("UTF-8"));

            httpGet.setHeader("Authorization", "Basic " +encoding);

            HttpResponse response = Client.execute(httpGet);

            System.out.println("response =" + response);

            BufferedReader breader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuilder responseString = new StringBuilder();
            String line = "";
            while ((line = breader.readLine()) !=null) {
                responseString.append(line);
            }
            breader.close();
            String repsonseStr =responseString.toString();

            System.out.println("repsonseStr =" + repsonseStr);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
