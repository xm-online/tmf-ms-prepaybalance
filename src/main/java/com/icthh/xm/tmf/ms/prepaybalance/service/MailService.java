package com.icthh.xm.tmf.ms.prepaybalance.service;

import com.icthh.xm.commons.mail.provider.MailProviderService;
import com.icthh.xm.commons.tenant.TenantContextHolder;
import java.nio.charset.StandardCharsets;
import javax.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailService {

    private final MailProviderService mailProviderService;
    private final TenantContextHolder tenantContextHolder;

    public void sendEmail(String to, String subject, String content, String from) {
        log.debug("Send email[multipart '{}' and html '{}'] to '{}' with subject '{}' and content={}",
            false, true, to, subject, content);

        JavaMailSender javaMailSender = mailProviderService
            .getJavaMailSender(tenantContextHolder.getTenantKey().toUpperCase());
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage,
                false, StandardCharsets.UTF_8.name());
            message.setTo(to);
            message.setFrom(from);
            message.setSubject(subject);
            message.setText(content, true);
            javaMailSender.send(mimeMessage);
            log.debug("Sent email to User '{}'", to);
        } catch (Exception e) {
            log.warn("Email could not be sent to user '{}': {}", to, e.getMessage());
        }
    }
}
