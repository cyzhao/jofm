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
package net.jofm;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.jofm.format.Format.Pad;
import net.jofm.metadata.FieldMetaData;
import net.jofm.metadata.FixedFieldListMetaData;
import net.jofm.metadata.FixedFieldMetaData;
import net.jofm.metadata.FixedMetaData;
import net.jofm.metadata.PrimitiveFieldListMetaData;
import net.jofm.metadata.PrimitiveFieldMetaData;

/**
 * The default implementation of <code>FixedMapper</code>.
 * 
 * @author Chunyun Zhao
 * @since 1.0
 */
public class DefaultFixedMapper implements FixedMapper {
	private static Log log = LogFactory.getLog(DefaultFixedMapper.class);
	
	/**
	 * Caches the metadata for Fixed classes.
	 */
	private ConcurrentMap<Class<?>, FixedMetaData> metadataCache = new ConcurrentHashMap<Class<?>, FixedMetaData>();
	private FormatConfig formatConfig = new FormatConfig();
	
	public void setPad(Pad pad) {
		formatConfig.setPad(pad);
	}

	public void setPadWith(char padWith) {
		formatConfig.setPadWith(padWith);
	}

	public void setBooleanFormat(String booleanFormat) {
		formatConfig.setBooleanFormat(booleanFormat);
	}

	public void setDateFormat(String dateFormat) {
		formatConfig.setDateFormat(dateFormat);
	}

	public void setShortFormat(String shortFormat) {
		formatConfig.setShortFormat(shortFormat);
	}

	public void setIntFormat(String intFormat) {
		formatConfig.setIntFormat(intFormat);
	}

	public void setLongFormat(String longFormat) {
		formatConfig.setLongFormat(longFormat);
	}

	public void setFloatFormat(String floatFormat) {
		formatConfig.setFloatFormat(floatFormat);
	}

	public void setDoubleFormat(String doubleFormat) {
		formatConfig.setDoubleFormat(doubleFormat);
	}

	public void setBigDecimalFormat(String bigDecimalFormat) {
		formatConfig.setBigDecimalFormat(bigDecimalFormat);
	}

	public String marshall(Object bean) {
		try {
			return toFixed(bean, getMetadata(bean.getClass()));
		} catch ( FixedMappingException fme ) {
			throw fme;
		} catch (Exception e) {
			throw new FixedMappingException("Unable to marshall fixed object due to: " + e.getMessage(), e);
		}
	}

	public Object unmarshall(String line, Class<?> beanClazz) {
		try {
			Object result = beanClazz.newInstance();
			fromFixed(new StringBuilder(line), result, getMetadata(beanClazz));
			return result;
		} catch (FixedMappingException fme) { 
			throw fme;
		} catch (Exception e) {
			throw new FixedMappingException("Unable to unmarshall fixed object due to: " + e.getMessage(), e);
		}
	}

	protected String toFixed(Object sourceObject, FixedMetaData fixedMetadata) throws Exception {
		StringBuilder line = new StringBuilder();
		for (FieldMetaData fieldMetaData : fixedMetadata) {
			Object value = PropertyUtils.getProperty(sourceObject, fieldMetaData.getField().getName());
			
			if ( value == null && fieldMetaData.isRequired() ) {
				throw new FixedMappingException("Field '" + fieldMetaData.getField().getName() + "' of " + sourceObject.getClass() + " is required.");
			}
			
			if ( fieldMetaData instanceof FixedFieldMetaData ) {
				toFixedField(value, line, (FixedFieldMetaData)fieldMetaData, fieldMetaData.getField().getName());
			} else if ( fieldMetaData instanceof PrimitiveFieldMetaData ) {
				toPrimitiveField(value, line, (PrimitiveFieldMetaData)fieldMetaData);
			} else if ( fieldMetaData instanceof PrimitiveFieldListMetaData) {
				toPrimitiveFieldList(value, line, (PrimitiveFieldListMetaData)fieldMetaData);
			} else if ( fieldMetaData instanceof FixedFieldListMetaData) {
				toFixedFieldList(value, line, (FixedFieldListMetaData)fieldMetaData);
			}
		}
		return line.toString();
	}

