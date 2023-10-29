package com.crif.contact.etl.upload.aop;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import com.crif.contact.etl.upload.config.PropertyHandler;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * The Class LoggingAspect.
 */
@Aspect
@Component
public class LoggingAspect {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LogManager.getLogger(LoggingAspect.class);

	/** The Constant STR_DOT. */
	private static final String STR_DOT = ".";

	/** The Constant STR_SPACE. */
	private static final String STR_SPACE = " ";

	/** The Constant STR_COLON. */
	private static final String STR_COLON = ":: ";

	/** The Constant STR_MS. */
	private static final String STR_MS = " ms";

	/** The Constant STR_ETL_UPLOAD_INFO. */
	private static final String STR_ETL_UPLOAD_INFO = "EtlUploadController - Spring Boot Logging AOP EXAMPLE - Execution time of ";

	/** The env. */
	@Autowired
	private Environment env;

	/**
	 * Around Log method execution time.
	 *
	 * @param proceedingJoinPoint the proceeding join point
	 * @return the object
	 * @throws Throwable the throwable
	 */
	public Object logMethodExecutionTime(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getStaticPart().getSignature();

		final StopWatch stopWatch = new StopWatch();

		// calculate method execution time
		stopWatch.start();

		long startTime = System.currentTimeMillis();
		Object result = proceedingJoinPoint.proceed(proceedingJoinPoint.getArgs());
		long endtime = System.currentTimeMillis();
		stopWatch.stop();

		// Log method execution time
		LOGGER.info(new StringBuilder(STR_ETL_UPLOAD_INFO)
				.append(String.valueOf(methodSignature.getDeclaringType().getSimpleName())).append(STR_DOT)
				.append(methodSignature.getName()).append(STR_SPACE).append(methodSignature.getParameterNames())
				.append(STR_COLON).append(endtime - startTime).append(STR_MS));

		Long methodExecutionTime = stopWatch.getTotalTimeMillis();

		Long configureProp = env.getProperty(PropertyHandlerConstants.maxExecutionTime) == null ? 0L
				: Long.parseLong(env.getProperty(PropertyHandlerConstants.maxExecutionTime));
		LOGGER.debug("The condition satisfied with configured time = [{}]", configureProp);
		if (methodExecutionTime > configureProp) {
			fetchRequestPayloads(proceedingJoinPoint);
		}

		return result;
	}

	/**
	 * Fetch request payloads.
	 *
	 * @param proceedingJoinPoint the proceeding join point
	 */
	private void fetchRequestPayloads(ProceedingJoinPoint proceedingJoinPoint) {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
		objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

		try {

			if (proceedingJoinPoint.getArgs().length > 0) {
				for (int j = 0; j < proceedingJoinPoint.getArgs().length; j++) {
					LOGGER.info("The request parameters of ProceedingJoinPoint method .......................[{}] ",
							objectMapper.writeValueAsString(proceedingJoinPoint.getArgs()[j]));
				}
			}

		} catch (JsonProcessingException e) {
			LOGGER.error("The error is +++++ [{}]", e.getMessage());
		}
	}
}
