package com.ogzaitsev.springcalc;

import com.ogzaitsev.springcalc.controller.CalcController;
import com.ogzaitsev.springcalc.model.Expression;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class InvalidParenthesesTests {
    CalcController calcController = new CalcController();
    Expression expression = new Expression();

    @Test
    void invalidParentheses1() {
        expression.setInputExpression("sin(0.0)000)");
        calcController.showCalculator(expression);
        assertThat(expression.getResultExpression()).isEqualTo("Ошибка в выражении");
    }

    @Test
    void invalidParentheses2() {
        expression.setInputExpression("cos(((0.0))");
        calcController.showCalculator(expression);
        assertThat(expression.getResultExpression()).isEqualTo("Ошибка в выражении");
    }

    @Test
    void invalidParentheses3() {
        expression.setInputExpression("tan(0)+5)");
        calcController.showCalculator(expression);
        assertThat(expression.getResultExpression()).isEqualTo("Ошибка в выражении");
    }

    @Test
    void invalidParentheses4() {
        expression.setInputExpression(")");
        calcController.showCalculator(expression);
        assertThat(expression.getResultExpression()).isEqualTo("Ошибка в выражении");
    }

    @Test
    void invalidParentheses5() {
        expression.setInputExpression("(5+2*(6+(3/1)");
        calcController.showCalculator(expression);
        assertThat(expression.getResultExpression()).isEqualTo("Ошибка в выражении");
    }


}
