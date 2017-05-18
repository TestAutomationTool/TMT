/**
 * 
 */
package com.nielsen.engineering.wse;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * @author bhadraiah.kovuri
 * 
 */
public class ProcesslogGenerator {

	public static void Logger(Object Message) {

		try {

			Logger logger = Logger.getLogger(ProcesslogGenerator.class);
			logger.info(Message);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void updateLog4jConfiguration(String logFile) {
		Properties props = new Properties();
		try {
			InputStream configStream = ProcesslogGenerator.class
					.getResourceAsStream("/log4j.properties");
			props.load(configStream);
			configStream.close();
		} catch (IOException e) {
			System.out.println("Errornot laod configuration file ");
		}
		props.setProperty("log4j.appender.file.File", logFile);
		Logger.getRootLogger().getAppender("log4j.appender.file");
		LogManager.resetConfiguration();
		PropertyConfigurator.configure(props);
		
	}

}
