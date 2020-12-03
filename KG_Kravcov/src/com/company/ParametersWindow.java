package com.company;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import util.ArrayUtils;
import util.JTableUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class ParametersWindow extends JFrame {
    private JPanel panelMain;
    private JTable parametersTable;
    private JTextField expression;
    private Map<String, Double> parameterToValue = new HashMap<>();
    private Set<String> curParameters = new HashSet<>();

    public ParametersWindow() {
        this.setTitle("Window");
        this.setSize(400, 600);
        this.setContentPane(panelMain);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

        JTableUtils.initJTableForArray(parametersTable, getWidth() / 3, false, false, true, false);
        parametersTable.setRowHeight(25);
        JTableUtils.writeArrayToJTable(parametersTable, ArrayUtils.mapToObjectArray(parameterToValue));

        expression.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                if (expression.getText().length() != 0) {
                    curParameters.clear();
                    DrawPanel dp = DrawPanel.getDp();
                    dp.setFunction(x -> toExpression(expression.getText(), x));
                    dp.repaint();
                } else {
                    expression.setText("Введите выражение");
                }
            }
        });
    }


    public double toExpression(final String str, double curX) {
        return new Object() {
            int pos = -1, ch;

            void nextChar() {
                ch = (++pos < str.length()) ? str.charAt(pos) : -1;
            }

            boolean eat(int charToEat) {
                while (ch == ' ') nextChar();
                if (ch == charToEat) {
                    nextChar();
                    return true;
                }
                return false;
            }

            double parse() {
                nextChar();
                double x = parseExpression();
                if (pos < str.length()) throw new RuntimeException("Unexpected: " + (char) ch);
                return x;
            }

            double parseExpression() {
                double x = parseTerm();
                for (; ; ) {
                    if (eat('+')) x += parseTerm();
                    else if (eat('-')) x -= parseTerm();
                    else return x;
                }
            }

            double parseTerm() {
                double x = parseFactor();
                for (; ; ) {
                    if (eat('*')) x *= parseFactor();
                    else if (eat('/')) x /= parseFactor();
                    else return x;
                }
            }

            double parseFactor() {
                if (eat('+')) return parseFactor();
                if (eat('-')) return -parseFactor();
                double x;
                int startPos = this.pos;
                if (eat('(')) {
                    x = parseExpression();
                    eat(')');
                } else if ((ch >= '0' && ch <= '9') || ch == '.') {
                    while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
                    x = Double.parseDouble(str.substring(startPos, this.pos));
                } else if (ch >= 'A' && ch <= 'Z' || (ch >= '0' && ch <= '9')) {
                    while (ch >= 'A' && ch <= 'Z' || (ch >= '0' && ch <= '9')) nextChar();
                    String parameter = str.substring(startPos, this.pos);
                    curParameters.add(parameter);

                    for (int i = 0; i < curParameters.size(); i++) {
                        System.out.println(JTableUtils.readObjMatrixFromJTable(parametersTable).length);
                        if (JTableUtils.readObjMatrixFromJTable(parametersTable).length > 1) {
                            System.out.println(curParameters.toArray()[i]);
                            System.out.println(parameterToValue.toString());
                            if (!parameterToValue.containsKey(curParameters.toArray()[i]) &&
                                    !ArrayUtils.arrToMap(JTableUtils.readObjMatrixFromJTable(parametersTable)).containsKey(curParameters.toArray()[i])) {
//                                System.out.println(parameterToValue.toString());
//                                System.err.println(curParameters.toArray()[i]);
                                parameterToValue.put((String) curParameters.toArray()[i], 1.0);
                                JTableUtils.writeArrayToJTable(parametersTable, ArrayUtils.mapToObjectArray(parameterToValue));
                            } else {
                                parameterToValue = ArrayUtils.arrToMap(JTableUtils.readObjMatrixFromJTable(parametersTable));
                                JTableUtils.writeArrayToJTable(parametersTable, ArrayUtils.mapToObjectArray(parameterToValue));
                            }
                        }
                    }

//                    for (int i = 0; i < parameterToValue.size(); i++) {
//                        if (!curParameters.contains(parameterToValue.keySet().toArray()[i])) {
//                            parameterToValue.remove(parameterToValue.keySet().toArray()[i]);
//                            JTableUtils.writeArrayToJTable(parametersTable, ArrayUtils.mapToObjectArray(parameterToValue));
//                        }
//                    }

                    if (!Arrays.deepToString(ArrayUtils.mapToObjectArray(parameterToValue)).equals
                            (Arrays.deepToString(Objects.requireNonNull(JTableUtils.readObjMatrixFromJTable(parametersTable))))) {
                        if (!parameterToValue.containsKey(parameter)) {
//                            System.out.println(parameter + " not contains");
                            parameterToValue.put(parameter, 1.0);
                        } else {
//                            System.out.println(parameter + " contains");
                            if (JTableUtils.readObjMatrixFromJTable(parametersTable).length > 1) {
                                parameterToValue = ArrayUtils.arrToMap(JTableUtils.readObjMatrixFromJTable(parametersTable));
                            }
                            JTableUtils.writeArrayToJTable(parametersTable, ArrayUtils.mapToObjectArray(parameterToValue));
                        }
                    }

//                    System.out.println(parameterToValue.toString());
//                    System.out.println(curParameters.toString());

                    x = (parameterToValue.get(parameter));

                } else if (ch == 'x') {
                    while (ch == 'x') nextChar();
                    x = curX;
                } else if (ch == 'e') {
                    while (ch == 'e') nextChar();
                    x = Math.exp(curX);
                } else if (ch >= 'a' && ch <= 'z') {
                    while (ch >= 'a' && ch <= 'z' && ch != 'x') nextChar();
                    String func = str.substring(startPos, this.pos);
                    x = parseFactor();
                    if (func.equals("sqrt")) x = Math.sqrt(x);
                    else if (func.equalsIgnoreCase("sin")) x = Math.sin(x);
                    else if (func.equalsIgnoreCase("cos")) x = Math.cos(x);
                    else if (func.equalsIgnoreCase("tan")) x = Math.tan(x);
                    else throw new RuntimeException("Unknown function: " + func);
                } else {
                    throw new RuntimeException("Unexpected: " + (char) ch);
                }
                if (eat('^')) x = Math.pow(x, parseFactor());
                return x;
            }
        }.parse();
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        panelMain = new JPanel();
        panelMain.setLayout(new GridLayoutManager(5, 3, new Insets(10, 10, 10, 10), 10, 10));
        panelMain.setBorder(BorderFactory.createTitledBorder(""));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panelMain.add(panel1, new GridConstraints(0, 1, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        panel1.add(spacer1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panelMain.add(panel2, new GridConstraints(4, 1, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final Spacer spacer2 = new Spacer();
        panel2.add(spacer2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final JScrollPane scrollPane1 = new JScrollPane();
        scrollPane1.setVerticalScrollBarPolicy(21);
        panelMain.add(scrollPane1, new GridConstraints(1, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(-1, 200), null, 0, false));
        parametersTable = new JTable();
        parametersTable.setFillsViewportHeight(false);
        parametersTable.setUpdateSelectionOnSort(true);
        scrollPane1.setViewportView(parametersTable);
        expression = new JTextField();
        panelMain.add(expression, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setText("Введите выражение");
        panelMain.add(label1, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panelMain;
    }

}
