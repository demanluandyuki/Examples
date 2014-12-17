package com.jayfulmath.designpattern.composite;

import com.jayfulmath.designpattern.main.BasicExample;

public class CompositeMain extends BasicExample {

	@Override
	public void startDemo() {
		// TODO Auto-generated method stub
		CompositeCompany root = new CompositeCompany("北京总公司");
		root.Add(new HRDepartment("总公司HR"));
		root.Add(new FianceDepartment("总公司FD"));
		
		CompositeCompany comp = new CompositeCompany("上海分公司");
		comp.Add(new HRDepartment("上海分公司HR"));
		comp.Add(new FianceDepartment("上海分公司FD"));
		root.Add(comp);
		
		CompositeCompany comp1 = new CompositeCompany("浦东办事处");
		comp1.Add(new HRDepartment("浦东办事处HR"));
		comp1.Add(new FianceDepartment("浦东办事处FD"));
		comp.Add(comp1);
		
		CompositeCompany comp2 = new CompositeCompany("人民广场办事处");
		comp2.Add(new HRDepartment("人民广场办事处HR"));
		comp2.Add(new FianceDepartment("人民广场办事处FD"));
		comp.Add(comp2);
		
		System.out.println("结构图：");
		root.Display(1);
		
		System.out.println("职责：");
		root.LineofDuty();
	}

}
