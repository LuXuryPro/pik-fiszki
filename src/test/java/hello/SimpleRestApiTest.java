package hello;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class SimpleRestApiTest {
    private final MockMvc mockMvc = MockMvcBuilders.standaloneSetup(new SimpleRestApi())
            .build();

    @Test
    public void getall() throws Exception {
        mockMvc.perform(get("/getall"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("question").value("Tak"));
    }

    @Test
    public void getallError() throws Exception {
        mockMvc.perform(get("/getalle"))
                .andExpect(status().isNotFound());
    }

}