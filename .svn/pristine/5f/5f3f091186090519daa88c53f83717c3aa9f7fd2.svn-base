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

import java.math.BigDecimal;
import java.text.DecimalFormat;

import net.jofm.FixedMappingException;

import org.apache.commons.lang.StringUtils;

/**
 * @author Chunyun Zhao
 * @since 1.0
 */
public class NumberFormat extends Format {
	public NumberFormat(Pad pad, char padWith, int length, String format) {
		super(pad, padWith, length, format);
	}

	@Override
	protected String doFormat(Object value) {
		if ( StringUtils.isNotEmpty(format) ) {
			DecimalFormat formatter = new DecimalFormat(format);
			return formatter.format(value);
		} else {
			return value.toString();
		}
	}

	@Override
	protected Object doParse(String value, Class<?> destinationClazz) {
		Number result;
		try {
			if ( StringUtils.isNotEmpty(format) ) {
				DecimalFormat formatter = new DecimalFormat(format);
				result = formatter.parse(value);
			} else {
				result = Double.valueOf(value);
			}
		} catch (Exception pe) {
			throw new FixedMappingException("Unable to parse the value '" + value + "' to number with format '" + format + "'");
		}
		
		return convert(result, destinationClazz);
	}

	private Object convert(Number result, Class<?> destinationClazz) {
		if ( destinationClazz.equals(BigDecimal.class) ) {
			return new BigDecimal(result.doubleValue());
		}
		if ( destinationClazz.equals(Short.class) || destinationClazz.equals(short.class) ) {
			return new Short(result.shortValue());
		}
		if ( destinationClazz.equals(Integer.class) || destinationClazz.equals(int.class) ) {
			return new Integer(result.intValue());
		}
		if ( destinationClazz.equals(Long.class) || destinationClazz.equals(long.class) ) {
			return new Long(result.longValue());
		}
		if ( destinationClazz.equals(Float.class) || destinationClazz.equals(float.class) ) {
			return new Float(result.floatValue());
		}
		if ( destinationClazz.equals(Double.class) || destinationClazz.equals(double.class) ) {
			return new Double(result.doubleValue());
		}
		
		throw new FixedMappingException("Unable to parse the data to type " + destinationClazz.getName() + " using " + this.getClass().getName());
	}
}
