package org.istic.taa.todoapp.web.rest;

import org.istic.taa.todoapp.Application;
import org.istic.taa.todoapp.domain.TODOItem;
import org.istic.taa.todoapp.repository.TODOItemRepository;
import org.istic.taa.todoapp.web.rest.dto.TODOItemDTO;
import org.istic.taa.todoapp.web.rest.mapper.TODOItemMapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the TODOItemResource REST controller.
 *
 * @see TODOItemResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
@WithMockUser(value = "admin")
@WithUserDetails(value = "admin")
public class TODOItemResourceTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");

    private static final String DEFAULT_CONTENT = "SAMPLE_TEXT";
    private static final String UPDATED_CONTENT = "UPDATED_TEXT";

    private static final DateTime DEFAULT_END_DATE = new DateTime(0L, DateTimeZone.UTC);
    private static final DateTime UPDATED_END_DATE = new DateTime(DateTimeZone.UTC).withMillisOfSecond(0);
    private static final String DEFAULT_END_DATE_STR = dateTimeFormatter.print(DEFAULT_END_DATE);

    private static final Boolean DEFAULT_DONE = false;
    private static final Boolean UPDATED_DONE = true;

    @Inject
    private TODOItemRepository tODOItemRepository;

    @Inject
    private TODOItemMapper tODOItemMapper;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restTODOItemMockMvc;

    private TODOItem tODOItem;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TODOItemResource tODOItemResource = new TODOItemResource();
        ReflectionTestUtils.setField(tODOItemResource, "tODOItemRepository", tODOItemRepository);
        ReflectionTestUtils.setField(tODOItemResource, "tODOItemMapper", tODOItemMapper);
        this.restTODOItemMockMvc = MockMvcBuilders.standaloneSetup(tODOItemResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        tODOItem = new TODOItem();
        tODOItem.setContent(DEFAULT_CONTENT);
        tODOItem.setEndDate(DEFAULT_END_DATE);
        tODOItem.setDone(DEFAULT_DONE);
    }

    @Test
    @Transactional
    public void createTODOItem() throws Exception {
        int databaseSizeBeforeCreate = tODOItemRepository.findAll().size();

        // Create the TODOItem
        TODOItemDTO tODOItemDTO = tODOItemMapper.tODOItemToTODOItemDTO(tODOItem);

        restTODOItemMockMvc.perform(post("/api/tODOItems")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(tODOItemDTO)))
                .andExpect(status().isCreated());

        // Validate the TODOItem in the database
        List<TODOItem> tODOItems = tODOItemRepository.findAll();
        assertThat(tODOItems).hasSize(databaseSizeBeforeCreate + 1);
        TODOItem testTODOItem = tODOItems.get(tODOItems.size() - 1);
        assertThat(testTODOItem.getContent()).isEqualTo(DEFAULT_CONTENT);
        assertThat(testTODOItem.getEndDate().toDateTime(DateTimeZone.UTC)).isEqualTo(DEFAULT_END_DATE);
        assertThat(testTODOItem.getDone()).isEqualTo(DEFAULT_DONE);
    }

    @Test
    @Transactional
    public void getAllTODOItems() throws Exception {
        // Initialize the database
        tODOItemRepository.saveAndFlush(tODOItem);

        // Get all the tODOItems
        restTODOItemMockMvc.perform(get("/api/tODOItems"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(tODOItem.getId().intValue())))
                .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT.toString())))
                .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE_STR)))
                .andExpect(jsonPath("$.[*].done").value(hasItem(DEFAULT_DONE.booleanValue())));
    }

    @Test
    @Transactional
    public void getTODOItem() throws Exception {
        // Initialize the database
        tODOItemRepository.saveAndFlush(tODOItem);

        // Get the tODOItem
        restTODOItemMockMvc.perform(get("/api/tODOItems/{id}", tODOItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(tODOItem.getId().intValue()))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE_STR))
            .andExpect(jsonPath("$.done").value(DEFAULT_DONE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTODOItem() throws Exception {
        // Get the tODOItem
        restTODOItemMockMvc.perform(get("/api/tODOItems/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTODOItem() throws Exception {
        // Initialize the database
        tODOItemRepository.saveAndFlush(tODOItem);

		int databaseSizeBeforeUpdate = tODOItemRepository.findAll().size();

        // Update the tODOItem
        tODOItem.setContent(UPDATED_CONTENT);
        tODOItem.setEndDate(UPDATED_END_DATE);
        tODOItem.setDone(UPDATED_DONE);

        TODOItemDTO tODOItemDTO = tODOItemMapper.tODOItemToTODOItemDTO(tODOItem);

        restTODOItemMockMvc.perform(put("/api/tODOItems")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(tODOItemDTO)))
                .andExpect(status().isOk());

        // Validate the TODOItem in the database
        List<TODOItem> tODOItems = tODOItemRepository.findAll();
        assertThat(tODOItems).hasSize(databaseSizeBeforeUpdate);
        TODOItem testTODOItem = tODOItems.get(tODOItems.size() - 1);
        assertThat(testTODOItem.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testTODOItem.getEndDate().toDateTime(DateTimeZone.UTC)).isEqualTo(UPDATED_END_DATE);
        assertThat(testTODOItem.getDone()).isEqualTo(UPDATED_DONE);
    }

    @Test
    @Transactional
    public void deleteTODOItem() throws Exception {
        // Initialize the database
        tODOItemRepository.saveAndFlush(tODOItem);

		int databaseSizeBeforeDelete = tODOItemRepository.findAll().size();

        // Get the tODOItem
        restTODOItemMockMvc.perform(delete("/api/tODOItems/{id}", tODOItem.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<TODOItem> tODOItems = tODOItemRepository.findAll();
        assertThat(tODOItems).hasSize(databaseSizeBeforeDelete - 1);
    }
}
