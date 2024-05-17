package com.zp;

import com.zp.mapper.DicMapper;
import com.zp.model.entity.Dic;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
class ApplicationTests {

    @Resource
    private DicMapper dicMapper;

    @Test
    void jdbcTest() {
        List<Dic> dics = dicMapper.selectList(null);
        System.out.println(dics.toString());
        Dic dic = new Dic();
        dic.setCode("222");
        dicMapper.insert(dic);
    }

    // @Test
    // void jdbcTest1(){
    //     // 创建一个Mock的List，模拟从数据库获取数据
    //     List<Dic> mockDics = Mockito.mock(List.class);
    //     when(dicMapper.selectList(null)).thenReturn(mockDics);
    //
    //     // 创建一个Mock的Dic对象，设置mock的属性
    //     Dic mockDic = Mockito.mock(Dic.class);
    //     when(mockDic.getCode()).thenReturn("222");
    //
    //     // 调用测试方法，但不执行真实的insert操作
    //     dicMapper.insert(mockDic);
    //
    //     // 检查mock是否被正确调用
    //     Mockito.verify(dicMapper).selectList(null);
    //     Mockito.verifyNoMoreInteractions(dicMapper);
    // }

}
