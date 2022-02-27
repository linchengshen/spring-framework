package com.gitlab.spring.advisor;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class MyMethodInterceptor implements MethodInterceptor {

	public static final MethodInterceptor INSTANCE = new MyMethodInterceptor();
	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		System.out.println(invocation);
		return invocation.proceed();
	}
}
