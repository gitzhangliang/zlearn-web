package com.zl;

import com.zl.event.EatPublisher;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author zl
 * @date 2019/5/30.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebApplication.class)
public class SpringEventTest {
    @Resource
    private EatPublisher eatPublisher;

    @Test
    public void testSelect() {
        eatPublisher.publishEatEvent("12");
    }

}
