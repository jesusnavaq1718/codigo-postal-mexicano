package com.codigospostalesmexicoapi.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

//import org.hibernate.validator.constraints.NotEmpty;





@Entity
@Table(name="codigo_Postal")
public class CodigoPostal implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	@Column(name="zip_code")
    private String zip_code;

	@Column(name="city")
    private String city;
	
    @Column(name="federal_entity")
    private String federal_entity;
  
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "settlements_id", referencedColumnName = "id")
    private Settlements settlements;
    
    @Column(name="municipality")
    private String municipality;
    
    @Column(name="official_key")
    private String official_key;
    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getZip_code() {
		return zip_code;
	}

	public void setZip_code(String zip_code) {
		this.zip_code = zip_code;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getFederal_entity() {
		return federal_entity;
	}

	public void setFederal_entity(String federal_entity) {
		this.federal_entity = federal_entity;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getMunicipality() {
		return municipality;
	}

	public void setMunicipality(String municipality) {
		this.municipality = municipality;
	}

	public String getOfficial_key() {
		return official_key;
	}

	public void setOfficial_key(String official_key) {
		this.official_key = official_key;
	}

	public Settlements getSettlements() {
		return settlements;
	}

	public void setSettlements(Settlements settlements) {
		this.settlements = settlements;
	}

 
	
}

