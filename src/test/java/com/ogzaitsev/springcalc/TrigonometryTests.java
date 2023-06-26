package com.ogzaitsev.springcalc;

import com.ogzaitsev.springcalc.controller.CalcController;
import com.ogzaitsev.springcalc.model.Expression;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class TrigonometryTests {
    CalcController calcController = new CalcController();
    Expression expression = new Expression();

    @Test
    void testTrigonometryOperations1() {
        expression.setInputExpression("sin(0.0000)");
        calcController.showCalculator(expression);
        assertThat(expression.getResultExpression()).isEqualTo("0.0000000");
    }
    @Test
    void testTrigonometryOperations2() {
        expression.setInputExpression("cos(0)");
        calcController.showCalculator(expression);
        assertThat(expression.getResultExpression()).isEqualTo("1.0000000");
    }
    @Test
    void testTrigonometryOperations3() {
        expression.setInputExpression("tan(0)");
        calcController.showCalculator(expression);
        assertThat(expression.getResultExpression()).isEqualTo("0.0000000");
    }
    @Test
    void testTrigonometryOperations4() {
        expression.setInputExpression("sin(cos(tan(3.1415)))");
        calcController.showCalculator(expression);
        assertThat(expression.getResultExpression()).isEqualTo("0.8414710");
    }
    @Test
    void testTrigonometryOperations5() {
        expression.setInputExpression("sin(3.32^2.801*4.3+5-1.0/6^(tan(16)))*5.000");
        calcController.showCalculator(expression);
        assertThat(expression.getResultExpression()).isEqualTo("2.2147720");
    }
}
