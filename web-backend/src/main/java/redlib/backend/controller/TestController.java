package redlib.backend.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redlib.backend.dto.TestDTO;
import redlib.backend.service.TestService;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @Autowired
    private TestService testService;

    @PostMapping("addRecord")
    public void addRecord(@RequestBody TestDTO testDTO) {
        testService.insert(testDTO);

    }
}
