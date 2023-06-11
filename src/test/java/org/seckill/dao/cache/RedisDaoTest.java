/**
 * 
 */
package org.seckill.dao.cache;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.dao.SeckillDao;
import org.seckill.entity.Seckill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author zzgwh
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
//告诉junit 在加载springIOC容器时去加载spring配置文件spring-dao.xml
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class RedisDaoTest {

	private long id = 1;
	
	@Autowired
    private RedisDao redisDao;

    @Autowired
    private SeckillDao seckillDao;

    @Test
    public void seckill() throws Exception {
        // get and put
        Seckill seckill = redisDao.getSeckill(id);
        if(null == seckill){
            seckill = seckillDao.queryById(id);
            if(seckill != null){
                String result = redisDao.putSeckill(seckill);
                System.out.println(result);
                seckill = redisDao.getSeckill(id);
                System.out.println(seckill);
            }
        }
    }

}
