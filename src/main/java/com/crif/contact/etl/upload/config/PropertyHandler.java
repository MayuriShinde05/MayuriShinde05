package com.crif.contact.etl.upload.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

/**
 * The Class PropertyHandler.
 */
@ConfigurationProperties(prefix = "contact.grab")
@ConfigurationPropertiesScan
public class PropertyHandler {

	/** The max execution time. */
	private String maxExecutionTime;
	
	/** The package structure. */
	private String packageStructure;

	/**
	 * Gets the max execution time.
	 *
	 * @return the max execution time
	 */
	public String getMaxExecutionTime() {
		return maxExecutionTime;
	}

	/**
	 * Sets the max execution time.
	 *
	 * @param maxExecutionTime the new max execution time
	 */
	public void setMaxExecutionTime(String maxExecutionTime) {
		this.maxExecutionTime = maxExecutionTime;
	}

	/**
	 * Gets the package structure.
	 *
	 * @return the package structure
	 */
	public String getPackageStructure() {
		return packageStructure;
	}

	/**
	 * Sets the package structure.
	 *
	 * @param packageStructure the new package structure
	 */
	public void setPackageStructure(String packageStructure) {
		this.packageStructure = packageStructure;
	}

}
