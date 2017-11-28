package mx.infotec.dads.sekc.config.dbmigrations.domain;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "name",
    "hexadecimal"
})

public class Color implements Serializable {

	private static final long serialVersionUID = 1L;
	@JsonProperty("name")
	private String name;
	@JsonProperty("hexadecimal")
	private String hexadecimal;
	
	@JsonProperty("name")
	public String getName() {
		return name;
	}
	
	@JsonProperty("name")
	public void setName(String name) {
		this.name = name;
	}
	
	@JsonProperty("hexadecimal")
	public String getHexadecimal() {
		return hexadecimal;
	}
	
	@JsonProperty("hexadecimal")
	public void setHexadecimal(String hexadecimal) {
		this.hexadecimal = hexadecimal;
	}

}
