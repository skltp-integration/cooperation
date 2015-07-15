package se.skltp.cooperation.web.rest.v1;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import se.skltp.cooperation.CooperationApplication;
import se.skltp.cooperation.domain.ConnectionPoint;
import se.skltp.cooperation.repository.ConnectionPointRepository;
import se.skltp.cooperation.web.rest.v1.controller.ConnectionPointController;

import javax.annotation.PostConstruct;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test class for the ConnectionPointController REST controller.
 *
 * @see ConnectionPointController
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CooperationApplication.class)
@WebAppConfiguration
@IntegrationTest
public class ConnectionPointControllerTest {

    @Autowired
    private ConnectionPointRepository connectionPointRepository;

    private MockMvc restConnectionPointMockMvc;

    private ConnectionPoint cp1;
    private ConnectionPoint cp2;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ConnectionPointController uut = new ConnectionPointController();
        ReflectionTestUtils.setField(uut, "connectionPointRepository", connectionPointRepository);
        this.restConnectionPointMockMvc = MockMvcBuilders.standaloneSetup(uut).build();
    }

    @Before
    public void initTest() {
        cp1 = new ConnectionPoint();
        cp1.setPlatform("cp1.platform");
        cp1.setEnvironment("cp1.environment");
        cp2 = new ConnectionPoint();
        cp2.setPlatform("cp2.platform");
        cp2.setEnvironment("cp2.environment");
    }

    @Test
    @Transactional
    public void getAll_shouldReturnAll() throws Exception {
        // Initialize the database
        connectionPointRepository.saveAndFlush(cp1);
        connectionPointRepository.saveAndFlush(cp2);

        // Get all the connectionPoints
        restConnectionPointMockMvc.perform(get("/v1/connectionPoints"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$", hasSize(2)))
            .andExpect(jsonPath("$.[0].id").value(is(cp1.getId().intValue())))
            .andExpect(jsonPath("$.[0].platform").value(is(cp1.getPlatform())))
            .andExpect(jsonPath("$.[0].environment").value(is(cp1.getEnvironment())));
    }
    @Test
    @Transactional
    public void getAll_shouldReturnEmptyList() throws Exception {
        // Initialize the database
        connectionPointRepository.deleteAll();

        // Get all the connectionPoints
        restConnectionPointMockMvc.perform(get("/v1/connectionPoints"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    @Transactional
    public void get_shouldReturnOne() throws Exception {
        // Initialize the database
        connectionPointRepository.saveAndFlush(cp1);

        // Get the cp1
        restConnectionPointMockMvc.perform(get("/v1/connectionPoints/{id}", cp1.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(cp1.getId().intValue()))
            .andExpect(jsonPath("$.platform").value(cp1.getPlatform()))
            .andExpect(jsonPath("$.environment").value(cp1.getEnvironment()));
    }

    @Test
    @Transactional
    public void get_shouldReturnNotFound() throws Exception {
        connectionPointRepository.deleteAll();
        restConnectionPointMockMvc.perform(get("/v1/connectionPoints/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

}
