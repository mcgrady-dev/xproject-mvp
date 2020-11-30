//package com.mcgrady.common_core.utils;
//
//import android.view.View;
//
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Pointcut;
//import org.aspectj.lang.reflect.MethodSignature;
//
//import java.lang.reflect.Method;
//
///**
// * AOP方式实现防止按钮连续点击
// * Created by mcgrady on 2020/4/29.
// */
//@Aspect
//public class SingleClickAspect {
//    private static final long DEFAULT_TIME_INTERVAL = 5000;
//
//    /**
//     * 定义切点，标记切点为被{@link SingleClick}注解的方法
//     */
//    @Pointcut("execution(@com.mcgrady.common_core.utils.SingleClick * *(..))")
//    public void methodAnnotated() {}
//
//    /**
//     * 定义一个切面方法，包裹切点方法
//     * @param joinPoint
//     * @throws Throwable
//     */
//    @Around("methodAnnotated()")
//    public void aroundJoinPoint(ProceedingJoinPoint joinPoint) throws Throwable {
//        View view = null;
//        //获取方法的参数
//        for (Object arg : joinPoint.getArgs()) {
//            if (arg instanceof View) {
//                view = (View) arg;
//                break;
//            }
//        }
//
//        if (view == null) {
//            return;
//        }
//
//        //获取方法的注解
//        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
//        Method method = methodSignature.getMethod();
//        if (!method.isAnnotationPresent(SingleClick.class)) {
//            return;
//        }
//
//        SingleClick singleClick = method.getAnnotation(SingleClick.class);
//        if (!MultiClickUtils.isMultiClick(view, singleClick.value())) {
//            //执行原方法
//            joinPoint.proceed();
//        }
//    }
//}
