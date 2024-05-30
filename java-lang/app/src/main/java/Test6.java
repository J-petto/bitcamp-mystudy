// 실습
// - 다음과 같이 실행결과가 나오도록 코드를 완성하라.
//      $ java -classpath ... Test6 aaa bbb ccc ddd
//      'aaa'
//      'bbb'
//      'ccc'
//      'ddd'

class Test6 {
    public static void main(String[] args) {
        // 코드를 완성하시오!

        // for (int i = 0; i < args.length; i++) {
        // System.out.println("'" + args[i] + "'");
        // }

        for (String arg : args) {
            System.out.println("'" + arg + "'");
        }

        // int i = 0;
        // while (true) {
        // System.out.println("'" + args[i] + "'");
        // i++;

        // if (i >= args.length)
        // break;
        // }
    }
}
