package org.hinst.web.counter;

import java.time.Instant;

public record Riddle(Instant createdAt, long id, long product) {
}
