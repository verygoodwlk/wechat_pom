package com.qf.util;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class PinyinUtils {

    public static String nickname2Pinyin(String nickname){

        if(nickname == null){
            return null;
        }

        //
        StringBuffer sb = new StringBuffer();

        //设置pinyin4j的格式
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);

        try {
            char[] chars = nickname.toCharArray();
            for (char c : chars) {
                String[] ss = PinyinHelper.toHanyuPinyinStringArray(c, format);
                if(ss != null){
                    sb.append(ss[0]);
                } else {
                    //非汉字原封不动
                    //sb.append(c);
                }
            }
        } catch (BadHanyuPinyinOutputFormatCombination e) {
            e.printStackTrace();
        }

        return sb.toString();
    }

    public static void main(String[] args) throws BadHanyuPinyinOutputFormatCombination {
        System.out.println(nickname2Pinyin("#今天￥天气不错，心情是极好的！"));
    }
}
