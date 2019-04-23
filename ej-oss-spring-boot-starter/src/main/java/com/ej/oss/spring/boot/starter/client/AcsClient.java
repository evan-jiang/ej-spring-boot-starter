package com.ej.oss.spring.boot.starter.client;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.auth.sts.AssumeRoleRequest;
import com.aliyuncs.auth.sts.AssumeRoleResponse;
import com.aliyuncs.auth.sts.AssumeRoleResponse.Credentials;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.http.ProtocolType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.ej.oss.spring.boot.starter.dto.AcsInfoDto;
import com.ej.oss.spring.boot.starter.exception.OssUnknownException;
import com.ej.oss.spring.boot.starter.properties.AcsConfig;

/**
 * 
 * @Description: Acs客户端
 * @author Evan Jiang
 * @date 2019年4月22日 下午6:45:14
 *
 */
public class AcsClient {
    private static final String REGION_CN_HANGZHOU = "cn-hangzhou";

    private static final String STANDARD_DATE_FORMAT_UTC = "yyyy-MM-dd'T'HH:mm:ss'Z'";

    private static final String POLICY_TEMPLATE = "{\"Version\":\"1\",\"Statement\":[{\"Effect\":\"Allow\",\"Action\":[\"oss:GetObject\",\"oss:PutObject\",\"oss:DeleteObject\",\"oss:ListParts\",\"oss:AbortMultipartUpload\",\"oss:ListObjects\"],\"Resource\":[\"acs:oss:*:*:%s/*\"]}]}";

    private AcsConfig acsConfig;

    private DefaultAcsClient acsClient;
    
    public String getAlias() {
        return acsConfig.getAlias();
    }
    /**
     * 初始化acs客户端
     * @param acsConfig
     * @param isMulti 是否属多连接，判断是否需要校验连接的别名，用于工厂管理
     */
    public AcsClient(AcsConfig acsConfig,boolean isMulti) {
        if(isMulti) {
            acsConfig.multiCheck();
        }else {
            acsConfig.singleCheck();
        }
        this.acsConfig = acsConfig;
    }

    /**
     * 
     * @Description: 初始化acs连接
     * @author Evan Jiang
     * @date 2019年4月23日 下午5:09:04
     */
    private void initAcsClient() {
        IClientProfile profile = DefaultProfile.getProfile(REGION_CN_HANGZHOU,
                acsConfig.getKeyId(), acsConfig.getKeySecret());
        acsClient = new DefaultAcsClient(profile);
    }
    
    /**
     * 
     * @Description: 获取可用的acs连接
     * @author Evan Jiang
     * @date 2019年4月23日 下午5:05:36
     */
    private DefaultAcsClient usableAcsClient() {
        if (acsClient == null) {
            synchronized (this) {
                initAcsClient();
            }
        }
        return acsClient;
    }

    /**
     * 
     * @Description: 获取acs临时令牌
     * @author Evan Jiang
     * @date 2019年4月23日 下午5:09:56 
     * @return
     */
    public AcsInfoDto getAcsInfo() {
        AssumeRoleRequest request = new AssumeRoleRequest();
        request.setMethod(MethodType.POST);
        request.setProtocol(ProtocolType.HTTPS);
        request.setRoleArn(acsConfig.getArn());
        request.setRoleSessionName(acsConfig.getBucketName());
        request.setPolicy(
                String.format(POLICY_TEMPLATE, acsConfig.getBucketName()));
        request.setDurationSeconds((long) (60 * 60));
        try {
            AssumeRoleResponse response = usableAcsClient()
                    .getAcsResponse(request);
            Credentials credentials = response.getCredentials();
            AcsInfoDto acsInfoDto = new AcsInfoDto();
            acsInfoDto.setEndpoint(acsConfig.getEndpoint());
            acsInfoDto.setBucket(acsConfig.getBucketName());
            acsInfoDto.setAccessKeyId(credentials.getAccessKeyId());
            acsInfoDto.setAccessKeySecret(credentials.getAccessKeySecret());
            acsInfoDto.setSecurityToken(credentials.getSecurityToken());
            acsInfoDto
                    .setExpiration(LocalDateTime
                            .parse(credentials.getExpiration(),
                                    DateTimeFormatter.ofPattern(
                                            STANDARD_DATE_FORMAT_UTC))
                            .toInstant(ZoneOffset.UTC).toEpochMilli());
            return acsInfoDto;
        } catch (Exception e) {
            throw new OssUnknownException(e);

        }
    }
}
