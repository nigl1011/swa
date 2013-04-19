package de.shop.util;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

@Log
@Interceptor
public class LogInterceptor {

	public LogInterceptor() {
		// TODO Auto-generated constructor stub
	}

	@AroundInvoke
	public Object log(InvocationContext ic) throws Exception {
		return null;
	}

}
