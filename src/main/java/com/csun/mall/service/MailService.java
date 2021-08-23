package com.csun.mall.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * @Author Joker Zheng
 * @create 2021/8/21 14:38
 */
@Service
public class MailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;

    public void send(String to, String code) {

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = null;
        try {
            helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject("你的男神给你发送了一封账号激活邮件");
            helper.setText("<tbody>\n" +
                    "  <tr>\n" +
                    "    <td>\n" +
                    "      <table cellspacing=\"0\" id=\"content\" cellpadding=\"0\">\n" +
                    "        <tbody>\n" +
                    "        <tr>\n" +
                    "          <td id=\"header\">\n" +
                    "            <table cellspacing=\"0\" cellpadding=\"0\">\n" +
                    "              <tbody>\n" +
                    "              <tr>\n" +
                    "                <td width=\"50\" id=\"logo\">\n" +
                    "<img src=\"https://ss3.baidu.com/9fo3dSag_xI4khGko9WTAnF6hhy/baike/s=250/sign=141ca7c2f7246b607f0eb571dbf91a35/77094b36acaf2edd09d9b0788c1001e939019373.jpg\">                </td>\n" +
                    "                <td width=\"500\" align=\"right\" id=\"title\"><h1>Verify your new account</h1></td>\n" +
                    "              </tr>\n" +
                    "              </tbody>\n" +
                    "            </table>\n" +
                    "          </td>\n" +
                    "        </tr>\n" +
                    "\n" +
                    "        <tr>\n" +
                    "          <td id=\"verificationMsg\">\n" +
                    "            <p>To verify your email address, please use the following One Time Password (OTP):</p>\n" +
                    "            <b class=\"otp\">"+code+"</p>\n" +
                    "          </td>\n" +
                    "        </tr>\n" +
                    "\n" +
                    "        <tr>\n" +
                    "          <td id=\"accountSecurity\">\n" +
                    "            <p>Do not share this OTP with anyone. Customer Service will never ask you to disclose or verify your password, OTP, credit card, or banking account number. If you receive a suspicious email with a link to update your account information, do not click on the link—instead, report the email to us for investigation. </p>\n" +
                    "          </td>\n" +
                    "        </tr>\n" +
                    "\n" +
                    "        <tr>\n" +
                    "          <td id=\"closing\">\n" +
                    "            <p>Thank you!</p>\n" +
                    "          </td>\n" +
                    "        </tr>\n" +
                    "        </tbody>\n" +
                    "      </table>\n" +
                    "    </td>\n" +
                    "  </tr>\n" +
                    "  </tbody>", true);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        mailSender.send(message);
    }
}

