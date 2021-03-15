package ru.turvendiz.practice_test_tasks.string_unwrapper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUnwrapper {
    public static void main(String[] args) {
        String s = "3[x2[abc]yz]2[xy]z"; // xabcabcyzxabcabcyzxabcabcyzxyxyz
        String s2 = "4[3[2[3a]]]"; // 3a3a3a3a3a3a3a3a3a3a3a3a3a3a3a3a3a3a3a3a3a3a3a3a
        System.out.println(unwrapBrackets(s2));
    }

    private static String unwrapBrackets(String bracketString) {
        Pattern pattern = Pattern.compile("(\\d+)\\[(\\w+)]");
        Matcher matcher = pattern.matcher(bracketString);
        if (matcher.find()) {
            String replacement = matcher.group(2).repeat(Integer.parseInt(matcher.group(1)));
            return unwrapBrackets(bracketString.substring(0, matcher.start())
                    .concat(replacement)
                    .concat(bracketString.substring(matcher.end())));
        } else {
            return bracketString;
        }
    }
}
