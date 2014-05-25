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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.jofm.FixedMappingException;

import org.apache.commons.lang.StringUtils;

/**
 * @author Chunyun Zhao
 * @since 1.0
 */
public class BooleanFormat extends Format {
	public final static String DEFAULT_FORMAT = "Y|N";
	
	private String trueStr;
	private String falseStr;
	
	public BooleanFormat(Pad pad, char padWith, int length, String format) {
		super(pad, padWith, length, format);
		if ( StringUtils.isEmpty(format) ) {
			this.format = DEFAULT_FORMAT;
		}
		parseFormat(this.format);
	}

	@Override
	protected String doFormat(Object value) {
		if (((Boolean)value).booleanValue()) {
			return trueStr;
		} else {
			return falseStr;
		}
	}

	@Override
	protected Object doParse(String value, Class<?> destinationClazz) {
		if ( value.startsWith(trueStr) ) {
			return Boolean.TRUE;
		} else {
			return Boolean.FALSE;
		}
	}
	

	/**
	 * Parses the format and initializes trueStr and falseStr
	 */
	private void parseFormat(String format) {
		if ( format == null ) return;
		
		String regex = "(.*)\\|(.*)";
		Matcher matcher = Pattern.compile(regex).matcher(format);
		if ( matcher.matches() ) {
			trueStr = matcher.group(1);
			falseStr = matcher.group(2);
		} else {
			throw new FixedMappingException("The format for boolean field should match the pattern " + regex);
		}
	}	
}
