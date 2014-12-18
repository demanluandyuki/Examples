package com.jayfulmath.designpattern.bridge;

import com.jayfulmath.designpattern.main.BasicExample;

/*Bridge mode:����������źܶ෽��仯���仯�����ǿ��԰�ÿ���仯��������
 * ���ڲ�ͬ�ķ��棬���Խ�����Ӧ��
 * 
 * */

public class BridgeMain extends BasicExample {

	@Override
	public void startDemo() {
		/*1.highway 50km car
		 *2.cityWay 30km motorcycle 
		 *3.suburban 10km car 
		 * */	
		double h1 = getRunTime(Road.TYPE_HIGHWAY,Automobile.TYPE_CAR,50);
		double h2 = getRunTime(Road.TYPE_CITY,Automobile.TYPE_MOTORCYCLE,30);
		double h3 = getRunTime(Road.TYPE_SUBURBAN,Automobile.TYPE_CAR,10);
		
		System.out.println("Total time is "+(h1+h2+h3));
		
	}
	
	private double getRunTime(int roadtype,int cartype,int pathlength)
	{
		double hour = 0;
		Road _mRoad = RoadFactory.CreateRoad(roadtype, pathlength);
		_mRoad.set_mAuto(AutomobileFactory.CreateAutoMobile(cartype));
		hour = _mRoad.Run();
		System.out.println("***************************************");
		return hour;
	}
}
