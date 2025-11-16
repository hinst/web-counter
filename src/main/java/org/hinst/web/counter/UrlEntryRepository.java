package org.hinst.web.counter;

import org.springframework.data.repository.CrudRepository;

public interface UrlEntryRepository extends CrudRepository<UrlEntry, String> {
	Object locker = new Object();
}