	protected void fromFixed(StringBuilder line, Object destinationObject, FixedMetaData fixedMetadata) throws Exception {
		if ( line.length() < fixedMetadata.getMinLength() ) {
			throw new FixedMappingException("The length of data must be greater than or equal to " + fixedMetadata.getMinLength() + " for Fixed class " + destinationObject.getClass().getName());
		}
		
		for (FieldMetaData fieldMetaData : fixedMetadata )  {
			if ( fieldMetaData instanceof FixedFieldMetaData ) {
				fromFixedField(destinationObject, line, (FixedFieldMetaData)fieldMetaData, fieldMetaData.getField().getType(), fieldMetaData.getField().getName());
			} else if ( fieldMetaData instanceof PrimitiveFieldMetaData ) {
				fromPrimitiveField(destinationObject, line, (PrimitiveFieldMetaData)fieldMetaData);
			} else if ( fieldMetaData instanceof PrimitiveFieldListMetaData) {
				fromPrimitiveFieldList(destinationObject, line, (PrimitiveFieldListMetaData)fieldMetaData);
			} else if ( fieldMetaData instanceof FixedFieldListMetaData) {
				fromFixedFieldList(destinationObject, line, (FixedFieldListMetaData)fieldMetaData);
			}
		}
	}	

	private void toPrimitiveField(Object value, StringBuilder line, PrimitiveFieldMetaData primitiveFieldMetadata) throws Exception {
		String formatted = primitiveFieldMetadata.getFormatter().format(value);
		
		if (log.isDebugEnabled()) {
			log.debug("Getting field: " + primitiveFieldMetadata.getField().getName() + ", value:"
					+ value + ", formatted:" + formatted);
		}
		
		line.append(formatted);
	}
	
	private void toPrimitiveFieldList(Object value, StringBuilder line, PrimitiveFieldListMetaData primitiveFieldListMetadata) throws Exception {
		Object[] valueArray = (Object[])value;
		
		String formattedCounter = primitiveFieldListMetadata.getCounterFieldMetaData().getFormatter().format(valueArray.length);
		line.append(formattedCounter);
		
		if ( valueArray != null ) {
			for (Object valueElement :  valueArray ) {
				String formattedField = primitiveFieldListMetadata.getFieldMetaData().getFormatter().format(valueElement);
				if (log.isDebugEnabled()) {
					log.debug("Getting field: " + primitiveFieldListMetadata.getField().getName() + ", value:"
							+ value + ", formatted:" + formattedField);
				}
				line.append(formattedField);
			}
		}
	}
	
	private void toFixedFieldList(Object value, StringBuilder line, FixedFieldListMetaData fixedFieldMetaData) throws Exception {
		Object[] valueArray = (Object[])value;
		
		PrimitiveFieldMetaData counterFieldMetaData = fixedFieldMetaData.getCounterFieldMetaData();
		if ( counterFieldMetaData.getLength() > 0) {
			line.append(counterFieldMetaData.getFormatter().format(valueArray.length));
		}
		
		if ( valueArray != null ) {
			for ( Object valueElement: valueArray ) {
				toFixedField(valueElement, line, fixedFieldMetaData.getFieldMetaData(), fixedFieldMetaData.getField().getName());
			}
		}
	}	

	private void toFixedField(Object value, StringBuilder line, FixedFieldMetaData fixedFieldMetadata, String fieldName) throws Exception {
		if ( value == null ) return;
		
		line.append(fixedFieldMetadata.getIdentification());
		
		String formatted = toFixed(value, fixedFieldMetadata.getFixedFieldMetaData());
		if (log.isDebugEnabled()) {
			log.debug("Getting field: " + fieldName + ", value:" + value + ", formatted:" + formatted);
		}			
		line.append(formatted);
	}

	private void fromPrimitiveField(Object destinationObject,StringBuilder line, PrimitiveFieldMetaData primitiveFieldMetadata)
			throws Exception {
		int length = primitiveFieldMetadata.getLength();
		String data = read(line, length);
		
		Object parsed = primitiveFieldMetadata.getFormatter().parse(data, primitiveFieldMetadata.getField().getType());
		
		setProperty(destinationObject, primitiveFieldMetadata.getField().getName(), data, parsed, primitiveFieldMetadata.isIgnored());
	}
	

	private void fromPrimitiveFieldList(Object destinationObject,
			StringBuilder line, PrimitiveFieldListMetaData primitiveFieldListMetadata) throws Exception {
		PrimitiveFieldMetaData counterFieldMetaData = primitiveFieldListMetadata.getCounterFieldMetaData();
		int counterLength = counterFieldMetaData.getLength();
		
		String counterData = read(line, counterLength);
		
		int counter = (Integer)counterFieldMetaData.getFormatter().parse(counterData, int.class);
		
		PrimitiveFieldMetaData fieldMetaData = primitiveFieldListMetadata.getFieldMetaData();
		int fieldLength = fieldMetaData.getLength();
		
		Object array = Array.newInstance(primitiveFieldListMetadata.getComponentType(), counter);
		
		StringBuilder readData = new StringBuilder(); 
		for ( int i = 0; i < counter; i++ ) {
			String fieldData = read(line, fieldLength);
			readData.append(fieldData);
			Array.set(array, i, fieldMetaData.getFormatter().parse(fieldData, primitiveFieldListMetadata.getComponentType()));
		}
		
		setProperty(destinationObject, primitiveFieldListMetadata.getField().getName(), readData.toString(), array, primitiveFieldListMetadata.isIgnored());
	}
	
