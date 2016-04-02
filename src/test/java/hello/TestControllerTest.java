package hello;

import java.nio.charset.Charset;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class TestControllerTest {
    private final MockMvc mockMvc = MockMvcBuilders.standaloneSetup(new TestController())
            .build();

    @Test
    public void testTestController() throws Exception {
        mockMvc.perform(get("/test"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(new MediaType("text", "html", Charset.forName("utf-8"))));
    }
}
