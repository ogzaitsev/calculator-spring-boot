package com.ogzaitsev.springcalc.controller;

import com.ogzaitsev.springcalc.model.Expression;
import com.ogzaitsev.springcalc.model.ReversePolishNotation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class CalcController {

    @GetMapping("")
    public String showMainPage() {
        return "index";
    }
    @GetMapping("/calculator")
    public String showCalculator(@ModelAttribute("expression") Expression expression) {

        if(expression.getInputExpression() == null || expression.getInputExpression().equals("")) {
            return "calculator";
        }
        if (!expression.isValid()) {
            expression.setResultExpression("Ошибка в выражении");
            return "calculator";
        }

        expression.tokenizeInput();
        ReversePolishNotation rpn = new ReversePolishNotation(expression);
        rpn.toPolishNotation();
        rpn.calculate();

        return "calculator";
    }

    @GetMapping("/documentation")
    public String showDocumentation() {
        return "documentation";
    }

}
