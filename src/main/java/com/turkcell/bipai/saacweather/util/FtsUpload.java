package com.turkcell.bipai.saacweather.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.Base64Utils;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.turkcell.bipai.saacweather.model.Data;
import com.turkcell.bipai.saacweather.model.FileMessageResource;
import com.turkcell.bipai.saacweather.model.SaacMessage;
import com.turkcell.bipai.saacweather.service.HelloWorld;

public class FtsUpload {
	
	
	public SaacMessage saacMessage;
	private static final Logger logger = LoggerFactory.getLogger(HelloWorld.class);
	public static final String wsuser= "",wspass = "",url = "";
	public String jsonData = "{ \"txnid\": \"translate-"+ System.currentTimeMillis() +"\", \"receiver\":\"\", \"avatarOwner\":\"\", \"isGroup\":\"false\", \"isAvatar\":\"false\", \"fileType\":\"P\" }";
	public String imageDir = "D:\\dev\\out.jpg";
	
	public FtsUpload() {
		// TODO Auto-generated constructor stub
		
	}
	public static File getImageFromUrl(String urlFromAccuweather)
    {
    	BufferedImage image = null;
        try {

            URL url = new URL(urlFromAccuweather);
            image = ImageIO.read(url);
            File file = new File("D:\\dev\\out.jpg");
            ImageIO.write(image, "jpg",file);
           
            return file;
        } catch (IOException e) {
        	e.printStackTrace();
        	return null;
        }
        
    }
	public String uploadToFts(String apiImageUrl) throws IOException{
		
		String wsuser	=	ConstantUtil.getConf("FTS_USERNAME");
		String wspass	=	ConstantUtil.getConf("FTS_PASSWORD");
		String url		=	ConstantUtil.getConf("FTS_URL");

//		File outputFile = getImageFromUrl(apiImageUrl); // apiden gelen string
		
		File outputFile = new File("D:\\dev\\out.jpg"); // yukardaki comment in, this comment out
		long fileLength	=	outputFile.length();
		byte[] buffer = null;
		try {
			buffer = FileCopyUtils.copyToByteArray(outputFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		String uploadURL = uploadImgToFts(buffer, System.currentTimeMillis() + ".jpg", wsuser, wspass, url);
//		saacMessage.setMessage(uploadURL);
//		saacMessage.setType(2);
//		
		if (fileLength <= 0) {
			try {
				fileLength	=	HttpFileSizeUtil.getSize(uploadURL);
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		outputFile.delete();
		return uploadURL;
//		Data data = new Data();
//		data.setSize(fileLength);
//		saacMessage.setData(data);
		
		//LoggerUtil.log(getClass(), logMsg + " - saacMessage: " + saacMessage);
		
		
		
	}
	public String uploadImgToFts(byte[] byteArr, String fileNamePrm, String wsuser, String wspass, String url) {
        String fileName = fileNamePrm;
        
        RestTemplate restTemplate = new RestTemplate();
        ((SimpleClientHttpRequestFactory) restTemplate.getRequestFactory()).setConnectTimeout(10000);
        ((SimpleClientHttpRequestFactory) restTemplate.getRequestFactory()).setReadTimeout(10000);

        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());

        String jsonData = "{ \"txnid\": \"translate-"+ System.currentTimeMillis() +"\", \"receiver\":\"\", \"avatarOwner\":\"\", \"isGroup\":\"false\", \"isAvatar\":\"false\", \"fileType\":\"P\" }";
        final MultiValueMap<String, Object> data = new LinkedMultiValueMap<String, Object>();
        data.add("file", new FileMessageResource(byteArr, fileName));
        data.add("data", jsonData);

        // URI postForLocation = restTemplate.postForLocation(url, data);

        final HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<MultiValueMap<String, Object>>(
                     data, createHeaders(wsuser, wspass));
        ResponseEntity<Map<String, String>> exchangeResult = restTemplate.exchange(url, HttpMethod.POST, requestEntity,
                     new ParameterizedTypeReference<Map<String, String>>() {
                     });
        if(exchangeResult.getStatusCode().is2xxSuccessful()){
               return exchangeResult.getBody().get("url");            
        }else{
               return null;
        }

  }
	
	
	private org.springframework.http.HttpHeaders createHeaders(final String username, final String password) {
		return new org.springframework.http.HttpHeaders() {
			{
				String auth = username + ":" + password;
				byte[] encodedAuth = Base64Utils.encode(auth.getBytes(Charset.forName("UTF-8")));
				String authHeader = "Basic " + new String(encodedAuth, Charset.forName("UTF-8"));
				set("Authorization", authHeader);
			}
		};
	}
	
}
