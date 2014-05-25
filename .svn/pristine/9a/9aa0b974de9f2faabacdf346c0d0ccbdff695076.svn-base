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

import java.lang.reflect.Constructor;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import net.jofm.FixedMappingException;
import net.jofm.FormatConfig;
import net.jofm.annotations.Field;
import net.jofm.format.BooleanFormat;
import net.jofm.format.DateFormat;
import net.jofm.format.DefaultFormat;
import net.jofm.format.EnumFormat;
import net.jofm.format.Format;
import net.jofm.format.NumberFormat;
import net.jofm.format.StringFormat;
import net.jofm.format.Format.Pad;

/**
 * Captures mapping metadata for a primitive field.
 * 
 * @author Chunyun Zhao
 * @since 1.0
 */
public class PrimitiveFieldMetaData extends FieldMetaData {
	private Format formatter;
	
	public static PrimitiveFieldMetaData fromField(java.lang.reflect.Field field, FormatConfig formatConfig) {
		Field fieldAnnotation = field.getAnnotation(Field.class);
		
		PrimitiveFieldMetaData fieldMetaData = fromFieldAnnotation(fieldAnnotation, field.getType(), field.getName(), formatConfig);
		
		fieldMetaData.setField(field);
		
		return fieldMetaData;		
	}
	
	public static PrimitiveFieldMetaData fromFieldAnnotation(Field fieldAnnotation, Class<?> fieldType, String fieldName, FormatConfig formatConfig) {
		PrimitiveFieldMetaData fieldMetaData = new PrimitiveFieldMetaData();
		
		fieldMetaData.setPosition(fieldAnnotation.position());
		fieldMetaData.setRequired(fieldAnnotation.required());
		fieldMetaData.setIgnored(fieldAnnotation.ignore());
		
		fieldMetaData.setLength(fieldAnnotation.length());
		fieldMetaData.setFormatter(createFormatter(fieldAnnotation, fieldType, fieldName, formatConfig));
		
		return fieldMetaData;		
	}
	
	protected PrimitiveFieldMetaData() {
	}

	public Format getFormatter() {
		return formatter;
	}

	public void setFormatter(Format formatter) {
		this.formatter = formatter;
	}

	private static Format createFormatter(Field fieldAnnotation, Class<?> fieldType, String fieldName, FormatConfig formatConfig) {
		String defaultFormat = "";
		Class<? extends Format> formatterClazz = null;
		if ( Date.class.isAssignableFrom(fieldType) || Calendar.class.isAssignableFrom(fieldType) ) {
			defaultFormat = formatConfig.getDateFormat();
			formatterClazz = DateFormat.class;
		} else if ( String.class.isAssignableFrom(fieldType) ) {
			formatterClazz = StringFormat.class;
		} else if ( fieldType.equals(BigDecimal.class) ) {
			defaultFormat = formatConfig.getBigDecimalFormat();
			formatterClazz = NumberFormat.class;
		} else if ( fieldType.equals(Short.class) || fieldType.equals(short.class) ) {
			defaultFormat = formatConfig.getShortFormat();
			formatterClazz = NumberFormat.class;
		} else if ( fieldType.equals(Integer.class) || fieldType.equals(int.class) ) {
			defaultFormat = formatConfig.getIntFormat();
			formatterClazz = NumberFormat.class;
		} else if ( fieldType.equals(Long.class) || fieldType.equals(long.class) ) {
			defaultFormat = formatConfig.getLongFormat();
			formatterClazz = NumberFormat.class;
		} else if ( fieldType.equals(Float.class) || fieldType.equals(float.class) ) {
			defaultFormat = formatConfig.getFloatFormat();
			formatterClazz = NumberFormat.class;
		} else if ( fieldType.equals(Double.class) || fieldType.equals(double.class) ) {
			defaultFormat = formatConfig.getDoubleFormat();
			formatterClazz = NumberFormat.class;
		} else if ( fieldType.equals(Boolean.class) || fieldType.equals(boolean.class) ) {
			defaultFormat = formatConfig.getBooleanFormat();
			formatterClazz = BooleanFormat.class;
		} else if ( fieldType.isEnum() ) {
			formatterClazz = EnumFormat.class;
		}
		
		if ( fieldAnnotation.formatter() != null && !fieldAnnotation.formatter().equals(DefaultFormat.class)) {
			formatterClazz = fieldAnnotation.formatter();
		}
		
		if ( formatterClazz == null ) {
			throw new FixedMappingException("Unable to find formatter for field '" + fieldName + "' of type " + fieldType);
		}
		
		try {
			Constructor<?> c = formatterClazz.getConstructor(new Class[] {Pad.class, char.class, int.class, String.class });
			
			Pad pad = fieldAnnotation.pad() == Pad.DEFAULT ? formatConfig.getPad() : fieldAnnotation.pad();
			char padWith = fieldAnnotation.padWith() == Character.MIN_VALUE ? formatConfig.getPadWith() : fieldAnnotation.padWith();
			int length = fieldAnnotation.length();
			String format = StringUtils.isEmpty(fieldAnnotation.format()) ? defaultFormat : fieldAnnotation.format();
			
			return (Format) c.newInstance(new Object[] { pad, padWith, length, format });
		} catch (Exception e) {
			throw new FixedMappingException("Unable to instantiate the formatter " + formatterClazz + " for field '" + fieldName + "'", e);
		}
	}	
}