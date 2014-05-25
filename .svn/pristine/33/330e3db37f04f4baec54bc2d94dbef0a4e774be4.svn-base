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

import net.jofm.FixedMappingException;
import net.jofm.FormatConfig;
import net.jofm.annotations.FieldList;

/**
 * Captures the metadata for attributes annotated with @Fields.
 * 
 * @author Chunyun Zhao
 * @since 1.0
 */
public class PrimitiveFieldListMetaData extends FieldListMetaData<PrimitiveFieldMetaData> {
	public static PrimitiveFieldListMetaData fromField(java.lang.reflect.Field field, FormatConfig formatConfig) {
		Class<?> componentType = field.getType().getComponentType();
		if ( componentType == null ) {
			throw new FixedMappingException("Can not apply @FieldList to a non-array type.");
		}
		
		FieldList fieldListAnnotation = field.getAnnotation(FieldList.class);
		
		PrimitiveFieldListMetaData fieldListMetaData = new PrimitiveFieldListMetaData();
		
		fieldListMetaData.setField(field);
		fieldListMetaData.setPosition(fieldListAnnotation.position());
		fieldListMetaData.setRequired(fieldListAnnotation.required());
		fieldListMetaData.setRequired(fieldListAnnotation.ignore());
		fieldListMetaData.setComponentType(componentType);
		
		fieldListMetaData.setLength(fieldListAnnotation.counterField().length());
		
		fieldListMetaData.setCounterFieldMetaData(PrimitiveFieldMetaData.fromFieldAnnotation(fieldListAnnotation.counterField(), int.class, "counter", formatConfig));	 
		fieldListMetaData.setFieldMetaData(PrimitiveFieldMetaData.fromFieldAnnotation(fieldListAnnotation.field(), componentType, field.getName(), formatConfig));	 
		
		return fieldListMetaData;
	}
}
