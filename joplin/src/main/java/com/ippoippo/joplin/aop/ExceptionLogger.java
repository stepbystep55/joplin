package com.ippoippo.joplin.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
public class ExceptionLogger {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * コントローラー用エラーログを出力します。
	 * @param joinPoint
	 * @param e
	 * @throws Throwable
	 */
	@AfterThrowing(pointcut="execution(public * com.ippoippo.joplin.controller..*(..))", throwing = "e")
	public void contollerLogging(JoinPoint joinPoint, Throwable e) throws Throwable {
		logging(joinPoint, e);
	}

	private void logging(JoinPoint joinPoint, Throwable e) throws Throwable{
		logger.error(e.toString(), e);
		throw e;
	}
}
