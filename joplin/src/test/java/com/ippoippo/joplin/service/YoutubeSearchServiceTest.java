package com.ippoippo.joplin.service;

import static org.junit.Assert.fail;

import java.util.List;

import javax.inject.Inject;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.ippoippo.joplin.dto.YoutubeItem;
import com.ippoippo.joplin.dto.YoutubeSearchForm;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= { "file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml" })
@Transactional
@TransactionConfiguration(defaultRollback=true)
public class YoutubeSearchServiceTest {

	@Inject
	private YoutubeSearchService youtubeSearchService;

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testSearch() {
		try {
			YoutubeSearchForm youtubeForm = new YoutubeSearchForm();
			youtubeForm.setSearchText("hoge");
			youtubeForm.setStartIndex(1);
			youtubeForm.setListSize(10);
			List<YoutubeItem> list = youtubeSearchService.search("test", youtubeForm);
			System.out.println("list="+list);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
