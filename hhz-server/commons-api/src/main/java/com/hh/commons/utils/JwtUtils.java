package com.hh.commons.utils;

import com.hh.commons.condition.JwtCondition;
import com.hh.commons.excption.InvalidTokenException;
import com.hh.commons.excption.TimeOutTokenException;
import com.hh.commons.pojo.entity.user.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @Description: jwt验证相关工具类
 * @Author: hh
 * @Date: 2023/3/16 19:37
 * @Version: 1.0
 */

@Slf4j
public class JwtUtils {
    private String secretKey;
    private int expireTime;
    private String subject;
    private RedisUtils redisUtils;

    public JwtUtils(String secretKey, int expireTime, String subject, RedisUtils redisUtils) {
        this.secretKey = secretKey;
        this.expireTime = expireTime;
        this.subject = subject;
        this.redisUtils = redisUtils;
    }

    /**
     * 生成jwt token
     *
     * @param map 要存放负载信息
     */
    public String createJwtToken(Map<String, Object> map) {
        Calendar nowTime = Calendar.getInstance();
        nowTime.add(Calendar.MINUTE, expireTime); //30分钟后的时间
        return Jwts.builder()
                .setClaims(map)
                .setId(UUID.randomUUID().toString())  //设置唯一编号
                .setSubject(subject)  //设置主题
                .setIssuedAt(new Date())  // 设置签发日期
                .setExpiration(nowTime.getTime()) // 过期时间
                .signWith(getSecretKey(), SignatureAlgorithm.HS256) // 签名算法以及密匙
                .compact();
    }


    /**
     * 从令牌中获取数据,就是payLoad部分存放的数据。如果jwt被改，该函数会返回null
     *
     * @param token 令牌
     */
    public Claims parseToken(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getSecretKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            return null;
        }


    }

    /**
     * 验证用户信息
     *
     * @param token jwtToken
     */
    public User verifyJwtToken(String token) throws Exception {
        Claims claims = parseToken(token);
        if (claims == null) return null;
        String id = String.valueOf(claims.get("id"));
        //从redis中获取用户信息
        return (User) redisUtils.getValue(id);
    }


    /**
     * 刷新令牌时间，刷新redis缓存时间
     *
     * @param user 用户信息
     */
    public void refreshToken(User user) {
        //重新设置User对象的过期时间，再刷新缓存
        user.setExpireTime(System.currentTimeMillis() + 1000L * 60 * expireTime);
        redisUtils.putValue(String.valueOf(user.getId()), user, expireTime, TimeUnit.MINUTES);
    }

    /**
     * 设置用户的登录时间和令牌有效时间
     *
     * @param user
     * @return
     */
    public User setTime(User user) {
        user.setExpireTime(System.currentTimeMillis() + 1000L * 60 * expireTime);
        user.setLoginTime(System.currentTimeMillis());
        return user;
    }

    /**
     * 生成签名密钥
     *
     * @return
     */
    private Key getSecretKey() {
        if (StringUtils.isBlank(secretKey)) throw new RuntimeException("jwt配置的密钥不能为空");
        byte[] decode;
        try {
            decode = Decoders.BASE64.decode(secretKey);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("jwt密钥格式配置误");
        }
        return new SecretKeySpec(decode, SignatureAlgorithm.HS256.getJcaName());
    }

}
