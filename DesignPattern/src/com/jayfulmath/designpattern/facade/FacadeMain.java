package com.jayfulmath.designpattern.facade;

import com.jayfulmath.designpattern.main.BasicExample;

/* ���ģʽ�����õ����������Ǿ�����Ѷ�Ӳ���Ĳ�����camera��flashlight���ȵ�
 * �Ĳ��������װ��engine�����棬��ȷ��UI�㣬ֻ��Ҫ��engine��ͨ�ţ�������Ҫ֪��
 * ����Ӳ���Ĳ�����
 * 
 * */
public class FacadeMain extends BasicExample {

	@Override
	public void startDemo() {
		// TODO Auto-generated method stub
		Engine mEngine = new Engine();
		mEngine.openFlashlight();
		mEngine.closeFlashlight();
		mEngine.openVideo();
		mEngine.closeVideo();
	}

}
