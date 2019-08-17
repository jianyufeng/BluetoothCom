package com.puyu.mobile.base.util;

/**
 * author : 简玉锋
 * e-mail : yufeng_jian@fpi-inc.com
 * date   : 2019/7/9 13:44
 * desc   :
 * version: 1.0
 */
public class StringUtil {
    /**
     * 字符串判空
     *
     * @param src
     * @return
     */
    public static boolean isEmpty(String src) {
        return src == null || "".equals(src.trim()) || "null".equalsIgnoreCase(src) || "<null>".equalsIgnoreCase(src);
    }

    public static int getStringNumberIndex(String res, String rule, boolean last) {
        int index;
        if (last) {
            index = res.lastIndexOf(rule);
        } else {
            index = res.indexOf(rule);
        }
        if (index < 0) return -1;
        String substring = res.substring(index);
        if (NumberUtil.isInteger(substring)) {
            return index;
        }
        return -1;
    }
}
