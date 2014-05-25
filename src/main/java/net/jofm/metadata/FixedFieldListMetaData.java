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
import net.jofm.annotations.FixedFieldList;

/**
 * @author Chunyun Zhao
 * @since 1.0
 */
public class FixedFieldListMetaData extends FieldListMetaData<FixedFieldMetaData>{
	public static FixedFieldListMetaData fromField(java.lang.reflect.Field field, FormatConfig formatConfig) {
		Class<?> componentType = field.getType().getComponentType();
		if ( componentType == null ) {
			throw new FixedMappingException("Can not apply @FixedFieldList to a non-array type.");
		}
		
		FixedFieldList fixedFieldListAnnotation = field.getAnnotation(FixedFieldList.class);
		
		if ( fixedFieldListAnnotation.counterField().length() == 0 && fixedFieldListAnnotation.fixedField().identification().length() == 0 ) {
			throw new FixedMappingException("The @FixedFieldList field '" + field.getName() + "' must have either a valid counter or identification of which length is greater than 0.");
		}
		
		FixedFieldListMetaData fixedFieldListMetaData = new FixedFieldListMetaData();
		
		fixedFieldListMetaData.setField(field);
		fixedFieldListMetaData.setPosition(fixedFieldListAnnotation.position());
		fixedFieldListMetaData.setRequired(fixedFieldListAnnotation.required());
		fixedFieldListMetaData.setIgnored(fixedFieldListAnnotation.ignore());
		fixedFieldListMetaData.setComponentType(componentType);
		
		fixedFieldListMetaData.setLength(fixedFieldListAnnotation.counterField().length());
		
		fixedFieldListMetaData.setCounterFieldMetaData(PrimitiveFieldMetaData.fromFieldAnnotation(fixedFieldListAnnotation.counterField(), int.class, "counter", formatConfig));
		fixedFieldListMetaData.setFieldMetaData(FixedFieldMetaData.fromFixedFieldAnnotation(fixedFieldListAnnotation.fixedField(), componentType, field.getName(), formatConfig));	 
		
		return fixedFieldListMetaData;
	}
}
