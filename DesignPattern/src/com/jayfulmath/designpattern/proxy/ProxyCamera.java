package com.jayfulmath.designpattern.proxy;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class ProxyCamera implements ICameraOperator {

	private static final String AssemblyName = "com.jayfulmath.designpattern.proxy";
	private static final String DefaultCamera = "YUUCamera";
	private String CameraString = null;
	BasicCamera camera = null;
	
	private static ProxyCamera _mInstance = null;
	
	public static ProxyCamera getInstance()
	{
		if(_mInstance == null)
		{
			_mInstance = new ProxyCamera();
		}
		return _mInstance;
	}
	
	
	public ProxyCamera() {
		IXmlParse parse = new XmlParse("src/com/jayfulmath/designpattern/proxy/CameraConfig.xml");
		CameraString = parse.parseXmlValue("camera");
		if(CameraString == null)
		{
			CameraString = DefaultCamera;
		}
		String className = AssemblyName + "." + CameraString;
		Class<?> c;
		try {
			c = Class.forName(className);
			Constructor<?> ct = c.getConstructor();
			camera = (BasicCamera) (ct.newInstance());
		} catch (ClassNotFoundException | NoSuchMethodException
				| SecurityException | InstantiationException
				| IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public boolean open() {
		boolean result = false;
		if (camera != null) {
			result = camera.open();
		}
		return result;
	}

	@Override
	public void close() {
		if (camera != null) {
			camera.close();
		}
	}

}
