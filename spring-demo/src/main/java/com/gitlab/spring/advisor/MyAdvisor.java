package com.gitlab.spring.advisor;

import org.aopalliance.aop.Advice;
import org.springframework.aop.Advisor;
import org.springframework.stereotype.Component;

/**
 * spring aop
 * 1.获取ioc容器的Advisor实例
 * 2.支持aspectJ(被@Aspect标记的ioc容器组件)
 * 3.获取系统所有的Advisor(根据一定方法匹配规则过滤，排序)
 * 4.创建cglib/JDK动态代理对象，包含了Advisors
 * 5.调用代理对象方法时，创建MethodInterceptor拦截器链进行调用
 */
@Component
public class MyAdvisor implements Advisor {
	@Override
	public Advice getAdvice() {
		return MyMethodInterceptor.INSTANCE;
	}

	@Override
	public boolean isPerInstance() {
		return false;
	}
}
