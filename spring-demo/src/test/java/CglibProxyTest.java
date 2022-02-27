import com.gitlab.spring.service.impl.HelloServiceImpl;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class CglibProxyTest {
	public static void main(String[] args) {
		final Object target = new HelloServiceImpl();
		HelloServiceImpl helloService = CglibProxyFactory.newProxy(HelloServiceImpl.class, new MethodInterceptor() {
			@Override
			public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
				System.out.println("before...");
				Object result = method.invoke(target, objects);
				System.out.println("after...");
				return result;
			}
		});
		helloService.sayHello("Taylor Swift");
	}

	static class CglibProxyFactory {
		static <T> T newProxy(Class<T> clazz, MethodInterceptor methodInterceptor) {
			Enhancer enhancer = new Enhancer();
			enhancer.setSuperclass(clazz);
			enhancer.setCallback(methodInterceptor);
			Object o = enhancer.create();
			return (T) o;
		}
	}
}
