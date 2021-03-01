package com.codigospostalesmexicoapi.serviceImpl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.codigospostalesmexicoapi.entity.CodigoPostal;
import com.codigospostalesmexicoapi.entity.Settlements;
import com.codigospostalesmexicoapi.repository.CodigoPostalRepository;
import com.codigospostalesmexicoapi.repository.SettlementsRepository;
import com.codigospostalesmexicoapi.service.CodigoPostalService;

@Service
public class CodigoPostalServiceImpl implements CodigoPostalService {

	@Autowired
	private CodigoPostalRepository codigoPostalRepository;

	@Autowired
	private SettlementsRepository settlementsRepository;

	@Override
	@Transactional()
	public List<Map<String, Object>> ObtenerDatosCP(String cp) {

		// TODO Auto-generated method stub
		// Variables
		List<Map<String, Object>> responseList = new ArrayList<>();
		// List<Map<String, Object>> asentamientos = new ArrayList<>();

		MultiValueMap<String, String> datos = new LinkedMultiValueMap<String, String>();

		HttpHeaders headersInicio = new HttpHeaders();

		List<CodigoPostal> codigoPostalLista = new ArrayList<CodigoPostal>();

		// Informacion de header del sistema SERVICIO POSTAL MEXICANO en linea
		headersInicio.add("Accept",
				"text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
		headersInicio.add("Accept-Encoding", "gzip, deflate, br");
		headersInicio.add("Accept-Language", "es-AR,es-US;q=0.9,es-419;q=0.8,es;q=0.7");
		headersInicio.add("Cache-Control", "max-age=0");
		headersInicio.add("Connection", "keep-alive");
		headersInicio.add("Host", "www.correosdemexico.gob.mx");
		headersInicio.add("sec-ch-ua", "Chromium\";v=\"88\", \"Google Chrome\";v=\"88\", \";Not A Brand\";v=\"99");
		headersInicio.add("sec-ch-ua-mobile", "?0");
		headersInicio.add("Sec-Fetch-Dest", "document");
		headersInicio.add("Sec-Fetch-Mode", "navigate");
		headersInicio.add("Sec-Fetch-Site", "none");
		headersInicio.add("Sec-Fetch-User", "?1");
		headersInicio.add("Upgrade-Insecure-Requests", "1");
		headersInicio.add("User-Agent",
				"Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/88.0.4324.182 Safari/537.36");

		// Datos solicitados en el request de los headers del sistema SERVICIO POSTAL
		// MEXICANO en linea donde se integra el cp a nuscar
		datos.add("__EVENTTARGET", "");
		datos.add("__EVENTARGUMENT", "");
		datos.add("__LASTFOCUS", "");
		datos.add("__VIEWSTATE",
				"/wEPDwUJNzI4NzMyMzU1D2QWAgIBD2QWAgIBD2QWDAIDDxAPFgYeDURhdGFUZXh0RmllbGQFA0Vkbx4ORGF0YVZhbHVlRmllbGQFBUlkRWRvHgtfIURhdGFCb3VuZGdkEBUhIy0tLS0tLS0tLS0gVCAgbyAgZCAgbyAgcyAtLS0tLS0tLS0tDkFndWFzY2FsaWVudGVzD0JhamEgQ2FsaWZvcm5pYRNCYWphIENhbGlmb3JuaWEgU3VyCENhbXBlY2hlFENvYWh1aWxhIGRlIFphcmFnb3phBkNvbGltYQdDaGlhcGFzCUNoaWh1YWh1YRFDaXVkYWQgZGUgTcOpeGljbwdEdXJhbmdvCkd1YW5hanVhdG8IR3VlcnJlcm8HSGlkYWxnbwdKYWxpc2NvB03DqXhpY28UTWljaG9hY8OhbiBkZSBPY2FtcG8HTW9yZWxvcwdOYXlhcml0C051ZXZvIExlw7NuBk9heGFjYQZQdWVibGEKUXVlcsOpdGFybwxRdWludGFuYSBSb28QU2FuIEx1aXMgUG90b3PDrQdTaW5hbG9hBlNvbm9yYQdUYWJhc2NvClRhbWF1bGlwYXMIVGxheGNhbGEfVmVyYWNydXogZGUgSWduYWNpbyBkZSBsYSBMbGF2ZQhZdWNhdMOhbglaYWNhdGVjYXMVIQIwMAIwMQIwMgIwMwIwNAIwNQIwNgIwNwIwOAIwOQIxMAIxMQIxMgIxMwIxNAIxNQIxNgIxNwIxOAIxOQIyMAIyMQIyMgIyMwIyNAIyNQIyNgIyNwIyOAIyOQIzMAIzMQIzMhQrAyFnZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnZ2cWAWZkAgcPEA8WBh8ABQlNdW5pY2lwaW8fAQULSWRNdW5pY2lwaW8fAmdkEBUBIy0tLS0tLS0tLS0gVCAgbyAgZCAgbyAgcyAtLS0tLS0tLS0tFQEDMDAwFCsDAWdkZAIXDzwrAAsAZAIZDzwrAAsAZAIhDw8WAh4EVGV4dAUVVG90YWwgZGUgUmVnaXN0cm9zOiAwZGQCJQ8PFgIfAwU6w5psdGltYSBBY3R1YWxpemFjacOzbiBkZSBJbmZvcm1hY2nDs246IEZlYnJlcm8gMjcgZGUgMjAyMWRkGAEFHl9fQ29udHJvbHNSZXF1aXJlUG9zdEJhY2tLZXlfXxYCBQdidG5GaW5kBQdidG5TYXZlkpXqH0LxqxUYTQkO5UEUoot8VV8=");
		datos.add("__VIEWSTATEGENERATOR", "F65E4BF1");
		datos.add("__EVENTVALIDATION",
				"/wEWKALTzO/5CwK4wIWDCQKor+/uBQKor+PuBQKor+fuBQKor9vuBQKor9/uBQKor9PuBQKor9fuBQKor8vuBQKor4/tBQKor4PtBQK3r+/uBQK3r+PuBQK3r+fuBQK3r9vuBQK3r9/uBQK3r9PuBQK3r9fuBQK3r8vuBQK3r4/tBQK3r4PtBQK2r+/uBQK2r+PuBQK2r+fuBQK2r9vuBQK2r9/uBQK2r9PuBQK2r9fuBQK2r8vuBQK2r4/tBQK2r4PtBQK1r+/uBQK1r+PuBQK1r+fuBQKezciABgKVotGbCgKY+871AQL5zb65CwKct7iSDNwfUOJHAwD24sY+VpiWxS6PfNdQ");
		datos.add("DdlEstado", "00");
		datos.add("DdlMuni", "000");
		datos.add("txtAsentami", "");
		datos.add("txtcp", cp);
		datos.add("btnFind.x", "50");
		datos.add("btnFind.y", "11");
		datos.add("__EVENTTARGET", "");

		// Integracion del hedaer y los datos solicitados en la peticion para la
		// busqueda del cp en sistema SERVICIO POSTAL MEXICANO en linea donde se integra
		// el cp a nuscar
		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(datos,
				headersInicio);

		// Se obtiene la respuesta de la peticion solicitada
		ResponseEntity<String> response = new RestTemplate().postForEntity(
				"https://www.correosdemexico.gob.mx/SSLServicios/ConsultaCP/Descarga.aspx", request, String.class);

		// Se hace uso de Jsoup para poder obtener los datos solicitados
		Document doc = Jsoup.parse(response.toString());
		Element stats2 = doc.body().getElementById("dgCP");

		// Validacion del cp si no existe
		if (stats2 == null) {
			/*
			 * Por si se requiere que se agregue un mensaje de error descriptivo Map<String,
			 * Object> responseError = new HashMap<>(); responseError.put("mensaje",
			 * "El codigo postal " + cp + " , no existe"); responseList.add(responseError);
			 */
			return responseList;

		}

		// Esta version del sistema solo guardara en nuestra db, pero no los consultara
		// aun, si existiera la version 2 se implementaria
		codigoPostalLista = null;
		codigoPostalLista = codigoPostalRepository.ObtenerDatosLista(cp);
		// Integracion de los datos en nuestra respuesta y persistir los datos si no
		// existe en nuestra db para tener un respaldo si la pagina oficial no esta
		// funcionando pero aun no se integrara la parte de consultar la db directamente
		// en esta version
		for (Element row : stats2.select("tr.dgNormal")) {

			Map<String, Object> contenido = new HashMap<>();
			Map<String, Object> asentamiento = new HashMap<>();

			Elements tds = row.select("td");

			contenido.put("zip_code", tds.get(0).text());
			contenido.put("city", tds.get(5).text());
			contenido.put("federal_entity", tds.get(4).text());
			asentamiento.put("name", tds.get(1).text());
			asentamiento.put("settlement_type", tds.get(2).text());
			// asentamientos.add(asentamiento);
			contenido.put("settlements", asentamiento);
			contenido.put("municipality", tds.get(3).text());
			contenido.put("official_key", tds.get(6).text());
			responseList.add(contenido);

			// Si no existen los datos,los agregara a la db
			if (codigoPostalLista.isEmpty() || codigoPostalLista == null) {
				Settlements settlements = new Settlements();
				settlements.setName(tds.get(1).text());
				settlements.setSettlement_type(tds.get(2).text());
				settlements = settlementsRepository.save(settlements);

				CodigoPostal codigoPostal = new CodigoPostal();
				codigoPostal.setZip_code(tds.get(0).text());
				codigoPostal.setFederal_entity(tds.get(4).text());
				codigoPostal.setMunicipality(tds.get(3).text());
				codigoPostal.setOfficial_key(tds.get(6).text());
				codigoPostal.setSettlements(settlements);
				codigoPostal = codigoPostalRepository.save(codigoPostal);
			}

		}

		return responseList;
	}

}
