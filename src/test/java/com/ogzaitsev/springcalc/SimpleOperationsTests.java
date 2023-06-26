package com.ogzaitsev.springcalc;

import com.ogzaitsev.springcalc.controller.CalcController;
import com.ogzaitsev.springcalc.model.Expression;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class SimpleOperationsTests {
    CalcController calcController = new CalcController();
    Expression expression = new Expression();

    @Test
    void calculatorSimpleOperation1() {
        expression.setInputExpression("1+2");
        calcController.showCalculator(expression);
        assertThat(expression.getResultExpression()).isEqualTo("3.0000000");
    }

    @Test
    void calculatorSimpleOperation2() {
        expression.setInputExpression("1*2*3/2");
        calcController.showCalculator(expression);
        assertThat(expression.getResultExpression()).isEqualTo("3.0000000");
    }

    @Test
    void calculatorSimpleOperation3() {
        expression.setInputExpression("1*2+3^2");
        calcController.showCalculator(expression);
        assertThat(expression.getResultExpression()).isEqualTo("11.0000000");
    }

    @Test
    void calculatorSimpleOperation4() {
        expression.setInputExpression("1.0*2-3-2");
        calcController.showCalculator(expression);
        assertThat(expression.getResultExpression()).isEqualTo("-3.0000000");
    }

    @Test
    void calculatorSimpleOperation5() {
        expression.setInputExpression("1-2*3^2+0.0");
        calcController.showCalculator(expression);
        assertThat(expression.getResultExpression()).isEqualTo("-17.0000000");
    }



}
