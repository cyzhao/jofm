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
import net.jofm.annotations.FixedField;


/**
 * Captures mapping metadata for a fixed field.
 * 
 * @author Chunyun Zhao
 * @since 1.0
 */
public class FixedFieldMetaData extends FieldMetaData {
	private String identification;
	private FixedMetaData fixedFieldMetaData;
	
	public static FixedFieldMetaData fromField(java.lang.reflect.Field field, FormatConfig formatConfig) {
		FixedField fixedFieldAnnotation = field.getAnnotation(FixedField.class);
		
		FixedFieldMetaData fixedFieldMetaData = fromFixedFieldAnnotation(fixedFieldAnnotation, field.getType(), field.getName(), formatConfig);
		fixedFieldMetaData.setField(field);
		
		return fixedFieldMetaData;
	}
	
	public static FixedFieldMetaData fromFixedFieldAnnotation(FixedField fixedFieldAnnotation,
			Class<?> fieldType, String name, FormatConfig formatConfig) {
		FixedFieldMetaData fixedFieldMetaData = new FixedFieldMetaData();

		fixedFieldMetaData.setPosition(fixedFieldAnnotation.position());
		fixedFieldMetaData.setRequired(fixedFieldAnnotation.required());
		fixedFieldMetaData.setIgnored(fixedFieldAnnotation.ignore());
		
		FixedMetaData fixedMetaData = FixedMetaData.fromFixedClass(fieldType, formatConfig);
		fixedFieldMetaData.setFixedFieldMetaData(fixedMetaData);
		fixedFieldMetaData.setLength(fixedFieldAnnotation.identification().length() + fixedMetaData.getMinLength());
		fixedFieldMetaData.setIdentification(fixedFieldAnnotation.identification());
		
		return fixedFieldMetaData;
	}
	
	public FixedFieldMetaData() {
	}
	
	public FixedMetaData getFixedFieldMetaData() {
		return fixedFieldMetaData;
	}
	
	public void setFixedFieldMetaData(FixedMetaData fixedFieldMetaData) {
		this.fixedFieldMetaData = fixedFieldMetaData;
	}

	public String getIdentification() {
		return identification;
	}

	public void setIdentification(String identification) {
		this.identification = identification;
	}
}
