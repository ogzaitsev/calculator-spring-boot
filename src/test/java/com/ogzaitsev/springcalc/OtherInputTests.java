package com.ogzaitsev.springcalc;

import com.ogzaitsev.springcalc.controller.CalcController;
import com.ogzaitsev.springcalc.model.Expression;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class OtherInputTests {
    CalcController calcController = new CalcController();
    Expression expression = new Expression();

    @Test
    void whitespaces1() {
        expression.setInputExpression("sin( 0 . 0 )");
        calcController.showCalculator(expression);
        assertThat(expression.getResultExpression()).isEqualTo("0.0000000");
    }

    @Test
    void whitespaces2() {
        expression.setInputExpression("cos ( t a n ( 5 *   0   . 1))");
        calcController.showCalculator(expression);
        assertThat(expression.getResultExpression()).isEqualTo("0.8544513");
    }

    @Test
    void uppercase1() {
        expression.setInputExpression("COS(0.0)");
        calcController.showCalculator(expression);
        assertThat(expression.getResultExpression()).isEqualTo("1.0000000");
    }

    @Test
    void uppercase2() {
        expression.setInputExpression("cOs(0.0) + sIn(0)");
        calcController.showCalculator(expression);
        assertThat(expression.getResultExpression()).isEqualTo("1.0000000");
    }
}
