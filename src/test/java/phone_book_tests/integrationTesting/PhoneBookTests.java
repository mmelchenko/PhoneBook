package phone_book_tests.integrationTesting;

import bk.springRS.PhoneBookConfig;
import bk.springRS.controller.ContactController;
import bk.springRS.entity.Contact;
import bk.springRS.request.ContactRequest;
import org.hibernate.annotations.SQLDelete;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import phone_book_tests.testConfig.TestConfig;
import phone_book_tests.util.TestUtil;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;

import static junit.framework.TestCase.assertNotNull;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(loader = AnnotationConfigWebContextLoader.class, classes = {TestConfig.class})
//@WebAppConfiguration
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
//@WebMvcTest
//@AutoConfigureMockMvc
public class PhoneBookTests {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mvc = MockMvcBuilders.webAppContextSetup(this.context).build();
        try {
            this.mvc.perform(post("/contacts/insert"));
        } catch (Exception e) {
            System.err.println("Exception during data pinching.");
            e.printStackTrace();
        }
    }
/*
    @Test
    public void test() throws Exception {
        this.mvc.perform(post("/contacts/insert")).andDo(print());
        this.mvc.perform(get("/contacts/all_contacts")).andDo(print());
    }
*/
    @Test
    public void createContactTest() throws Exception {
        ContactRequest sixth = new ContactRequest("John", "Doe", "666");

        String str = new String(TestUtil.convertToJsonBytes(sixth), StandardCharsets.UTF_8);
        System.out.println("######################" + str);

        this.mvc.perform(post("/contacts/create_or_update")
                //.header("Content-Type", TestUtil.contentType)
                .param("update", "false")
                .contentType(TestUtil.contentType)
                .content(TestUtil.convertToJsonBytes(sixth)))
        .andDo(print());
/*
        MediaType MEDIA_TYPE_JSON_UTF8 = new MediaType("application", "json", java.nio.charset.Charset.forName("UTF-8"));
        MockHttpServletRequestBuilder request = post("/contacts/create_or_update?update=false");
        request.content(TestUtil.convertToJsonBytes(sixth));
        request.accept(MEDIA_TYPE_JSON_UTF8);
        request.contentType(MEDIA_TYPE_JSON_UTF8);
        mvc.perform(request)
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)
                );
*/
        this.mvc.perform(get("/contacts/all_contacts")).andDo(print());
    }

    @Test
    public void updateContactTest() {
        //TODO
    }

    @Test
    public void deleteContactTest() {
        //TODO
    }
}
