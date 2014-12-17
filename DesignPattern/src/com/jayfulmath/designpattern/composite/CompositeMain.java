package com.jayfulmath.designpattern.composite;

import com.jayfulmath.designpattern.main.BasicExample;

public class CompositeMain extends BasicExample {

	@Override
	public void startDemo() {
		// TODO Auto-generated method stub
		CompositeCompany root = new CompositeCompany("�����ܹ�˾");
		root.Add(new HRDepartment("�ܹ�˾HR"));
		root.Add(new FianceDepartment("�ܹ�˾FD"));
		
		CompositeCompany comp = new CompositeCompany("�Ϻ��ֹ�˾");
		comp.Add(new HRDepartment("�Ϻ��ֹ�˾HR"));
		comp.Add(new FianceDepartment("�Ϻ��ֹ�˾FD"));
		root.Add(comp);
		
		CompositeCompany comp1 = new CompositeCompany("�ֶ����´�");
		comp1.Add(new HRDepartment("�ֶ����´�HR"));
		comp1.Add(new FianceDepartment("�ֶ����´�FD"));
		comp.Add(comp1);
		
		CompositeCompany comp2 = new CompositeCompany("����㳡���´�");
		comp2.Add(new HRDepartment("����㳡���´�HR"));
		comp2.Add(new FianceDepartment("����㳡���´�FD"));
		comp.Add(comp2);
		
		System.out.println("�ṹͼ��");
		root.Display(1);
		
		System.out.println("ְ��");
		root.LineofDuty();
	}

}
