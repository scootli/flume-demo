package org.apache.flume.interceptor;

import java.util.List;
import java.util.regex.Pattern;

import org.apache.flume.Context;
import org.apache.flume.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;

import static org.apache.flume.interceptor.logInfoInterceptor.Constants.*;


public class logInfoInterceptor implements Interceptor{

	 private static final Logger logger = LoggerFactory
     .getLogger(logInfoInterceptor.class);
	 
	 private final String successInfo;
	 private final boolean preserveExisting;
	 
	 /**
	  * Only {@link RegexFilteringInterceptor.Builder} can build me
	 */
	 private logInfoInterceptor(String successInfo, boolean preserveExisting) {
	   this.successInfo = successInfo;
	   this.preserveExisting = preserveExisting;
	}
	
	public void initialize() {
		 //no-op
	}
	
	/**
	 * Returns the event if it passes the regular expression filter and null
	 * otherwise.
	 */
	public Event intercept(Event event) {
		String str = new String(event.getBody());
		Pattern patternInfo = Pattern.compile(".+\\-\\[INFO\\].+");
		Pattern patternSuccess = Pattern.compile("^.+" + successInfo + ".+$");
		Pattern patternWarn = Pattern.compile(".+\\-\\[WARN\\].+");
		Pattern patternError = Pattern.compile(".+\\-\\[ERROR\\].+");
		Pattern patternFatal = Pattern.compile(".+\\-\\[FATAL\\].+");
		
		String bodyStr = new String(event.getBody());
		if(preserveExisting){
			boolean successInfo = patternInfo.matcher(bodyStr).find() && 
				patternSuccess.matcher(bodyStr).find();
			boolean warnInfo = patternWarn.matcher(bodyStr).find();
			boolean errorInfo = patternError.matcher(bodyStr).find();
			boolean fatalInfo = patternFatal.matcher(bodyStr).find();
			//只保留warn.error,fatal和info中满足successInfo的信息
			if (successInfo || warnInfo || errorInfo || fatalInfo) {
		        return event;
			}
		}
		
		return null;
	}
	
	/**
	 * Returns the set of events which pass filters, according to
	 * {@link #intercept(Event)}.
	 * @param events
	 * @return
	*/
	public List<Event> intercept(List<Event> events) {
		List<Event> out = Lists.newArrayList();
	    for (Event event : events) {
	      Event outEvent = intercept(event);
	      if (outEvent != null) { out.add(outEvent); }
	    }
	    return out;
	}

	public void close() {
		 //no-op
	}
	
	
	/**
	   * Builder which builds new instance of the StaticInterceptor.
	   */
	  public static class Builder implements Interceptor.Builder {

	    private boolean preserveExisting;
	    private String successInfo;

	    public void configure(Context context) {
	      preserveExisting = context.getBoolean(PRESERVE, PRESERVE_DEFAULT);
		  successInfo = context.getString(SUCCESS, DEFAULT_SUCCESS);
	    }

	    public Interceptor build() {
	      logger.info(String.format(
	          "Creating logInfoInterceptor: successInfo=%s,preserveExisting=%s",
			  successInfo, preserveExisting));
	      return new logInfoInterceptor(successInfo, preserveExisting);
	    }
	  }
	
	public static class Constants {
	    public static String SUCCESS = "successInfo";
		public static final String DEFAULT_SUCCESS = "message Send successfully";
	    public static String PRESERVE = "preserveExisting";
	    public static boolean PRESERVE_DEFAULT = true;
	  }
}
