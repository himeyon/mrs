package mrs.app.login;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import mrs.MrsApplication;
import mrs.ThymeleafConfig;
import mrs.WebSecurityConfig;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = WebEnvironment.RANDOM_PORT,
        classes= {MrsApplication.class, WebSecurityConfig.class, ThymeleafConfig.class})
@AutoConfigureMockMvc
public class LoginControllerTest {
    
    //@MockBean For Autowired Service.

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void test_loginForm( ) throws Exception {
        this.mockMvc.perform(get("/loginForm"))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.view().name("login/loginForm"));
    }
}