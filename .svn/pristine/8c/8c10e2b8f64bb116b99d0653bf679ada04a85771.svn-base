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
import net.jofm.annotations.Field;
import net.jofm.annotations.FieldList;
import net.jofm.annotations.FixedField;
import net.jofm.annotations.FixedFieldList;


/**
 * Captures the basic information for field level metadata.
 * 
 * @author Chunyun Zhao
 * @since 1.0
 */
public abstract class FieldMetaData {
	private java.lang.reflect.Field field;
	private int position;
	private int length;
	private boolean required;
	private boolean ignored;

	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

	protected FieldMetaData() {
	}

	/**
	 * Returns <code>FieldMetaData</code> instance for a given <code>java.lang.reflect.Field</code>. 
	 * Returns null if the field is not annotated with Field or FixedField.
	 * @param formatConfig 
	 * 
	 * 
	 * @Field or
	 * @FixedField.
	 */
	public static FieldMetaData fromField(java.lang.reflect.Field field, FormatConfig formatConfig) {
		if (field.isAnnotationPresent(Field.class) ) {
			return PrimitiveFieldMetaData.fromField(field, formatConfig);
		} else if (field.isAnnotationPresent(FixedField.class) ) {
			return FixedFieldMetaData.fromField(field, formatConfig);
		} else if ( field.isAnnotationPresent(FieldList.class) || field.isAnnotationPresent(FixedFieldList.class) ) {
			return FieldListMetaData.fromField(field, formatConfig);
		}

		return null;
	}

	public java.lang.reflect.Field getField() {
		return field;
	}

	public void setField(java.lang.reflect.Field field) {
		this.field = field;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public boolean isIgnored() {
		return ignored;
	}

	public void setIgnored(boolean ignored) {
		this.ignored = ignored;
	}
}
