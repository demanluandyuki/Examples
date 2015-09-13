package com.joyfulmath.study.factory;

public class InterviewMethod implements IWorkMethod {

	@Override
	public void startWork() {
		work();
	}
	
	public void work() {
		String str = new String("abc");

		char[] ch = { 'a', 'b', 'c' };

		change(str, ch);

		System.out.print(str + " ");

		System.out.print(ch);
		short s1 = 1;
		s1 = (short) (s1 + 1);
	}

	public static void change(String str, char[] ch) {

		str = "gbc";

		ch[0] = 'g';
	}
}
