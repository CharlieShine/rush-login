package com.rush.house.common.util;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by 17512 on 2018/3/6.
 */
public class StrUtils {

    private static final Logger log = LoggerFactory.getLogger(StrUtils.class);

    //统一编码
    public static final String ENCODING_UTF8 = "UTF-8";
    public static final String ENCODING_ISO_8859_1 = "ISO-8859-1";

    public static Boolean isMobile (String src) {
        if (src == null || src.trim().equals("")) {
            return false;
        }
        return Pattern.compile("^1[\\d]{10}$").matcher(src).matches();
    }

    /**
     * 验证邮箱
     * @param src
     * @return
     */
    public static Boolean isEmail (String src) {
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$");
        Matcher matcher = pattern.matcher(src);
        return matcher.matches();
    }

    /**
     * 验证链接
     * @param src
     * @return
     */
    public static Boolean isUrl (String src) {
        Pattern pattern = Pattern.compile("(http[s]?:)?//[\\w./?=&]*");
        Matcher matcher = pattern.matcher(src);
        return matcher.matches();
    }

    public static String md5 (String src) {
        try {
            if (src == null || src.trim().length() < 1) {
                return null;
            }
            String result = new String(DigestUtils.md5Hex(src));
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 随机len位数的数字
     * @param len
     * @return
     */
    public static Long randomNumber (Integer len) {
        if (len == null) {
            return null;
        }
        StringBuilder result = new StringBuilder("");
        for (int i = 0; i < len; i += 1) {
            Integer r = (int)(Math.random() * 10);
            if (i == 0 && r == 0) { // 第一位不能为0
                while (true) {
                    r = (int)(Math.random() * 10);
                    if (r != 0) {
                        break;
                    }
                }
            }
            result.append(r);
        }
        return Long.parseLong(result.toString());
    }

    /**
     * 随机uuid
     * @return
     */
    public static String randomUUID () {
        String uuid = UUID.randomUUID().toString();
        uuid = uuid.replaceAll("-", "");
        return uuid;
    }

    /**
     * 获取当前classpath 绝对路径
     * @return
     */
    public static String getClasspath () {
        return Thread.currentThread().getContextClassLoader().getResource("/").getPath();
    }

    /**
     * 通过request获取请求url前缀
     * @param req
     * @return
     */
    public static String getRequestUrlPreffix (HttpServletRequest req) {
        String url = req.getRequestURL().toString();
        String uri = req.getRequestURI().toString();
        String context = req.getContextPath();
        String result = req.getRequestURL().delete(url.length() - uri.length(), url.length())
                .append(context).toString();
        return result;
    }

    /**
     * 去掉影响json判断的标志
     * @return
     */
    public static String removeJsonSign (String src) {
        if (src == null) {
            return null;
        }
        return src.replaceAll("[\\[\\]\\{\\}]", "");
    }

    /**
     * 搜索关键词按空格分解
     * @param src
     * @return
     */
    public static List<String> getKeywordList (String src) {
        if (src == null || "".equals(src.trim())) {
            return null;
        }
        String[] keywordList = src.split(" ");
        List<String> keywords = new ArrayList<>();
        for (String kw : keywordList) {
            kw = "%" + kw + "%";
            keywords.add(kw);
        }
        return keywords;
    }

    /**
     * 从字符串中提取itemId
     * @param src
     * @return
     */
    public static Long getItemIdFromStr (String src) {
        if (src == null) {
            return null;
        }
        Long result = null;
        Pattern pattern = Pattern.compile("\"itemId\":[\\d]+");
        Matcher matcher = pattern.matcher(src);
        while (matcher.find()) {
            result = Long.parseLong(matcher.group().replace("\"itemId\":", ""));
            break;
        }
        return result;
    }

    /**
     * 判断字符串是否是数字
     * @param str
     * @return
     */
    public static boolean isNum(String str) {
        if (str == null) {
            return false;
        }
        Pattern pattern = Pattern.compile("-?[0-9]*\\.?[0-9]*");
        Matcher isNum = pattern.matcher(str);
        return isNum.matches();
    }

    /**
     * 检查src是否包含在targetList中
     * @param targetList
     * @param src
     * @return
     */
    public static boolean containsInList (List<String> targetList, String src) {
        if (targetList == null || targetList.size() < 1) {
            return false;
        }
        if (src == null || "".equals(src.trim())) {
            return false;
        }
        boolean result = false;
        for (String s : targetList) {
            if (src.equals(s)) {
                result = true;
                break;
            }
        }
        return result;
    }

    public static String decodeUnicode(final String src) {
        try {
            if (src == null || src.trim().length() < 1) {
                return src;
            }
            String result = src;
            Pattern pattern = Pattern.compile("\\\\u[0-9abcdefABCDEF]{4}");
            Matcher matcher = pattern.matcher(src);
            while (matcher.find()) {
                String code = matcher.group().replaceAll("\\\\u", "");
                char letter = (char) Integer.parseInt(code, 16);
                result = result.replaceAll("\\\\u" + code, letter + "");
            }
            return result;
        } catch (Exception e) {
            log.error("解码unicode字符串异常:" + src);
            return null;
        }
    }

}
