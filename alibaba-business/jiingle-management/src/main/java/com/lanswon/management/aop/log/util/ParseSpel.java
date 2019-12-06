package com.lanswon.management.aop.log.util;

import com.lanswon.management.aop.log.annotation.Log;
import lombok.experimental.UtilityClass;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.lang.reflect.Method;

/**
 * @Description:
 * @Author GU-YW
 * @Date 2019/12/4 13:37
 */
@UtilityClass
public class ParseSpel {

    //解析spel表达式
    ExpressionParser parser = new SpelExpressionParser();
    //将方法参数纳入Spring管理
    LocalVariableTableParameterNameDiscoverer discoverer = new LocalVariableTableParameterNameDiscoverer();

    public String parseSpel(ProceedingJoinPoint pjp) throws Throwable {
        //获取参数对象数组
        Object[] args = pjp.getArgs();
        //获取方法
        Method method = ((MethodSignature) pjp.getSignature()).getMethod();
        Log log = method.getAnnotation(Log.class);
        String spel = log.message();
        //获取方法参数名
        String[] params = discoverer.getParameterNames(method);
        //将参数纳入Spring管理
        EvaluationContext context = new StandardEvaluationContext();
        for (int len = 0; len < params.length; len++) {
            context.setVariable(params[len], args[len]);
        }
        Expression expression = parser.parseExpression(spel);
        spel = expression.getValue(context, String.class);

        return spel;
    }
}
