package com.storyhasyou.generator;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.storyhasyou.kratos.utils.CollectionUtils;
import com.storyhasyou.kratos.utils.JsonUtils;
import com.storyhasyou.kratos.utils.OkHttpUtils;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * The type Translation utils.
 *
 * @author fangxi created by 2020/11/17
 */
public class TranslationUtils {

    private static final String YOUDAO_URL = "https://openapi.youdao.com/api";

    private static final String APP_KEY = "1dd58899d62d5289";
    private static final String APP_SECRET = "bj7zxfEoxbjENLscRlvf93HdbJhmBpOI";


    /**
     * Translation map.
     *
     * @param taxtMap the taxt map
     * @return the map
     */
    public static List<EnumGenerateModel.Element> translation(Map<Object, String> taxtMap) {
        if (CollectionUtils.isEmpty(taxtMap)) {
            return Collections.emptyList();
        }
        List<EnumGenerateModel.Element> elements = new ArrayList<>(taxtMap.size());
        taxtMap.forEach((code, value) -> {
            Map<String, Object> params = new HashMap<>();
            String salt = String.valueOf(System.currentTimeMillis());
            params.put("from", "zh-CHS");
            params.put("to", "en");
            params.put("signType", "v3");
            String curtime = String.valueOf(System.currentTimeMillis() / 1000);
            params.put("curtime", curtime);
            String signStr = APP_KEY + truncate(value) + salt + curtime + APP_SECRET;
            String sign = getDigest(signStr);
            params.put("appKey", APP_KEY);
            params.put("q", value);
            params.put("salt", salt);
            params.put("sign", sign);
            String response = OkHttpUtils.form(YOUDAO_URL, params);
            if (StringUtils.isNotBlank(response)) {
                TranslationResponseDTO translationResponseDTO = JsonUtils.parse(response, TranslationResponseDTO.class);
                List<String> translation = translationResponseDTO.getTranslation();
                if (CollectionUtils.isNotEmpty(translation)) {
                    translation.stream()
                            .findFirst()
                            .ifPresent(message -> {
                                EnumGenerateModel.Element element = new EnumGenerateModel.Element();
                                element.setChinese(value);
                                element.setMessage(message);
                                element.setCode(code);
                                elements.add(element);
                            });
                }
            }
        });
        return elements;
    }

    private static String truncate(String q) {
        if (q == null) {
            return null;
        }
        int len = q.length();
        return len <= 20 ? q : (q.substring(0, 10) + len + q.substring(len - 10, len));
    }

    /**
     * 生成加密字段
     */
    private static String getDigest(String string) {
        if (string == null) {
            return null;
        }
        char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        byte[] btInput = string.getBytes(StandardCharsets.UTF_8);
        try {
            MessageDigest mdInst = MessageDigest.getInstance("SHA-256");
            mdInst.update(btInput);
            byte[] md = mdInst.digest();
            int j = md.length;
            char[] str = new char[j * 2];
            int k = 0;
            for (byte byte0 : md) {
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }


    /**
     * The type Translation response dto.
     */
    @Data
    public static class TranslationResponseDTO implements Serializable {

        private String query;
        private String errorCode;
        private String l;
        @JsonProperty("tSpeakUrl")
        private String tSpeakUrl;
        private String requestId;
        private Dict dict;
        private Webdict webdict;
        private Basic basic;
        private Boolean isWord;
        private String speakUrl;
        private List<String> returnPhrase;
        private List<Web> web;
        private List<String> translation;

        @NoArgsConstructor
        @Data
        public static class Dict {
            private String url;
        }

        @NoArgsConstructor
        @Data
        public static class Webdict {
            private String url;
        }

        @NoArgsConstructor
        @Data
        public static class Basic {
            private String phonetic;
            private List<String> explains;
        }

        @NoArgsConstructor
        @Data
        public static class Web {
            private String key;
            private List<String> value;
        }
    }


}
