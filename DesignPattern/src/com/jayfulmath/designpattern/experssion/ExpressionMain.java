package com.jayfulmath.designpattern.experssion;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

import com.jayfulmath.designpattern.main.BasicExample;

public class ExpressionMain extends BasicExample {

	@Override
	public void startDemo() {
		String expStr = "";;
		HashMap<String, Integer> var = null;
		try {
			expStr = getExpStr();
			var = getValue(expStr);
			System.out.println("var size:"+var.size());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Calculater cal = new Calculater(expStr);

		System.out.println("������Ϊ��" + expStr + "=" + cal.run(var));
	}

	// ��ñ��ʽ

	public static String getExpStr() throws IOException {

		System.out.print("��������ʽ��");

		return (new BufferedReader(new InputStreamReader(System.in)))
				.readLine();

	}

	// ���ֵӳ��

	public static HashMap<String, Integer> getValue(String exprStr)
			throws IOException {

		HashMap<String, Integer> map = new HashMap<String, Integer>();

		// �����м�������Ҫ����

		for (char ch : exprStr.toCharArray()) {

			if (ch != '+' && ch != '-') {

				// ����ظ�����������

				if (!map.containsKey(String.valueOf(ch))) {
					System.out.print("������" + ch + "��ֵ:");

					String in = (new BufferedReader(new InputStreamReader(
							System.in))).readLine();

					map.put(String.valueOf(ch), Integer.valueOf(in));

				}

			}

		}

		return map;

	}
}
