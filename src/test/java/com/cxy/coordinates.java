package com.cxy;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author 陈翔宇
 * @version 1.0.0
 * @ClassName coordinates.java
 * @Description 坐标计算
 * @createTime 2021年07月20日 09:35:00
 */
//该注解会启动springboot项目
//@RunWith(SpringRunner.class)
//只有该注解 不会启动springboot项目
@SpringBootTest
public class coordinates {

    @Test
    public void getCoordinates() {
//        int oldBlockX = 1;
        int oldBlockY = 1;
        int oldBlockZ = 1;
        int oldBlockX = (int) (Math.random() * 10) - 10;
//        int oldBlockY = (int) (Math.random() * 10);
//        int oldBlockZ = (int) (Math.random() * 10);

        int oldPersonX = 0, oldPersonY = 0, oldPersonZ = 0;

        int oldPersonDirectionX = 1;
        int oldPersonDirectionY = 0;
        int oldPersonDirectionZ = 0;


        int newPersonX = (int) (Math.random() * 10) - 10;
        int newPersonY = (int) (Math.random() * 10) - 10;
        int newPersonZ = (int) (Math.random() * 10) - 10;

        int newPersonDirectionX = -1;
        int newPersonDirectionY = 0;
        int newPersonDirectionZ = 0;


        int newBlockX = 0, newBlockY = 0, newBlockZ = 0;
        if (oldPersonDirectionX == newPersonDirectionX
                || oldPersonDirectionY == newPersonDirectionY
                || oldPersonDirectionZ == newPersonDirectionZ) {
            newBlockX = oldBlockX - oldPersonX + newPersonX;
            newBlockY = oldBlockY - oldPersonY + newPersonY;
            newBlockZ = oldBlockZ - oldPersonZ + newPersonZ;
        }

        if (oldPersonDirectionX == -newPersonDirectionX
                || oldPersonDirectionY == -newPersonDirectionY
                || oldPersonDirectionZ == -newPersonDirectionZ) {
            newBlockX = oldBlockX - oldPersonX + newPersonX;
            newBlockY = oldBlockY - oldPersonY + newPersonY;
            newBlockZ = oldBlockZ - oldPersonZ + newPersonZ;

            //旋转
            newBlockX = newPersonX - oldBlockX - oldPersonX;
            newBlockY = newPersonY - oldBlockY - oldPersonY;
            newBlockZ = newPersonZ - oldBlockZ - oldPersonZ;

        }
        System.out.println("newBlockX = " + newBlockX);
    }


}
