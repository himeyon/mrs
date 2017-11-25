package mrs.app.room;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import java.time.LocalDate;

import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import mrs.MrsApplication;
import mrs.ThymeleafConfig;
import mrs.WebSecurityConfig;
import mrs.domain.service.room.RoomService;

/**
 * @see <a href="https://qiita.com/nvtomo1029/items/911d359f89491a1da2ba">Spring Bootで認証を通さずにWebAPIの単体テストを行う方法</a>
 */
@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = WebEnvironment.RANDOM_PORT,
        classes= {MrsApplication.class, WebSecurityConfig.class, ThymeleafConfig.class})
@AutoConfigureMockMvc
public class RoomsControllerTestOk {
    
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext webAppContext;
    
    private SecurityContext securityContext;

    @Before
    public void before() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webAppContext)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
        Authentication authentication =
                new UsernamePasswordAuthenticationToken(
                        "dummyUser",
                        "dummyPassword",
                        AuthorityUtils.createAuthorityList("ADMIN"));
        securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(authentication);
        SecurityContextHolder.setContext(securityContext);
    }
    
    @After
    public void releaseAuthenticatedSetting() throws Exception{} {
        SecurityContextHolder.clearContext();
    }

    @MockBean
    private RoomService roomService;

    //@Test
    public void test_rooms() throws Exception {
        this.mockMvc.perform(get("/rooms"))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.view().name("room/listRooms"));
    }
    
    @Test
    public void test_rooms_日付指定() throws Exception {
        this.mockMvc.perform(get("/rooms/{date}", LocalDate.of(2017,10,20)))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("2017/10/20の会議室")))
        .andExpect(MockMvcResultMatchers.view().name("room/listRooms"));
    }
}