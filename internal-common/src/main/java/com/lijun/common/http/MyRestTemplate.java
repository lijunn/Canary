package com.lijun.common.http;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * @author : LiJun
 * @date : 2023-12-25 15:46
 **/
public class MyRestTemplate extends RestTemplate {


    public <T> ResponseEntity<T> sendRequest(String url, HttpMethod method, HttpHeaders headers,
                                             Object requestObject, Class<T> responseType) {

        HttpEntity<Object> requestEntity = new HttpEntity<>(requestObject, headers);
        return this.exchange(url, method, requestEntity, responseType);
    }

    public <T> ResponseEntity<T> sendRequest(String url, HttpMethod method, HttpHeaders headers,
                                             Object requestObject, ParameterizedTypeReference<T> responseType) {

        HttpEntity<Object> requestEntity = new HttpEntity<>(requestObject, headers);
        return this.exchange(url, method, requestEntity, responseType);
    }


    public <T> T sendGetRequest(String url, HttpHeaders headers, Class<T> responseType) {
        return sendRequest(url, HttpMethod.GET, headers, null, responseType).getBody();
    }

    public <T> T sendGetRequest(String url, HttpHeaders headers, ParameterizedTypeReference<T> responseType) {
        return sendRequest(url, HttpMethod.GET, headers, null, responseType).getBody();
    }

    public <T> T sendPostRequest(String url, HttpHeaders headers,
                                                 Object requestObject, Class<T> responseType) {
        return sendRequest(url, HttpMethod.POST, headers, requestObject, responseType).getBody();
    }
}
