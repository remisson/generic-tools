package com.file;
/**
 * Java properties manipulation
 */


import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;

import com.tools.StringUtils;

/**
 * @author Remisson dos Santos Silva
 * @since 2020-07-14 11:50
 *
 */
public class Properties
{
	public static final Map<String,String> PROP_CACHE = new HashMap<String,String>();

	private String fileName;

	public Properties(String fileName)
	{
		this.fileName = fileName;
	}

	public String getProperty(String propertyName)
	{
		if (StringUtils.isEmpty(propertyName))
			throw new IllegalArgumentException(
				"Invalid param (propertyName):"+propertyName);

		if(PROP_CACHE.containsKey(propertyName))
			return PROP_CACHE.get(propertyName);

		InputStream stream = null;
		try
		{
			stream = new ClassPathResource("/"+fileName+".properties").getInputStream();
			StringBuilder sb = new StringBuilder();
			sb.append(IOUtils.toString(stream));
			String[] a = sb.toString().split("\n");
			if(a == null || a.length <= 0)
				return null;
			for(int i=0;i<a.length;i++)
			{
				if(isPropertyLine(a[i]))
				{
					Object[] value = extractIfPropertyFound(propertyName,a[i]);
					if(value != null && value.length > 0 && (Boolean) value[0] == true)
					{
						String v = (String) value[1];
						PROP_CACHE.put(propertyName, v);
						return v;
					}
				}
			}
		}
		catch(Exception e)
		{
			throw new RuntimeException(e);
		}
		finally
		{
			IOUtils.closeQuietly(stream);
		}
		return null;
	}

	private boolean isPropertyLine(String line)
	{
		return !StringUtils.isEmpty(line) && line.contains("=") && '#' != line.charAt(0);
	}

	private Object[] extractIfPropertyFound(String propertyName, String line)
	{
		String replC = "__EQ_SUBS_CONST__";
		String [] a = line.replaceFirst("=", replC).split(replC);
		if(a != null && a.length > 0 && propertyName.trim()
			.equalsIgnoreCase(String.valueOf(a[0]).trim()))
			return new Object[]{true,String.valueOf(a[1])
				.replace("\r", "").replace("\n", "")};
		return new Object[] {false};
	}
}
