package com.demo.spitter.web;

import org.junit.Test;
import org.powermock.api.mockito.PowerMockito;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceView;

import com.demo.spitter.entity.Spitter;
import com.demo.spitter.service.SpitterService;

public class SpitterControllerTest {

	@Test
	public void testShowRegistrationForm() throws Exception {
		// 1. Generate controller
		SpitterController controller = new SpitterController();

		// 2. Create Mock
		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

		// 3.Perform and testing result
		mockMvc.perform(MockMvcRequestBuilders.get("/spitter/register"))
				.andExpect(MockMvcResultMatchers.view().name("registerForm"));
	}

	@Test
	public void testProcessRegistration() throws Exception {
		// 1. Create expected object
		Spitter expected = new Spitter("a1", "pass", "fn", "ln");

		// 1. Generate Service
		SpitterService mockService = PowerMockito.mock(SpitterService.class);
		PowerMockito.when(mockService.save(expected)).thenReturn(expected);

		// 2. Generate controller
		SpitterController controller = new SpitterController();
		controller.setSpitterService(mockService);

		// 3. Create Mock
		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

		// 4. Testing the right case
		mockMvc.perform(MockMvcRequestBuilders.post("/spitter/register").param("userName", "aaaa1111")
				.param("firstName", "firstName").param("lastName", "lastName").param("password", "password"))
				.andExpect(MockMvcResultMatchers.redirectedUrl("/spitter/aaaa1111"));
		
		// 5.Testing the wrong case
		// User name is too short
		mockMvc.perform(MockMvcRequestBuilders.post("/spitter/register").param("userName", "a1")
				.param("firstName", "firstName").param("lastName", "lastName").param("password", "password"))
				.andExpect(MockMvcResultMatchers.view().name("registerForm"));
		// Password is empty
		mockMvc.perform(MockMvcRequestBuilders.post("/spitter/register").param("userName", "aaa1111")
				.param("firstName", "firstName").param("lastName", "lastName").param("password", ""))
				.andExpect(MockMvcResultMatchers.view().name("registerForm"));
	}

	@Test
	public void testShowProfile() throws Exception {
		// 1. Create expected obj
		Spitter expected = new Spitter("a1", "ps", "fn", "ln");

		// 2. Mock Service
		SpitterService mockService = PowerMockito.mock(SpitterService.class);
		PowerMockito.when(mockService.findByUserName("a1")).thenReturn(expected);

		// 3. Mock controller and setup
		SpitterController controller = new SpitterController();
		controller.setSpitterService(mockService);
		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller)
				.setSingleView(new InternalResourceView("/WEB-INF/views/profile.jsp")).build();

		// 4. Perform and testing
		mockMvc.perform(MockMvcRequestBuilders.get("/spitter/a1"))
				.andExpect(MockMvcResultMatchers.view().name("profile"))
				.andExpect(MockMvcResultMatchers.model().attributeExists("spitter"))
				.andExpect(MockMvcResultMatchers.model().attribute("spitter", expected));
	}

}
