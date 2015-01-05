package com.jayfulmath.designpattern.experssion;

import java.util.HashMap;
import java.util.Stack;

public class Calculater {
	// ����ı��ʽ
	private Experssion expression;

	// ���캯������,������
	public Calculater(String expStr) {
		
		// ����һ����ջ������������Ⱥ�˳��
		Stack<Experssion> stack = new Stack<Experssion>();
		
		// ���ʽ���Ϊ�ַ�����
		char[] charArray = expStr.toCharArray();
		System.out.println("expStr:"+expStr+charArray.length);
		// ����
		Experssion left = null;
		Experssion right = null;

		for (int i = 0; i < charArray.length; i++) {
			System.out.println("charArray[i]:"+charArray[i]+"I:"+i);
			switch (charArray[i]) {
			case '+': // �ӷ�
				// �ӷ�����ŵ���ջ��
				left = stack.pop();
				right = new VarExperssion(String.valueOf(charArray[++i]));
				stack.push(new AddExperssion(left, right));
				break;
			case '-':
				left = stack.pop();
				right = new VarExperssion(String.valueOf(charArray[++i]));
				stack.push(new SubExperssion(left, right));
				break;
			default: // ��ʽ�еı���
				stack.push(new VarExperssion(String.valueOf(charArray[i])));
			}
		}

		// ���������׳���
		this.expression = stack.pop();

	}

	// ��ʼ����
	public int run(HashMap<String, Integer> var) {
		return this.expression.interpreter(var);
	}
}
