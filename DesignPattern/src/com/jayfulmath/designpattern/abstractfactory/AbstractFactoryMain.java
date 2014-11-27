package com.jayfulmath.designpattern.abstractfactory;

import com.jayfulmath.designpattern.main.BasicExample;

/*抽象方法：
 *数据库需要做各种更换，但是客户界面逻辑并不需要做任何修改。也就是客户端不关心数据库逻辑的实现。
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
