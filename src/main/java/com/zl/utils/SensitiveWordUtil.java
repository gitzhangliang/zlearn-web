package com.zl.utils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author zhangliang
 * @date 2019/12/31.
 */
public class SensitiveWordUtil {
    private Map sensitiveWordMap = null;

    /**
     * 词最小长度
     */
    private static final int WORD_MIN_LENGTH = 1;

    private static final String IS_END = "isEnd";

    /**
     * 构造函数，初始化敏感词库
     */
    public SensitiveWordUtil(Set<String> keyWordSet){
        addSensitiveWordToHashMap(keyWordSet);
    }

    /**
     * 获取文字中的敏感词
     */
    public Set<String> getSensitiveWord(String txt){
        Set<String> sensitiveWordList = new HashSet<>();
        int i = 0;
        int textLen = txt.length();
        while (textLen != i) {
            //判断是否包含敏感字符
            int length = checkSensitiveWord(txt, i);
            if (length > 0) {
                sensitiveWordList.add(txt.substring(i, i + length));
                i = i + length;
            } else {
                i++;
            }

        }
        return sensitiveWordList;
    }


    /**
     * 检查文字中是否包含敏感字符，检查规则如下：如果存在，则返回敏感词字符的长度，不存在返回0
     */
    private int checkSensitiveWord(String txt, int beginIndex){
        boolean  flag = false;
        int matchFlag = 0;
        char word;
        Map nowMap = sensitiveWordMap;
        for(int i = beginIndex; i < txt.length() ; i++){
            word = txt.charAt(i);
            nowMap = (Map) nowMap.get(word);
            if(nowMap != null){
                matchFlag++;
                if("1".equals(nowMap.get(IS_END))){
                    flag = true;
                    break;
                }
            } else{
                break;
            }
        }
        if(matchFlag < WORD_MIN_LENGTH || !flag){
            matchFlag = 0;
        }
        return matchFlag;
    }

    @SuppressWarnings({ "unchecked"})
    private void addSensitiveWordToHashMap(Set<String> keyWordSet) {
        sensitiveWordMap = new HashMap(keyWordSet.size());
        String key;
        Map nowMap;
        Map<String, String> newWorMap;
        for (String s : keyWordSet) {
            key = s;
            nowMap = sensitiveWordMap;
            for (int i = 0; i < key.length(); i++) {
                char keyChar = key.charAt(i);
                Object wordMap = nowMap.get(keyChar);
                if (wordMap != null) {
                    nowMap = ( Map ) wordMap;
                } else {
                    newWorMap = new HashMap<>(16);
                    newWorMap.put(IS_END, "0");
                    nowMap.put(keyChar, newWorMap);
                    nowMap = newWorMap;
                }

                if (i == key.length() - 1) {
                    nowMap.put(IS_END, "1");
                }
            }
        }
    }
}
