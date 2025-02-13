package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.entity.Event;
import com.example.demo.exception.EventNotFoundException;
import com.example.demo.repository.EventRepo;
import com.example.demo.service.EventServiceImpl;

@SpringBootTest
class EventServiceApplicationTests {

	@Mock
	private EventRepo eventRepo;

	@InjectMocks
	private EventServiceImpl eventService;

	private Event event1;
	private Event event2;

	@BeforeEach
	void setUp() throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

		Date date1 = (Date) dateFormat.parse("15-11-2023");
		Date date2 = (Date) dateFormat.parse("05-12-2023");
		event1 = new Event(1, "Event One", date1, "Location One", "Venue One",
				"Description One");

		event2 = new Event(2, "Event Two", date2, "Location Two", "Venue Two",
				"Description Two");
	}

	@Test
	void testSave() {
		eventService.save(event1);
		verify(eventRepo, times(1)).save(event1);
	}

	@Test
	void testGetAll() {
		when(eventRepo.findAll()).thenReturn(Arrays.asList(event1, event2));

		List<Event> events = eventService.getAll();

		assertEquals(2, events.size());
		assertEquals("Event One", events.get(0).getName());
		verify(eventRepo, times(1)).findAll();
	}

	@Test
	void testGetById() throws EventNotFoundException {
		when(eventRepo.findById(event1.getId())).thenReturn(Optional.of(event1));

		Event result = eventService.getById(event1.getId());

		assertEquals("Event One", result.getName());
		verify(eventRepo, times(1)).findById(event1.getId());
	}

	@Test
	void testGetByName() throws EventNotFoundException {
		when(eventRepo.findByNameContaining("Event"))
				.thenReturn(Arrays.asList(event1, event2));

		List<Event> events = eventService.getByName("Event");

		assertEquals(2, events.size());
		verify(eventRepo, times(1)).findByNameContaining("Event");
	}

	@Test
	void testGetByLocation() throws EventNotFoundException {
		when(eventRepo.findByLocation("Location One")).thenReturn(Arrays.asList(event1));

		List<Event> events = eventService.getByLocation("Location One");

		assertEquals(1, events.size());
		assertEquals("Event One", events.get(0).getName());
		verify(eventRepo, times(1)).findByLocation("Location One");
	}

	@Test
	void testUpdate() throws EventNotFoundException {
		eventService.update(event1);
		verify(eventRepo, times(1)).save(event1);
	}

	@Test
	void testDelete() throws EventNotFoundException {
		eventService.delete(event1.getId());
		verify(eventRepo, times(1)).deleteById(event1.getId());
	}
}
