package com.guyao.mrg;

import com.guyao.mrg.common.utils.RSAUtils;
import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.security.KeyPair;
import java.util.UUID;

public class MrgApplicationTests {

    @Test
    public void contextLoads() {
        UUID uuid = UUID.randomUUID();
        System.out.println(uuid.toString().replace("-",""));
        System.out.println("$123".substring(1,2));
    }


    @Test
    public void test() {
        String admin = new BCryptPasswordEncoder().encode("admin");
        System.out.println(admin);
    }

    @Test
    public void deEncode() {
        KeyPair keyPair = RSAUtils.getKeyPair();
        String publicKey = RSAUtils.getPublicKey(keyPair);
        String privateKey = RSAUtils.getPrivateKey(keyPair);
        String password = "abcdefg456+-=";
        byte[] encodeBytes = RSAUtils.publicEncrytype(password.getBytes(), RSAUtils.string2PublicKey(publicKey));
        String encodeStr = new String(encodeBytes);
        byte[] decodeBytes = RSAUtils.privateDecrypt(encodeBytes, RSAUtils.string2Privatekey(privateKey));
        String decodeStr = new String(decodeBytes);
        System.out.println("初始字符串："+password);
        System.out.println("加密后的字符串："+encodeStr);
        System.out.println("解密后的字符串："+decodeStr);


    }

    @Test
    public void testSqlCombine() {
    }

}
