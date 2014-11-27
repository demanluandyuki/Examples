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
//		IFactory factory = new AccessFactory();
		IUser iu = DataAccessReflection.CreateUser();
		if(iu!=null)
		{
			iu.insertUser(user);
			iu.getUser(1);
		}
		
		IDepartment id = DataAccessReflection.CreateDepartment();
		if(id!=null)
		{
			id.insertDepartment(department);
			id.getDepartment(1);
		}

	}

}
