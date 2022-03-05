#1.spring事务原理

基于spring aop的动态代理(cglib)--->AopProxy--->AbstractAutoProxyCreactor-->wrapIfNecessary
1.查询系统的所有Advisor的Bean，如果是顶级接口Advisor，默认为能够增强此Bean，如果是PointCutAdvisor 要用PointCut进行方法匹配
2.适配AspectJ语法，查询系统所有的@Aspect标记的组件，同样是做了过滤的
3.获取两面两者的Advisor，使用AnnotationAwareOrderComparator排序
4.AopProxy生成的代理对象 包含了系统能够增强此类的所有Advisor，也包含了TargetSource
使用cglib动态代理(MethodInterceptor植入代理逻辑)，调用代理方法的时候确认代理链


publicMethodsOnly代码设置为true

org.springframework.transaction.interceptor.AbstractFallbackTransactionAttributeSource.computeTransactionAttribute
javax.transaction.Transactional
javax.ejb.TransactionAttribute
org.springframework.transaction.annotation.Transactional

    org.springframework.transaction.interceptor.TransactionAttributeSourcePointcut
	@Override
	public boolean matches(Method method, Class<?> targetClass) {
		TransactionAttributeSource tas = getTransactionAttributeSource();
		return (tas == null || tas.getTransactionAttribute(method, targetClass) != null);
	}

分为两个过程：
1.匹配需要使用事务的方法且解析事务注解 得到TransactionAttribute
2.代理对象调用方法时，获取到相应的MethodInterceptor 代理逻辑 TransactionInterceptor
org.springframework.transaction.annotation.SpringTransactionAnnotationParser.parseTransactionAnnotation
    org.springframework.transaction.interceptor.RuleBasedTransactionAttribute
    
    
事务代理逻辑
从事务管理器获取事务，将开启事务(将自动提交设置为false)
入口
org.springframework.transaction.interceptor.TransactionAspectSupport.createTransactionIfNecessary
第一次开启事务 会将connection绑定到当前线程，也会将TransactionInfo绑定到当前线程
同一个线程内 如果继续需要事务 会根据事务传播来决定是否新建事务等操作
如果存在事务，按照事务传播行为处理



org.springframework.transaction.interceptor.DefaultTransactionAttribute.rollbackOn,默认情况下是RuntimeException ERROR才会滚事务
事务完成 提交事务
事务失败 匹配rollbackOn 匹配成功则回滚事务，否则提交事务

代理对象持有原对象的引用
#1 spring事务总结
1.事务方法内部调用会失效
2.事务和线程绑定、连接和线程绑定
3.事务传播