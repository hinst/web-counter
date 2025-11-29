package org.hinst.web.counter;

import java.time.Instant;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class RiddleEntry {
	@Id
	@Getter
	@Setter
	private long id;

	@Getter
	@NonNull
	private Instant createdAt;

	@Getter
	private long product;
}
