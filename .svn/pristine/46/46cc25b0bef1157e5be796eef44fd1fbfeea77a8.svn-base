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

/**
 * This interface defines methods to map fixed length data to and from Java Beans.
 *  
 * @author Chunyun Zhao
 * @since 1.0
 */
public interface FixedMapper {
	/**
	 * Marshalls the java bean to fixed length data.
	 */
	public String marshall(Object bean);
	
	/**
	 * Unmarshalls the fixed length data to a Java Bean. 
	 */
	public Object unmarshall(String fixedLengthData, Class<?> beanClazz);
}
