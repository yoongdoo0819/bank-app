package shop.mtcoding.bank.temp;

import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

public class RegexTest {

    @Test
    public void 한글만가능_test() throws Exception {
        String value = "가나한";
        boolean result = Pattern.matches("^[가-힣]+$", value);
        System.out.println("result = " + result);
    }

    @Test
    public void 한글은불가능_test() throws Exception {
        String value = "apple";
        boolean result = Pattern.matches("^[^ㄱ-ㅎ가-힣]*$", value);
        System.out.println("result = " + result);
    }

    @Test
    public void 영어만가능_test() throws Exception {
        String value = "ssar";
        boolean result = Pattern.matches("^[a-zA-Z]+$", value);
        System.out.println("result = " + result);
    }

    @Test
    public void 영어는불가능_test() throws Exception {
        String value = "ssar";
        boolean result = Pattern.matches("^[^a-zA-Z]*$", value);
        System.out.println("result = " + result);
    }

    @Test
    public void 영어와숫자만가능_test() throws Exception {
        String value = "12ssar";
        boolean result = Pattern.matches("^[a-zA-Z0-9]+$", value);
        System.out.println("result = " + result);
    }

    @Test
    public void 영어만가능_길이는최소2최대4_test() throws Exception {
        String value = "ss";
        boolean result = Pattern.matches("^[a-zA-Z]{2,4}$", value);
        System.out.println("result = " + result);

        String value2 = "s";
        boolean result2 = Pattern.matches("^[a-zA-Z]{2,4}$", value2);
        System.out.println("result = " + result2);

        String value3 = "sssss";
        boolean result3 = Pattern.matches("^[a-zA-Z]{2,4}$", value3);
        System.out.println("result = " + result3);

        String value4 = "";
        boolean result4 = Pattern.matches("^[a-zA-Z]{2,4}$", value4);
        System.out.println("result = " + result4);

        String value5 = "ssss";
        boolean result5 = Pattern.matches("^[a-zA-Z]{2,4}$", value5);
        System.out.println("result = " + result5);
    }
}
