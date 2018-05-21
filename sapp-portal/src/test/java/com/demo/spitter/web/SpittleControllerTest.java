package com.demo.spitter.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hamcrest.CoreMatchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceView;

import com.demo.spitter.config.SpitterWebAppInitializer;
import com.demo.spitter.entity.Spittle;
import com.demo.spitter.service.SpittleService;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = { SpitterWebAppInitializer.class })
public class SpittleControllerTest {

	@Test
	public void testSpittles() throws Exception {
		// 1. Create expected object.
		List<Spittle> expected = createSpittleList(20);

		// 2. Generate Mock service and set method result.
		SpittleService mockService = PowerMockito.mock(SpittleService.class);
		PowerMockito.when(mockService.getRecentSpittles(20)).thenReturn(expected);

		// 3. Generate Mock controller and set mock service
		SpittleController controller = new SpittleController();
		controller.setSpittleService(mockService);

		// 4. Setup controller
		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller)
				.setSingleView(new InternalResourceView("/WEB-INF/views/spittles.jsp")).build();
		// 5. Perform and testing result
		mockMvc.perform(MockMvcRequestBuilders.get("/spittles"))
				.andExpect(MockMvcResultMatchers.view().name("spittles"))
				.andExpect(MockMvcResultMatchers.model().attributeExists("spittleList")).andExpect(MockMvcResultMatchers
						.model().attribute("spittleList", CoreMatchers.hasItems(expected.toArray())));

	}

	@Test
	public void testShowSpittleByPathVariable() throws Exception {
		// 1. Create expect object.
		Spittle expected = new Spittle("hello", new Date());

		// 2. Generate Mock service and set return value
		SpittleService mockService = PowerMockito.mock(SpittleService.class);
		PowerMockito.when(mockService.findById(1l)).thenReturn(expected);

		// 3. Generate Mock controller and set mock service
		SpittleController controller = new SpittleController();
		controller.setSpittleService(mockService);

		// 4. Setup controller
		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller)
				.setSingleView(new InternalResourceView("/WEB-INF/views/spittle.jsp")).build();

		// 5. Perform and testing result
		mockMvc.perform(MockMvcRequestBuilders.get("/spittles/showSpittleByPathVariable/1"))
				.andExpect(MockMvcResultMatchers.view().name("spittle"))
				.andExpect(MockMvcResultMatchers.model().attributeExists("spittle"))
				.andExpect(MockMvcResultMatchers.model().attribute("spittle", expected));

	}

	@Test
	public void testShowSpittleByRequestParam() throws Exception {
		// 1. Create expect object.
		Spittle expected = new Spittle("hello", new Date());

		// 2. Generate Mock service and set return value
		SpittleService mockService = PowerMockito.mock(SpittleService.class);
		PowerMockito.when(mockService.findById(1l)).thenReturn(expected);

		// 3. Generate Mock controller and set mock service
		SpittleController controller = new SpittleController();
		controller.setSpittleService(mockService);

		// 4. Setup controller
		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller)
				.setSingleView(new InternalResourceView("/WEB-INF/views/spittle.jsp")).build();

		// 5. Perform and testing result
		mockMvc.perform(MockMvcRequestBuilders.get("/spittles/showSpittleByRequestParam?spittleId=1"))
				.andExpect(MockMvcResultMatchers.view().name("spittle"))
				.andExpect(MockMvcResultMatchers.model().attributeExists("spittle"))
				.andExpect(MockMvcResultMatchers.model().attribute("spittle", expected));

	}

	/**
	 * Create Spittles for mock
	 * 
	 * @param count
	 * @return
	 */
	private List<Spittle> createSpittleList(int count) {
		List<Spittle> spittles = new ArrayList<Spittle>();
		for (int i = 0; i < count; i++) {
			spittles.add(new Spittle("Spittle " + i, new Date()));
		}
		return spittles;
	}

}
