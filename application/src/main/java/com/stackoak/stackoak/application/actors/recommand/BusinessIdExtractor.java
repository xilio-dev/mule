package com.stackoak.stackoak.application.actors.recommand;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class BusinessIdExtractor {

    private static final ExpressionParser SPEL_PARSER = new SpelExpressionParser();

    /**
     * 从方法参数中提取 businessId
     * @param joinPoint 切点对象
     * @param expr SpEL 表达式，如 "#article.aid"
     * @return 提取的 businessId（String 类型）
     */
    public static String extractBusinessId(JoinPoint joinPoint, String expr) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Parameter[] parameters = method.getParameters();
        Object[] args = joinPoint.getArgs();

        // 解析表达式中的参数名（如 "article"）
        String paramName = parseParamNameFromExpr(expr);

        // 查找匹配的参数
        for (int i = 0; i < parameters.length; i++) {
            Parameter param = parameters[i];
            if (paramName.equals(param.getName())) {
                return evaluateSpel(expr, paramName, args[i]);
            }
        }

        // 如果未找到匹配参数，返回空字符串
        return "";
    }

    /**
     * 从 SpEL 表达式中解析参数名
     * 示例: "#article.aid" -> "article"
     */
    private static String parseParamNameFromExpr(String expr) {
        if (expr == null || !expr.startsWith("#")) {
            throw new IllegalArgumentException("Invalid SpEL expression: " + expr);
        }
        return expr.substring(1).split("\\.")[0]; // 截取第一个点之前的部分
    }

    /**
     * 执行 SpEL 表达式
     */
    private static String evaluateSpel(String expr, String paramName, Object arg) {
        try {
            // 构建 SpEL 上下文
            StandardEvaluationContext context = new StandardEvaluationContext();
            context.setVariable(paramName, arg); // 将参数绑定到变量名

            // 解析表达式
            Expression expression = SPEL_PARSER.parseExpression(expr);
            Object value = expression.getValue(context);

            // 将结果转换为字符串
            return value != null ? value.toString() : "";
        } catch (Exception e) {
            throw new IllegalArgumentException("SpEL解析失败: " + expr, e);
        }
    }
}
