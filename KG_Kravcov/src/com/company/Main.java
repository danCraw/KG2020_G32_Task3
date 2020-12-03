package com.company;

import util.ArrayUtils;

import javax.swing.*;
import java.util.Arrays;


public class Main {
    public static void main(String[] args) {
        MainWindow frame = new MainWindow();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocation(387, 0);
        frame.setVisible(true);
        ParametersWindow parametersWindow = new ParametersWindow();
    }
}
