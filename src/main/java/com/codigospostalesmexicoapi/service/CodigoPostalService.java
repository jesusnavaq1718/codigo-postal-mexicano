package com.codigospostalesmexicoapi.service;

import java.util.List;
import java.util.Map;

import com.codigospostalesmexicoapi.entity.CodigoPostal;

public interface CodigoPostalService {
	
	public List<Map<String, Object>> ObtenerDatosCP(String cp);

}
