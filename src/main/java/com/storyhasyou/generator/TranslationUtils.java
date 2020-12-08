package com.storyhasyou.generator;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.storyhasyou.kratos.enums.LanguageEnum;
import com.storyhasyou.kratos.handler.TranslationHandler;
import com.storyhasyou.kratos.utils.CollectionUtils;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The type Translation utils.
 *
 * @author fangxi created by 2020/11/17
 */
public class TranslationUtils {

    /**
     * Translation map.
     *
     * @param textMap the map
     * @return the map
     */
    public static List<EnumGenerateModel.Element> translation(Map<Object, String> textMap) {
        if (CollectionUtils.isEmpty(textMap)) {
            return Collections.emptyList();
        }
        List<EnumGenerateModel.Element> elements = new ArrayList<>(textMap.size());
        textMap.forEach((code, value) -> {
            TranslationHandler translationHandler = TranslationHandler.builder().build();
            String message = translationHandler.translation(value, LanguageEnum.ZH_CHS, LanguageEnum.EN);
            EnumGenerateModel.Element element = new EnumGenerateModel.Element();
            element.setChinese(value);
            element.setMessage(message);
            element.setCode(code);
            elements.add(element);
        });
        return elements;
    }

    /**
     * Truncate string.
     *
     * @param q the q
     * @return the string
     */
    private static String truncate(String q) {
        if (q == null) {
            return null;
        }
        int len = q.length();
        return len <= 20 ? q : (q.substring(0, 10) + len + q.substring(len - 10, len));
    }

    /**
     * 生成加密字段
     *
     * @param string the string
     * @return the digest
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

        /**
         * The Query.
         */
        private String query;
        /**
         * The Error code.
         */
        private String errorCode;
        /**
         * The L.
         */
        private String l;
        /**
         * The T speak url.
         */
        @JsonProperty("tSpeakUrl")
        private String tSpeakUrl;
        /**
         * The Request id.
         */
        private String requestId;
        /**
         * The Dict.
         */
        private Dict dict;
        /**
         * The Webdict.
         */
        private Webdict webdict;
        /**
         * The Basic.
         */
        private Basic basic;
        /**
         * The Is word.
         */
        private Boolean isWord;
        /**
         * The Speak url.
         */
        private String speakUrl;
        /**
         * The Return phrase.
         */
        private List<String> returnPhrase;
        /**
         * The Web.
         */
        private List<Web> web;
        /**
         * The Translation.
         */
        private List<String> translation;

        /**
         * The type Dict.
         */
        @NoArgsConstructor
        @Data
        public static class Dict {
            /**
             * The Url.
             */
            private String url;
        }

        /**
         * The type Webdict.
         */
        @NoArgsConstructor
        @Data
        public static class Webdict {
            /**
             * The Url.
             */
            private String url;
        }

        /**
         * The type Basic.
         */
        @NoArgsConstructor
        @Data
        public static class Basic {
            /**
             * The Phonetic.
             */
            private String phonetic;
            /**
             * The Explains.
             */
            private List<String> explains;
        }

        /**
         * The type Web.
         */
        @NoArgsConstructor
        @Data
        public static class Web {
            /**
             * The Key.
             */
            private String key;
            /**
             * The Value.
             */
            private List<String> value;
        }
    }


}
