package redlib.backend.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import redlib.backend.dao.TestMapper;
import redlib.backend.dto.TestDTO;
import redlib.backend.model.Test;
import redlib.backend.service.TestService;

@Service
public class TestServiceImpl implements TestService {

    @Autowired
    private TestMapper testMapper;

    @Override
    public void insert(TestDTO testDTO) {
        Assert.hasText(testDTO.getName(), "名称不能为空");
        Assert.isTrue(testDTO.getAge() > 0, "年龄必须大于0");
        Test test = new Test();
        test.setAge(testDTO.getAge());
        test.setName(testDTO.getName());
        testMapper.insert(test);

    }
}
