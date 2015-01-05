package com.jayfulmath.designpattern.experssion;

import java.util.HashMap;
import java.util.Stack;

public class Calculater {
	// 定义的表达式
	private Experssion expression;

	// 构造函数传参,并解析
	public Calculater(String expStr) {
		
		// 定义一个堆栈，安排运算的先后顺序
		Stack<Experssion> stack = new Stack<Experssion>();
		
		// 表达式拆分为字符数组
		char[] charArray = expStr.toCharArray();
		System.out.println("expStr:"+expStr+charArray.length);
		// 运算
		Experssion left = null;
		Experssion right = null;

		for (int i = 0; i < charArray.length; i++) {
			System.out.println("charArray[i]:"+charArray[i]+"I:"+i);
			switch (charArray[i]) {
			case '+': // 加法
				// 加法结果放到堆栈中
				left = stack.pop();
				right = new VarExperssion(String.valueOf(charArray[++i]));
				stack.push(new AddExperssion(left, right));
				break;
			case '-':
				left = stack.pop();
				right = new VarExperssion(String.valueOf(charArray[++i]));
				stack.push(new SubExperssion(left, right));
				break;
			default: // 公式中的变量
				stack.push(new VarExperssion(String.valueOf(charArray[i])));
			}
		}

		// 把运算结果抛出来
		this.expression = stack.pop();

	}

	// 开始运算
	public int run(HashMap<String, Integer> var) {
		return this.expression.interpreter(var);
	}
}
