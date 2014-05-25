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
package net.jofm.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import net.jofm.format.DefaultFormat;
import net.jofm.format.Format;
import net.jofm.format.Format.Pad;

/**
 * Indicates a primitive field. 
 * 
 * @author Chunyun Zhao
 * @author John Gilbert
 * @since 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target( { ElementType.FIELD })
public @interface Field {
	/**
	 * Position relative to other field
	 * 
	 * @return
	 */
	int position();

	/**
	 * Length of the field in the file
	 * 
	 * @return
	 */
	int length();
	
	/**
	 * (Optional) Left or right pad the data.
	 */
	Pad pad() default Pad.DEFAULT;
	
	/**
	 * (Optional) Used to pad the data with.
	 */
	char padWith() default Character.MIN_VALUE;

	/**
	 * @return
	 */
	String format() default "";
	
	/**
	 * Whether the field is required. Set to false by default.
	 */
	boolean required() default false;
	
	/**
	 * Whether the field will be ignored. When the field is ignored,
	 * the value of the field won't be set in JavaBean.
	 * 
	 * Set to false by default.
	 */
	boolean ignore() default false;

	/**
	 * (Optional) Format class that implements java.text.Format
	 * 
	 * @return
	 */
	Class<? extends Format> formatter() default DefaultFormat.class;
}
