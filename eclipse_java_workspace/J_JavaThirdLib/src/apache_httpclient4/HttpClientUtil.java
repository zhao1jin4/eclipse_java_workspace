package apache_httpclient4;


import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

public class HttpClientUtil {
    private static Logger logger = Logger.getLogger(HttpClientUtil.class);


    private static final int TIMEOUT = 5 * 1000;//5 * 1000 
    private static HttpClientUtil instance = null;
 
    public HttpClientUtil() {

    }

    public static HttpClientUtil getInstance() {

        if (null == instance) {

            synchronized (HttpClientUtil.class) {

                if (instance == null) {

                    instance = new HttpClientUtil();

                }

            }

        }

        return instance;

    }



    /**
     *
     * @param paramMap
     * @param url 调用地址
     * @return
     */
    public String sendPost(Map<String, Object> paramMap, String url) {
        String responseString = "";
        HttpClient httpclient = new DefaultHttpClient();

        try {

            List<NameValuePair> parameters = new ArrayList<NameValuePair>();
            // 添加参数
            if(paramMap!=null&&paramMap.keySet()!=null){
                for(String key:paramMap.keySet()){
                    parameters.add(new BasicNameValuePair(key,paramMap.get(key)+""));
                }
            }
            logger.info(String.format("http 请求地址:%s,请求参数>%s",url,JSONObject.toJSONString(paramMap)));
            UrlEncodedFormEntity formEntiry = new UrlEncodedFormEntity(parameters,"UTF-8");
            HttpPost post = new HttpPost(url);

            post.setEntity(formEntiry);
            HttpResponse reponse = httpclient.execute(post);
            HttpEntity entity = reponse.getEntity();
            responseString= EntityUtils.toString(entity,"UTF-8");

            logger.info(String.format("http 请求地址:%s,返回参数>%s",url,responseString));
        } catch (Exception e) {
            logger.error(String.format("Exception%s,q请求失败",url),e);
        } finally {
            httpclient.getConnectionManager().shutdown();
        }

        return responseString;
    }

    public String sendPostJson(String address,String sb) throws RuntimeException{
        HttpURLConnection connection = null;
        StringBuffer str = new StringBuffer();
        String resposeCode = null;
        InputStream is = null;
        try {
            URL url = new URL(address);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);
            connection.setUseCaches(false);
            connection.setDoInput(true);
            connection.setConnectTimeout(TIMEOUT*2);
            connection.setReadTimeout(TIMEOUT);
            connection.setRequestProperty("Charset", "UTF-8");
            OutputStreamWriter os = new OutputStreamWriter(connection.getOutputStream());

            os.write(sb);
            os.close();

            is = connection.getInputStream();
            if(connection.getResponseCode() != 200){
                is = connection.getInputStream();
                if(connection.getResponseCode() != 200){
                    is.close();
                    connection.disconnect();
                    logger.info("接口没有成功响应,MSG:"+connection.getResponseCode());
                    throw new RuntimeException("接口没有成功响应,MSG:"+connection.getResponseCode());
                }
            }

            BufferedReader in = new BufferedReader(new InputStreamReader(is,"UTF-8"));
            String inputLine = "";
            while ((inputLine = in.readLine()) != null) {
                str.append(inputLine);
            }

        } catch (Exception e) {
            logger.error(e, e);
            throw new RuntimeException("接口无响应,MSG:"+e.getMessage());
        }finally{
            try {
                if(null != is)is.close();
                connection.disconnect();
            } catch (Exception e) {
                logger.error("关闭连接失败",e);
            }
        }
        return str.toString();
    }
 
}

