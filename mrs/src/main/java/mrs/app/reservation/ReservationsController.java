package mrs.app.reservation;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;

import mrs.app.report.ExcelDownloadView;
import mrs.domain.model.*;
import mrs.domain.service.reservation.*;
import mrs.domain.service.room.RoomService;
import mrs.domain.service.user.ReservationUserDetails;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

@Controller
@RequestMapping("reservations/{date}/{roomId}")
public class ReservationsController {
	@Autowired
	RoomService roomService;
	@Autowired
	ReservationService reservationService;
	@Autowired
	protected ResourceLoader resourceLoader;

	@ModelAttribute
	ReservationForm setUpForm() {
		ReservationForm form = new ReservationForm();
		// デフォルト値
		form.setStartTime(LocalTime.of(9, 0));
		form.setEndTime(LocalTime.of(10, 0));
		return form;
	}

	@RequestMapping(method = RequestMethod.GET)
	String reserveForm(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @PathVariable("date") LocalDate date,
			@PathVariable("roomId") Integer roomId, Model model) {
		ReservableRoomId reservableRoomId = new ReservableRoomId(roomId, date);
		List<Reservation> reservations = reservationService.findReservations(reservableRoomId);
		List<LocalTime> timeList = Stream.iterate(LocalTime.of(0, 0), t -> t.plusMinutes(30)).limit(24 * 2)
				.collect(Collectors.toList());
		model.addAttribute("room", roomService.findMeetingRoom(roomId));
		model.addAttribute("reservations", reservations);
		model.addAttribute("timeList", timeList);
		return "reservation/reserveForm";
	}

	private User dummyUser() {
		User user = new User();
		user.setUserId("taro-yamada");
		user.setFirstName("太郎");
		user.setLastName("山田");
		user.setRoleName(RoleName.USER);
		return user;
	}

	@RequestMapping(method = RequestMethod.POST)
	String reserve(@Validated ReservationForm form, BindingResult bindingResult,
			@AuthenticationPrincipal ReservationUserDetails userDetails,
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @PathVariable("date") LocalDate date,
			@PathVariable("roomId") Integer roomId, Model model) {
		if (bindingResult.hasErrors()) {
			return reserveForm(date, roomId, model);
		}
		ReservableRoom reservableRoom = new ReservableRoom(new ReservableRoomId(roomId, date));
		Reservation reservation = new Reservation();
		reservation.setStartTime(form.getStartTime());
		reservation.setEndTime(form.getEndTime());
		reservation.setReservableRoom(reservableRoom);
		reservation.setUser(userDetails.getUser());
		try {
			reservationService.reserve(reservation);
		} catch (UnavailableReservationException | AlreadyReservedException e) {
			model.addAttribute("error", e.getMessage());
			return reserveForm(date, roomId, model);
		}
		return "redirect:/reservations/{date}/{roomId}";
	}

	@RequestMapping(method = RequestMethod.POST, params = "cancel")
	String cancel(@RequestParam("reservationId") Integer reservationId, @PathVariable("roomId") Integer roomId,
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @PathVariable("date") LocalDate date, Model model) {
		try {
			Reservation reservation = reservationService.findOne(reservationId);
			reservationService.cancel(reservation);
		} catch (AccessDeniedException e) {
			model.addAttribute("error", e.getMessage());
			return reserveForm(date, roomId, model);
		}
		return "redirect:/reservations/{date}/{roomId}";
	}

	@RequestMapping(method = RequestMethod.GET, params = "excel")
	public ModelAndView downloadExcel(HttpServletRequest request, @RequestParam("startEndTime") String startEnd, Model model) throws Exception {
		
		
		model.addAttribute("serverTime", new Date());
		model.addAttribute("startEndTime", startEnd);
		String sessionId = WebUtils.getSessionId(request);
	
		Workbook workbook = null;
		File tmpFile = null;
		// TODO Move utility class.
		// TODO Session directory cleaning.
		try {
			// create workspace (tmp\session)
			String root = System.getProperty("java.io.tmpdir") + File.separator + sessionId;
			File rootpath = new File(root);
			rootpath.mkdir();
			
			// create temporary file
			Path tmp = Files.createTempFile(Paths.get(root), UUID.randomUUID().toString(), "");

			model.addAttribute("filepath", tmp);
			model.addAttribute("filename", "himeyon");

			tmpFile = tmp.toFile();
			Resource resource = resourceLoader.getResource("classpath:xlsx/template001.xlsx");
			workbook = WorkbookFactory.create(resource.getInputStream());
			Sheet sheet = workbook.getSheetAt(0);
			Row row = sheet.createRow(0);
			Cell cell = row.createCell(0);
			cell.setCellValue("TESTTEST");

			// save excel file to temporary file
			FileOutputStream fos = new FileOutputStream(tmpFile);
			workbook.write(fos);

		} catch (EncryptedDocumentException | InvalidFormatException e) {
			e.printStackTrace();
		}
		
		ExcelDownloadView view = new ExcelDownloadView();
		return new ModelAndView(view);
	}
}