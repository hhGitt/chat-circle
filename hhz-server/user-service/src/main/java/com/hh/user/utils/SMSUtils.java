package com.hh.user.utils;

import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.aliyun.tea.TeaException;
import com.aliyun.teaopenapi.models.Config;
import com.aliyun.teautil.Common;
import com.aliyun.teautil.models.RuntimeOptions;
import com.hh.user.config.properties.SMSProperties;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description: 阿里短信服务
 * @Param:
 * @return:
 * @Author: hh
 * @Date: 2022/12/30
 */
@Slf4j
public class SMSUtils {

    private Config clientConfig;
    private String signName;
    private String templateCode;
    private String templateParam;

    public SMSUtils(SMSProperties properties) {
        log.info("阿里短信业务相关配置: {}", properties);
        setSignName(properties.getSignName());
        setTemplateCode(properties.getTemplateCode());
        setTemplateParam(properties.getTemplateParam());
        setClientConfig(properties.getAccessKeyId(), properties.getAccessKeySecret(), properties.getType(), properties.getEndpoint());
    }

    public void setClientConfig(String accessKeyId, String accessKeySecret, String type, String endpoint) {
        Config config = new Config();
        config.accessKeyId = accessKeyId;
        config.accessKeySecret = accessKeySecret;
        config.type = type;
        config.endpoint = endpoint;
        clientConfig = config;
    }

    public void setSignName(String signName) {
        this.signName = signName;
    }

    public void setTemplateCode(String templateCode) {
        this.templateCode = templateCode;
    }

    public void setTemplateParam(String templateParam) {
        this.templateParam = templateParam;
    }

    /**
     * 发送验证码给指定用户手机号
     *
     * @param phoneNumber 手机号
     * @param code 验证码
     * @throws Exception
     */
    public boolean sendCodeToPhone(String phoneNumber, String code) throws Exception {
        Client client = new Client(clientConfig);
        SendSmsRequest sendSmsRequest = new SendSmsRequest()
                .setSignName(signName)
                .setTemplateCode(templateCode)
                .setPhoneNumbers(phoneNumber)
                .setTemplateParam(String.format(templateParam, code));
        RuntimeOptions runtime = new RuntimeOptions();
        try {
            SendSmsResponse sendSmsResponse = client.sendSmsWithOptions(sendSmsRequest, runtime);
            log.info("发送短信相关响应: phoneNumber: {},code: {},message: {}", phoneNumber, sendSmsResponse.getBody().getCode(), sendSmsResponse.getBody().getMessage());
            return "OK".equals(sendSmsResponse.getBody().getCode());
        } catch (TeaException error) {
            log.error("发送短信业务出错: {}", Common.assertAsString(error.message));
        } catch (Exception _error) {
            TeaException error = new TeaException(_error.getMessage(), _error);
            log.error("发送短信业务出错: {}", Common.assertAsString(error.message));
        }
        return false;
    }
}
