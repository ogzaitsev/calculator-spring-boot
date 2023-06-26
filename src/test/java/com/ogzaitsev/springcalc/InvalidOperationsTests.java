package com.ogzaitsev.springcalc;

import com.ogzaitsev.springcalc.controller.CalcController;
import com.ogzaitsev.springcalc.model.Expression;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class InvalidOperationsTests {
    CalcController calcController = new CalcController();
    Expression expression = new Expression();

    @Test
    void invalidOperations1() {
        expression.setInputExpression("sin(0.0+)");
        calcController.showCalculator(expression);
        assertThat(expression.getResultExpression()).isEqualTo("Ошибка в выражении");
    }

    @Test
    void invalidOperations2() {
        expression.setInputExpression("cos(*(0.0))");
        calcController.showCalculator(expression);
        assertThat(expression.getResultExpression()).isEqualTo("Ошибка в выражении");
    }

    @Test
    void invalidOperations3() {
        expression.setInputExpression("tan(0)+");
        calcController.showCalculator(expression);
        assertThat(expression.getResultExpression()).isEqualTo("Ошибка в выражении");
    }

    @Test
    void invalidOperations4() {
        expression.setInputExpression("(-)");
        calcController.showCalculator(expression);
        assertThat(expression.getResultExpression()).isEqualTo("Ошибка в выражении");
    }

    @Test
    void invalidOperations5() {
        expression.setInputExpression("3.32^2.801*+5");
        calcController.showCalculator(expression);
        assertThat(expression.getResultExpression()).isEqualTo("Ошибка в выражении");
    }

    @Test
    void invalidOperations6() {
        expression.setInputExpression("(3.32^^2.8");
        calcController.showCalculator(expression);
        assertThat(expression.getResultExpression()).isEqualTo("Ошибка в выражении");
    }

    @Test
    void invalidOperations7() {
        expression.setInputExpression("3.32^*(2.8)");
        calcController.showCalculator(expression);
        assertThat(expression.getResultExpression()).isEqualTo("Ошибка в выражении");
    }

    @Test
    void invalidOperations8() {
        expression.setInputExpression("-sin(1)");
        calcController.showCalculator(expression);
        assertThat(expression.getResultExpression()).isEqualTo("-0.8414710");
    }
}
