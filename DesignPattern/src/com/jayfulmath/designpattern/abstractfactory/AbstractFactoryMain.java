package com.jayfulmath.designpattern.abstractfactory;

import com.jayfulmath.designpattern.main.BasicExample;

/*���󷽷���
 *���ݿ���Ҫ�����ָ��������ǿͻ������߼�������Ҫ���κ��޸ġ�Ҳ���ǿͻ��˲��������ݿ��߼���ʵ�֡�
 * 
 * 
 * 
 * */

public class AbstractFactoryMain extends BasicExample {

	@Override
	public void startDemo() {
		// TODO Auto-generated method stub
		User user = new User();
		Department department = new Department();
		IFactory factory = new AccessFactory();
		IUser iu = factory.CreateUser();
		iu.insertUser(user);
		iu.getUser(1);
		
		IDepartment id = factory.CreateDepartment();
		id.insertDepartment(department);
		id.getDepartment(1);
	}

}
