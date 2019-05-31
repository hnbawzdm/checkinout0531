package io.zjl.checkinout0531.advisor;


import io.zjl.checkinout0531.dto.TextMsg;
import io.zjl.checkinout0531.exception.WechatClientException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionAdvisor {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler
    public ResponseEntity<TextMsg> handleClientException(WechatClientException ex){
        logger.warn("ClientException, Errcode: {}, ErrMsg: {}", ex.getErrCode(), ex.getMessage());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_XML);

        TextMsg textResMsg = new TextMsg(ex.getOpenID(), ex.getMessage());
        ResponseEntity<TextMsg> textResMsgResponseEntity = new ResponseEntity<>(textResMsg, headers, HttpStatus.OK);
        return textResMsgResponseEntity;
    }
}
