package com.xuecheng.auth;

import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaSigner;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
import org.springframework.test.context.junit4.SpringRunner;

import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Administrator
 * @version 1.0
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
public class TestJwt {

    //创建jwt令牌
    @Test
    public void testCreateJwt(){
        //密钥库文件
        String keystore = "xc.keystore";
        //密钥库的密码
        String keystore_password = "xuechengkeystore";

        //密钥库文件路径
        ClassPathResource classPathResource = new ClassPathResource(keystore);
        //密钥别名
        String alias  = "xckey";
        //密钥的访问密码
        String key_password = "xuecheng";
        //密钥工厂
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(classPathResource,keystore_password.toCharArray());
        //密钥对（公钥和私钥）
        KeyPair keyPair = keyStoreKeyFactory.getKeyPair(alias, key_password.toCharArray());
        //获取私钥
        RSAPrivateKey aPrivate = (RSAPrivateKey) keyPair.getPrivate();
        //jwt令牌的内容
        Map<String,String> body = new HashMap<>();
        body.put("name","itcast");
        String bodyString = JSON.toJSONString(body);
        //生成jwt令牌
        Jwt jwt = JwtHelper.encode(bodyString, new RsaSigner(aPrivate));
        //生成jwt令牌编码
        String encoded = jwt.getEncoded();
        System.out.println(encoded);

    }

    //校验jwt令牌
    @Test
    public void testVerify(){
        //公钥
        String publickey = "-----BEGIN PUBLIC KEY-----MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAnASXh9oSvLRLxk901HANYM6KcYMzX8vFPnH/To2R+SrUVw1O9rEX6m1+rIaMzrEKPm12qPjVq3HMXDbRdUaJEXsB7NgGrAhepYAdJnYMizdltLdGsbfyjITUCOvzZ/QgM1M4INPMD+Ce859xse06jnOkCUzinZmasxrmgNV3Db1GtpyHIiGVUY0lSO1Frr9m5dpemylaT0BV3UwTQWVW9ljm6yR3dBncOdDENumT5tGbaDVyClV0FEB1XdSKd7VjiDCDbUAUbDTG1fm3K9sx7kO1uMGElbXLgMfboJ963HEJcU01km7BmFntqI5liyKheX+HBUCD4zbYNPw236U+7QIDAQAB-----END PUBLIC KEY-----";
        //jwt令牌
        String jwtString = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJjb21wYW55SWQiOiIxIiwidXNlcnBpYyI6bnVsbCwidXNlcl9uYW1lIjoiaXRjYXN0Iiwic2NvcGUiOlsiYXBwIl0sIm5hbWUiOiJ0ZXN0MDIiLCJ1dHlwZSI6IjEwMTAwMiIsImlkIjoiNDkiLCJleHAiOjE1ODQ5MTIxNTksImF1dGhvcml0aWVzIjpbInhjX3RlYWNobWFuYWdlcl9jb3Vyc2VfYmFzZSIsInhjX3RlYWNobWFuYWdlcl9jb3Vyc2VfZGVsIiwieGNfdGVhY2htYW5hZ2VyX2NvdXJzZV9saXN0IiwieGNfdGVhY2htYW5hZ2VyX2NvdXJzZV9wbGFuIiwieGNfdGVhY2htYW5hZ2VyX2NvdXJzZSIsImNvdXJzZV9maW5kX2xpc3QiLCJ4Y190ZWFjaG1hbmFnZXIiLCJ4Y190ZWFjaG1hbmFnZXJfY291cnNlX21hcmtldCIsInhjX3RlYWNobWFuYWdlcl9jb3Vyc2VfcHVibGlzaCIsInhjX3RlYWNobWFuYWdlcl9jb3Vyc2VfYWRkIl0sImp0aSI6IjEwZmEyZTI5LTQ5YTMtNGQ3OC04MGZhLWQ4NWFmYWJjYTc4ZiIsImNsaWVudF9pZCI6IlhjV2ViQXBwIn0.agUchDtHzPtLcEPrO4sbYJcVik_78drydW9x22uiCQ8AOsuNxz1RW6JwHpBzooOCpItBuNWQUVqlISM-BOpOVn-HHQoBiNrsrMQtIGm0HISJcgUeEy0jj6Kwp4irpuZlJaEoVugeY11jtH6e6La5_PFJ-K3xSuZbfhw9qQ8gl1yb-3NpOOsJ_Wxh2TkQ4mE7I0OJjZzoMSYZT37gJVESzEliDcHlFtwq2nLXwgH3Ozq3L-732GRmpPahByr-bCdrqj2Co1LohfkJzg6-tZJuNTjeVIOlre_EUoQQrle7XaZW8Ahc6cmbixqqmBJ3OxvWNPBrgoxqIDKALGYXdpIH1A"; //校验jwt令牌
        Jwt jwt = JwtHelper.decodeAndVerify(jwtString, new RsaVerifier(publickey));
        //拿到jwt令牌中自定义的内容
        String claims = jwt.getClaims();
        System.out.println(claims);
    }
}
