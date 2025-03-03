//package com.example.demo;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.Arrays;
//import java.util.Date;
//import java.util.List;
//import java.util.Optional;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import com.example.demo.entity.Booking;
//import com.example.demo.repository.BookingRepository;
//import com.example.demo.service.BookingServiceImpl;
//
//@SpringBootTest
//class BookingServiceApplicationTests {
//
//	@Mock
//	private BookingRepository repository;
//
//	@InjectMocks
//	private BookingServiceImpl service;
//
//	private Booking booking1;
//	private Booking booking2;
//
//	@BeforeEach
//	void setUp() throws ParseException {
//		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
//
//		Date date1 = dateFormat.parse("15-11-2023");
//		Date date2 = dateFormat.parse("16-11-2023");
//
//		booking1 = new Booking(1, 100, "Alice", 200, "Spring Boot Workshop", date1, "Online", "Zoom");
//
//		booking2 = new Booking(2, 101, "Bob", 201, "Java Conference", date2, "San Francisco, CA", "Moscone Center");
//	}
//
//	@Test
//	void testSave() {
//		service.save(booking1);
//		verify(repository, times(1)).save(booking1);
//	}
//
//	@Test
//	void testGetAll() {
//		when(repository.findAll()).thenReturn(Arrays.asList(booking1, booking2));
//
//		List<Booking> bookings = service.getAll();
//
//		assertEquals(2, bookings.size());
//		assertEquals("Alice", bookings.get(0).getUserName());
//		verify(repository, times(1)).findAll();
//	}
//
//	@Test
//	void testGetById() {
//		when(repository.findById(1)).thenReturn(Optional.of(booking1));
//
//		Booking booking = service.getById(1);
//
//		assertEquals(1, booking.getId());
//		assertEquals("Alice", booking.getUserName());
//		verify(repository, times(1)).findById(1);
//	}
//
//	@Test
//	void testDelete() {
//		service.delete(1);
//		verify(repository, times(1)).deleteById(1);
//	}
//
//	@Test
//	void testGetByUserId() {
//		when(repository.getByUserId(100)).thenReturn(Arrays.asList(booking1));
//
//		List<Booking> bookings = service.getByUserId(100);
//
//		assertEquals(1, bookings.size());
//		assertEquals(100, bookings.get(0).getUserId());
//		verify(repository, times(1)).getByUserId(100);
//	}
//
//	@Test
//	void testDeleteByEventId() {
//		service.deleteByEventId(200);
//		verify(repository, times(1)).deleteByEventId(200);
//	}
//
//	@Test
//	void testDeleteByUserId() {
//		service.deleteByUserId(100);
//		verify(repository, times(1)).deleteByUserId(100);
//	}
//
//	@Test
//	void testDeleteByUserIdAndEventId() {
//		service.deleteByUserIdAndEventId(100, 200);
//		verify(repository, times(1)).deleteByUserIdAndEventId(100, 200);
//	}
//}
