package com.company;

@FunctionalInterface
public interface IFunction {
    double compute(double x); /*Кроме x может потребоваться передача и других параметров, от которых зависит построение функции. Надо подумать и принять обоснованное решение о том, какие ещё параметры передавать на данном этапе*/
}