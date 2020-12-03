package util;

import com.company.IFunction;
import com.company.ParametersWindow;

public class Function implements IFunction {

    @Override
    public double compute(double x) {

        return Math.sin(x);
//        return (1/(2*x+4)+3);
    }

//    public double toExpression(final String str) throws ParseException {
//        return new Object() {
//            int pos = -1, ch;
//
//            void nextChar() {
//                ch = (++pos < str.length()) ? str.charAt(pos) : -1;
//            }
//
//            boolean eat(int charToEat) {
//                while (ch == ' ') nextChar();
//                if (ch == charToEat) {
//                    nextChar();
//                    return true;
//                }
//                return false;
//            }
//
//            double parse() throws ParseException {
//                nextChar();
//                double x = parseExpression();
//                if (pos < str.length()) throw new RuntimeException("Unexpected: " + (char) ch);
//                return x;
//            }
//
//            double parseExpression() throws ParseException {
//                double x = parseTerm();
//                for (; ; ) {
//                    if (eat('+')) x += parseTerm();
//                    else if (eat('-')) x -= parseTerm();
//                    else return x;
//                }
//            }
//
//            double parseTerm() throws ParseException {
//                double x = parseFactor();
//                for (; ; ) {
//                    if (eat('*')) x *= parseFactor();
//                    else if (eat('/')) x /= parseFactor();
//                    else return x;
//                }
//            }
//
//            double parseFactor() throws ParseException {
//                if (eat('+')) return parseFactor();
//                if (eat('-')) return -parseFactor();
//
//                double x;
//                int startPos = this.pos;
//                if (eat('(')) {
//                    x = parseExpression();
//                    eat(')');
//                } else if ((ch >= '0' && ch <= '9') || ch == '.') { // numbers
//                    while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
//                    x = Double.parseDouble(str.substring(startPos, this.pos));
//                } else if (ch >= 'A' && ch <= 'Z') { // functions
//                    while (ch >= 'A' && ch <= 'Z') nextChar();
//                    String parameter = str.substring(startPos, this.pos);
//                    System.out.println(parameter);
//                    if (!ParametersWindow.getParameterToValue().containsKey(parameter)) ParametersWindow.getParameterToValue().put(parameter, 1);
//                    JTableUtils.writeArrayToJTable(ParametersWindow.getParametersTable(), ArrayUtils.mapToObjectArray(ParametersWindow.getParameterToValue()));
//                    x = (ParametersWindow.getParameterToValue().get(parameter));
//                } else if (ch == 'x') { // functions
//                    while (ch == 'x') nextChar();
//                    String parameter = str.substring(startPos, this.pos);
//                    x = 10;
//                } else if (ch >= 'a' && ch <= 'z') {
//                    while (ch >= 'a' && ch <= 'z' && ch != 'x') nextChar();
//                    String func = str.substring(startPos, this.pos);
//                    x = parseFactor();
//                    if (func.equals("sqrt")) x = Math.sqrt(x);
//                    else if (func.equalsIgnoreCase("sin")) x = Math.sin(Math.toRadians(x));
//                    else if (func.equalsIgnoreCase("cos")) x = Math.cos(Math.toRadians(x));
//                    else if (func.equalsIgnoreCase("tan")) x = Math.tan(Math.toRadians(x));
//                    else throw new RuntimeException("Unknown function: " + func);
//                } else {
//                    throw new RuntimeException("Unexpected: " + (char) ch);
//                }
//                if (eat('^')) x = Math.pow(x, parseFactor());
//                return x;
//            }
//        }.parse();
//    }
}
