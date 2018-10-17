package com.ywj.swiftbuy.admin.service;


import com.ywj.swiftbuy.dao.model.tables.TestCode;
import com.ywj.swiftbuy.dao.model.tables.records.TestCodeRecord;
import com.ywj.swiftbuy.service.common.CommonService;
import com.ywj.swiftbuy.service.utils.Md5Utils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
public class TestServiceImpl extends CommonService implements TestService {

    private static final TestCode TABLE = TestCode.TEST_CODE;

    /**
     * 检测是否有emoji字符
     *
     * @param source
     * @return
     */
    private static boolean containsEmoji(String source) {
        if (StringUtils.isBlank(source)) {
            return false;
        }

        int len = source.length();

        for (int i = 0; i < len; i++) {
            char codePoint = source.charAt(i);

            if (isEmojiCharacter(codePoint)) {

                return true;
            }
        }

        return false;
    }

    private static boolean isEmojiCharacter(char codePoint) {
        return !notEmojiCharacter(codePoint);
    }

    private static boolean notEmojiCharacter(char codePoint) {
        return (codePoint == 0x0) ||
                (codePoint == 0x9) ||
                (codePoint == 0xA) ||
                (codePoint == 0xD) ||
                ((codePoint >= 0x20) && (codePoint <= 0xD7FF)) ||
                ((codePoint >= 0xE000) && (codePoint <= 0xFFFD)) ||
                ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF));
    }

    /**
     * 过滤emoji 或者 其他非文字类型的字符
     *
     * @param source
     * @return
     */
    public static String filterEmoji(String source) {

        if (!containsEmoji(source)) {
            return source;//
        }
        StringBuilder buf = new StringBuilder();

        for (int i = 0; i < source.length(); i++) {
            char codePoint = source.charAt(i);

            if (!isEmojiCharacter(codePoint)) {
                buf.append(codePoint);
            }
        }

        return buf.toString();
    }

    public synchronized boolean test1() throws Exception {
        System.out.println("test1=============");
        Thread.sleep(1000 * 15);
        return false;
    }

    public synchronized void test2() throws Exception {
        System.out.println("test2=============");
        test1();
    }


    @Test
    public void test3() {
        String pwd = Md5Utils.md5("123456");
        System.out.print("pwd=" + pwd);
    }

    private List<Integer> getList() {

        return null;
    }

    @Test
    public void test4() {
        try {
            add();
        } catch (Exception e) {
            System.out.print(e);
        }

    }

    @Transactional
    public void add() {
        updateField(TABLE, TABLE.AGE, 22, TABLE.ID.eq(1));
//        int a=0;
//        int b=1/a;
    }


    public static void main(String[] args) {
        //e1883e8faf748dacd4f4b05fbc2ea270
        String data = "aW52ZW5vX2JsYW5rMDA1X2FzZmRhc2RmYXNk5Y2h" + "JsYW5rMDA1XWvhueggWFzZGZhZHNm5p2l55yL" + String.valueOf(1539270477000L);
        String tokenExpected = Md5Utils.md5(data);
        System.out.print(tokenExpected);
    }

    private static long get() {
        return -1;
    }


}
