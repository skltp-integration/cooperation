package se.skltp.cooperation.web.rest.v1;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import se.skltp.cooperation.CooperationApplication;
import se.skltp.cooperation.domain.ConnectionPoint;
import se.skltp.cooperation.repository.ConnectionPointRepository;
import se.skltp.cooperation.web.rest.v1.controller.ConnectionPointController;
import se.skltp.cooperation.web.rest.v1.dto.ConnectionPointDTO;

import javax.annotation.PostConstruct;

import java.util.Arrays;
import java.util.Collections;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
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
public class ConnectionPointControllerTest {

    @Mock
    private ConnectionPointRepository connectionPointRepositoryMock;
    @Mock
    private DozerBeanMapper mapperMock;
    @InjectMocks
    ConnectionPointController uut;

    private ConnectionPoint cp1;
    private ConnectionPoint cp2;
    private MockMvc mockMvc;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(uut).build();
    }


    @Test
    public void getAll_shouldReturnAll() throws Exception {

        ConnectionPoint cp1 = new ConnectionPoint();
        ConnectionPoint cp2 = new ConnectionPoint();
        ConnectionPointDTO dto1 = new ConnectionPointDTO();
        dto1.setId(1L);
        dto1.setPlatform("dt01.platform");
        dto1.setEnvironment("dto1.environment");
        ConnectionPointDTO dto2 = new ConnectionPointDTO();
        dto2.setId(2L);
        dto2.setPlatform("dt02.platform");
        dto2.setEnvironment("dto2.environment");

        when(connectionPointRepositoryMock.findAll()).thenReturn(Arrays.asList(cp1, cp2));
        when(mapperMock.map(cp1, ConnectionPointDTO.class)).thenReturn(dto1);
        when(mapperMock.map(cp2, ConnectionPointDTO.class)).thenReturn(dto2);

        // Get all the connectionPoints
        mockMvc.perform(get("/v1/connectionPoints"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$", hasSize(2)))
            .andExpect(jsonPath("$.[0].id").value(is(dto1.getId().intValue())))
            .andExpect(jsonPath("$.[0].platform").value(is(dto1.getPlatform())))
            .andExpect(jsonPath("$.[0].environment").value(is(dto1.getEnvironment())))
            .andExpect(jsonPath("$.[1].id").value(is(dto2.getId().intValue())))
            .andExpect(jsonPath("$.[1].platform").value(is(dto2.getPlatform())))
            .andExpect(jsonPath("$.[1].environment").value(is(dto2.getEnvironment())));

        verify(connectionPointRepositoryMock, times(1)).findAll();
        verifyNoMoreInteractions(connectionPointRepositoryMock);

    }

    @Test
    public void getAll_shouldReturnEmptyList() throws Exception {

        when(connectionPointRepositoryMock.findAll()).thenReturn(Collections.emptyList());

        // Get all the connectionPoints
        mockMvc.perform(get("/v1/connectionPoints"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void get_shouldReturnOne() throws Exception {
        ConnectionPoint cp1 = new ConnectionPoint();
        cp1.setId(1L);
        ConnectionPointDTO dto1 = new ConnectionPointDTO();
        dto1.setId(1L);
        dto1.setPlatform("dt01.platform");
        dto1.setEnvironment("dto1.environment");

        when(connectionPointRepositoryMock.findOne(cp1.getId())).thenReturn(cp1);
        when(mapperMock.map(cp1, ConnectionPointDTO.class)).thenReturn(dto1);

        // Get the cp1
        mockMvc.perform(get("/v1/connectionPoints/{id}", cp1.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(cp1.getId().intValue()))
            .andExpect(jsonPath("$.platform").value(cp1.getPlatform()))
            .andExpect(jsonPath("$.environment").value(cp1.getEnvironment()));
    }

    @Test
    public void get_shouldReturnNotFound() throws Exception {

        when(connectionPointRepositoryMock.findOne(anyLong())).thenReturn(null);

        mockMvc.perform(get("/v1/connectionPoints/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

}
