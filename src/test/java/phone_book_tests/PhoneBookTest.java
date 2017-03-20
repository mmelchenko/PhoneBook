package phone_book_tests;

import bk.springRS.PhoneBookConfig;
import bk.springRS.controller.ContactController;
import bk.springRS.request.ContactRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(loader = AnnotationConfigWebContextLoader.class, classes = {PhoneBookConfig.class})
@WebAppConfiguration
public class PhoneBookTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ContactController contactController;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
    }

    @Test
    public void createOrUpdateTest() throws Exception {
        ContactRequest sixth = new ContactRequest("John", "Doe", "666");

        this.mockMvc.perform(MockMvcRequestBuilders.get("/contacts//all_contacts").accept(MediaType.TEXT_PLAIN)).andExpect(status().isOk());
    }

}
