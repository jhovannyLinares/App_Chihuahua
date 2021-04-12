package mx.morena.presentacion.controlador;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mx.morena.security.controller.MasterController;

@RestController
@RequestMapping(value = "reportes")
@CrossOrigin
public class ReportesAsignacionController extends MasterController{

}
