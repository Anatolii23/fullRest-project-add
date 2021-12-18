package com.example.payroll;

import com.example.payroll.rest.controller.CustomerController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ControllerTests {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private CustomerController customerController;

    @Test
    public void testCustomerController() throws Exception {
       this.mockMvc.perform(get("/customers"))
               .andDo(print())
               .andExpect(status().isOk());
    }

}
