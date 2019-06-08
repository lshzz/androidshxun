package com.ash.transport.utils;

import java.util.regex.Pattern;

/*----------------------------------------------*
 * @package:   com.ash.transport.utils
 * @fileName:  RegUtil.java
 * @describe:  正则表达式验证工具类
 *----------------------------------------------*
 * @author:    ash
 * @email:     Glaxyinfinite@outlook.com
 * @date:      on 2019-05-29 16:49
 *----------------------------------------------*/
public class RegUtil {

    // 3位整数 正则表达式 验证规则
    private static final Pattern PATTERN_INTEGER_3 = Pattern.compile("^\\d{1,3}$");
    // IPv4地址 正则表达式 验证规则
    private static final Pattern PATTERN_IP = Pattern.compile("^(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)(\\.(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)){3}$");


    // 验证字符串是否符合 3位整数 规则
    public static boolean isInteger3(String money) {
        return PATTERN_INTEGER_3.matcher(money).matches();
    }

    // 验证字符串是否符合 IPv4 地址 规则
    public static boolean isIP(String ip) {
        return PATTERN_IP.matcher(ip).matches();
    }
}
