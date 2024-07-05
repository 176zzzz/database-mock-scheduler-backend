package com.zp;

import static org.assertj.core.api.Assertions.assertThat;

import com.zp.common.exception.ServiceException;
import com.zp.core.generators.DataGenerator;
import com.zp.mapper.DicMapper;
import com.zp.mapper.TargetMapper;
import com.zp.model.entity.Dic;
import com.zp.model.entity.JobColumnStrategy;
import com.zp.model.entity.JobInfo;
import com.zp.model.enums.MockDataRuleEnum;
import com.zp.service.DicService;
import com.zp.service.JobInfoService;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ApplicationTests {

    @Resource
    private DicMapper dicMapper;

    @Resource
    private JobInfoService jobInfoService;

    @Resource
    private TargetMapper cuteMapper;

    @Resource
    private DicService dicService;

    @Resource
    private DataGenerator dataGenerator;

    @Test
    void jdbcTest() {
        Dic dic = dicMapper.selectById(2);
        if(dic == null){
            System.out.println("为空");
        }

    }

    @Test
    void EnumTest() throws ServiceException {
        String valueById = dicService.getValueById(1L);
        System.out.println(valueById);
    }

    @Test
    void MPGetOneTest(){
        JobInfo byCode = jobInfoService.getByCode("test_01",true);
        System.out.println(byCode.getDescription());
    }

    @Test
    void cuteMapperTest(){
        cuteMapper.insertBySql("insert into example_target(column1,column2) values ('固定值',3)");
    }

    @Test
    void testDic() throws ServiceException {
        final JobColumnStrategy jobColumnStrategy = new JobColumnStrategy();
        jobColumnStrategy.setDicId(1L);
        jobColumnStrategy.setValueType(MockDataRuleEnum.DIC.getCode());
        String value = dataGenerator.getValue(jobColumnStrategy, null);
        System.out.print(value);

        List<String> list = new ArrayList<>();
        list.add("xxx");
        list.add("xxx2");
        assertThat(value).isIn(list);
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
