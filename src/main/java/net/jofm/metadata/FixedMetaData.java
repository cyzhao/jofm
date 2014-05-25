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
package net.jofm.metadata;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import net.jofm.FixedMappingException;
import net.jofm.FormatConfig;
import net.jofm.annotations.Fixed;

/**
 * Captures the mapping metadata for a Fixed class. 
 *  
 * @author Chunyun Zhao
 * @since 1.0
 */
public class FixedMetaData implements Iterable<FieldMetaData> {
	/**
	 * The mininum length of the fixed length data.
	 */
	private int minLength = 0;

	private Map<Integer, FieldMetaData> fieldsMetadata = new TreeMap<Integer, FieldMetaData>();

	private FixedMetaData() {
	}

	public static FixedMetaData fromFixedClass(Class<?> clazz, FormatConfig formatConfig) {
		validateFixedClazz(clazz);

		FixedMetaData fixedMetaData = new FixedMetaData();

		java.lang.reflect.Field[] fields = clazz.getDeclaredFields();
		int minLength = 0;
		for (java.lang.reflect.Field field : fields) {
			FieldMetaData fieldMetaData = FieldMetaData.fromField(field, formatConfig);
			if (fieldMetaData != null) {
				fixedMetaData.getFieldsMetadata().put(
						fieldMetaData.getPosition(), fieldMetaData);
				if (fieldMetaData instanceof FixedFieldMetaData) {
					if (fieldMetaData.isRequired()) {
						minLength += fieldMetaData.getLength();
					}
				} else {
					minLength += fieldMetaData.getLength();
				}
			}
		}
		fixedMetaData.setMinLength(minLength);

		return fixedMetaData;
	}

	private static void validateFixedClazz(Class<?> clazz) {
		if (!clazz.isAnnotationPresent(Fixed.class)) {
			throw new FixedMappingException(
					"The class "
							+ clazz
							+ " is not annotated with @Fixed, can not map it to fixed length data.");
		}
		
		try {
			clazz.getConstructor(new Class[0]);
		} catch (SecurityException e) {
			throw new FixedMappingException("The default constructor of Fixed " + clazz + " is not accessible.", e);
		} catch (NoSuchMethodException e) {
			throw new FixedMappingException("The public default constructor is not defined in Fixed " + clazz);
		}
	}

	public Map<Integer, FieldMetaData> getFieldsMetadata() {
		return fieldsMetadata;
	}

	public Iterator<FieldMetaData> iterator() {
		return getFieldsMetadata().values().iterator();
	}

	public int getMinLength() {
		return minLength;
	}

	public void setMinLength(int minLength) {
		this.minLength = minLength;
	}
}
