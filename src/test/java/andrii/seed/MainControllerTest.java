package andrii.seed;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.xpath;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@WithUserDetails("q")
@TestPropertySource("/application-test.properties")
@Sql(value = {"/create-user-before.sql", "/posts-list-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/posts-list-after.sql", "/create-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class MainControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void mainPageTest() throws Exception {
        this.mockMvc.perform(get("/main"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("//*[@id='navbarSupportedContent']/form[2]/a").string("q"));
    }

    @Test
    public void messageListTest() throws Exception {
        this.mockMvc.perform(get("/main"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("//*[@id='posts']/div").nodeCount(4));
    }

    @Test
    public void filterMessageTest() throws Exception {
        this.mockMvc.perform(get("/main").param("filter", "tag"))
                .andDo(print())
                .andExpect(xpath("//*[@id='posts']/div").nodeCount(2))
                .andExpect(xpath("//*[@id='posts']/div[@data-id=1]").exists())
                .andExpect(xpath("//*[@id='posts']/div[@data-id=4]").exists());
    }

    @Test
    public void filterNoMessageTest() throws Exception {
        this.mockMvc.perform(get("/main").param("filter", "new"))
                .andDo(print())
                .andExpect(xpath("//*[@id='posts']/div").nodeCount(0));
    }

    @Test
    public void addMessageToListTest() throws Exception {
        MockHttpServletRequestBuilder multipart = multipart("/main")
                .file("imageFile", "123".getBytes())
                .param("text", "Some text")
                .param("tagString", "new one")
                .with(csrf());

        this.mockMvc.perform(multipart)
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("//*[@id='posts']/div").nodeCount(5))
                .andExpect(xpath("//*[@id='posts']/div[@data-id=20]").exists())
                .andExpect(xpath("//*[@id='posts']/div[@data-id=20]/div/p").string("Some text"))
                .andExpect(xpath("//*[@id='posts']/div[@data-id=20]/div/span[2]/small").string("#new"))
                .andExpect(xpath("//*[@id='posts']/div[@data-id=20]/div/span[3]/small").string("#one"));
    }
}
