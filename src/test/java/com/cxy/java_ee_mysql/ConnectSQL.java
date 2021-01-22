package com.cxy.java_ee_mysql;

import java.sql.*;

/**
 * @author 陈翔宇
 * @version 1.0.0
 * @ClassName ConnectSQL.java
 * @Description
 * @createTime 2021年01月13日 17:26:00
 */
public class ConnectSQL {
	//初始化Connection对象
	private static Connection connection = null;
	//定义String类型的驱动名driver
	private static String driver = "com.mysql.cj.jdbc.Driver";
	//定义String类型的数据库url
	private static String url = "jdbc:mysql://localhost:3306/template_project?serverTimezone=GMT%2B8";
	//定义String类型的用户名user
	private static String user = "root";
	//定义String类型的密码password
	private static String password = "root";
	//初始化Statement对象
	private static Statement statement = null;
	//定义String类型的SQL语句
	private static String sql = "SELECT * FROM nation LIMIT 10";
	//初始化ResultSet对象
	private static ResultSet resultSet = null;
	//定义String类型的字段code
	private static Integer nationId = null;
	//定义String类型的字段name
	private static String nationName = null;
	//主函数
	public static void main(String[] args) {
		try {
			//加载驱动
			Class.forName(driver);
			//连接数据库
			connection = DriverManager.getConnection(url, user, password);
			//实例化statement对象
			statement = connection.createStatement();
			//执行查询
			resultSet = statement.executeQuery(sql);
			//展开结果集数据库
			while(resultSet.next()) {
				//通过字段搜索，获取code和name这一列的数据
				nationId = resultSet.getInt("nation_id");
				nationName = resultSet.getString("nation_name");
				System.out.println(nationId + ":" + nationName);
				//ABW:Aruba
				//AFG:Afghanistan
				//AGO:Angola
				//AIA:Anguilla
				//ALB:Albania
				//AND:Andorra
				//ANT:Netherlands Antilles
				//ARE:United Arab Emirates
				//ARG:Argentina
				//ARM:Armenia
			}
		} catch (ClassNotFoundException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
			}
			if(resultSet != null) {
				try {
					resultSet.close();
				} catch (SQLException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
			}
		}
	}
}