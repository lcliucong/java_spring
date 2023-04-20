package com.example.spring.controller.Others;

import com.example.spring.constant.ErrorEnum;
import com.example.spring.filter.SendEmailFilter;
import com.example.spring.utils.ErrorMessage;
import com.example.spring.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class SendEmailExampleController {
    @Autowired
    private JavaMailSender javaMailSender;

    public void checkMail(SendEmailFilter filter) throws ErrorMessage {
        if (StringUtils.isBlank(filter.getSendTo()) || StringUtils.isBlank(filter.getSendFrom()) || StringUtils.isBlank(filter.getSubjects())){
            throw new ErrorMessage(ErrorEnum.COMMON_ERROR.getCode(), ErrorEnum.COMMON_ERROR.getMessage());
        }
    }

    @RequestMapping("/sendEmailExample")
    public Result sendEmailExample(SendEmailFilter filter) throws ErrorMessage {
        SimpleMailMessage message = new SimpleMailMessage();
        checkMail(filter);
        message.setFrom(filter.getSendFrom());
        message.setTo(filter.getSendTo());
        message.setText(filter.getText());
        message.setSubject(filter.getSubjects());
        message.setSentDate(filter.getSendTime());
        javaMailSender.send(message);
        return Result.success();
    }
}
