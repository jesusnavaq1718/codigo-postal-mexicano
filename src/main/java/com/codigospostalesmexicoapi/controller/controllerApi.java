package com.codigospostalesmexicoapi.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.codigospostalesmexicoapi.entity.CodigoPostal;
import com.codigospostalesmexicoapi.repository.CodigoPostalRepository;
import com.codigospostalesmexicoapi.service.CodigoPostalService;


@CrossOrigin()
@RestController
@RequestMapping("/zip-codes")
public class controllerApi {

	@Autowired
	private CodigoPostalService codigoPostalService;

	@Transactional
	@GetMapping(value = "/{cp}")
	public ResponseEntity<?> obtnerDatosPersona(@PathVariable String cp) {

		// Validacion del cp si no existe
		if (codigoPostalService.ObtenerDatosCP(cp) == null) {
			return new ResponseEntity<List<Map<String, Object>>>(codigoPostalService.ObtenerDatosCP(cp),
					HttpStatus.NOT_FOUND);

		}

		return new ResponseEntity<List<Map<String, Object>>>(codigoPostalService.ObtenerDatosCP(cp), HttpStatus.OK);

	}

}
