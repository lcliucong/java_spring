package com.example.spring.utils;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.data.redis.connection.convert.StringToDataTypeConverter;

public class JwtUtil {

    private final static String SECRET_KEY = "my-secret-key";
    private final static long EXPIRE_TIME = 30000;


}
