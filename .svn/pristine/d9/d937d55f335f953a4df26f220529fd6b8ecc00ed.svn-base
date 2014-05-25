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

import net.jofm.FormatConfig;
import net.jofm.annotations.FieldList;
import net.jofm.annotations.FixedFieldList;

/**
 * @author Chunyun Zhao
 * @since 1.0
 */
public abstract class FieldListMetaData<T extends FieldMetaData> extends FieldMetaData {
	private PrimitiveFieldMetaData counterFieldMetaData;
	private T fieldMetaData;
	private Class<?> componentType;

	public static FieldListMetaData<?> fromField(java.lang.reflect.Field field, FormatConfig formatConfig) {
		if ( field.getAnnotation(FieldList.class) != null ) {
			return PrimitiveFieldListMetaData.fromField(field, formatConfig);
		} else if ( field.getAnnotation(FixedFieldList.class) != null ) {
			return FixedFieldListMetaData.fromField(field, formatConfig);
		}
		
		return null;
	}

	public PrimitiveFieldMetaData getCounterFieldMetaData() {
		return counterFieldMetaData;
	}

	public void setCounterFieldMetaData(PrimitiveFieldMetaData counterFieldMetaData) {
		this.counterFieldMetaData = counterFieldMetaData;
	}

	public T getFieldMetaData() {
		return fieldMetaData;
	}

	public void setFieldMetaData(T fieldMetaData) {
		this.fieldMetaData = fieldMetaData;
	}
	
	public Class<?> getComponentType() {
		return componentType;
	}

	public void setComponentType(Class<?> componentType) {
		this.componentType = componentType;
	}	
}
