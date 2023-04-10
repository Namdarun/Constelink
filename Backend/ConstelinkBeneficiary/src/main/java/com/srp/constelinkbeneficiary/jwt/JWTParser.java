package com.srp.constelinkbeneficiary.jwt;

import java.util.Base64;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import org.springframework.stereotype.Component;

import com.srp.constelinkbeneficiary.common.exception.CustomException;
import com.srp.constelinkbeneficiary.common.exception.CustomExceptionType;

@Component
public class JWTParser {
	private final JSONParser jsonParser;
	private final Base64.Decoder decoder;
	public JWTParser() {
		this.jsonParser = new JSONParser();
		this.decoder = Base64.getDecoder();
	}

	public Long resolveToken(String token) {  // Token에 들어있는 멤버아이디 얻기
		String accessToken = isBearerToken(token);
		String[] body = accessToken.split("\\.");
		String decode = new String(decoder.decode(body[1]));
		Long subValue;
		try {
			JSONObject jsonObject = (JSONObject) jsonParser.parse(decode);
			String strSub = (String)jsonObject.get("sub");
			subValue = Long.valueOf(strSub);
		} catch (Exception e) {
			throw new CustomException(CustomExceptionType.RUNTIME_EXCEPTION);
		}
		return subValue;
	}


	private String isBearerToken(String token) {
		String[] tokenInfo = token.split(" ");
		return tokenInfo[1];
	}



}
