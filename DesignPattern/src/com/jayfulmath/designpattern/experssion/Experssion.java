package com.jayfulmath.designpattern.experssion;

import java.util.HashMap;

public abstract class Experssion {
	//������ʽ����ֵ,����var�е�keyֵ���ǹ�ʽ�еĲ�����valueֵ�Ǿ��������
	public abstract int interpreter(HashMap<String, Integer> var);
}
