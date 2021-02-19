package com.sample.kafka;


import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import com.sample.kafka.model.Message;
import com.sample.kafka.service.MessageProducerService;
import com.sample.kafka.utils.Response;


@SpringBootTest(webEnvironment=WebEnvironment.MOCK, classes={ ApplicationArguments.class })
public class MessageControllerTest {

	private MockMvc mockMvc;
	
	@Autowired
	private WebApplicationContext webApplicationContext;

	@MockBean 
	private MessageProducerService messageProducerServiceMock;
	
	
	public void setUp() {
		 this.mockMvc = webAppContextSetup(webApplicationContext).build();
	}

	@Test
	public void should_not_pulish_message_When_InValidRequest() throws Exception {
		setUp();
		Response response = new Response();
		response.setStatus("Success");
		response.setMessage("Message sent successfully");
		when(messageProducerServiceMock.produceMessage(null)).thenReturn(response);
		
		mockMvc.perform(post("/message/publish")
			   .contentType(MediaType.APPLICATION_JSON)
			   .content("{ \"message\": \"kafka message\", \"id\": 1 }")						
			   .accept(MediaType.APPLICATION_JSON))
			   .andExpect(status().isNotFound());	   
			   		
	}
	
	@Test
	public void should_pulish_message_When_ValidRequest() throws Exception {
		setUp();
		Response response = new Response();
		response.setStatus("Success");
		response.setMessage("Message sent successfully");
		Message message = new Message();
		message.setId(1);
		message.setMessage("kafka message");
		
		when(messageProducerServiceMock.produceMessage(message)).thenReturn(response);
		
		mockMvc.perform(post("/message/publish")
			   .content("{ \"id\": 1,\\\"message\\\": \\\"kafka message\\\" }")						
			   .accept(MediaType.APPLICATION_JSON))
			   .andExpect(status().isNotFound());	   
			   		
	}
}
