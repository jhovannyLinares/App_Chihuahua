package mx.morena.presentacion.controlador;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mx.morena.security.controller.MasterController;

@RestController
@RequestMapping(value = "reportes/")
@CrossOrigin
public class ReportesController extends MasterController {

//	@GetMapping("/download")
//	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
//	public ResponseEntity export( HttpServletRequest request )
//	{}
	
	
	@Autowired
    CsvService csvService;

	@GetMapping("/download-residents-csv")
	public ResponseEntity<Resource> getCsv(

			@RequestHeader(name = "Content-disposition") final String fileName,
			@RequestHeader(name = "Content-Type") final String mediaType) {

		List<Resident> residents = new ArrayList<Resident>();

		residents.add(new Resident("juan", "Perez"));
		residents.add(new Resident("pepe", "pecas"));
		

		final InputStreamResource resource = new InputStreamResource(csvService.load(residents));
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, fileName)
				.contentType(MediaType.parseMediaType(mediaType)).body(resource);
	}

}
