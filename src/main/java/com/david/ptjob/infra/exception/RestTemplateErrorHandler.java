package com.david.ptjob.infra.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.springframework.http.HttpStatus.Series.CLIENT_ERROR;
import static org.springframework.http.HttpStatus.Series.SERVER_ERROR;

@Component
@Slf4j
public class RestTemplateErrorHandler implements ResponseErrorHandler {

    private static final String NO_ACCESS_TOKEN_MESSAGE = "Access token required.";
    private static final String NO_EXISTED_DATA = "No data";

    @Override
    public boolean hasError(ClientHttpResponse httpResponse) throws IOException {
        return (httpResponse.getStatusCode().series() == CLIENT_ERROR
                || httpResponse.getStatusCode().series() == SERVER_ERROR);
    }

    @Override
    public void handleError(ClientHttpResponse httpResponse) throws IOException {
        String body = new String(httpResponse.getBody().readAllBytes(), StandardCharsets.UTF_8);
        // 5xx error
        if (httpResponse.getStatusCode().series() == HttpStatus.Series.SERVER_ERROR) {
            log.error("Status: {}, Headers:{}, Body:{} ", httpResponse.getStatusCode(), httpResponse.getHeaders(), body);
            throw new HttpServerErrorException(httpResponse.getStatusCode());
        }
        // 4xx error
        if (httpResponse.getStatusCode().series() == HttpStatus.Series.CLIENT_ERROR) {
            log.error("Status: {}, Headers:{}, Body:{} ", httpResponse.getStatusCode(), httpResponse.getHeaders(), body);
            requireAccessToken(body);
            requireExistedItem(body);
        }
    }

    private void requireExistedItem(String body) {
        if (body.equals(NO_EXISTED_DATA)) {
            throw new IllegalArgumentException("존재하지 않는 청과물입니다.");
        }
    }

    private void requireAccessToken(String body) {
        if (body.equals(NO_ACCESS_TOKEN_MESSAGE)) {
            throw new RuntimeException("액세스 토큰이 필요합니다.");
        }
    }
}
