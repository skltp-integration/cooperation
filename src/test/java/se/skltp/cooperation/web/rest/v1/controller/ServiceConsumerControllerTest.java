package se.skltp.cooperation.web.rest.v1.controller;

import org.dozer.DozerBeanMapper;
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
import se.skltp.cooperation.domain.ServiceConsumer;
import se.skltp.cooperation.repository.ServiceConsumerRepository;
import se.skltp.cooperation.web.rest.v1.dto.ServiceConsumerDTO;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.Collections;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.xpath;

/**
 * Test class for the ServiceConsumerController REST controller.
 *
 * @author Peter Merikan
 * @see ServiceConsumerController
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CooperationApplication.class)
@WebAppConfiguration
public class ServiceConsumerControllerTest {

    @InjectMocks
    ServiceConsumerController uut;
    @Mock
    private ServiceConsumerRepository serviceConsumerRepositoryMock;
    @Mock
    private DozerBeanMapper mapperMock;
    private MockMvc mockMvc;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(uut).build();
    }


    @Test
    public void getAll_shouldReturnAllAsJson() throws Exception {

        ServiceConsumer c1 = new ServiceConsumer();
        ServiceConsumer c2 = new ServiceConsumer();
        ServiceConsumerDTO dto1 = new ServiceConsumerDTO();
        dto1.setId(1L);
        dto1.setDescription("dto1.description");
        dto1.setHsaId("dto1.hsaId");
        ServiceConsumerDTO dto2 = new ServiceConsumerDTO();
        dto2.setId(2L);
        dto2.setDescription("dto2.description");
        dto2.setHsaId("dto2.hsaId");

        when(serviceConsumerRepositoryMock.findAll()).thenReturn(Arrays.asList(c1, c2));
        when(mapperMock.map(c1, ServiceConsumerDTO.class)).thenReturn(dto1);
        when(mapperMock.map(c2, ServiceConsumerDTO.class)).thenReturn(dto2);

        // Get all serviceConsumers
        mockMvc.perform(get("/v1/serviceConsumers").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$", hasSize(2)))
            .andExpect(jsonPath("$.[0].id").value(is(dto1.getId().intValue())))
            .andExpect(jsonPath("$.[0].description").value(is(dto1.getDescription())))
            .andExpect(jsonPath("$.[0].hsaId").value(is(dto1.getHsaId())))
            .andExpect(jsonPath("$.[1].id").value(is(dto2.getId().intValue())))
            .andExpect(jsonPath("$.[1].description").value(is(dto2.getDescription())))
            .andExpect(jsonPath("$.[1].hsaId").value(is(dto2.getHsaId())));

        verify(serviceConsumerRepositoryMock, times(1)).findAll();
        verifyNoMoreInteractions(serviceConsumerRepositoryMock);

    }

    @Test
    public void getAll_shouldReturnAllAsXml() throws Exception {

        ServiceConsumer c1 = new ServiceConsumer();
        ServiceConsumer c2 = new ServiceConsumer();
        ServiceConsumerDTO dto1 = new ServiceConsumerDTO();
        dto1.setId(1L);
        dto1.setDescription("dto1.description");
        dto1.setHsaId("dto1.hsaId");
        ServiceConsumerDTO dto2 = new ServiceConsumerDTO();
        dto2.setId(2L);
        dto2.setDescription("dto2.description");
        dto2.setHsaId("dto2.hsaId");

        when(serviceConsumerRepositoryMock.findAll()).thenReturn(Arrays.asList(c1, c2));
        when(mapperMock.map(c1, ServiceConsumerDTO.class)).thenReturn(dto1);
        when(mapperMock.map(c2, ServiceConsumerDTO.class)).thenReturn(dto2);

        // Get all serviceConsumers
        mockMvc.perform(get("/v1/serviceConsumers").accept(MediaType.APPLICATION_XML))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_XML))
            .andExpect(xpath("/serviceConsumers/serviceConsumer[1]/id").string(is(dto1.getId().toString())))
            .andExpect(xpath("/serviceConsumers/serviceConsumer[1]/description").string(is(dto1.getDescription())))
            .andExpect(xpath("/serviceConsumers/serviceConsumer[1]/hsaId").string(is(dto1.getHsaId())))
            .andExpect(xpath("/serviceConsumers/serviceConsumer[2]/id").string(is(dto2.getId().toString())))
            .andExpect(xpath("/serviceConsumers/serviceConsumer[2]/description").string(is(dto2.getDescription())))
            .andExpect(xpath("/serviceConsumers/serviceConsumer[2]/hsaId").string(is(dto2.getHsaId())));

        verify(serviceConsumerRepositoryMock, times(1)).findAll();
        verifyNoMoreInteractions(serviceConsumerRepositoryMock);

    }

    @Test
    public void getAll_shouldReturnEmptyList() throws Exception {

        when(serviceConsumerRepositoryMock.findAll()).thenReturn(Collections.emptyList());
        // Get all serviceConsumers
        mockMvc.perform(get("/v1/serviceConsumers").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$", hasSize(0)));
    }


    @Test
    public void get_shouldReturnOneAsJson() throws Exception {
        ServiceConsumer c1 = new ServiceConsumer();
        c1.setId(1L);
        ServiceConsumerDTO dto1 = new ServiceConsumerDTO();
        dto1.setId(1L);
        dto1.setDescription("dto1.description");
        dto1.setHsaId("dto1.hsaId");

        when(serviceConsumerRepositoryMock.findOne(c1.getId())).thenReturn(c1);
        when(mapperMock.map(c1, ServiceConsumerDTO.class)).thenReturn(dto1);

        // Get the c1
        mockMvc.perform(get("/v1/serviceConsumers/{id}", c1.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(is(dto1.getId().intValue())))
            .andExpect(jsonPath("$.description").value(is(dto1.getDescription())))
            .andExpect(jsonPath("$.hsaId").value(is(dto1.getHsaId())));

    }

    @Test
    public void get_shouldReturnOneAsXml() throws Exception {
        ServiceConsumer c1 = new ServiceConsumer();
        c1.setId(1L);
        ServiceConsumerDTO dto1 = new ServiceConsumerDTO();
        dto1.setId(1L);
        dto1.setDescription("dto1.description");
        dto1.setHsaId("dto1.hsaId");

        when(serviceConsumerRepositoryMock.findOne(c1.getId())).thenReturn(c1);
        when(mapperMock.map(c1, ServiceConsumerDTO.class)).thenReturn(dto1);

        // Get the c1
        mockMvc.perform(get("/v1/serviceConsumers/{id}", c1.getId())
            .accept(MediaType.APPLICATION_XML))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_XML))
            .andExpect(xpath("/serviceConsumer/id").string(is(dto1.getId().toString())))
            .andExpect(xpath("/serviceConsumer/description").string(is(dto1.getDescription())))
            .andExpect(xpath("/serviceConsumer/hsaId").string(is(dto1.getHsaId())));

    }

    @Test
    public void get_shouldReturnNotFound() throws Exception {

        when(serviceConsumerRepositoryMock.findOne(anyLong())).thenReturn(null);

        mockMvc.perform(get("/v1/serviceConsumers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

}