	private void fromFixedField(Object destinationObject,StringBuilder line, FixedFieldMetaData fixedFieldMetadata, Class<?> fieldType, String fieldName) throws Exception {
		String savedLine = line.toString();
		Object fieldValue = fromFixedField(line, fixedFieldMetadata, fieldType);
		
		String readData = savedLine.substring(0, savedLine.length() - line.length());
		setProperty(destinationObject, fixedFieldMetadata.getField().getName(), readData, fieldValue, fixedFieldMetadata.isIgnored());
	}
	
	private Object fromFixedField(StringBuilder line, FixedFieldMetaData fixedFieldMetadata, Class<?> fieldType) throws Exception {
		String fixedFieldIdentification = fixedFieldMetadata.getIdentification();
		if ( !line.toString().startsWith(fixedFieldIdentification)) {
			if ( fixedFieldMetadata.isRequired() ) {
				throw new FixedMappingException("The data doesn't start with '" + fixedFieldIdentification + "' for required fixed field '" + fixedFieldMetadata.getField().getName() + "'");
			} else {
				//Do nothing if it is an optional fixed field.
				return null;
			}
		} else {
			line.delete(0, fixedFieldIdentification.length());
			Object fieldValue = fieldType.newInstance();
			fromFixed(line, fieldValue, fixedFieldMetadata.getFixedFieldMetaData());
			return fieldValue;
		}		
	}	

	private void fromFixedFieldList(Object destinationObject, StringBuilder line, FixedFieldListMetaData fixedFieldListMetaData) throws Exception {
		String savedLine = line.toString();
		
		PrimitiveFieldMetaData counterFieldMetaData = fixedFieldListMetaData.getCounterFieldMetaData();
		int counterLength = counterFieldMetaData.getLength();
		Object[] arrayFieldValue = null;
		if ( counterLength > 0 ) {
			String counterData = read(line, counterLength);
			int counter = (Integer)counterFieldMetaData.getFormatter().parse(counterData, int.class);
			arrayFieldValue = (Object[])Array.newInstance(fixedFieldListMetaData.getComponentType(), counter);
			for ( int i = 0; i < counter; i++ ) {
				Object fieldValue = fromFixedField(line, fixedFieldListMetaData.getFieldMetaData(), fixedFieldListMetaData.getComponentType());
				Array.set(arrayFieldValue, i, fieldValue);
			}
		} else {
			List<Object> objectList = new ArrayList<Object>();

			Object fieldValue = null;
			do { 
				fieldValue = fromFixedField(line, fixedFieldListMetaData.getFieldMetaData(), fixedFieldListMetaData.getComponentType());
				
				if ( fieldValue != null ) {
					objectList.add(fieldValue);
				}
			} while ( fieldValue != null);
			
			arrayFieldValue = (Object[])Array.newInstance(fixedFieldListMetaData.getComponentType(), objectList.size());
			arrayFieldValue = objectList.toArray(arrayFieldValue);
		}
		
		String readData = savedLine.substring(0, savedLine.length() - line.length());
		setProperty(destinationObject, fixedFieldListMetaData.getField().getName(), readData, arrayFieldValue, fixedFieldListMetaData.isIgnored());
	}

	private void setProperty(Object destinationObject,
			String fieldName,
			String data,
			Object value, boolean ignored) throws IllegalAccessException,
			InvocationTargetException, NoSuchMethodException {
		if ( value != null && !ignored) {
			if (log.isDebugEnabled()) {
				log.debug("Setting field: " + fieldName + ", fixed data:" + data + ", value:" + value);
			}			
			PropertyUtils.setProperty(destinationObject, fieldName, value);
		}
	}
	
	
	protected FixedMetaData getMetadata(Class<?> clazz) {
		if ( metadataCache.containsKey(clazz) ) {
			return metadataCache.get(clazz);
		} else {
			FixedMetaData fixedMetaData = FixedMetaData.fromFixedClass(clazz, formatConfig);
			metadataCache.putIfAbsent(clazz, fixedMetaData);
			return fixedMetaData;
		}
	}
	
	/**
	 * Reads the data from a <code>StringBuilder<code> from the beginning, and removes the data as well.
	 */
	private String read(StringBuilder data, int length) {
		String result = data.substring(0, length);
		data.delete(0, length);
		return result;
	}
}
