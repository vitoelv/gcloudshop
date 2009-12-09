/**
 * 
 */
package com.jcommerce.core.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author yli
 *
 */

@Retention(RetentionPolicy.RUNTIME)
public @interface IsPK {
	public String myclazz();
}
