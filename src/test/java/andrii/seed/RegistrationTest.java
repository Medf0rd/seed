package andrii.seed;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.MultiValueMap;

import java.util.HashMap;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
class RegistrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void contextLoad() throws Exception {
        this.mockMvc.perform(get("/registration"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Sign up")));
    }

    @Test
    public void correctRegistrationTest() throws Exception {
        this.mockMvc.perform(post("/registration")
                .param("username", "testUser")
                .param("password", "psw")
                .param("passwordConfirmation", "psw")
                .param("email", "hiwin86363@emailnube.com")
                .with(csrf()))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
    }

    @Test
    public void emptyFieldsRegistrationTest() throws Exception {
        this.mockMvc.perform(post("/registration")
                .param("username", "")
                .param("password", "")
                .param("passwordConfirmation", "")
                .param("email", "")
                .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Username cannot be empty")))
                .andExpect(content().string(containsString("Password cannot be empty")))
                .andExpect(content().string(containsString("Password confirmation cannot be empty")))
                .andExpect(content().string(containsString("Email cannot be empty")));
    }

    @Test
    @Sql(value = {"/create-user-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/create-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void userExistsRegistrationTest() throws Exception {
        this.mockMvc.perform(post("/registration")
                .param("username", "q")
                .param("password", "psw")
                .param("passwordConfirmation", "psw")
                .param("email", "hiwin86363@emailnube.com")
                .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("User exists!!")));
    }

    @Test
    public void passwordsConfirmationFailRegistrationTest() throws Exception {
        this.mockMvc.perform(post("/registration")
                .param("username", "q")
                .param("password", "psw")
                .param("passwordConfirmation", "psw2")
                .param("email", "hiwin86363@emailnube.com")
                .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Passwords don't match")));
    }

    @Test
    public void emailFailRegistrationTest() throws Exception {
        this.mockMvc.perform(post("/registration")
                .param("username", "q")
                .param("password", "psw")
                .param("passwordConfirmation", "psw")
                .param("email", "emailnube.com")
                .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Email is not correct")));
    }
}
