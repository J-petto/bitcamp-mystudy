import java.util.Enumeration;
import java.util.Properties;
import java.util.Set;
import java.util.Map.Entry;

public class Test {
    public static void main(String[] args) {
        // 프로그램 아규먼트
        // $ java Test aaa bbb ccc

        printProgramArguments(args);
    }

    static void printProgramArguments(String[] args) {
        System.out.println("[프로그램 아규먼트]");
        for (String arg : args) {
            System.out.println(arg);
        }
    }

    static void printJvmArguments() {
        Properties props = System.getProperties();
        Set<Entry<Object, Object>> entrySet = props.entrySet();
        for (Entry<Object, Object> entry : entrySet) {
            System.out.println(entry);
        }
    }
}
