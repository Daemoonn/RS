package com.example.rs.util;

public class ChineseJudger {
    // 判断一个字符是否是中文
    public static boolean isChinese(char c) {
        return c >= 0x4E00 &&  c <= 0x9FA5;// 根据字节码判断
    }

    // 判断一个字符串是否含有中文
    public static boolean isChinese(String str) {
        if (str == null) return false;
        for (char c : str.toCharArray()) {
            if (isChinese(c)) return true;// 有一个中文字符就返回
        }
        return false;
    }

    public static void main(String[] args) {
        String cn = "我不是 wala!";
        String en = "english wala";
        System.out.println(isChinese(cn));
        System.out.println(isChinese(en));
    }
}
