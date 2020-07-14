/**
 * Java properties manipulation test
*/
package com.file.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.jupiter.api.Test;

import com.file.Properties;

/**
 * @author Remisson dos Santos Silva
 * @since 2020-07-14 11:50
 *
 */
class PropertiesTest
{
	@Test
	void testPropFound()
	{
		String PARAM = new Properties("test").getProperty("PARA_AHA_FF");
		assertNotNull(PARAM);
		assertEquals("https://ttt.com.br/a?bb=1",PARAM);
	}

	@Test
	void testPropNotFound()
	{
		String PARAM = new Properties("test").getProperty("PARA_AHA_OO");
		assertNull(PARAM);
	}
}
