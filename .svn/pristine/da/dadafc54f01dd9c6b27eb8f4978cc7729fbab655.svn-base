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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import net.jofm.FixedMappingException;

/**
 * @author Chunyun Zhao
 * @since 1.0
 */
public class DateFormat extends Format {
	public static String DEFAULT_FORMAT = "MMddyyyy";
	
	public DateFormat(Pad pad, char padWith, int length, String format) {
		super(pad, padWith, length, format);
		if ( format == null ) {
			//Default to 'MMddyyyy'
			format = DEFAULT_FORMAT;
		}
	}

	@Override
	protected String doFormat(Object value) {
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		if ( value instanceof Date ) {
			return formatter.format((Date)value);
		} else if ( value instanceof Calendar ) {
			return formatter.format(((Calendar)value).getTime());
		}
		throw new FixedMappingException("Unable to format instance of " + value.getClass().getName() + " using " + this.getClass().getName());
	}

	@Override
	protected Object doParse(String value, Class<?> destinationClazz) {
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		Date parsedDate = null;
		try {
			parsedDate = formatter.parse(value);
		} catch (ParseException e) {
			throw new FixedMappingException("Unable to parse the date string '" + value + "' using pattern " + format);
		}
		
		if ( destinationClazz.equals(Calendar.class) ) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(parsedDate);
			return calendar;
		}
		
		if ( destinationClazz.equals(Date.class)) {
			return parsedDate;
		}
		
		if ( destinationClazz.equals(java.sql.Date.class)) {
			return new java.sql.Date(parsedDate.getTime());
		}
		
		throw new FixedMappingException("Unable to parse the data to type " + destinationClazz.getName() + " using " + this.getClass().getName());
	}

}
