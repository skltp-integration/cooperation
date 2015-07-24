package se.skltp.cooperation.web.rest.v1.controller;

import com.mysema.query.types.Predicate;
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
import se.skltp.cooperation.domain.Cooperation;
import se.skltp.cooperation.repository.CooperationRepository;
import se.skltp.cooperation.web.rest.v1.dto.CooperationDTO;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.Collections;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNull;
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
 * Tests for {@link CooperationController}
 *
 * @author Peter Merikan
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CooperationApplication.class)
@WebAppConfiguration
public class CooperationControllerTest {

    @InjectMocks
    CooperationController uut;
    @Mock
    private CooperationRepository cooperationRepositoryMock;
    @Mock
    private DozerBeanMapper mapperMock;
    private MockMvc mockMvc;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(uut).build();
    }


    @Test
    public void getAllAsJson_shouldReturnAll() throws Exception {
        Cooperation c1 = new Cooperation();
        Cooperation c2 = new Cooperation();
        CooperationDTO dto1 = new CooperationDTO();
        dto1.setId(1L);
        CooperationDTO dto2 = new CooperationDTO();
        dto2.setId(2L);

        when(cooperationRepositoryMock.findAll()).thenReturn(Arrays.asList(c1, c2));
        when(mapperMock.map(c1, CooperationDTO.class)).thenReturn(dto1);
        when(mapperMock.map(c2, CooperationDTO.class)).thenReturn(dto2);

        mockMvc.perform(get("/v1/cooperations").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$", hasSize(2)))
            .andExpect(jsonPath("$.[0].id").value(is(dto1.getId().intValue())))
            .andExpect(jsonPath("$.[1].id").value(is(dto2.getId().intValue())));

        verify(cooperationRepositoryMock, times(1)).findAll();
        verifyNoMoreInteractions(cooperationRepositoryMock);

    }

    @Test
    public void testGetAllAsXml_shouldReturnAll() throws Exception {
        Cooperation c1 = new Cooperation();
        Cooperation c2 = new Cooperation();
        CooperationDTO dto1 = new CooperationDTO();
        dto1.setId(1L);
        CooperationDTO dto2 = new CooperationDTO();
        dto2.setId(2L);

        when(cooperationRepositoryMock.findAll()).thenReturn(Arrays.asList(c1, c2));
        when(mapperMock.map(c1, CooperationDTO.class)).thenReturn(dto1);
        when(mapperMock.map(c2, CooperationDTO.class)).thenReturn(dto2);

        mockMvc.perform(get("/v1/cooperations").accept(MediaType.APPLICATION_XML))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_XML))
            .andExpect(xpath("/cooperations/cooperation[1]/id").string(is(dto1.getId().toString())))
            .andExpect(xpath("/cooperations/cooperation[2]/id").string(is(dto2.getId().toString())));

        verify(cooperationRepositoryMock, times(1)).findAll();
        verifyNoMoreInteractions(cooperationRepositoryMock);

    }

    @Test
    public void getAllAsJson_shouldReturnEmptyList() throws Exception {

        when(cooperationRepositoryMock.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/v1/cooperations").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void getAllAsXml_shouldReturnEmptyList() throws Exception {

        when(cooperationRepositoryMock.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/v1/cooperations").accept(MediaType.APPLICATION_XML))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_XML))
            .andExpect(xpath("/cooperations").nodeCount(1)).
            andExpect(xpath("/cooperations/cooperation").nodeCount(0));
    }

    @Test
    public void get_shouldReturnOneAsJson() throws Exception {
        Cooperation c1 = new Cooperation();
        c1.setId(1L);
        CooperationDTO dto1 = new CooperationDTO();
        dto1.setId(1L);

        when(cooperationRepositoryMock.findOne(c1.getId())).thenReturn(c1);
        when(mapperMock.map(c1, CooperationDTO.class)).thenReturn(dto1);

        mockMvc.perform(get("/v1/cooperations/{id}", c1.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(dto1.getId().intValue()));
    }

    @Test
    public void get_shouldReturnOneAsXml() throws Exception {
        Cooperation c1 = new Cooperation();
        c1.setId(1L);
        CooperationDTO dto1 = new CooperationDTO();
        dto1.setId(1L);

        when(cooperationRepositoryMock.findOne(c1.getId())).thenReturn(c1);
        when(mapperMock.map(c1, CooperationDTO.class)).thenReturn(dto1);

        mockMvc.perform(get("/v1/cooperations/{id}", c1.getId()).accept(MediaType.APPLICATION_XML))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_XML))
            .andExpect(xpath("/cooperation/id").string(is(dto1.getId().toString())));
    }

    @Test
    public void get_shouldReturnNotFound() throws Exception {

        when(cooperationRepositoryMock.findOne(anyLong())).thenReturn(null);

        mockMvc.perform(get("/v1/cooperations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void buildCriteria_shouldBuild() throws Exception {

        Predicate predicate = uut.buildCriteria(1L, null, null, null);
        assertThat(predicate.toString(),is("cooperation.serviceConsumer.id = 1"));
        predicate = uut.buildCriteria(1L, 2L, null, null);
        assertThat(predicate.toString(),is("cooperation.serviceConsumer.id = 1 && cooperation.logicalAddress.id = 2"));
        predicate = uut.buildCriteria(1L, 2L, 3L, null);
        assertThat(predicate.toString(),is("cooperation.serviceConsumer.id = 1 && cooperation.logicalAddress.id = 2 && cooperation.serviceContract.id = 3"));
        predicate = uut.buildCriteria(1L, 2L, 3L, 4L);
        assertThat(predicate.toString(), is("cooperation.serviceConsumer.id = 1 && cooperation.logicalAddress.id = 2 && cooperation.serviceContract.id = 3 && cooperation.connectionPoint.id = 4"));

    }
    @Test
    public void buildCriteria_shouldReturnNull() throws Exception {

        Predicate predicate = uut.buildCriteria(null, null, null, null);
        assertNull(predicate);
    }
}
