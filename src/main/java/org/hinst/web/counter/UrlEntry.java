package org.hinst.web.counter;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
public class UrlEntry {
	@Id
	@Getter
	@Setter
	private String url;

	@Getter
	@Setter
	private Long count;
}