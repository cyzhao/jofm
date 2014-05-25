/*
 * Copyright 2008 (C) Chunyun Zhao(Chunyun.Zhao@gmail.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *	http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.jofm.format;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * The abstract <code>Format</code> implementation. It should be extended by all the concrete <code>Format</code> 
 * implementations. <code>Format</code> is used to map the Java primitive field value to and from fixed length
 * data based on the field level metadata.
 * 
 * @author Chunyun Zhao
 * @since 1.0
 */
public abstract class Format {
	private Log logger = LogFactory.getLog(Format.class);
	
	public static enum Pad {LEFT, RIGHT, DEFAULT};
	
	public static char DEFAULT_PADWITH = ' ';
	public static Pad DEFAULT_PAD = Pad.RIGHT;
	
	protected Pad pad;
	protected char padWith;
	protected int length;
	protected String format;
	
	public Format(Pad pad, char padWith, int length, String format) {
		if ( pad == Pad.DEFAULT ) {
			this.pad = DEFAULT_PAD;
		} else {
			this.pad = pad;
		}
		
		if ( padWith == Character.MIN_VALUE ) {
			this.padWith = DEFAULT_PADWITH;
		} else {
			this.padWith = padWith;
		}
		
		this.length = length;
		this.format = format;
	}
	
	public final String format(Object value) {
		if ( value == null ) {
			return StringUtils.repeat(String.valueOf(padWith), length);
		}
		String formattedValue = doFormat(value);
		
		if ( formattedValue.length() < length ) {
			if ( pad == Pad.LEFT ) {
				return StringUtils.leftPad(formattedValue, length, padWith);
			} else if ( pad == Pad.RIGHT ) { 
				return StringUtils.rightPad(formattedValue, length, padWith);
			} 
		} else if ( formattedValue.length() > length ) {
			if ( logger.isWarnEnabled() ) {
				logger.warn("The length of formatted value '" + formattedValue + "' is greater than the length(" + length +") of the field. It is trimmed to match the length.");
			}
			return formattedValue.substring(0, length);
		}
		
		return formattedValue;
	}
	
	public final Object parse(String value, Class<?> destinationClazz) {
		if ( value == null ) return null;
		
		value = value.trim();
		if ( pad == Pad.RIGHT ) {
			value = trimRightPad(value);
		} else {
			value = trimLeftPad(value);
		}
		
		if ( value.length() == 0 ) {
			return null;
		}
		
		return doParse(value, destinationClazz);
	}
	
	
	/**
	 * Format the value as String. The implementation should perform format ONLY and 
	 * DOES NOT need to:
	 * 
	 * 	- Check null.
	 * 	- Pad the formatted value based on length.
	 *  - Cut formatted value to match the length
	 */
	protected abstract String doFormat(Object value);
	/**
	 * Parse the value as Object. The implemenation should perform parse ONLY and
	 * DOES NOT need to:
	 * 
	 * 	- Check null.
	 * 	- Trim the padding
	 */
	protected abstract Object doParse(String value, Class<?> destinationClazz);
	 
	private String trimLeftPad(String data) {
		if (data == null || data.length() ==0) {
			return data;
		}
		StringBuilder builder = new StringBuilder(data);
		while (builder.length() > 0 && builder.charAt(0) == padWith) {
			builder.deleteCharAt(0);
		}
		return builder.toString();
	}

	private String trimRightPad(String data) {
		if (data == null || data.length() ==0) {
			return data;
		}
		StringBuilder builder = new StringBuilder(data);
		while (builder.length() > 0 && builder.charAt(builder.length() - 1) == padWith) {
			builder.deleteCharAt(builder.length() - 1);
		}
		return builder.toString();
	}
}
