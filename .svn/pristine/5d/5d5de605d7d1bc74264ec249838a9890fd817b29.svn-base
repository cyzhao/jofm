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

import java.lang.reflect.Method;

import net.jofm.FixedMappingException;

/**
 * @author Chunyun Zhao
 * @since 1.0
 */
public class EnumFormat extends Format {
	public EnumFormat(Pad pad, char padWith, int length, String format) {
		super(pad, padWith, length, format);
	}

	@Override
	protected String doFormat(Object value) {
		return value.toString();
	}

	@Override
	protected Object doParse(String value, Class<?> destinationClazz) {
		if ( destinationClazz.isEnum() ) {
			try {
				Method valueOf = destinationClazz.getMethod("valueOf", new Class[] {String.class});
				return valueOf.invoke(null, value);
			} catch (Exception e) {
				throw new FixedMappingException("Unable to parse enum data for Enum " + destinationClazz + " with value '" + value + "'", e);
			}
		}
		
		throw new FixedMappingException("Unable to parse non-Enum data with EnumFormat.");
	}
}
