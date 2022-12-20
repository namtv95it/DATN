package nem.com.service.impl;

import nem.com.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String sender;

    @Override
    public boolean sendSimpleMail(String email, String resetPasswordLink) {
        try {
            MimeMessage message = this.javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);

            helper.setFrom(sender, "NEM FASHION Support");
            helper.setTo(email);

            String subject = "Đây là đường dẫn thay đổi mật khẩu của bạn.";
            String content = "<p>Xin chào, </p>" + "<p>Bạn đã gửi yêu cầu thay đổi mật khẩu</p>"
                    + "<p>Nhấn vào đường dẫn bên dưới để thay đổi mật khẩu của bạn: </p>" + "<a href=\"" + resetPasswordLink
                    + "\">Thay đổi mật khẩu</a>";

            helper.setSubject(subject);
            helper.setText(content, true);

            this.javaMailSender.send(message);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
